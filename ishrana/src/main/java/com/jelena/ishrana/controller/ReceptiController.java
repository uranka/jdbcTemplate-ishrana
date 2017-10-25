package com.jelena.ishrana.controller;

import com.jelena.ishrana.model.Recept;
import com.jelena.ishrana.service.memory.ReceptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
@RequestMapping("/recepti")
public class ReceptiController {

    @Autowired
    private ReceptService receptService;


    @RequestMapping("/all")
    public String vratiRecepte(Model model) {
        System.out.println("inside vratiRecepte method");
        List<Recept> lst = receptService.findAll();
        model.addAttribute("recepti", lst);
        return "recepti";
    }


    @RequestMapping("/edit/{recept_id}")
    public String editujRecept(Model model, @PathVariable("recept_id") Long recept_id) {
        System.out.println("inside editujRecept method, recept_id: " + recept_id);
        Recept recept = receptService.findOne(recept_id);
        model.addAttribute("recept", recept);
        return "receptForm";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String post(@ModelAttribute("recept") Recept recept, HttpServletRequest request) {
        System.out.println("inside post method");
        System.out.println(recept);
        // ako je pritisnut button sa imenom save_button onda snimi inace ne
        if (request.getParameter("save_button") != null) {
            receptService.save(recept);
        }
        return "redirect:recepti/all";
    }

    @RequestMapping("/add")
    public String dodajNoviRecept(Model model) {
        System.out.println("inside dodajNoviRecept method");
        Recept recept = new Recept();
        recept.setRecept_id(0);
        System.out.println(recept);
        model.addAttribute("recept", recept);
        return "receptForm";
    }


    @RequestMapping(value="/remove/{recept_id}", method = RequestMethod.GET)
    public String remove(@PathVariable("recept_id") Long recept_id) {
        System.out.println("inside remove method, recept_id: " + recept_id);
        receptService.remove(recept_id);
        return "redirect:/recepti/all";
    }

    @RequestMapping("/get/{recept_id}")
    public String getRecept(Model model, @PathVariable("recept_id") Long recept_id) {
        System.out.println("inside getRecept method, recept_id: " + recept_id);
        Recept recept = receptService.findOne(recept_id);
        model.addAttribute("recept", recept);
        return "recept";
    }

}
