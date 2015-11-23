package blackjack.service;

import java.util.List;

import org.springframework.stereotype.Service;

import blackjack.model.Card;

@Service
public class ScoreService {

	public int count(List<Card> cards) {
		int score = 0;
		for (Card card : cards) {
			switch (card.getRank()) {
			case TWO:
				score += 2;
				break;
			case THREE:
				score += 3;
				break;
			case FOUR:
				score += 4;
				break;
			case FIVE:
				score += 5;
				break;
			case SIX:
				score += 6;
				break;
			case SEVEN:
				score += 7;
				break;
			case EIGHT:
				score += 8;
				break;
			case NINE:
				score += 9;
				break;
			case TEN:
			case JACK:
			case QUEEN:
			case KING:
				score += 10;
				break;
			case ACE:
				score += score > 10 ? 1 : 11;
				break;
			default:
				break;
			}
		}

		return score;
	}

	public boolean blackjack(int score, int cardsCount) {
		return score == 21 && cardsCount == 2;
	}

	public boolean bust(int score) {
		return score > 21;
	}

	public boolean push(int playerScore, int dealerScore) {
		return playerScore == dealerScore;
	}
}
