package blackjack.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import blackjack.controller.dto.Bet;
import blackjack.model.Wallet;
import blackjack.repository.WalletRepository;

@Service
public class WalletService {

	@Autowired
	private WalletRepository walletRepository;

	public List<Wallet> getAll() {
		return walletRepository.findAll();
	}

	public Wallet getById(Long id) {
		return walletRepository.findOne(id);
	}

	public Wallet create(Wallet wallet) {
		return walletRepository.save(wallet);
	}

	public Wallet update(Wallet wallet) {
		return walletRepository.save(wallet);
	}

	public void delete(Long id) {
		walletRepository.delete(id);
	}

	@Transactional
	public Wallet withdrawBet(Bet bet) {
		Wallet wallet = getById(bet.getWalletId());
		// withdraw bet from player wallet
		long newAmount = wallet.getAmount() - bet.getAmount();
		wallet.setAmount(newAmount);
		wallet = update(wallet);

		// TODO Add money to bank or Casino wallet
		// bankService.addMoney(deal.getBet());

		return wallet;
	}

}
