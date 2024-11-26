package com.casino.game.Services;

import com.casino.game.DTO.GameTableDTO;
import com.casino.game.DTO.ResultRouletteDTO;
import com.casino.game.Entities.RouletteBet;
import com.casino.game.Handler.GameTableSocketHandler;
import com.casino.game.Handler.RouletteSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class SocketServiceImpl implements SocketService {

  @Autowired
  private GameTableSocketHandler gameTableSocketHandler;

  @Autowired
  private RouletteSocketHandler rouletteSocketHandler;

  @Override
  public void sendListTable(List<GameTableDTO> listTable) {
    this.gameTableSocketHandler.sendListTable(listTable);
  }

  @Override
  public void sendLogsFromRoulette(List<RouletteBet> listRouletteBet) {
    AtomicReference<String> message = new AtomicReference<>("");
    listRouletteBet.forEach(rouletteBet -> {
      message.set(message + "\n" + "Roulette: " + rouletteBet.getRouletteId() + " - " + rouletteBet.getUserId() + " - " + rouletteBet.getAmount() + " - " + rouletteBet.getNumber());
    });
    this.rouletteSocketHandler.sendMessage(listRouletteBet.getFirst().getRouletteId(), listRouletteBet.getFirst().getUserId(), message.toString());

  }

  @Override
  public void sendLogsFromRoulette(ResultRouletteDTO resultRoulette) {
    String messageResult = "Roulette: " + resultRoulette.getTableId() + " - " + resultRoulette.getNumber() ;
    this.rouletteSocketHandler.sendResult(resultRoulette.getTableId(), messageResult);
    resultRoulette.getListUserBet().forEach(userBet -> {
      String message = "WINNER: " + userBet.getUserId() + " - " +  userBet.getWinAmount();
      this.rouletteSocketHandler.sendMessage(resultRoulette.getTableId(),userBet.getUserId(), message);
    });
  }
}
