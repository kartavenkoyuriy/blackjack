package blackjack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import blackjack.controller.dto.Bet;
import blackjack.controller.dto.GameView;
import blackjack.controller.dto.HitView;
import blackjack.service.GameService;

@RestController
@RequestMapping("/games")
public class GameController {

	@Autowired
	private GameService gameService;

	/**
	 * Starts game. Possible use: Button "Bet".
	 * 
	 * @param bet
	 */
	@RequestMapping(value = "/start", method = RequestMethod.POST)
	public GameView start(@RequestBody Bet bet) {
		GameView gameView = gameService.start(bet);
		return gameView;
	}

	@RequestMapping(value = "/{gameId}/player/hit", method = RequestMethod.POST)
	public HitView playerHit(@PathVariable long gameId) {
		HitView hitView = gameService.playerHit(gameId);
		return hitView;
	}

	@RequestMapping(value = "/{gameId}/dealer/open-card", method = RequestMethod.POST)
	public HitView dealerOpenCard(@PathVariable long gameId) {
		HitView hitView = gameService.dealerOpenCard(gameId);
		return hitView;
	}

	@RequestMapping(value = "/{gameId}/dealer/hit", method = RequestMethod.POST)
	public HitView dealerHit(@PathVariable long gameId) {
		HitView hitView = gameService.dealerHit(gameId);
		return hitView;
	}

	@RequestMapping(value = "/{gameId}/player/stand", method = RequestMethod.POST)
	public GameView playerStand(@PathVariable long gameId) {
		GameView gameView = gameService.playerStand(gameId);
		return gameView;
	}

}
