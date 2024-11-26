package com.casino.game.Enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public enum RouletteNumberEnum {
  ZERO(0, RouletteColorsEnum.GREEN),
  ONE(1, RouletteColorsEnum.RED),
  TWO(2, RouletteColorsEnum.BLACK),
  THREE(3, RouletteColorsEnum.RED),
  FOUR(4, RouletteColorsEnum.BLACK),
  FIVE(5, RouletteColorsEnum.RED),
  SIX(6, RouletteColorsEnum.BLACK),
  SEVEN(7, RouletteColorsEnum.RED),
  EIGHT(8, RouletteColorsEnum.BLACK),
  NINE(9, RouletteColorsEnum.RED),
  TEN(10, RouletteColorsEnum.BLACK),
  ELEVEN(11, RouletteColorsEnum.BLACK),
  TWELVE(12, RouletteColorsEnum.RED),
  THIRTEEN(13, RouletteColorsEnum.BLACK),
  FOURTEEN(14, RouletteColorsEnum.RED),
  FIFTEEN(15, RouletteColorsEnum.BLACK),
  SIXTEEN(16, RouletteColorsEnum.RED),
  SEVENTEEN(17, RouletteColorsEnum.BLACK),
  EIGHTEEN(18, RouletteColorsEnum.RED),
  NINETEEN(19, RouletteColorsEnum.RED),
  TWENTY(20, RouletteColorsEnum.BLACK),
  TWENTY_ONE(21, RouletteColorsEnum.RED),
  TWENTY_TWO(22, RouletteColorsEnum.BLACK),
  TWENTY_THREE(23, RouletteColorsEnum.RED),
  TWENTY_FOUR(24, RouletteColorsEnum.BLACK),
  TWENTY_FIVE(25, RouletteColorsEnum.RED),
  TWENTY_SIX(26, RouletteColorsEnum.BLACK),
  TWENTY_SEVEN(27, RouletteColorsEnum.RED),
  TWENTY_EIGHT(28, RouletteColorsEnum.BLACK),
  TWENTY_NINE(29, RouletteColorsEnum.BLACK),
  THIRTY(30, RouletteColorsEnum.RED),
  THIRTY_ONE(31, RouletteColorsEnum.BLACK),
  THIRTY_TWO(32, RouletteColorsEnum.RED),
  THIRTY_THREE(33, RouletteColorsEnum.BLACK),
  THIRTY_FOUR(34, RouletteColorsEnum.RED),
  THIRTY_FIVE(35, RouletteColorsEnum.BLACK),
  THIRTY_SIX(36, RouletteColorsEnum.RED);

  private final int number;
  private final RouletteColorsEnum color;

  RouletteNumberEnum(int number, RouletteColorsEnum color) {
    this.number = number;
    this.color = color;
  }

}
