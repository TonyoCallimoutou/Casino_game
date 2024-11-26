package com.casino.game.Services;

import com.casino.game.DTO.GameTableDTO;
import com.casino.game.Entities.GameTable;
import com.casino.game.Enums.GameEnum;
import com.casino.game.Enums.GameTableStatusEnum;
import com.casino.game.Exceptions.NoRouletteException;
import com.casino.game.Exceptions.RouletteAlreadyExistException;
import com.casino.game.Mappers.GameTableMapper;
import com.casino.game.Repository.GameTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.UUID;

@Service
public class GameTableServiceImpl implements GameTableService {
  private final SocketService socketService;

  private final GameTableRepository gameTableRepository;

  @Autowired
  private final GameTableMapper mapper;

  public GameTableServiceImpl(SocketService socketService, GameTableRepository gameTableRepository, GameTableMapper mapper) {
    this.socketService = socketService;
    this.gameTableRepository = gameTableRepository;
    this.mapper = mapper;
  }

  @Override
  public void sendListTable() {
    List<GameTable> listTable = this.gameTableRepository.findAllOpenTable(GameTableStatusEnum.OPEN);
    List<GameTableDTO> listTableDTO = this.mapper.listEntityToListDTO(listTable);
    this.socketService.sendListTable(listTableDTO);
  }


  @Override
  public void openTable(GameEnum gameEnum) {
    GameTable gameTable = new GameTable();
    gameTable.setType(gameEnum);
    gameTable.setStatus(GameTableStatusEnum.OPEN);
    this.gameTableRepository.save(gameTable);
    this.sendListTable();
  }

  @Override
  public void closeTable(GameEnum gameEnum) {
    UUID tableId = findOpenTable(gameEnum);
    this.gameTableRepository.closeTable(tableId, GameTableStatusEnum.CLOSED);
    this.sendListTable();
  }

  @Override
  public UUID findOpenTable(GameEnum gameEnum) {
    List<UUID> tableIds = this.gameTableRepository.findOpenTable(gameEnum, GameTableStatusEnum.OPEN);
    if (ObjectUtils.isEmpty(tableIds)) {
      throw new NoRouletteException("No table found for : " + gameEnum);
    }
    if (tableIds.size() > 1) {
      throw new RouletteAlreadyExistException("More than one table found for : " + gameEnum);
    }
    return tableIds.getFirst();
  }
}
