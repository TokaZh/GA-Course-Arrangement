package com.tokaku.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ArrangementController {
    @RequestMapping("arrangement")
    public String autoArrangement() {
        return "arrangement";
    }

}
