package blackjack.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Game {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Long walletId;
	private Long betAmount;
	private GameStatus status;

	@OneToMany(cascade = CascadeType.ALL)
	@ElementCollection
	private List<Card> cardDeck;

	@OneToMany(cascade = CascadeType.ALL)
	@ElementCollection
	private List<Card> dealerCards;
	@OneToMany(cascade = CascadeType.ALL)
	@ElementCollection
	private List<Card> playerCards;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public List<Card> getCardDeck() {
		return cardDeck;
	}

	public void setCardDeck(List<Card> cardDeck) {
		this.cardDeck = cardDeck;
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

	public GameStatus getStatus() {
		return status;
	}

	public void setStatus(GameStatus status) {
		this.status = status;
	}

}
