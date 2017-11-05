package com.jelena.ishrana.service.db;

import com.jelena.ishrana.model.Namirnica;
import com.jelena.ishrana.repository.NamirnicaRepository;
import com.jelena.ishrana.service.NamirnicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InDbNamirnicaService implements NamirnicaService {

    // koristi NamirnicaRepository
    @Autowired
    private NamirnicaRepository namirnicaRepository;


    @Override
    public List<Namirnica> findAll() {
        return namirnicaRepository.findAll();
    }

    @Override
    public List<Namirnica> findByCategory(String category) {
        return null;
    }

    @Override
    public Namirnica findOne(Long id) {
        return null;
    }

    @Override
    public Namirnica save(Namirnica namirnica) {
        return null;
    }

    @Override
    public void remove(Long id) throws IllegalArgumentException {

    }
}
