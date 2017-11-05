package com.jelena.ishrana.service.memory;

import com.jelena.ishrana.model.Namirnica;
import com.jelena.ishrana.model.Recept;

import java.util.List;

public interface ReceptService {
    List<Recept> findAll();
    Recept findOne(Long id);
    Recept save(Recept recept);
    void remove(Long id) throws IllegalArgumentException;
    void removeNamirnica(Recept recept, Long namirnica_id) throws IllegalArgumentException;
    void addNamirnica(Recept recept, Namirnica namirnica, Integer kolicina);
   // void addNamirnica(Recept recept, Long namirnica_id, Integer kolicina);

}
