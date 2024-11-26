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
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Scope("singleton")
public class GameTableSocketHandler extends TextWebSocketHandler {

  private final ObjectMapper objectMapper;
  @Getter
  private final Set<WebSocketSession> sessions;

  @Autowired
  public GameTableSocketHandler(ObjectMapper objectMapper) {
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

  public void sendListTable(List<GameTableDTO> result) {

    for (WebSocketSession session : this.sessions) {  // Parcourt les sessions actives
      try {
        String resultJson = objectMapper.writeValueAsString(result);
        session.sendMessage(new TextMessage(resultJson));
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
