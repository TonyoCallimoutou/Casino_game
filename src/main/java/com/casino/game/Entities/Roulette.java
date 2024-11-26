package com.casino.game.Entities;

import com.casino.game.Enums.RouletteNumberEnum;
import jakarta.persistence.*;
import jakarta.ws.rs.DefaultValue;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Roulette {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;
  private UUID tableId;
  private Date startingBetDate;
  private Date finishedBetDate;
  @Enumerated(EnumType.STRING)
  private RouletteNumberEnum number;

  @DefaultValue("false")
  private boolean isVerified;
  @DefaultValue("false")
  private boolean isInError;
  @DefaultValue("false")
  private boolean isCompleted;

  @PrePersist
  public void prePersist() {
    this.startingBetDate = new Date();
  }

  @PreUpdate
  public void preUpdate() {
    this.finishedBetDate = new Date();
  }
}