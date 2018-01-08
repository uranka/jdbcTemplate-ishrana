package com.jelena.ishrana.service.db;

import com.jelena.ishrana.model.Namirnica;
import com.jelena.ishrana.model.Recept;
import com.jelena.ishrana.repository.ReceptRepository;
import com.jelena.ishrana.service.ReceptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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

    @Override
    public Map<String, Double> analizaRecepta(Long id) {
        return receptRepository.analizaRecepta(id);
    }

    @Override
    public void removeSlika(Long recept_id){
        receptRepository.removeSlika(recept_id);
    }

    @Override
    public void addSlika(Long recept_id, byte[] slika) {
        receptRepository.addSlika(recept_id, slika);
    }
}
