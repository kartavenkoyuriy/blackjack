package blackjack.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import blackjack.model.Wallet;
import blackjack.service.WalletService;

@RestController
@RequestMapping("/wallets")
public class WalletController {

	@Autowired
	private WalletService walletService;

	@RequestMapping(method = RequestMethod.GET)
	public List<Wallet> getAll() {
		return walletService.getAll();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Wallet getById(@PathVariable Long id) {
		return walletService.getById(id);
	}

	@RequestMapping(method = RequestMethod.POST)
	public Wallet create(@RequestBody Wallet wallet) {
		return walletService.create(wallet);
	}

	@RequestMapping(method = RequestMethod.PUT)
	public Wallet update(@RequestBody Wallet wallet) {
		return walletService.update(wallet);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable Long id) {
		walletService.delete(id);
	}
}
