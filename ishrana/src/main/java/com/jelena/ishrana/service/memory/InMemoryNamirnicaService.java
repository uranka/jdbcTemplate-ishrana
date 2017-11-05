package com.jelena.ishrana.service.memory;

import com.jelena.ishrana.model.Namirnica;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class InMemoryNamirnicaService implements NamirnicaService {

    private Map<Long, Namirnica> map = new HashMap<>();
    private final AtomicLong sequence = new AtomicLong(1);

    public InMemoryNamirnicaService() {
        Namirnica n1 = new Namirnica();
        n1.setNaziv("kupina");
        n1.setKategorija("voće");
        n1.setKcal(35);
        n1.setM(0.1);
        n1.setP(0.5);
        n1.setUh(12);
        n1.setNamirnica_id(sequence.getAndIncrement());
        map.put(n1.getNamirnica_id(), n1);

        Namirnica n2 = new Namirnica();
        n2.setNaziv("jogurt 2.8% mm");
        n2.setKategorija("mleko i mlečni proizvodi");
        n2.setKcal(40);
        n2.setM(2.8);
        n2.setP(4.5);
        n2.setUh(5.0);
        n2.setNamirnica_id(sequence.getAndIncrement());
        map.put(n2.getNamirnica_id(), n2);

        Namirnica n3 = new Namirnica();
        n3.setNaziv("jabuka");
        n3.setKategorija("voće");
        n3.setKcal(48);
        n3.setM(0.8);
        n3.setP(0.5);
        n3.setUh(12.0);
        n3.setNamirnica_id(sequence.getAndIncrement());
        map.put(n3.getNamirnica_id(), n3);
    }

    @Override
    public List<Namirnica> findAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    public List<Namirnica> findByCategory(String category) {

        List<Namirnica> lst = new ArrayList<>();

        Set<Map.Entry<Long, Namirnica>> entries = map.entrySet();
        for ( Map.Entry<Long, Namirnica> currentEntry : entries ){
            Long key = currentEntry.getKey();
            Namirnica value = currentEntry.getValue();
            System.out.println("key=" + key + ", value=" + value);
            if (value.getKategorija().equalsIgnoreCase(category)) {
                lst.add(value);
            }
        }
        return lst;
    }

    @Override
    public Namirnica findOne(Long id) {
        System.out.println("inside findOne namirnice");
        return map.get(id);
    } // sta ako nema trazene namirnice?

    // if namirnica does not have id give it id and save it
    // otherwise just save it with its id
    // sad to nema id sam prebacila u id je nula zbog problema sa slanjem--- MORAS RESITI OVO DA MOZE SA null da se radi!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    // forme u kojoj je id na null
    @Override
    public Namirnica save(Namirnica namirnica) {
        //if (namirnica.getNamirnica_id() == null) {
        if (namirnica.getNamirnica_id() == 0) { // NECU OVAKO,  hocu da stize namirnica sa id=null
            namirnica.setNamirnica_id(sequence.getAndIncrement());
            System.out.println("**************");
        }
        map.put(namirnica.getNamirnica_id(), namirnica);
        return namirnica;
    }

    @Override
    public void remove(Long id) throws IllegalArgumentException {
        Namirnica removed = map.remove(id);
        if (removed == null) {
            throw new IllegalArgumentException("Removing unexisting namirnica with id=" + id);
        }
    }
}
