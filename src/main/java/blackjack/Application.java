package blackjack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import blackjack.controller.GameController;
import blackjack.controller.WalletController;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class Application {

	private static final Object[] SOURCES = new Object[] { Application.class, WalletController.class, GameController.class, };

	public static void main(String[] args) {
		SpringApplication.run(SOURCES, args);
	}
}
