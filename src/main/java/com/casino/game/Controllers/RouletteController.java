package com.casino.game.Controllers;

import com.casino.game.DTO.ResultRouletteDTO;
import com.casino.game.DTO.UserBetDTO;
import com.casino.game.Entities.Roulette;
import com.casino.game.Entities.RouletteBet;
import com.casino.game.Enums.GameEnum;
import com.casino.game.Repository.RouletteRepository;
import com.casino.game.Services.RouletteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@RestController
@RequestMapping("/roulette")
public class RouletteController {

  @Autowired
  private final RouletteService service;

  private final Logger logger = Logger.getLogger(RouletteController.class.getName());

  public RouletteController(RouletteService service) {
    this.service = service;
  }

  @GetMapping("/role")
  @PreAuthorize("hasRole('COUCOU')")
  //TODO ROLE NE SECURISE PAS
  public ResponseEntity<String> helloRole() {
    return ResponseEntity.ok("Hello from GAME Service!");
  }

  @GetMapping
  public ResponseEntity<String> hello() {
    return ResponseEntity.ok("Hello from GAME Service!");
  }


  @PutMapping("/start/automation")
  public void startAutomation() {
    service.startAutomation();
    logger.info("Automation started");
  }

  @PutMapping("/stop/automation")
  public void stopAutomation() {
    service.stopAutomation();
    logger.info("Automation stopped");
  }

  @PostMapping("/bet")
  Boolean betRoulette(@RequestBody List<UserBetDTO> listUserBetDTO) {
    return service.betRoulette(listUserBetDTO);
  }
}