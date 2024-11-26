package com.casino.game.Utils;

import com.casino.game.Entities.RouletteBet;
import com.casino.game.Enums.RouletteBetEnum;
import com.casino.game.Enums.RouletteNumberEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RouletteUtilsTest {

  @Test
  void calculateWinAmountOnNumberTest() {
    // Given
    RouletteBet rouletteBet = new RouletteBet();
    rouletteBet.setBetType(RouletteBetEnum.NUMBER);
    rouletteBet.setNumber(RouletteNumberEnum.FIVE);
    rouletteBet.setAmount(100);

    Assertions.assertEquals(0, RouletteUtils.calculateWinAmount(rouletteBet, RouletteNumberEnum.ZERO));
    Assertions.assertEquals(3600, RouletteUtils.calculateWinAmount(rouletteBet, RouletteNumberEnum.FIVE));
    Assertions.assertEquals(0, RouletteUtils.calculateWinAmount(rouletteBet, RouletteNumberEnum.TEN));
    Assertions.assertEquals(0, RouletteUtils.calculateWinAmount(rouletteBet, RouletteNumberEnum.FIFTEEN));
    Assertions.assertEquals(0, RouletteUtils.calculateWinAmount(rouletteBet, RouletteNumberEnum.TWENTY));
    Assertions.assertEquals(0, RouletteUtils.calculateWinAmount(rouletteBet, RouletteNumberEnum.TWENTY_FIVE));
    Assertions.assertEquals(0, RouletteUtils.calculateWinAmount(rouletteBet, RouletteNumberEnum.THIRTY));
  }

  @Test
  void calculateWinAmountOnRedTest() {
    // Given
    RouletteBet rouletteBet = new RouletteBet();
    rouletteBet.setBetType(RouletteBetEnum.RED);
    rouletteBet.setAmount(100);

    Assertions.assertEquals(0, RouletteUtils.calculateWinAmount(rouletteBet, RouletteNumberEnum.ZERO));
    Assertions.assertEquals(200, RouletteUtils.calculateWinAmount(rouletteBet, RouletteNumberEnum.FIVE));
    Assertions.assertEquals(0, RouletteUtils.calculateWinAmount(rouletteBet, RouletteNumberEnum.TEN));
    Assertions.assertEquals(0, RouletteUtils.calculateWinAmount(rouletteBet, RouletteNumberEnum.FIFTEEN));
    Assertions.assertEquals(0, RouletteUtils.calculateWinAmount(rouletteBet, RouletteNumberEnum.TWENTY));
    Assertions.assertEquals(200, RouletteUtils.calculateWinAmount(rouletteBet, RouletteNumberEnum.TWENTY_FIVE));
    Assertions.assertEquals(200, RouletteUtils.calculateWinAmount(rouletteBet, RouletteNumberEnum.THIRTY));
  }

  @Test
  void calculateWinAmountOnBlackTest() {
    // Given
    RouletteBet rouletteBet = new RouletteBet();
    rouletteBet.setBetType(RouletteBetEnum.BLACK);
    rouletteBet.setAmount(100);

    Assertions.assertEquals(0, RouletteUtils.calculateWinAmount(rouletteBet, RouletteNumberEnum.ZERO));
    Assertions.assertEquals(0, RouletteUtils.calculateWinAmount(rouletteBet, RouletteNumberEnum.FIVE));
    Assertions.assertEquals(200, RouletteUtils.calculateWinAmount(rouletteBet, RouletteNumberEnum.TEN));
    Assertions.assertEquals(200, RouletteUtils.calculateWinAmount(rouletteBet, RouletteNumberEnum.FIFTEEN));
    Assertions.assertEquals(200, RouletteUtils.calculateWinAmount(rouletteBet, RouletteNumberEnum.TWENTY));
    Assertions.assertEquals(0, RouletteUtils.calculateWinAmount(rouletteBet, RouletteNumberEnum.TWENTY_FIVE));
    Assertions.assertEquals(0, RouletteUtils.calculateWinAmount(rouletteBet, RouletteNumberEnum.THIRTY));
  }

  @Test
  void calculateWinAmountOnOddTest() {
    // Given
    RouletteBet rouletteBet = new RouletteBet();
    rouletteBet.setBetType(RouletteBetEnum.ODD);
    rouletteBet.setAmount(100);

    Assertions.assertEquals(0, RouletteUtils.calculateWinAmount(rouletteBet, RouletteNumberEnum.ZERO));
    Assertions.assertEquals(200, RouletteUtils.calculateWinAmount(rouletteBet, RouletteNumberEnum.FIVE));
    Assertions.assertEquals(0, RouletteUtils.calculateWinAmount(rouletteBet, RouletteNumberEnum.TEN));
    Assertions.assertEquals(200, RouletteUtils.calculateWinAmount(rouletteBet, RouletteNumberEnum.FIFTEEN));
    Assertions.assertEquals(0, RouletteUtils.calculateWinAmount(rouletteBet, RouletteNumberEnum.TWENTY));
    Assertions.assertEquals(200, RouletteUtils.calculateWinAmount(rouletteBet, RouletteNumberEnum.TWENTY_FIVE));
    Assertions.assertEquals(0, RouletteUtils.calculateWinAmount(rouletteBet, RouletteNumberEnum.THIRTY));
  }

  @Test
  void calculateWinAmountOnEvenTest() {
    // Given
    RouletteBet rouletteBet = new RouletteBet();
    rouletteBet.setBetType(RouletteBetEnum.EVEN);
    rouletteBet.setAmount(100);

    Assertions.assertEquals(0, RouletteUtils.calculateWinAmount(rouletteBet, RouletteNumberEnum.ZERO));
    Assertions.assertEquals(0, RouletteUtils.calculateWinAmount(rouletteBet, RouletteNumberEnum.FIVE));
    Assertions.assertEquals(200, RouletteUtils.calculateWinAmount(rouletteBet, RouletteNumberEnum.TEN));
    Assertions.assertEquals(0, RouletteUtils.calculateWinAmount(rouletteBet, RouletteNumberEnum.FIFTEEN));
    Assertions.assertEquals(200, RouletteUtils.calculateWinAmount(rouletteBet, RouletteNumberEnum.TWENTY));
    Assertions.assertEquals(0, RouletteUtils.calculateWinAmount(rouletteBet, RouletteNumberEnum.TWENTY_FIVE));
    Assertions.assertEquals(200, RouletteUtils.calculateWinAmount(rouletteBet, RouletteNumberEnum.THIRTY));
  }
}
