package com.jelena.ishrana.service.memory;

import com.jelena.ishrana.model.Namirnica;
import com.jelena.ishrana.service.NamirnicaService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class InMemoryNamirnicaServiceTest {
    private NamirnicaService namirnicaService;

    @Before
    public void setUp() {
        namirnicaService = new InMemoryNamirnicaService(); // puni mapu namirnica sa tri namirnice
        Namirnica n1 = new Namirnica();  // jos jedna namirnica
        n1.setNamirnica_id(1000L);
        n1.setNaziv("kisela jabuka");
        n1.setP(0);
        n1.setM(0);
        n1.setUh(12);
        n1.setKcal(40);
        n1.setKategorija("voće");
        namirnicaService.save(n1);

        Namirnica n2 = new Namirnica(); // jos jedna namirnica
        n2.setNamirnica_id(2000L);
        n2.setNaziv("slatka jabuka");
        n2.setP(0);
        n2.setM(0);
        n2.setUh(15);
        n2.setKcal(48);
        n2.setKategorija("voće");
        namirnicaService.save(n2);
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
        Assert.assertEquals(5, namirnice.size()); // tri namirnice iz konstruktora i jos dvedodate u setup-u

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
        n.setNaziv("nova namirnica");
        n.setKategorija("meso");
        n.setP(20);
        n.setM(5);
        n.setUh(0);
        n.setKcal(300);
        n.setNamirnica_id(0L); // ja bih ovog da se resim, ali ne znam kako!!!!!!!!!!!!!!!!!!!!!
        Namirnica saved = namirnicaService.save(n);

        Assert.assertNotNull(saved.getNamirnica_id()); // da li moze sad ovo
        Assert.assertEquals("nova namirnica", namirnicaService.findOne(saved.getNamirnica_id()).getNaziv());
        Assert.assertEquals("meso", namirnicaService.findOne(saved.getNamirnica_id()).getKategorija());
        Assert.assertEquals(20, namirnicaService.findOne(saved.getNamirnica_id()).getP(), 0.01);
        Assert.assertEquals(5, namirnicaService.findOne(saved.getNamirnica_id()).getM(), 0.01);
        Assert.assertEquals(0, namirnicaService.findOne(saved.getNamirnica_id()).getUh(), 0.01);
        Assert.assertEquals(300, namirnicaService.findOne(saved.getNamirnica_id()).getKcal(), 0.01);
    }

    @Test
    public void testRemove() {
        // uveri se da imamo namirnice 1000L i 2000L
        Assert.assertNotNull(namirnicaService.findOne(1000L));
        Assert.assertNotNull(namirnicaService.findOne(2000L));

        // ukloni namirnicu 1000L, ali ne i namirnicu 2000L
        namirnicaService.remove(1000L);

        // proveri da li je 1000L uklonjena, a 2000L nije
        Assert.assertNull(namirnicaService.findOne(1000L));
        Assert.assertNotNull(namirnicaService.findOne(2000L));
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
