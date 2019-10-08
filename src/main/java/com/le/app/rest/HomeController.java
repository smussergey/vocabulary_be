package com.le.app.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    @RequestMapping("/api/content/")
    public @ResponseBody
    String greeting() {
        return "Hello World";
    }

}
