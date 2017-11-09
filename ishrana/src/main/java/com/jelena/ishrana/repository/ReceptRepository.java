package com.jelena.ishrana.repository;


import com.jelena.ishrana.model.Namirnica;
import com.jelena.ishrana.model.Recept;

import java.util.List;

public interface ReceptRepository {
    List<Recept> findAll();
    Recept findOne(Long id);
    Recept save(Recept recept);
    void remove(Long id) throws IllegalArgumentException; // delete
    void removeNamirnica(Recept recept, Long namirnica_id) throws IllegalArgumentException;
    void addNamirnica(Recept recept, Namirnica namirnica, Integer kolicina);
}