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
    public List<Namirnica> findAll(int firstRow, int rowCount) {
        return namirnicaRepository.findAll(firstRow, rowCount);
    }

    @Override
    public List<Namirnica> findByCategory(String category) {
        return namirnicaRepository.findByCategory(category);
    }

    @Override
    public List<Namirnica> findByCategory(String category, int firstRow, int rowCount) {
        return namirnicaRepository.findByCategory(category, firstRow, rowCount);
    }

    @Override
    public Namirnica findOne(Long id) {
        return namirnicaRepository.findOne(id);
    }

    @Override
    public Namirnica save(Namirnica namirnica) {
        return namirnicaRepository.save(namirnica);
    }

    @Override
    public void remove(Long id) throws IllegalArgumentException {
        namirnicaRepository.remove(id);
    }

    @Override
    public int count() {
        return namirnicaRepository.count();
    }

    @Override
    public int count(String category) {
        return namirnicaRepository.count(category);
    }
}
