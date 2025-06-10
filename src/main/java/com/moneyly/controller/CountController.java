package com.moneyly.controller;

import com.moneyly.model.ApiResponse;
import com.moneyly.model.Conto;
import com.moneyly.service.CountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/count")
public class CountController {

    @Autowired
    CountService countService;

    @PostMapping("/create")
    public ResponseEntity<?> createCount(@RequestBody Conto conto) {
        ApiResponse apiResponse = countService.createConto(conto);
        if(getCountsByIdUtente(conto) instanceof String){
            return ResponseEntity.badRequest().body(apiResponse);
        }else{
            ArrayList<Conto> list = (ArrayList<Conto>) getCountsByIdUtente(conto);
            if(list.contains(conto)){
                apiResponse.setMessage("Nome conto gia utilizzato. Provane uno diverso");
                return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
            }else{
                return ResponseEntity.ok().body(apiResponse);
            }
        }
    }

    @GetMapping("/list")
    public ResponseEntity<?> getAllCounts(@RequestParam("id_utente") UUID idUtente){
        Map<String, Object> responseMap = countService.getCountByIdUtente(idUtente);
        if(responseMap.containsKey("counts")){
            ArrayList<Conto> list = (ArrayList<Conto>) responseMap.get("counts");
            return ResponseEntity.ok().body(Collections.singletonMap("list" , list));
        }else{
            return ResponseEntity.ok().body(Collections.singletonMap("list", new ArrayList<>()));
        }
    }


    private Object getCountsByIdUtente(Conto conto) {
        Map<String, Object> responseMap = countService.getCountByIdUtente(conto.getUtenteId());
        if(responseMap.containsKey("counts")){
           return responseMap.get("counts");
        }else{
            return responseMap.get("error");
        }
    }

}
