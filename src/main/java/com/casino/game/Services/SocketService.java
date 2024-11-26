package com.casino.game.Services;

import com.casino.game.DTO.GameTableDTO;
import com.casino.game.DTO.ResultRouletteDTO;
import com.casino.game.Entities.RouletteBet;

import java.util.List;

public interface SocketService {
  void sendListTable(List<GameTableDTO> listTable);
  void sendLogsFromRoulette(List<RouletteBet> rouletteBet);
  void sendLogsFromRoulette(ResultRouletteDTO resultRoulette);
}
