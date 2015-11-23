package blackjack.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import blackjack.controller.dto.Action;
import blackjack.controller.dto.Bet;
import blackjack.controller.dto.GameView;
import blackjack.controller.dto.HitView;
import blackjack.model.Card;
import blackjack.model.Game;
import blackjack.model.GameResult;
import blackjack.model.GameStatus;
import blackjack.model.Standard52CardDeck;
import blackjack.model.Wallet;
import blackjack.repository.GameRepository;

@Service
public class GameService {

	private static double BLACKJACK_PRICE_MULTIPLIER = 1.5;

	@Autowired
	private GameRepository gameRepository;

	@Autowired
	private WalletService walletService;

	@Autowired
	private DealerService dealerService;

	@Autowired
	private ScoreService scoreService;

	@Transactional
	public GameView start(Bet bet) {

		validate(bet);
		Game game = new Game();
		game.setWalletId(bet.getWalletId());
		game.setBetAmount(bet.getAmount());
		game.setStatus(GameStatus.PLAYER_TURN);
		List<Card> cardDeck = Standard52CardDeck.newInstance();
		dealerService.shuffleCardDeck(cardDeck);
		game.setCardDeck(cardDeck);

		List<Card> playerCards = dealerService.getPlayerCards(cardDeck);
		game.setPlayerCards(playerCards);
		List<Card> dealerCards = dealerService.getDealerCards(cardDeck);
		game.setDealerCards(dealerCards);

		game = gameRepository.save(game);

		return startGameView(game);
	}

	private void validate(Bet bet) {
		Wallet wallet = walletService.getById(bet.getWalletId());
		if (wallet.getAmount() < bet.getAmount()) {
			throw new RuntimeException("Not enough money in your wallet to bet.");
		}
	}

	private GameView startGameView(Game game) {
		GameView gameView = new GameView(game);

		final List<Card> playerCards = game.getPlayerCards();
		List<Action> actions = new ArrayList<Action>();
		final int playerScore = scoreService.count(playerCards);
		if (scoreService.blackjack(playerScore, playerCards.size())) {
			game.setStatus(GameStatus.DEALER_OPEN_CARD);
			game = gameRepository.save(game);
			actions.add(Action.dealerOpenCard(game.getId()));
		} else {
			actions.add(Action.playerHit(game.getId()));
			actions.add(Action.playerStand(game.getId()));
		}

		gameView.setPossibleActions(actions);
		return gameView;
	}

	public HitView playerHit(long gameId) {
		Game game = gameRepository.findOne(gameId);

		if (game.getStatus() != GameStatus.PLAYER_TURN) {
			throw new UnsupportedOperationException("You cannot hit right now!");
		}
		List<Card> cardDeck = game.getCardDeck();

		// I expect that player can hit, before dealer can hit, so the dealer
		// cards count is two.
		final int dealerCardsCount = 2;
		int cardDeckIndex = game.getPlayerCards().size() + dealerCardsCount - 1;
		Card newCard = cardDeck.get(cardDeckIndex);
		game.getPlayerCards().add(newCard);
		game = gameRepository.save(game);

		return hitGameView(game, newCard);
	}

	private GameView playerStandView(Game game) {
		GameView gameView = new GameView(game);
		List<Action> actions = new ArrayList<Action>();
		game.setStatus(GameStatus.DEALER_OPEN_CARD);
		game = gameRepository.save(game);
		actions.add(Action.dealerOpenCard(game.getId()));
		gameView.setPossibleActions(actions);
		return gameView;
	}

	public GameView playerStand(long gameId) {
		Game game = gameRepository.findOne(gameId);

		if (game.getStatus() != GameStatus.PLAYER_TURN) {
			throw new UnsupportedOperationException("You cannot stand right now!");
		}
		return playerStandView(game);
	}

	private HitView hitGameView(Game game, Card newCard) {
		HitView hitView = new HitView(game, newCard);

		final List<Card> playerCards = game.getPlayerCards();
		List<Action> actions = new ArrayList<Action>();
		final int playerScore = scoreService.count(playerCards);
		if (scoreService.bust(playerScore)) {
			Wallet wallet = walletService.getById(game.getWalletId());
			wallet.setAmount(wallet.getAmount() - game.getBetAmount());
			walletService.update(wallet);
			game.setStatus(GameStatus.ENDED);
			game = gameRepository.save(game);
			hitView.setResult(GameResult.bust());
			actions.add(Action.startGame());
		} else if (playerScore == 21) {
			game.setStatus(GameStatus.DEALER_OPEN_CARD);
			game = gameRepository.save(game);
			actions.add(Action.dealerOpenCard(game.getId()));
		} else {
			actions.add(Action.playerHit(game.getId()));
			actions.add(Action.playerStand(game.getId()));
		}

		hitView.setPossibleActions(actions);
		return hitView;
	}

	public HitView dealerOpenCard(long gameId) {
		Game game = gameRepository.findOne(gameId);

		if (game.getStatus() != GameStatus.DEALER_OPEN_CARD) {
			throw new UnsupportedOperationException("Dealer cannot open card right now!");
		}

		List<Card> cardDeck = game.getCardDeck();

		final int closedCardIndex = 3;
		Card openedCard = cardDeck.get(closedCardIndex);
		game.getDealerCards().add(openedCard);
		game = gameRepository.save(game);

		return dealerHitView(game, openedCard);

	}

	private HitView dealerHitView(Game game, Card openedCard) {
		HitView hitView = new HitView(game, openedCard);

		final List<Card> playerCards = game.getPlayerCards();
		final List<Card> dealerCards = game.getDealerCards();

		List<Action> actions = new ArrayList<Action>();
		final int playerScore = scoreService.count(playerCards);
		final int dealerScore = scoreService.count(dealerCards);
		if (scoreService.blackjack(playerScore, playerCards.size())) {
			game.setStatus(GameStatus.ENDED);
			game = gameRepository.save(game);

			if (scoreService.blackjack(dealerScore, dealerCards.size())) {
				hitView.setResult(GameResult.push());

				actions.add(Action.startGame());
			} else {
				long winPrice = (long) BLACKJACK_PRICE_MULTIPLIER * game.getBetAmount();
				// Transfer price to player
				Wallet wallet = walletService.getById(game.getWalletId());
				wallet.setAmount(wallet.getAmount() + winPrice);
				walletService.update(wallet);

				hitView.setResult(GameResult.win(winPrice));
				actions.add(Action.startGame());
			}
		} else if (scoreService.blackjack(dealerScore, dealerCards.size())) {
			game.setStatus(GameStatus.ENDED);
			game = gameRepository.save(game);

			hitView.setResult(GameResult.noWin());

			Wallet wallet = walletService.getById(game.getWalletId());
			wallet.setAmount(wallet.getAmount() - game.getBetAmount());
			walletService.update(wallet);

			actions.add(Action.startGame());
		} else if (scoreService.bust(dealerScore)) {
			game.setStatus(GameStatus.ENDED);
			game = gameRepository.save(game);

			// Transfer price to player
			long winPrice = game.getBetAmount();
			Wallet wallet = walletService.getById(game.getWalletId());
			wallet.setAmount(wallet.getAmount() + winPrice);
			walletService.update(wallet);

			hitView.setResult(GameResult.win(winPrice));
			actions.add(Action.startGame());
		} else if (dealerScore <= 17) {
			game.setStatus(GameStatus.DEALER_TURN);
			game = gameRepository.save(game);
			actions.add(Action.dealerHit(game.getId()));
		} else if (scoreService.push(playerScore, dealerScore)) {
			game.setStatus(GameStatus.ENDED);
			game = gameRepository.save(game);
			hitView.setResult(GameResult.push());
			actions.add(Action.startGame());
		} else if (dealerScore > playerScore) {
			game.setStatus(GameStatus.ENDED);
			game = gameRepository.save(game);

			hitView.setResult(GameResult.noWin());

			Wallet wallet = walletService.getById(game.getWalletId());
			wallet.setAmount(wallet.getAmount() - game.getBetAmount());
			walletService.update(wallet);

			actions.add(Action.startGame());
		} else {
			game.setStatus(GameStatus.ENDED);
			game = gameRepository.save(game);

			// Transfer price to player
			long winPrice = game.getBetAmount();
			Wallet wallet = walletService.getById(game.getWalletId());
			wallet.setAmount(wallet.getAmount() + winPrice);
			walletService.update(wallet);

			hitView.setResult(GameResult.win(winPrice));
			actions.add(Action.startGame());
		}

		hitView.setPossibleActions(actions);
		return hitView;
	}

	public HitView dealerHit(long gameId) {
		Game game = gameRepository.findOne(gameId);

		if (game.getStatus() != GameStatus.DEALER_TURN) {
			throw new UnsupportedOperationException("Dealer cannot hit right now!");
		}

		List<Card> cardDeck = game.getCardDeck();
		int cardDeckIndex = game.getPlayerCards().size() + game.getDealerCards().size();
		Card newCard = cardDeck.get(cardDeckIndex);
		game.getDealerCards().add(newCard);
		game = gameRepository.save(game);

		return dealerHitView(game, newCard);
	}

}
