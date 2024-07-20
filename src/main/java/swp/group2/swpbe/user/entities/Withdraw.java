package swp.group2.swpbe.user.entities;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Withdraw {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "amount")
    private double amount;
    @Column(name = "created_at")
    private Date createdAt;
    @Column(name = "transaction_id")
    private String transactionId;

    public Withdraw(String userId, double amount, String transactionId) {
        this.userId = userId;
        this.amount = amount;
        this.createdAt = new Date();
        this.transactionId = transactionId;
    }

}
