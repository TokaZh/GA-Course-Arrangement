package com.tokaku.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AppErrorController {

    @RequestMapping(value = "/404", produces = {"text/html"})
    public ModelAndView errorPage404() {
        return new ModelAndView("/404");
    }

    @RequestMapping("/404")
    @ResponseBody
    public ResponseEntity error404() {
        return ResponseEntity.status(404).build();
    }

    @RequestMapping(value = "/500", produces = {"text/html"})
    public ModelAndView errorPage500() {
        return new ModelAndView("/blank");
    }

    @RequestMapping(value = "/500")
    @ResponseBody
    public ResponseEntity error500() {
        return ResponseEntity.status(500).build();
    }
}
