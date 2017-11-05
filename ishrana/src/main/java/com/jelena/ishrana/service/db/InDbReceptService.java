package com.jelena.ishrana.service.db;

import com.jelena.ishrana.model.Namirnica;
import com.jelena.ishrana.model.Recept;
import com.jelena.ishrana.service.ReceptService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InDbReceptService  implements ReceptService{


    // koristi ReceptRepository

    @Override
    public List<Recept> findAll() {
        return null;
    }

    @Override
    public Recept findOne(Long id) {
        return null;
    }

    @Override
    public Recept save(Recept recept) {
        return null;
    }

    @Override
    public void remove(Long id) throws IllegalArgumentException {

    }

    @Override
    public void removeNamirnica(Recept recept, Long namirnica_id) throws IllegalArgumentException {

    }

    @Override
    public void addNamirnica(Recept recept, Namirnica namirnica, Integer kolicina) {

    }
}
