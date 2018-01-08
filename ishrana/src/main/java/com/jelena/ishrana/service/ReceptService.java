package com.jelena.ishrana.service;

import com.jelena.ishrana.model.Namirnica;
import com.jelena.ishrana.model.Recept;

import java.util.List;
import java.util.Map;

public interface ReceptService {
    List<Recept> findAll();
    Recept findOne(Long id);
    Recept save(Recept recept);
    void remove(Long id) throws IllegalArgumentException;
    void removeNamirnica(Recept recept, Long namirnica_id) throws IllegalArgumentException;
    void addNamirnica(Recept recept, Namirnica namirnica, Integer kolicina);
    Map<String, Double> analizaRecepta(Long id);
    void removeSlika(Long recept_id);
    void addSlika (Long recept_id, byte[] slika); // sta ako je recept_id nepostojeci ili null

}
