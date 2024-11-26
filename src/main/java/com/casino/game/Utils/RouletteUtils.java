package com.casino.game.Utils;

import com.casino.game.Entities.RouletteBet;
import com.casino.game.Enums.RouletteColorsEnum;
import com.casino.game.Enums.RouletteNumberEnum;

public class RouletteUtils {

  public static RouletteNumberEnum generateRandomNumber() {
    return RouletteNumberEnum.values()[(int) (Math.random() * RouletteNumberEnum.values().length)];
  }

  public static int calculateWinAmount(RouletteBet bet, RouletteNumberEnum number) {
    int winAmount = 0;
    switch (bet.getBetType()) {
      case NUMBER:
        if (bet.getNumber() == number) {
          winAmount = bet.getAmount() * 36;
        }
        break;
      case RED:
        if (number.getColor().equals(RouletteColorsEnum.RED)) {
          winAmount = bet.getAmount() * 2;
        }
        break;
      case BLACK:
        if (number.getColor().equals(RouletteColorsEnum.BLACK)) {
          winAmount = bet.getAmount() * 2;
        }
        break;
      case ODD:
        if (number.getNumber() % 2 != 0){
          winAmount = bet.getAmount() * 2;
        }
        break;
      case EVEN:
        if (number.getNumber() % 2 == 0 && number.getNumber() != 0){
          winAmount = bet.getAmount() * 2;
        }
        break;
    }
    return winAmount;
  }
}
