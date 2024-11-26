package com.casino.game.Controllers;

import com.casino.game.Handler.GameTableSocketHandler;
import com.casino.game.Handler.RouletteSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

  private final GameTableSocketHandler gameTableSocketHandler;
  private final RouletteSocketHandler rouletteSocketHandler;

  @Autowired
  public WebSocketConfig(GameTableSocketHandler gameTableSocketHandler, RouletteSocketHandler rouletteSocketHandler) {
    this.gameTableSocketHandler = gameTableSocketHandler;
    this.rouletteSocketHandler = rouletteSocketHandler;
  }

  @Override
  public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
    registry.addHandler(gameTableSocketHandler, "/ws/table")
        .setAllowedOrigins("http://localhost:4200") ;// Autoriser le front local

    registry.addHandler(rouletteSocketHandler, "/ws/roulette")
        .setAllowedOrigins("http://localhost:4200") ;// Autoriser le front local
  }
}
