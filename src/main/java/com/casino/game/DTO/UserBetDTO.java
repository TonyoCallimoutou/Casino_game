package com.casino.game.DTO;

import com.casino.game.Enums.RouletteBetEnum;
import com.casino.game.Enums.RouletteNumberEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class UserBetDTO {
  private UUID tableId;
  private UUID userId;
  private RouletteBetEnum betType;
  private RouletteNumberEnum number;
  private int amount;
  private int winAmount;
}
