package com.jelena.ishrana.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class NamirniceController {

    @RequestMapping("/namirnice")
    public String vratiNamirnice() {
        System.out.println("inside vratiNamirnice method");
        return "namirnice";
    }

}


