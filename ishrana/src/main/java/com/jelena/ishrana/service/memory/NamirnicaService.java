package com.jelena.ishrana.service.memory;

import com.jelena.ishrana.model.Namirnica;

import java.util.List;

/**
 * Created by Win10 on 10/22/2017.
 */
public interface NamirnicaService {
    List<Namirnica> findAll();
    List<Namirnica> findByCategory (String category);
}
