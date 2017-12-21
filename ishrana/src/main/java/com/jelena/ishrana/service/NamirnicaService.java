package com.jelena.ishrana.service;

import com.jelena.ishrana.model.Namirnica;

import java.util.List;

public interface NamirnicaService {
    List<Namirnica> findAll();
    List<Namirnica> findAll(int firstRow, int rowCount);
    List<Namirnica> findByCategory (String category);
    Namirnica findOne(Long id);
    Namirnica save(Namirnica namirnica);
    void remove(Long id) throws IllegalArgumentException;
    int count();
}
