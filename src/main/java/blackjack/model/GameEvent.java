package blackjack.model;

import java.util.Date;

public class GameEvent {

	private Date created;
	private long walletId;
	private GameEventType gameEventType;

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public long getWalletId() {
		return walletId;
	}

	public void setWalletId(long walletId) {
		this.walletId = walletId;
	}

	public GameEventType getGameEventType() {
		return gameEventType;
	}

	public void setGameEventType(GameEventType gameEventType) {
		this.gameEventType = gameEventType;
	}

}
