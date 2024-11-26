package com.casino.game.Entities;

import com.casino.game.Enums.RouletteBetEnum;
import com.casino.game.Enums.RouletteNumberEnum;
import jakarta.persistence.*;
import jakarta.ws.rs.DefaultValue;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
public class RouletteBet {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;
  private UUID rouletteId;
  private UUID userId;
  private Date betDate;
  @Enumerated(EnumType.STRING)
  private RouletteNumberEnum number;
  @Enumerated(EnumType.STRING)
  private RouletteBetEnum betType;
  private int amount;
  private Integer winAmount;
  @DefaultValue("false")
  private boolean isVerified;
  @DefaultValue("false")
  private boolean isInError;
  @DefaultValue("false")
  private boolean isCompleted;

  @PrePersist
  public void prePersist() {
    this.betDate = new Date();
  }
}
