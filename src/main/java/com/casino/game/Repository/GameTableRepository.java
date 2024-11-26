package com.casino.game.Repository;

import com.casino.game.Entities.GameTable;
import com.casino.game.Entities.Roulette;
import com.casino.game.Enums.GameEnum;
import com.casino.game.Enums.GameTableStatusEnum;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface GameTableRepository extends JpaRepository<GameTable, Long> {
  @Query(
      "SELECT t.id " +
      "FROM GameTable t " +
      "WHERE t.type = :type " +
      "AND t.status = :status"
  )
  List<UUID> findOpenTable(GameEnum type, GameTableStatusEnum status);

  @Query(
      "SELECT t " +
          "FROM GameTable t " +
          "WHERE t.status = :status"
  )
  List<GameTable> findAllOpenTable(GameTableStatusEnum status);

  @Transactional
  @Modifying
  @Query(
      "UPDATE GameTable " +
      "SET status = :status " +
      "WHERE id = :tableId"
  )
  void closeTable(UUID tableId, GameTableStatusEnum status);
}
