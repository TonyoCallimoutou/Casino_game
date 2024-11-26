package com.casino.game.Repository;

import com.casino.game.Entities.RouletteBet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface RouletteBetRepository extends JpaRepository<RouletteBet, Long> {

  @Query(
      "SELECT rb " +
      "FROM RouletteBet rb " +
      "WHERE rb.rouletteId = :rouletteId")
  List<RouletteBet> findAllByRouletteId(UUID rouletteId);

  @Transactional
  @Modifying
  @Query(
      "UPDATE RouletteBet " +
      "SET isVerified = true " +
      "WHERE isCompleted = false " +
      "AND winAmount IS NOT NULL ")
  void setVerified();

  @Transactional
  @Modifying
  @Query(
      "UPDATE RouletteBet " +
          "SET isInError = true " +
          "WHERE isCompleted = false " +
          "AND isVerified = true " +
          "AND number != (select r.number FROM Roulette r where r.id = rouletteId)" +
          "AND winAmount != 0 ")
  void setIsInError();

  @Transactional
  @Modifying
  @Query(
      "UPDATE RouletteBet " +
          "SET isCompleted = true " +
          "WHERE isCompleted = false "
  )
  void setIsCompleted();

  @Transactional
  @Modifying
  @Query(
      "DELETE FROM RouletteBet " +
      "WHERE isVerified = true " +
      "AND isCompleted = true " +
      "AND isInError = false")
  void deleteVerifiedData();
}
