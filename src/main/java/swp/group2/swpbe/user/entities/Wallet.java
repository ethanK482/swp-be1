package swp.group2.swpbe.user.entities;

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
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "user_id")
    private int userId;
    @Column(name = "total_balance")
    private Double balance;
    @Column(name = "is_locked")
    private int isLocked;
    @Column(name = "account_id")
    private String accountId;

    public Wallet(int userId, String accountId) {
        this.userId = userId;
        this.balance = 0.0;
        this.isLocked = 0;
        this.accountId = accountId;
    }

}
