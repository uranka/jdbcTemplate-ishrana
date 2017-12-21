package com.jelena.ishrana.controller;


import com.jelena.ishrana.model.Namirnica;
import com.jelena.ishrana.service.NamirnicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/namirnice")
public class NamirniceController {

    @Autowired
    private NamirnicaService namirnicaService;

    private static final String[] categories = { "voće", "povrće",
            "mleko i mlečni proizvodi", "meso", "masti", "žitarice"};

    @RequestMapping("/all")
    public String vratiNamirnice(@RequestParam(value = "page", required=false) String page,
                                 @RequestParam(value = "firstRow", required=false) Integer firstRow,
                                 Model model) {
        System.out.println("inside vratiNamirnice method");
        // List<Namirnica> lst = namirnicaService.findAll();

        // pagination
        int itemsTotal = namirnicaService.count();
        int itemsOnPage = 8; // number of namirnica on page;

        if (page == null) {
            firstRow = 0;
        }
        else {
            if (page.equals("next")){
                if (firstRow + itemsOnPage < itemsTotal) {
                    firstRow += itemsOnPage;
                }
            }
            if (page.equals("previous")) {
                if (firstRow - itemsOnPage >= 0) {
                    firstRow -= itemsOnPage;
                }
            }
        }

        model.addAttribute("firstRow", firstRow);
        List<Namirnica> lst = namirnicaService.findAll(firstRow, itemsOnPage);

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

