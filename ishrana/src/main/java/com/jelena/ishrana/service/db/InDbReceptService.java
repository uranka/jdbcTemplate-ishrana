package com.jelena.ishrana.service.db;

import com.jelena.ishrana.model.Namirnica;
import com.jelena.ishrana.model.Recept;
import com.jelena.ishrana.repository.ReceptRepository;
import com.jelena.ishrana.service.ReceptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InDbReceptService  implements ReceptService{


    // koristi ReceptRepository
    @Autowired
    private ReceptRepository receptRepository;

    @Override
    public List<Recept> findAll() {
        return receptRepository.findAll();
    }

    @Override
    public Recept findOne(Long id) {
        return receptRepository.findOne(id);
    }

    @Override
    public Recept save(Recept recept) {
        return receptRepository.save(recept);
    }

    @Override
    public void remove(Long id) throws IllegalArgumentException {
        receptRepository.remove(id);
    }

    @Override
    public void removeNamirnica(Recept recept, Long namirnica_id) throws IllegalArgumentException {
        receptRepository.removeNamirnica(recept, namirnica_id);
    }

    @Override
    public void addNamirnica(Recept recept, Namirnica namirnica, Integer kolicina) {
        receptRepository.addNamirnica(recept, namirnica, kolicina);
    }
}
