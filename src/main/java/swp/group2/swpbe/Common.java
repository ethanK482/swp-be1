package swp.group2.swpbe;

public class Common {
   public static Double calculateExpertAmount(double amount){
        Double stripeFee = (0.32 * 23000) + (amount * 2.9 / 100);
        Double platformFee = amount * (5.0 / 100);
        return amount - stripeFee - platformFee;
    }
}
