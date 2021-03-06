package com.jelena.ishrana.controller;

import com.jelena.ishrana.exceptions.NoImageReaderException;
import com.jelena.ishrana.model.Namirnica;
import com.jelena.ishrana.model.Recept;
import com.jelena.ishrana.service.NamirnicaService;
import com.jelena.ishrana.service.ReceptService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;


@Controller
@RequestMapping("/recepti")
@MultipartConfig
public class ReceptiController {
    private static final Logger LOG = LoggerFactory.getLogger(ReceptiController.class);

    private byte[] tempSlika = new byte[102400];
    // proveri koliku sliku zelis, imas i u binu multipartResolver ogranicenje max upload size

    @Autowired
    private ReceptService receptService;

    @Autowired
    private NamirnicaService namirnicaService;

    @InitBinder
    public void initialiseBinder (WebDataBinder binder){
        binder.registerCustomEditor(byte[].class,"slika", new ByteArrayMultipartFileEditor());
    }

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

        if (recept.getSlika() != null) {
            tempSlika = Arrays.copyOf(recept.getSlika(), recept.getSlika().length);
        }

        List<Namirnica> namirniceSelect = findNotInRecept(recept);
        model.addAttribute("namirniceSelect", namirniceSelect);
        System.out.println("namirniceSelect" + namirniceSelect);

        model.addAttribute("recept", recept);

        if (recept.getSlika() != null) {
            model.addAttribute("msgSlikaExists", "ima slike");
        }

        return "receptForm";
    }

    @RequestMapping(params="save_button", method = RequestMethod.POST)
    public String save(Recept recept) throws IOException {
        LOG.info("saving recept");

        recept.setSlika(tempSlika);
        receptService.save(recept);
        tempSlika = null;

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

        List<Namirnica> namirniceSelect = namirnicaService.findAll(); // nov je recept pa sve namirnice nudim za izbor
        model.addAttribute("namirniceSelect", namirniceSelect);
        System.out.println("namirniceSelect" + namirniceSelect);

        LOG.info("sends new recept to form with data: " + recept);
        model.addAttribute("recept", recept);

        if (recept.getSlika() != null) {
            model.addAttribute("msgSlikaExists", "ima slike");
        }

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

        Map<String, Double> map = receptService.analizaRecepta(recept_id);
        model.addAttribute("map", map);

        return "recept";
    }

    // parameter removeNamirnica must be present in post request
    @RequestMapping(params="removeNamirnica", method = RequestMethod.POST)
    public String removeNamirnica(Recept recept,
                                  @RequestParam(value = "removeNamirnica") Long namirnica_id, Model model,
                                  @RequestParam(value="msgSlikaExists") String msgSlikaExists) {
        System.out.println("inside removeNamirnica method klase ReceptiController, namirnica_id: " + namirnica_id);
        System.out.println(recept);

        //rekonstruisiListuNamirnicaNaOsnovuIdova(recept);

        receptService.removeNamirnica(recept, namirnica_id);

        System.out.println("namirnica_id u removeNamirnica : " + namirnica_id);

        List<Namirnica> namirniceSelect = findNotInRecept(recept);
        model.addAttribute("namirniceSelect", namirniceSelect);

        model.addAttribute("recept", recept);
        model.addAttribute("msgSlikaExists", msgSlikaExists); // samo prenosim msgSlikaExists
        return "receptForm";
    }


    @RequestMapping(params = { "addNamirnica" },  method = RequestMethod.POST)
    public String addNamirnica(Recept recept,
                               @RequestParam(value = "nid") Namirnica namirnica, // calls StringToNamirnicaConverter
                               @RequestParam(value = "kolicina") Integer kolicina, Model model,
                               @RequestParam(value="msgSlikaExists") String msgSlikaExists){
        System.out.println("inside addNamirnica method klase ReceptiController");
        System.out.println(recept);

// ne dozvoli da se primi null za kolicinu, ili da addNamirnica kolicinu null pretvori u 0
// nemam nikakve validacije u projektu za sada pa je
// uradjeno ovo drugo i to za klasu JdbcReceptRepository koja radi sa bazom,
// isto bi trebalo uraditi i za in memory impl. klasa InMemoryReceptService

        receptService.addNamirnica(recept, namirnica, kolicina);

        List<Namirnica> namirniceSelect = findNotInRecept(recept);
        model.addAttribute("namirniceSelect", namirniceSelect);
        System.out.println("namirniceSelect" + namirniceSelect);

        model.addAttribute("recept", recept);
        model.addAttribute("msgSlikaExists", msgSlikaExists); // samo prenosim msgSlikaExists
        return "receptForm";
    }

    // vraca listu namirnica koje nisu u receptu
    // ako takvih nema vraca praznu listu
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


    @RequestMapping("/photo/{recept_id}")
    public void showReceptImage(@PathVariable("recept_id") Long recept_id, HttpServletResponse response){
        System.out.println("prikazujem sliku recepta");
        Recept recept = receptService.findOne(recept_id);

        int length = recept.getSlika().length;
        response.setContentType("image/jpg");
        response.setContentLength(length);
        try {
            response.getOutputStream().write(recept.getSlika());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/tempSlika")
    public void showSlika(HttpServletResponse response){

        int length = tempSlika.length;
        response.setContentType("image/jpg");
        response.setContentLength(length);
        try {
            response.getOutputStream().write(tempSlika);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // parameter removeNamirnica must be present in post request
    @RequestMapping(params="removeSlika", method = RequestMethod.POST)
    public String removeSlika( Recept recept, @RequestParam(value = "removeSlika") Long recept_id, Model model) {
        System.out.println("inside removeSlika method klase ReceptiController, recept_id: " + recept_id);

        //receptService.removeSlika(recept_id);
        tempSlika = null;

        List<Namirnica> namirniceSelect = findNotInRecept(recept);
        model.addAttribute("namirniceSelect", namirniceSelect);

        model.addAttribute("recept", recept);
       /* model.addAttribute("msgSlikaRemoved", "Slika je obrisana");*/
        return "receptForm";
    }

    @RequestMapping(params="addSlika", method = RequestMethod.POST)
    public String addSlika(Recept recept, @RequestParam(value = "addSlika") Long recept_id, Model model) {
        System.out.println("inside addSlika method klase ReceptiController, recept_id: " + recept_id);

        //receptService.addSlika(recept_id, recept.getSlika()); // ako je recept_id nepostojeci jer
        // je potpuno nov i nije jos snimljen u bazu i nema validnog recept_id
        System.out.println("kontrola recept_id " + recept_id);
        if (isJpeg(recept.getSlika())) {
            tempSlika = Arrays.copyOf(recept.getSlika(), recept.getSlika().length);
            model.addAttribute("msgSlikaExists", "ima slike");
        }
        else{
            model.addAttribute("msgSlikaNotExist", "Fajl mora biti slika tipa jpeg!");
        }

        List<Namirnica> namirniceSelect = findNotInRecept(recept);
        model.addAttribute("namirniceSelect", namirniceSelect);

        model.addAttribute("recept", recept);
        return "receptForm";
    }


    // ispitivanje da li je fajl slika tipa jpeg staviti u kontroler
    // ako fajl nije slika tipa jpeg slika se ni ne salje na snimanje
    // TODO provere na duzinu fajla, a i nemam nikakav validation podataka u formama
    private boolean isImageFormatJpeg(InputStream is) throws IOException, NoImageReaderException {
        ImageInputStream iis = ImageIO.createImageInputStream(is);
        // get all currently registered readers that recognize the image format
        Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
        if (!iter.hasNext()) {
            throw new NoImageReaderException ("No readers found says isImageFormatJpeg function!");
        }

        System.out.println("Readers found");
        // get the first reader
        ImageReader reader = iter.next();
        String format = reader.getFormatName();
        //iis.close();
        return format.equalsIgnoreCase("JPEG")? true : false;
    }

    private boolean isJpeg(byte[] slika) {
        boolean isJpeg = false;
        if (slika != null) { // imam neki niz bajtova za sliku

            // utvrdjivanje da li je fajl slika tipa jpg
            try {
                InputStream fileContent = new ByteArrayInputStream(slika);
                isJpeg = isImageFormatJpeg(fileContent); // isImageFormatJpeg pokvari input stream
                //fileContent = new ByteArrayInputStream(recept.getSlika());
                System.out.println("ending try jpeg");
            } catch (NoImageReaderException e) {
                System.out.println(e.getMessage());
                System.out.println("ending no image reader catch jpeg");
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return isJpeg;
    }


    @RequestMapping("/uploadStatus")
    public String uploadStatus() {
        return "uploadStatus";
    }

}
