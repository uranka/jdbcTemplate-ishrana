package com.jelena.ishrana.repository;

import com.jelena.ishrana.model.Namirnica;

import java.util.List;

public interface NamirnicaRepository {
    List<Namirnica> findAll();
    List<Namirnica> findAll(int firstRow, int rowCount);
    List<Namirnica> findByCategory (String category);
    List<Namirnica> findByCategory(String category, int firstRow, int rowCount);
    Namirnica findOne(Long id);
    Namirnica save(Namirnica namirnica);
    void remove(Long id) /*throws IllegalArgumentException*/; // ovo ce biti delete
    int count();
    int count(String category);
}
