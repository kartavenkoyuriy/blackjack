package blackjack.model;

import java.util.ArrayList;
import java.util.List;

import blackjack.model.Card.Rank;
import blackjack.model.Card.Suit;

public final class Standard52CardDeck {


	private Standard52CardDeck() {
		throw new UnsupportedOperationException("Use Standard52CardDeck.newInstance().");
	}

	public static final List<Card> newInstance() {
		final List<Card> cardDeck = new ArrayList<Card>();
		for (Rank rank : Card.Rank.values()) {
			for (Suit suit : Card.Suit.values()) {
				cardDeck.add(new Card(rank, suit));
			}
		}
		return cardDeck;
	}

}
