package com.casino.game.DTO;

import com.casino.game.Enums.GameEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class GameTableDTO {
  UUID id;
  GameEnum type;

}
