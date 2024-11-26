package com.casino.game.Services;

import com.casino.game.Enums.GameEnum;

import java.util.UUID;

public interface GameTableService {
  void openTable(GameEnum gameEnum);
  void closeTable(GameEnum gameEnum);
  UUID findOpenTable(GameEnum gameEnum);
  void sendListTable();
}
