package com.jelena.ishrana.repository;

import com.jelena.ishrana.model.Namirnica;

import java.util.List;

public interface NamirnicaRepository {
    List<Namirnica> findAll();
    List<Namirnica> findByCategory (String category);
    Namirnica findOne(Long id);
    Namirnica save(Namirnica namirnica);
    void remove(Long id) /*throws IllegalArgumentException*/; // ovo ce biti delete
}
