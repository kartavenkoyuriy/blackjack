package blackjack.controller.dto;

public class Action {
	public Action() {
		super();
	}

	public Action(String key, String contextPath) {
		this.key = key;
		this.contextPath = contextPath;
	}

	private String key;
	private String contextPath;

	public String getContextPath() {
		return contextPath;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public static Action playerHit(long gameId) {
		return new Action("player-hit", "/games/" + gameId + "/player/hit");
	}

	public static Action playerStand(long gameId) {
		return new Action("player-stand", "/games/" + gameId + "/player/stand");
	}

	public static Action dealerOpenCard(long gameId) {
		return new Action("player-hit", "/games/" + gameId + "/dealer/open-card");
	}

	public static Action dealerHit(long gameId) {
		return new Action("dealer-hit", "/games/" + gameId + "/dealer/hit");
	}

	public static Action startGame() {
		return new Action("start-game", "/games/start");
	}
}
