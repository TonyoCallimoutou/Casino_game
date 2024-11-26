package com.casino.game.Repository;

import com.casino.game.Entities.Roulette;
import com.casino.game.Enums.GameTableStatusEnum;
import com.casino.game.Enums.RouletteNumberEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface RouletteRepository extends JpaRepository<Roulette, Long> {
  @Query(
      "SELECT r.id " +
          "FROM Roulette r " +
          "WHERE r.tableId = :tableId " +
          "AND r.finishedBetDate IS NULL")
  List<UUID> findRouletteIdByTableId(UUID tableId);

  @Transactional
  @Modifying
  @Query(
      "UPDATE Roulette " +
      "SET number = :resultRoulette, finishedBetDate = current_timestamp " +
      "WHERE id = :rouletteId ")
  void saveResult(UUID rouletteId, RouletteNumberEnum resultRoulette);

  @Transactional
  @Modifying
  @Query(
      "UPDATE Roulette " +
      "SET isVerified = true " +
      "WHERE isCompleted = false " +
      "AND finishedBetDate IS NOT NULL ")
  void setVerified();

  @Transactional
  @Modifying
  @Query(
      "UPDATE Roulette " +
      "SET isInError = true " +
      "WHERE isCompleted = false " +
      "AND isVerified = true " +
      "AND id in " +
          "(SELECT rouletteId " +
          "FROM RouletteBet " +
          "WHERE isCompleted = false " +
          "AND isInError = true) "
  )
  void setIsInError();

  @Transactional
  @Modifying
  @Query(
      "UPDATE Roulette " +
          "SET isCompleted = true " +
          "WHERE isCompleted = false "
  )
  void setIsCompleted();

  @Transactional
  @Modifying
  @Query(
      "DELETE Roulette " +
      "WHERE isVerified = true " +
      "AND isCompleted = true " +
      "AND isInError = false")
  void deleteVerifiedData();
}
