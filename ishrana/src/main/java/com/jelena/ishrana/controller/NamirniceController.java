package com.jelena.ishrana.controller;


import com.jelena.ishrana.model.Namirnica;
import com.jelena.ishrana.service.memory.NamirnicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class NamirniceController {

    @Autowired
    private NamirnicaService namirnicaService;

    @RequestMapping("/namirnice")
    public String vratiNamirnice(Model model) {
        System.out.println("inside vratiNamirnice method");
        List<Namirnica> lst = namirnicaService.findAll();
        model.addAttribute("namirnice", lst);
        return "namirnice";
    }

}

