package com.casino.game.Entities;

import com.casino.game.Enums.GameEnum;
import com.casino.game.Enums.GameTableStatusEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
public class GameTable {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;
  @Enumerated(EnumType.STRING)
  private GameEnum type;
  @Enumerated(EnumType.STRING)
  private GameTableStatusEnum status;

}
