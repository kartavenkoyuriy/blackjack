package blackjack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import blackjack.model.Wallet;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {

}
