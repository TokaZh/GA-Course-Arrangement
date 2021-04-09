package com.tokaku.controller;

import com.tokaku.mapper.ScheduleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ScheduleController {

    @Autowired
    ScheduleMapper scheduleMapper;

    @RequestMapping("/curriculum")
    public String GetCurriculum(Model model, @RequestParam(defaultValue = "1") Integer page) {
        return "schedule";
    }
}
