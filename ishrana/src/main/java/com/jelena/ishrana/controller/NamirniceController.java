package com.jelena.ishrana.controller;


import com.jelena.ishrana.model.Namirnica;
import com.jelena.ishrana.service.memory.NamirnicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
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
        System.out.println("inside vratiNamirnicePoKategoriji method");
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
/*

    // @ModelAttribute indicates that it indicates the argument should be retrieved from the model.
    // @ModelAttribute means supply this object to a Controller method

    // snimanje namirnice i redirekcija na stranicu koja prikazuje sve namirnice
    @RequestMapping(method = RequestMethod.POST)
    public String post(@ModelAttribute("namirnica") Namirnica namirnica, HttpServletRequest request) {
    //public String post(Namirnica namirnica, HttpServletRequest request) {
        System.out.println("inside post method");
        System.out.println(namirnica);
        // ako je pritisnut button sa imenom save_button onda snimi inace ne
        if (request.getParameter("save_button") != null) {
            namirnicaService.save(namirnica);
        }
        return "redirect:namirnice/all"; // daje http://localhost:8080/ishrana/namirnice/all GET metod
    }
*/


    @RequestMapping(params="save_button", method = RequestMethod.POST)
    public String save(Namirnica namirnica) {
        System.out.println("snimam namirnicu");
        namirnicaService.save(namirnica);
        return "redirect:namirnice/all";
    }

    @RequestMapping(params="cancel_button", method = RequestMethod.POST)
    public String cancel(){
        return "redirect:namirnice/all";
    }



    @RequestMapping("/add")
    public String dodajNovuNamirnicu(Model model) {
        System.out.println("inside dodajNovuNamirnicu method");
        Namirnica namirnica = new Namirnica();

        // problem kad se ne postavi id namirnice!!!!!!!!!!!!
        // dobija se prilikom slanja forme 400 bad request
        // zasto forma ne moze da salje za id koje je hidden field null vrednost???
        // postavila sam na 0 umesto da bude null sto inace bude posle new Namirnica()
        // pa nek joj se u save metodi(InMemoryNamirnicaService) dodeli pravi id, ovo je privremeni

        namirnica.setNamirnica_id(0); // NECU OVAKO, hocu da nova namirnica ima null za id, a ne 0!!!!!!!!

        System.out.println(namirnica);
        model.addAttribute("namirnica", namirnica);
        model.addAttribute("kategorije", categories);
        return "namirnicaForm";
    }


    @RequestMapping(value="/remove/{namirnica_id}", method = RequestMethod.GET)
    public String remove(@PathVariable("namirnica_id") Long namirnica_id) {
        System.out.println("inside remove method, namirnica_id: " + namirnica_id);
        namirnicaService.remove(namirnica_id);
       // return "redirect:.."; // vraca na namirnice, a treba na namirnice +all, ubacila sam vratiNamirnice1
        // koji radi isto sto i vratiNamirnice samo za mapping bez all, brisem to i resavam ovako:

        return "redirect:/namirnice/all"; // vidi HeadFirstServlets, str. 170
        //The forward slash at the beginning means relative to the root of this web Container
        //return "redirect:namirnice/all";
        // ovo ne moze jer daje http://localhost:8080/ishrana/namirnice/remove/namirnice/all
        //if you don’t use a forward slash, that part of the path is prepended
    }
}

