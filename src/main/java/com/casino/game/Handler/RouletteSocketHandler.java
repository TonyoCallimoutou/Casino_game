package com.casino.game.Handler;

import com.casino.game.DTO.GameTableDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@Scope("singleton")
public class RouletteSocketHandler extends TextWebSocketHandler {

  private final ObjectMapper objectMapper;
  @Getter
  private final Set<WebSocketSession> sessions;

  @Autowired
  public RouletteSocketHandler(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
    this.sessions = new HashSet<>();
  }

  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    System.out.println("New session: " + session.getId());
    sessions.add(session);  // Ajoute la session au Set
  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
    System.out.println("Session closed: " + session.getId());
    System.out.println("Status: " + status.getReason());
    sessions.remove(session);  // Supprime la session du Set
  }

  public void sendMessage(UUID tableId, UUID userId, String message) {
    for (WebSocketSession session : this.sessions) {  // Parcourt les sessions actives
      try {
        session.sendMessage(new TextMessage(message));
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public void sendResult(UUID tableId, String message) {
    for (WebSocketSession session : this.sessions) {  // Parcourt les sessions actives
      try {
        session.sendMessage(new TextMessage(message));
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
