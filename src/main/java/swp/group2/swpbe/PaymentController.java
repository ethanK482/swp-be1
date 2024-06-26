package swp.group2.swpbe;

import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.param.checkout.SessionCreateParams;
import com.stripe.param.checkout.SessionCreateParams.Locale;

import jakarta.servlet.http.HttpServletRequest;
import swp.group2.swpbe.constant.PaymentStatus;
import swp.group2.swpbe.course.CourseOrderRepository;
import swp.group2.swpbe.course.CourseRepository;
import swp.group2.swpbe.course.entites.Course;
import swp.group2.swpbe.course.entites.CourseOrder;
import swp.group2.swpbe.exception.ApiRequestException;
import swp.group2.swpbe.user.WalletRepository;
import swp.group2.swpbe.user.entities.Wallet;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;

@RestController
public class PaymentController {
    @Autowired
    AuthService authService;
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    CourseOrderRepository courseOrderRepository;

    @Value("${allow.origin}")
    private String allowedOrigins;
    @Autowired
    WalletRepository walletRepository;
    @Value("${stripe.api.key}")
    private String stripeApiKey;
    @Value("${stripe.sign.secret}")
    private String signSecret;

    @PostMapping("/payment")
    public String createPaymentUrl(@RequestHeader("Authorization") String token,
            @RequestParam("courseId") String courseId) {
        String userId = authService.loginUser(token);
        CourseOrder orderExist = courseOrderRepository.findByCourseIdAndUserId(Integer.parseInt(courseId),
                Integer.parseInt(userId));
        if (orderExist != null) {
            throw new ApiRequestException("You had bought this course already", HttpStatus.BAD_REQUEST);
        }
        try {
            Stripe.apiKey = stripeApiKey;
            Course course = courseRepository.findById(Integer.parseInt(courseId));
            String currency = "vnd";
            Map<String, String> metadata = new HashMap<>();
            metadata.put("courseId", courseId + "");
            metadata.put("userId", userId);
            metadata.put("expertId", course.getExpertId());
            SessionCreateParams params = SessionCreateParams.builder()
                    .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setLocale(Locale.EN)
                    .setSuccessUrl(allowedOrigins + "/payment/result?state=success")
                    .setCancelUrl(allowedOrigins + "/payment/result")
                    .addLineItem(
                            SessionCreateParams.LineItem.builder()
                                    .setQuantity(1L)
                                    .setPriceData(
                                            SessionCreateParams.LineItem.PriceData.builder()
                                                    .setCurrency(currency)
                                                    .setUnitAmountDecimal(BigDecimal.valueOf(course.getPrice()))
                                                    .setProductData(
                                                            SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                    .setName(course.getName())
                                                                    .build())
                                                    .build())
                                    .build())
                    .putAllMetadata(metadata)
                    .build();
            Session session = Session.create(params);
            return session.getUrl();
        } catch (StripeException e) {
            throw new ApiRequestException("FAILED", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/webhook")
    public ResponseEntity<String> handleWebhookEvent(
            @RequestBody String payload, HttpServletRequest request) {
        Event event = null;
        String header = request.getHeader("Stripe-Signature");
        String COMPLETED_EVENT = "checkout.session.completed";
        try {
            event = Webhook.constructEvent(payload, header, signSecret);
            if (event.getType().equals(COMPLETED_EVENT)) {
                @SuppressWarnings("deprecation")
                Session session = (Session) event.getData().getObject();
                String transactionId = session.getPaymentIntent();
                int userId = Integer.parseInt(session.getMetadata().get("userId"));
                String courseId = session.getMetadata().get("courseId");
                int expertId = Integer.parseInt(session.getMetadata().get("expertId"));
                PaymentStatus paymentStatus = PaymentStatus.valueOf(session.getPaymentStatus());
                Double amount = (double) session.getAmountTotal();
                Double expertAmount = Common.calculateExpertAmount(amount);
                Course course = courseRepository.findById(Integer.parseInt(courseId));
                CourseOrder order = new CourseOrder(course, userId, paymentStatus,
                        transactionId);
                courseOrderRepository.save(order);
                if (PaymentStatus.paid.equals(order.getPaymentStatus())) {
                    Wallet wallet = walletRepository.findByUserId(expertId);
                    if (wallet == null) {
                        Wallet newWallet = new Wallet(expertId, expertAmount);
                        walletRepository.save(newWallet);
                    } else {
                        wallet.setBalance(wallet.getBalance() + expertAmount);
                        walletRepository.save(wallet);
                    }
                }
            }

        } catch (SignatureVerificationException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok("Webhook received successfully");
    }
}
