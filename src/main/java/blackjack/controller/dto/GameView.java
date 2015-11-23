package blackjack.controller.dto;

import java.util.List;

import blackjack.model.Card;
import blackjack.model.Game;
import blackjack.model.GameResult;

public class GameView {

	private Long gameId;
	private Long walletId;
	private Long betAmount;

	private List<Card> dealerCards;
	private List<Card> playerCards;

	private GameResult result;
	private List<Action> possibleActions;

	public GameView() {
		super();
	}

	public GameView(Game game) {
		this.gameId = game.getId();
		this.walletId = game.getWalletId();
		this.betAmount = game.getBetAmount();
		this.playerCards = game.getPlayerCards();
		this.dealerCards = game.getDealerCards();
	}

	public Long getGameId() {
		return gameId;
	}

	public void setGameId(Long gameId) {
		this.gameId = gameId;
	}

	public Long getWalletId() {
		return walletId;
	}

	public void setWalletId(Long walletId) {
		this.walletId = walletId;
	}

	public Long getBetAmount() {
		return betAmount;
	}

	public void setBetAmount(Long betAmount) {
		this.betAmount = betAmount;
	}

	public List<Card> getDealerCards() {
		return dealerCards;
	}

	public void setDealerCards(List<Card> dealerCards) {
		this.dealerCards = dealerCards;
	}

	public List<Card> getPlayerCards() {
		return playerCards;
	}

	public void setPlayerCards(List<Card> playerCards) {
		this.playerCards = playerCards;
	}

	public GameResult getResult() {
		return result;
	}

	public void setResult(GameResult result) {
		this.result = result;
	}

	public List<Action> getPossibleActions() {
		return possibleActions;
	}

	public void setPossibleActions(List<Action> possibleActions) {
		this.possibleActions = possibleActions;
	}
}
