package com.casino.game.Mappers;

import com.casino.game.DTO.GameTableDTO;
import com.casino.game.DTO.UserBetDTO;
import com.casino.game.Entities.GameTable;
import com.casino.game.Entities.RouletteBet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface RouletteBetMapper {

  UserBetDTO entityToDTO(RouletteBet rouletteBet);
  List<UserBetDTO> listEntityToListDTO(List<RouletteBet> listRouletteBet);
  @Mapping(target = "tableId", source = "tableId")
  UserBetDTO entityWithTableIdToDTO(RouletteBet rouletteBet, UUID tableId);
  default List<UserBetDTO> listEntityWithTableIdToListDTO(List<RouletteBet> listRouletteBet, UUID tableId) {
    return listRouletteBet.stream()
        .map(bet -> entityWithTableIdToDTO(bet, tableId))
        .collect(Collectors.toList());
  }

  @Mapping(target = "rouletteId", source = "rouletteId")
  RouletteBet DtoWithRouletteIdToEntity(UserBetDTO userBetDTO, UUID rouletteId);
  default List<RouletteBet> listDtoWithRouletteIdToListEntity(List<UserBetDTO> listUserBetDTO, UUID rouletteId) {
    return listUserBetDTO.stream()
        .map(bet -> DtoWithRouletteIdToEntity(bet, rouletteId))
        .collect(Collectors.toList());
  }

}
