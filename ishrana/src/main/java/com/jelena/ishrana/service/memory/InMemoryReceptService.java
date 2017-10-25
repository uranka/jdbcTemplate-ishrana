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

//    @Autowired
//    NamirnicaService namirnicaService;

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

// trebalo bi namirnice da uzmem iz InMemoryNamirnicaService, ali kako
// sa InMemoryNamirnicaService findAll() metodom
// znaci sa Autowire ubaci ovde NamirnicaService namirnicaService
        //List<Namirnica> lst = namirnicaService.findAll();

        Recept r1 = new Recept();
        r1.setNaziv("mus od kupina");
        r1.setVremeKuvanja(30);
        r1.setVremePripreme(5);
        Map<Namirnica, Integer> map1 = new HashMap<>();
        map1.put(n1, 500);
        map1.put(n2, 300);
        r1.setMapaNamirnica(map1);
        r1.setRecept_id(sequence.getAndIncrement());  //r1, id =3
        map.put(r1.getRecept_id(), r1);

/*        Recept r2 = new Recept();
        r2.setNaziv("kolač od šljiva");
        r2.setVremeKuvanja(45);
        r2.setVremePripreme(30);
        r2.setRecept_id(sequence.getAndIncrement());
        map.put(r2.getRecept_id(), r2);*/

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
}
