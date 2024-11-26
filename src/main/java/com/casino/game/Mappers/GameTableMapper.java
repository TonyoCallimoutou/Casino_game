package com.casino.game.Mappers;

import com.casino.game.DTO.GameTableDTO;
import com.casino.game.Entities.GameTable;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GameTableMapper {

  GameTableDTO entityToDTO(GameTable gameTable);
  List<GameTableDTO> listEntityToListDTO(List<GameTable> gameTable);
  GameTable dtoToEntity(GameTableDTO gameTableDTO);
  List<GameTable> listDtoToListEntity(List<GameTableDTO> gameTableDTO);
}
