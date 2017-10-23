package com.jelena.ishrana.controller;


import com.jelena.ishrana.model.Namirnica;
import com.jelena.ishrana.service.memory.NamirnicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/namirnice")
public class NamirniceController {

    @Autowired
    private NamirnicaService namirnicaService;

    private static final String[] categories = { "voće", "povrće",
            "mleko i mlečni proizvodi", "meso", "masti"};

    @RequestMapping("/all")
    public String vratiNamirnice(Model model) {
        System.out.println("inside vratiNamirnice method");
        List<Namirnica> lst = namirnicaService.findAll();
        model.addAttribute("namirnice", lst);
        return "namirnice";
    }

    // http://localhost:8080/ishrana/namirnice/voće
    // http://localhost:8080/ishrana/namirnice/mleko i mlečni proizvodi
    @RequestMapping("/{category}")
    public String vratiNamirnicePoKategoriji(Model model, @PathVariable("category") String category) {
        System.out.println("inside vvvv method");
        List<Namirnica> lst = namirnicaService.findByCategory(category);
        model.addAttribute("namirnice", lst);
        return "namirnice";
    }

    @RequestMapping("/edit/{namirnica_id}")
    public String editujNamirnicu(Model model, @PathVariable("namirnica_id") Long namirnica_id) {
        System.out.println("inside editujNamirnicu method, namirnica id: " + namirnica_id);
        model.addAttribute("kategorije", categories);
        Namirnica namirnica = namirnicaService.findOne(namirnica_id);
        model.addAttribute("namirnica", namirnica);
        return "namirnicaForm";
    }

}

