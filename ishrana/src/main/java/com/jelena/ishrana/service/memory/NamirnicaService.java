package com.jelena.ishrana.service.memory;

import com.jelena.ishrana.model.Namirnica;

import java.util.List;

public interface NamirnicaService {
    List<Namirnica> findAll();
    List<Namirnica> findByCategory (String category);
    Namirnica findOne(Long id);
}
