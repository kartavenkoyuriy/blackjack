package blackjack.model;

import java.text.NumberFormat;
import java.util.Locale;

public class GameResult {
	private String message;

	public GameResult() {
		super();
	}

	public GameResult(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public static GameResult push() {
		return new GameResult("Push");
	}

	public static GameResult win(long amount) {
		NumberFormat n = NumberFormat.getCurrencyInstance(Locale.US);
		String price = n.format(amount / 100.0);
		return new GameResult("Win " + price);
	}

	public static GameResult noWin() {
		return new GameResult("No win");
	}

	public static GameResult bust() {
		return new GameResult("You bust");
	}

}
