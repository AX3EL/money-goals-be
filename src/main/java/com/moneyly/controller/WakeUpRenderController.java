package com.moneyly.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wake")
public class WakeUpRenderController {

    @GetMapping("/ping")
    public String ping() {
        return "Servizio di nuovo in funzione";
    }

}
