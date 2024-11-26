package com.casino.game.Services;

import com.casino.game.DTO.ResultRouletteDTO;
import com.casino.game.DTO.UserBetDTO;
import com.casino.game.Entities.GameTable;
import com.casino.game.Entities.RouletteBet;
import com.casino.game.Entities.Roulette;
import com.casino.game.Enums.GameEnum;
import com.casino.game.Enums.GameTableStatusEnum;
import com.casino.game.Enums.RouletteBetEnum;
import com.casino.game.Enums.RouletteNumberEnum;
import com.casino.game.Exceptions.NoRouletteException;
import com.casino.game.Exceptions.RouletteAlreadyExistException;
import com.casino.game.Mappers.RouletteBetMapper;
import com.casino.game.Repository.RouletteBetRepository;
import com.casino.game.Repository.RouletteRepository;
import com.casino.game.Repository.GameTableRepository;
import com.casino.game.Utils.RouletteUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class RouletteServiceImpl implements RouletteService {

  private final SocketService socketService;
  private final RouletteRepository rouletteRepository;
  private final RouletteBetRepository rouletteBetRepository;

  @Autowired
  private final GameTableService gameTableService;

  @Autowired
  private final RouletteBetMapper mapper;
  private boolean isRunning = false;
  private final int timeBeforeCloseBet = 10000; // 10 secondes
  private final int timeBeforeReopening = 5000; // 5 secondes
  private final int timeIterationRoulette = timeBeforeCloseBet + timeBeforeReopening;
  private final int timeBeforeVerifiedData = 3*timeIterationRoulette; // 45 secondes
  private final int timeBeforeDeleteData = 8*timeIterationRoulette; // 2min

  private final GameEnum gameType = GameEnum.ROULETTE;

  private final Logger logger = Logger.getLogger(RouletteServiceImpl.class.getName());

  public RouletteServiceImpl(SocketService socketService, RouletteRepository rouletteRepository, RouletteBetRepository rouletteBetRepository, GameTableService gameTableService, RouletteBetMapper mapper) {
    this.socketService = socketService;
    this.rouletteRepository = rouletteRepository;
    this.rouletteBetRepository = rouletteBetRepository;
    this.gameTableService = gameTableService;
    this.mapper = mapper;
  }

  @Scheduled(fixedDelay = timeIterationRoulette) // Exécute toutes les 50 secondes
  public void automateGameFlow() {
    if (isRunning) {
      UUID tableId = gameTableService.findOpenTable(gameType);
      if (tableId != null) {
        openBetOnRoulette(tableId);
        waitAndProcessBet(tableId);
      }
    }
  }

  private void waitAndProcessBet(UUID tableId) {
    CompletableFuture.runAsync(() -> {
      try {
        Thread.sleep(timeBeforeCloseBet); // Durée de la phase de pari
        turnRoulette(tableId);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt(); // Bonne pratique : rétablir l'état du thread
      }
    });
  }

  @Scheduled(fixedDelay = timeBeforeVerifiedData) // Exécute toutes les 45 secondes
  public void verifiedDataFlow() {
    if (isRunning) {
      rouletteRepository.setVerified();
      rouletteBetRepository.setVerified();
      rouletteBetRepository.setIsInError();
      rouletteRepository.setIsInError();
      rouletteBetRepository.setIsCompleted();
      rouletteRepository.setIsCompleted();
      logger.info("Data verified");
    }
  }

  @Scheduled(fixedDelay = timeBeforeDeleteData) // Exécute toutes les 2min
  public void deleteDataFlow() {
    if (isRunning) {
      rouletteBetRepository.deleteVerifiedData();
      rouletteRepository.deleteVerifiedData();
      logger.info("Data deleted");
    }
  }

  @Override
  public void startAutomation() {
    gameTableService.openTable(gameType);
    isRunning = true;
  }

  @Override
  public void stopAutomation() {
    isRunning = false;
    gameTableService.closeTable(gameType);
  }

  @Override
  public UUID openBetOnRoulette(UUID tableId) {
    List<UUID> rouletteId = this.rouletteRepository.findRouletteIdByTableId(tableId);
    if (ObjectUtils.isEmpty(rouletteId)) {
      Roulette roulette = new Roulette();
      roulette.setTableId(tableId);
      this.rouletteRepository.save(roulette);
      return roulette.getId();
    }
    else {
      throw new RouletteAlreadyExistException("Roulette already exist for tableId: " + tableId);
    }

  }

  @Override
  public void turnRoulette(UUID tableId) {
    UUID rouletteId = getRouletteIdByTableId(tableId);

    RouletteNumberEnum resultRoulette = RouletteUtils.generateRandomNumber();

    this.saveResultRoulette(rouletteId, resultRoulette);

    List<RouletteBet> rouletteBet = this.rouletteBetRepository.findAllByRouletteId(rouletteId);

    List<RouletteBet> winningBets = rouletteBet.stream()
        .filter(bet -> RouletteUtils.calculateWinAmount(bet, resultRoulette) > 0)
        .peek(bet -> bet.setWinAmount(RouletteUtils.calculateWinAmount(bet, resultRoulette)))
        .collect(Collectors.toList());

    ResultRouletteDTO resultRouletteDTO =
        new ResultRouletteDTO(
            tableId,
            mapper.listEntityWithTableIdToListDTO(winningBets, tableId),
            resultRoulette);

    this.socketService.sendLogsFromRoulette(resultRouletteDTO);

    rouletteBetRepository.saveAll(winningBets);
  }

  @Override
  public Boolean betRoulette(List<UserBetDTO> listUserBetDTO) {
    UUID rouletteId = getRouletteIdByTableId(listUserBetDTO.getFirst().getTableId());

    List<RouletteBet> listRouletteBet = mapper.listDtoWithRouletteIdToListEntity(listUserBetDTO, rouletteId);
    List<RouletteBet> listBetSave = this.rouletteBetRepository.saveAll(listRouletteBet);
    this.socketService.sendLogsFromRoulette(listBetSave);
    return true;

  }

  private UUID getRouletteIdByTableId(UUID tableId) {
    List<UUID> rouletteId = this.rouletteRepository.findRouletteIdByTableId(tableId);
    if (ObjectUtils.isEmpty(rouletteId)) {
      throw new NoRouletteException("No roulette found for tableId: " + tableId);
    }
    if (rouletteId.size() > 1) {
      throw new RouletteAlreadyExistException("More than one roulette found for tableId: " + tableId);
    }
    return rouletteId.getFirst();
  }

  private void saveResultRoulette(UUID rouletteId, RouletteNumberEnum rouletteNumberEnum) {
    this.rouletteRepository.saveResult(rouletteId, rouletteNumberEnum);
  }
}
