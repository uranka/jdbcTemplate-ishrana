package com.jelena.ishrana.controller;

import com.jelena.ishrana.model.Namirnica;
import com.jelena.ishrana.model.Recept;
import com.jelena.ishrana.service.memory.NamirnicaService;
import com.jelena.ishrana.service.memory.ReceptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;



@Controller
@RequestMapping("/recepti")
public class ReceptiController {

    @Autowired
    private ReceptService receptService;

    @Autowired
    private NamirnicaService namirnicaService;

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

        rekonstruisiListuNamirnicaNaOsnovuIdova(recept);

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
        recept.setRecept_id(0L);
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

    // parameter removeNamirnica must be present in post request
    @RequestMapping(params="removeNamirnica", method = RequestMethod.POST)
    public String removeNamirnica(Recept recept,
                                  @RequestParam(value = "removeNamirnica") Long namirnica_id, Model model) {
        System.out.println("inside removeNamirnica method klase ReceptiController, namirnica_id: " + namirnica_id);
        System.out.println(recept);

        rekonstruisiListuNamirnicaNaOsnovuIdova(recept);

        receptService.removeNamirnica(recept, namirnica_id);

        System.out.println("namirnica_id u removeNamirnica : " + namirnica_id);

        model.addAttribute("recept", recept);
        return "receptForm";
    }


    @RequestMapping(params="addNamirnica", method = RequestMethod.POST)
    public String addNamirnica(Recept recept, Model model){
        System.out.println("inside addNamirnica method");

        rekonstruisiListuNamirnicaNaOsnovuIdova(recept);

        List<Namirnica> namirniceSelect;
        // ako je lista prazna nece se stvoriti namirniceSelect
        if (!findNotInRecept(recept).isEmpty()) {
            System.out.println(findNotInRecept(recept) );
            namirniceSelect = findNotInRecept(recept);
            model.addAttribute("namirniceSelect", namirniceSelect);
            System.out.println("namirniceSelect" + namirniceSelect);
            receptService.addNamirnica(recept, namirniceSelect.get(0)); // prva iz liste namirnica koje nudimo za odabir
        }
// prazna lista-- nema elemenata, nije isto sto i lista je null!!
// ako je bio negde new ArrayList onda imamo praznu listu, ako nije onda je lista null
        model.addAttribute("recept", recept);
        return "receptForm";
    }


    private void rekonstruisiListuNamirnicaNaOsnovuIdova(Recept recept) {
        List<Namirnica> list = recept.getListaNamirnica();
        if (list != null) {
            List<Namirnica> newList = new ArrayList<>();
            for (Namirnica n : list) {
                newList.add(namirnicaService.findOne(n.getNamirnica_id()));
            }
            // novu listu setuj u recept
            recept.setListaNamirnica(newList);
        }
        else{// ovaj else brise on je samo kontrola sta se desava
            System.out.println("LISTA JE NULL"); // KADA NEMAM U LISTI NAMIRNICU list je null, a tada ne mozes proveravati nista metodom isEmpty jer liste i nemas
        }
    }

    // vraca listu namirnica koje nisu u receptu
    private List<Namirnica> findNotInRecept(Recept recept) {
        List<Namirnica> namirniceAll = namirnicaService.findAll();
        //List<Namirnica> namirniceRecept = recept.getListaNamirnica();
        List<Namirnica> namirniceNotInRecept = new ArrayList<>();

        for (Namirnica nAll : namirniceAll) {
            if (! inRecept(recept, nAll)){
                namirniceNotInRecept.add(nAll);
            }
        }
        System.out.println("not in recept: " +  namirniceNotInRecept);
        return namirniceNotInRecept;
    }

    // vraca true ako je namirnica u receptu
    private boolean inRecept(Recept recept, Namirnica namirnica) {
        boolean inRecept = false;
        List<Namirnica> namirniceRecept = recept.getListaNamirnica();
        if (namirniceRecept != null) {
            for (Namirnica nRecept : namirniceRecept) {
                if (nRecept.equals(namirnica)) {
                    inRecept = true;
                    break;
                }
            }
        }
        return inRecept;
    }

}
