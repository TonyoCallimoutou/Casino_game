package com.casino.game.Services;

import com.casino.game.DTO.ResultRouletteDTO;
import com.casino.game.DTO.UserBetDTO;
import com.casino.game.Entities.RouletteBet;

import java.util.List;
import java.util.UUID;

public interface RouletteService {
    void startAutomation();
    void stopAutomation();
    UUID openBetOnRoulette(UUID tableId);
    void turnRoulette(UUID tableId);
    Boolean betRoulette(List<UserBetDTO> userBetDTO);
}
