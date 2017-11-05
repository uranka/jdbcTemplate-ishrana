package com.jelena.ishrana.service.memory;

import com.jelena.ishrana.model.Namirnica;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class InMemoryNamirnicaServiceTest {
    private NamirnicaService namirnicaService; // NETESTIRANA KLASA

    @Before
    public void setUp() {
        namirnicaService = new InMemoryNamirnicaService(); // puni mapu namirnica sa tri namirnice
        Namirnica n1 = new Namirnica(); // i ovo bi bila jos jedna namirnica
        n1.setNamirnica_id(1000L);
        n1.setNaziv("kisela jabuka");
        n1.setP(0);
        n1.setM(0);
        n1.setUh(12);
        n1.setKcal(40);
        n1.setKategorija("voće");
        // treba snimiti ovu namirnicu, logicno je da je prvo pisan test za namirnicaService
        namirnicaService.save(n1);
    }

    @Test
    public void testFindOne() {
        Namirnica n1 = namirnicaService.findOne(1000L);
        Assert.assertNotNull(n1);
        Assert.assertEquals("kisela jabuka", n1.getNaziv());
    }

    @Test
    public void testFindAll() {
        List<Namirnica> namirnice = namirnicaService.findAll();
        Assert.assertEquals(4, namirnice.size()); // tri iz konstruktora i jedna dodata

        Namirnica n1 = namirnice.get(0);
        Namirnica n2 = namirnice.get(1);
        Namirnica n3 = namirnice.get(2);
        Namirnica n4 = namirnice.get(3);

        if (n1.getNamirnica_id().equals(1000L)) {
            Assert.assertEquals("kisela jabuka", n1.getNaziv());
        } else if (n2.getNamirnica_id().equals(1000L)) {
            Assert.assertEquals("kisela jabuka", n2.getNaziv());
        } else if (n3.getNamirnica_id().equals(1000L)) {
            Assert.assertEquals("kisela jabuka", n3.getNaziv());
        } else if (n4.getNamirnica_id().equals(1000L)){
            Assert.assertEquals("kisela jabuka", n4.getNaziv());
        }

        // itd. za druge id-ove
    }

    @Test
    public void testSave() {
        Namirnica n = new Namirnica();
        n.setNaziv("nova namirnica");
        n.setNamirnica_id(0L); // ja bih ovog da se resim, ali ne znam kako!!!!!!!!!!!!!!!!!!!!!
        Namirnica saved = namirnicaService.save(n);

        Assert.assertNotNull(saved.getNamirnica_id());
        Assert.assertEquals("nova namirnica", namirnicaService.findOne(saved.getNamirnica_id()).getNaziv());
    }

    @Test
    public void testRemove() {
        // uveri se da imamo namirnice 1000L, mozgal bih dodati jos jednu
        // uveri se da je uklonjena 1000L, a da nije uklonjena ta druga koju treba da dodam

        Assert.assertNotNull(namirnicaService.findOne(1000L));

        // ukloni namirnicu 1000L, ali ne i namirnicu.....
        namirnicaService.remove(1000L);

        // proveri da li 1000L uklonjen, a ona druga nije....
        Assert.assertNull(namirnicaService.findOne(1000L));
    }

    // testira da li pokusaj uklanjanja namirnice sa nepostojecim id baca izuzetak
    @Test(expected = IllegalArgumentException.class)
    public void testRemoveIllegalArgument() {
        // uveri se da je namirnica sa namirnica_id=30L nepostojeca
        Assert.assertNull(namirnicaService.findOne(30L));
        // probaj da je uklonis
        namirnicaService.remove(30L);
    }


}
