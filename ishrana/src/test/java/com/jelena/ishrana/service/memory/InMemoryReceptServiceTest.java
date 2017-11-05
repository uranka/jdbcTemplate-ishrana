package com.jelena.ishrana.service.memory;

import com.jelena.ishrana.model.Namirnica;
import com.jelena.ishrana.model.Recept;
import com.jelena.ishrana.service.ReceptService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;


public class InMemoryReceptServiceTest {

    private ReceptService receptService;

    @Before
    public void setUp() {

        receptService = new InMemoryReceptService();// ovaj konstruktor puni mapu sa dva recepta

        Recept recept1 = new Recept();
        recept1.setRecept_id(10L);
        recept1.setNaziv("pita od jabuka");

        Recept recept2 = new Recept();
        recept2.setRecept_id(20L);
        recept2.setNaziv("mus od kupina treci");


        receptService.save(recept1);
        receptService.save(recept2);

    }

    @Test
    public void testFindOne() {
        Recept r1 = receptService.findOne(10L);
        Assert.assertNotNull(r1);
        Assert.assertEquals("pita od jabuka", r1.getNaziv());
    }

    @Test
    public void testFindAll() {
        List<Recept> recepti = receptService.findAll();
        // posto su recepti u mapi ne garantuje se redosled


        Assert.assertEquals(4, recepti.size()); // 2 iz konstruktora, dva iz setup-a
        // onda mi ni ne treba setup?

        Recept r1 = recepti.get(0);
        Recept r2 = recepti.get(1);
        Recept r3 = recepti.get(2);
        Recept r4 = recepti.get(3);

        // ako je u r1 recept 10L ocekujem da je to pita od jabuka
        // ako je u r2 recept 10L ocekujem da je to pita od jabuka itd.
        if (r1.getRecept_id().equals(10L)) {
            Assert.assertEquals("pita od jabuka", r1.getNaziv());
        } else if (r2.getRecept_id().equals(10L)) {
            Assert.assertEquals("pita od jabuka", r2.getNaziv());
        } else if (r3.getRecept_id().equals(10L)) {
            Assert.assertEquals("pita od jabuka", r3.getNaziv());
        }
        else if (r4.getRecept_id().equals(10L)) {
            Assert.assertEquals("pita od jabuka", r4.getNaziv());
        }

        // ako je u r1 recept 20L ocekujem da je to mus od kupina treci
        // ako je u r2 recept 20L ocekujem da je to mus od kupina treci itd.
        if (r1.getRecept_id().equals(20L)) {
            Assert.assertEquals("mus od kupina treci", r1.getNaziv());
        } else if (r2.getRecept_id().equals(20L)) {
            Assert.assertEquals("mus od kupina treci", r2.getNaziv());
        } else if (r3.getRecept_id().equals(20L)) {
            Assert.assertEquals("mus od kupina treci", r3.getNaziv());
        }
        else if (r4.getRecept_id().equals(20L)) {
            Assert.assertEquals("mus od kupina treci", r4.getNaziv());
        }
    }

    @Test
    public void testSave() {
        Recept r = new Recept();
        r.setNaziv("novi recept");
        // a da dodam i sve ostale komponente recepta i proverim sta je snimljeno?

        Recept saved = receptService.save(r);

        Assert.assertNotNull(saved.getRecept_id());
        Assert.assertEquals("novi recept", receptService.findOne(saved.getRecept_id()).getNaziv());
    }

    @Test
    public void testRemove() {
        // uveri se da imamo 10L i 20L recepte
        Assert.assertNotNull(receptService.findOne(10L));
        Assert.assertNotNull(receptService.findOne(20L));

        // ukloni recept 10L, ali ne i recept 20L
        receptService.remove(10L);

        // proveri da li 10L uklonjen, a 20L nije
        Assert.assertNull(receptService.findOne(10L));
        Assert.assertNotNull(receptService.findOne(20L));
    }

    // testira da li pokusaj uklanjanja recepta sa nepostojecim recept_id baca izuzetak
    @Test(expected = IllegalArgumentException.class)
    public void testRemoveIllegalArgument() {
        // uveri se da je recept_id nepostojeci
        Assert.assertNull(receptService.findOne(30L));
        // probaj da ga uklonis
        receptService.remove(30L);
    }


    @Test
    public void testAddNamirnica() {
        Recept r = receptService.findOne(10L); // ovo je prazan recept bez namirnice
        System.out.println(r);

        Assert.assertNotNull(r.getListaNamirnica());
        Assert.assertTrue(r.getListaNamirnica().size() == 0);

        // dodajem namirnicu i proveravam da li je velicina liste 1
        // i da li ima nesto na prvom mestu u listi

        Namirnica n1 = new Namirnica();
        n1.setNamirnica_id(1000L);
        n1.setNaziv("kisela jabuka");
        n1.setP(0);
        n1.setM(0);
        n1.setUh(12);
        n1.setKcal(40);
        n1.setKategorija("voće");
        System.out.println(n1);

        receptService.addNamirnica(r, n1, 100);

        Assert.assertNotNull(r.getListaNamirnica());
        Assert.assertTrue(r.getListaNamirnica().size() == 1);
        Assert.assertNotNull(r.getListaNamirnica().get(0));
    }

}
