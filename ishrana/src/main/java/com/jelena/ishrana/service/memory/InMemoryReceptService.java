package com.jelena.ishrana.service.memory;

import com.jelena.ishrana.model.Namirnica;
import com.jelena.ishrana.model.Recept;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class InMemoryReceptService implements ReceptService {

    private Map<Long, Recept> map = new HashMap<>();
    private final AtomicLong sequence = new AtomicLong(1);


    @Autowired
    private NamirnicaService namirnicaService;



    public InMemoryReceptService() {

        Namirnica n1 = new Namirnica();
        n1.setNaziv("kupina");
        n1.setKategorija("voće");
        n1.setKcal(35);
        n1.setM(0.1);
        n1.setP(0.5);
        n1.setUh(12);
        n1.setNamirnica_id(sequence.getAndIncrement()); // n1, id=1

        Namirnica n2 = new Namirnica();
        n2.setNaziv("jogurt 2.8% mm");
        n2.setKategorija("mleko i mlečni proizvodi");
        n2.setKcal(40);
        n2.setM(2.8);
        n2.setP(4.5);
        n2.setUh(5.0);
        n2.setNamirnica_id(sequence.getAndIncrement());  //n2, id =2


        Recept r1 = new Recept();
        r1.setNaziv("mus od kupina");
        r1.setVremeKuvanja(30);
        r1.setVremePripreme(5);
        List<Namirnica> listaNamirnica = new ArrayList<>();
        listaNamirnica.add(n1);
        listaNamirnica.add(n2);
        List<Integer> listaKolicina = new ArrayList<>();
        listaKolicina.add(500);
        listaKolicina.add(300);
        r1.setListaKolicina(listaKolicina);
        r1.setListaNamirnica(listaNamirnica);
        r1.setRecept_id(sequence.getAndIncrement());
        map.put(r1.getRecept_id(), r1);


        Recept r2 = new Recept();
        r2.setNaziv("mus od kupina drugi");
        r2.setVremeKuvanja(20);
        r2.setVremePripreme(10);
        List<Namirnica> listaNamirnica2 = new ArrayList<>();
        listaNamirnica2.add(n1);
        listaNamirnica2.add(n2);
        List<Integer> listaKolicina2 = new ArrayList<>();
        listaKolicina2.add(550);
        listaKolicina2.add(350);

        r2.setListaKolicina(listaKolicina2);
        r2.setListaNamirnica(listaNamirnica2);
        r2.setRecept_id(sequence.getAndIncrement());
        map.put(r2.getRecept_id(), r2);

    }

    @Override
    public List<Recept> findAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    public Recept findOne(Long id) {
        return map.get(id);
    }

    @Override
    public Recept save(Recept recept) {
        if (recept.getRecept_id() == 0) {
            recept.setRecept_id(sequence.getAndIncrement());
            System.out.println("**************");
        }
        map.put(recept.getRecept_id(), recept);
        return recept;
    }

    @Override
    public void remove(Long id) throws IllegalArgumentException {
        Recept removed = map.remove(id);
        if (removed == null) {
            throw new IllegalArgumentException("Removing unexisting recept with id=" + id);
        }

    }

    @Override
    public void removeNamirnica(Recept recept, Long namirnica_id) throws IllegalArgumentException{
        System.out.println("- - - - - inside removeNamirnica- - - - - ");
        if (namirnica_id == null || namirnica_id <= 0) {
            throw new IllegalArgumentException("non existing namirnica_id: " + namirnica_id);
        }

        List<Namirnica> listaNamirnica = recept.getListaNamirnica();

        System.out.println(listaNamirnica);
        for(int i = 0; i < listaNamirnica.size(); i++) {
            if (listaNamirnica.get(i).getNamirnica_id().equals(namirnica_id) ) {
                recept.getListaNamirnica().remove(i);
                recept.getListaKolicina().remove(i);
                break;
            }
        }
    }


    // dodaje u recept namirnicu zadatog namirnica_id i zadate kolicine
    @Override
    public void addNamirnica(Recept recept, Long namirnica_id, Integer kolicina) {
        System.out.println("++++++++ inside addNamirnica+++++++ ");
        if (recept.getListaNamirnica() != null) {
            recept.getListaNamirnica().add(namirnicaService.findOne(namirnica_id));
            recept.getListaKolicina().add(kolicina);
        }
        else {
            System.out.println("metoda addNamirnica : LISTA NAMIRNICA JE NULL"); // TEST
            List<Namirnica> namirnicaList = new ArrayList<>();
            namirnicaList.add(namirnicaService.findOne(namirnica_id));
            List<Integer> kolicinaList = new ArrayList<>();
            kolicinaList.add(kolicina);
            recept.setListaNamirnica(namirnicaList);
            recept.setListaKolicina(kolicinaList);
        }
    }


}
