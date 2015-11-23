package blackjack.controller.dto;

import blackjack.model.Card;
import blackjack.model.Game;

public class HitView extends GameView {
	private Card newCard;

	public HitView() {
		super();
	}

	public HitView(Game game, Card newCard) {
		super(game);
		this.newCard = newCard;
	}

	public Card getNewCard() {
		return newCard;
	}

	public void setNewCard(Card newCard) {
		this.newCard = newCard;
	}

}
