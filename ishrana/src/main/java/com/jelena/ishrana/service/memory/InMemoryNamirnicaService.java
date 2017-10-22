package com.jelena.ishrana.service.memory;

import com.jelena.ishrana.model.Namirnica;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        return null;
    }
}
