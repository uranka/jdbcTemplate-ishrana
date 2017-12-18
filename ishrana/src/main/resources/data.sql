INSERT INTO `namirnice` (`naziv`, `kcal`, `p`, `m`, `uh`, `kategorija`) VALUES
('kupina', 45.0, 0.1, 0.1, 11, 'voće');
INSERT INTO `namirnice` (`naziv`, `kcal`, `p`, `m`, `uh`, `kategorija`) VALUES
('banana', '89.0', '1.1', '0.3', 22.8, 'voće');
INSERT INTO `namirnice` (`naziv`, `kcal`, `p`, `m`, `uh`, `kategorija`) VALUES
('jabuka', 52.0, 0, 0, 12, 'voće');
INSERT INTO `namirnice` (`naziv`, `kcal`, `p`, `m`, `uh`, `kategorija`) VALUES
('šljiva', 58, 0.1, 0.1, 14, 'voće');
INSERT INTO `namirnice` (`naziv`, `kcal`, `p`, `m`, `uh`, `kategorija`) VALUES
('jogurt 2,8%mm', 40.00, 4.2, 2.8, 5, 'mleko i mlečni proizvodi');
INSERT INTO `namirnice` (`naziv`, `kcal`, `p`, `m`, `uh`, `kategorija`) VALUES
('karfiol', 28, 2.1, 0.1, 4, 'povrće');

INSERT INTO recepti (`naziv`) VALUES
('mus od kupina');
INSERT INTO recepti (`naziv`, `vreme_pripreme`, `vreme_kuvanja`) VALUES
('kolač od šljiva', 20, 45);



INSERT INTO `recepti_namirnice` (`recept_id`, `namirnica_id`, `kolicina_namirnice`) VALUES
(1, 1, 100.0);
INSERT INTO `recepti_namirnice` (`recept_id`, `namirnica_id`, `kolicina_namirnice`) VALUES
(1, 5, 500.0);
INSERT INTO `recepti_namirnice` (`recept_id`, `namirnica_id`, `kolicina_namirnice`) VALUES
(2, 4, 500.0);
