package com.tokaku.controller;

import com.tokaku.mapper.ScheduleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ScheduleController {

    @Autowired
    ScheduleMapper scheduleMapper;

    @RequestMapping("/schedule")
    public String GetCurriculum() {
        return "schedule/table";
    }
}
