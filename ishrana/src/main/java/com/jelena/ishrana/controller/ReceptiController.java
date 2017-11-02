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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Controller
@RequestMapping("/recepti")
public class ReceptiController {
    private static final Logger LOG = LoggerFactory.getLogger(ReceptiController.class);

    @Autowired
    private ReceptService receptService;

    @Autowired
    private NamirnicaService namirnicaService;

    @RequestMapping("/all")
    public String vratiRecepte(Model model) {
        System.out.println("inside vratiRecepte method");
        LOG.info("inside vratiRecepte method");
        List<Recept> lst = receptService.findAll();
        model.addAttribute("recepti", lst);
        return "recepti";
    }


    @RequestMapping("/edit/{recept_id}")
    public String editujRecept(Model model, @PathVariable("recept_id") Long recept_id) {
        System.out.println("inside editujRecept method, recept_id: " + recept_id);

        Recept recept = receptService.findOne(recept_id);
        System.out.println("iz edita " + recept);

        List<Namirnica> namirniceSelect = findNotInRecept(recept);
        model.addAttribute("namirniceSelect", namirniceSelect);
        System.out.println("namirniceSelect" + namirniceSelect);

        model.addAttribute("recept", recept);
        return "receptForm";
    }

/*
    @RequestMapping(method = RequestMethod.POST)
    public String post(@ModelAttribute("recept") Recept recept, HttpServletRequest request) {
        System.out.println("inside post method");

        //rekonstruisiListuNamirnicaNaOsnovuIdova(recept);

        System.out.println(recept);

        // ako je pritisnut button sa imenom save_button onda snimi inace ne
        if (request.getParameter("save_button") != null) {
            receptService.save(recept);
        }
        return "redirect:recepti/all";
    }
*/

    @RequestMapping(params="save_button", method = RequestMethod.POST)
    public String save(Recept recept) {
        LOG.info("saving recept");
        receptService.save(recept);
        return "redirect:recepti/all";
    }

    @RequestMapping(params="cancel_button", method = RequestMethod.POST)
    public String cancel(){
        LOG.info("canceling saving recept");
        return "redirect:recepti/all";
    }


    @RequestMapping("/add")
    public String dodajNoviRecept(Model model) {
        LOG.info("start of adding new recept");
        Recept recept = new Recept();
        recept.setRecept_id(0L);

        List<Namirnica> namirniceSelect = namirnicaService.findAll(); // nov je recept pa sve namirnice nudim za izbor
        model.addAttribute("namirniceSelect", namirniceSelect);
        System.out.println("namirniceSelect" + namirniceSelect);

/*
        //listaNamirnica unutar recepta je null, pa da ne bi bila null nego da bude empty
        recept.setListaNamirnica(new ArrayList<Namirnica>());
        // isto za  kolicine
        List<Integer> listaKolicina = new ArrayList<Integer>();
        recept.setListaKolicina(listaKolicina);
*/

        LOG.info("sends new recept to form with data: " + recept);
        model.addAttribute("recept", recept);
        return "receptForm";
    }


    @RequestMapping(value="/remove/{recept_id}", method = RequestMethod.GET)
    public String remove(@PathVariable("recept_id") Long recept_id) {
        LOG.info("removes recept with recept_id = " + recept_id);
        receptService.remove(recept_id);
        return "redirect:/recepti/all";
    }

    @RequestMapping("/get/{recept_id}")
    public String getRecept(Model model, @PathVariable("recept_id") Long recept_id) {
        LOG.info("getting recept with recept_id = " + recept_id);
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

        //rekonstruisiListuNamirnicaNaOsnovuIdova(recept);

        receptService.removeNamirnica(recept, namirnica_id);

        System.out.println("namirnica_id u removeNamirnica : " + namirnica_id);

        List<Namirnica> namirniceSelect = findNotInRecept(recept);
        model.addAttribute("namirniceSelect", namirniceSelect);

        model.addAttribute("recept", recept);
        return "receptForm";
    }


    @RequestMapping(params = { "addNamirnica" },  method = RequestMethod.POST)
    public String addNamirnica(Recept recept,
                               @RequestParam(value = "nid") Long namirnica_id,
                               @RequestParam(value = "kolicina") Integer kolicina, Model model){
        System.out.println("inside addNamirnica method klase ReceptiController, namirnica_id: " + namirnica_id + " kolicina: " + kolicina);
        System.out.println(recept);

       // rekonstruisiListuNamirnicaNaOsnovuIdova(recept);

        receptService.addNamirnica(recept, namirnica_id, kolicina);

        List<Namirnica> namirniceSelect = findNotInRecept(recept);
        model.addAttribute("namirniceSelect", namirniceSelect);
        System.out.println("namirniceSelect" + namirniceSelect);

        model.addAttribute("recept", recept);
        return "receptForm";
    }

    // vraca listu namirnica koje nisu u receptu
    // ako takvih nema vraca praznu listu--proveri ovo
    // bitno zbog toga sto brisanje namirnice radi prema id, ne mogu da dozvolim da imam u listi namirnica vise sa istim id-om
    private List<Namirnica> findNotInRecept(Recept recept) {
        List<Namirnica> namirniceAll = namirnicaService.findAll();

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
        if (namirniceRecept == null) {
            LOG.info("lista namirniceRecept je null");
        }

        if (namirniceRecept != null) {
            if (!namirniceRecept.isEmpty()) {
                for (Namirnica nRecept : namirniceRecept) {
                    if (nRecept.equals(namirnica)) {
                        inRecept = true;
                        break;
                    }
                }
            }
        }
        System.out.println("inRecept " + inRecept);
        return inRecept;
    }

}
