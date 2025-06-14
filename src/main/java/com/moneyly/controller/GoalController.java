package com.moneyly.controller;

import com.moneyly.model.ApiResponse;
import com.moneyly.model.Conto;
import com.moneyly.model.Goal;
import com.moneyly.service.CountService;
import com.moneyly.service.GoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/goals")
public class GoalController {

    @Autowired
    GoalService goalService;

    @Autowired
    CountService countService;

    @PostMapping("/create/{idConto}")
    public ResponseEntity<?> createGoalAndLinkToConto(@PathVariable UUID idConto, @RequestBody Goal goal) {
        Map<String, Object> responseMap = countService.getCountById(idConto);

        if (responseMap.get("count") == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(404, "Conto non trovato"));
        }


        // Crea l’obiettivo e aggiorna il conto con l’ID del nuovo obiettivo
        ApiResponse apiResponse = goalService.createGoalAndUpdateConto(goal, idConto);

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @GetMapping("/list")
    public ResponseEntity<?> getAllGoals(){
        Map<String, Object> responseMap = goalService.getGoalList();
        if(responseMap.containsKey("goal")){;
            return ResponseEntity.ok().body(Collections.singletonMap("list" , responseMap.get("goal")));
        }else{
            return ResponseEntity.ok().body(Collections.singletonMap("list", new ArrayList<>()));
        }
    }

}
