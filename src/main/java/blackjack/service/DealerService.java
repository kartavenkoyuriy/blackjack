package blackjack.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import blackjack.model.Card;

@Service
public class DealerService {

	private static final int DEALER_OPEN_CARD_INDEX = 2;

	public List<Card> getPlayerCards(List<Card> shuffledCardDeck) {
		List<Card> cards = new ArrayList<Card>();
		for (int i = 0; i < DEALER_OPEN_CARD_INDEX; i++) {
			cards.add(shuffledCardDeck.get(i));
		}
		return cards;
	}

	public List<Card> getDealerCards(List<Card> shuffledCardDeck) {
		List<Card> cards = new ArrayList<Card>();
		cards.add(shuffledCardDeck.get(DEALER_OPEN_CARD_INDEX));
		return cards;
	}

	public void shuffleCardDeck(List<Card> cardDeck) {
		Collections.shuffle(cardDeck);
	}
}
