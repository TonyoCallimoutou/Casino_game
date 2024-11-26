package com.casino.game.DTO;

import com.casino.game.Enums.RouletteNumberEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Getter
public class ResultRouletteDTO {
  private UUID tableId;
  private List<UserBetDTO> listUserBet;
  private RouletteNumberEnum number;
}
