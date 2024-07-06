package swp.group2.swpbe.user;

import org.springframework.stereotype.Repository;

import swp.group2.swpbe.user.entities.Withdraw;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface WithdrawRepository extends JpaRepository<Withdraw, Integer> {

}
