INSERT INTO `namirnice` (`naziv`, `kcal`, `p`, `m`, `uh`, `kategorija`) VALUES
  ('jabuka', 52.0, 0.3, 0.2, 13.8, 'voće');
INSERT INTO `namirnice` (`naziv`, `kcal`, `p`, `m`, `uh`, `kategorija`) VALUES
  ('banana', 89.0, 1.1, 0.3, 22.8, 'voće');
INSERT INTO `namirnice` (`naziv`, `kcal`, `p`, `m`, `uh`, `kategorija`) VALUES
  ('pomorandža', 47.0, 0.9, 0.1, 11.7, 'voće');
INSERT INTO `namirnice` (`naziv`, `kcal`, `p`, `m`, `uh`, `kategorija`) VALUES
  ('grožđe', 67.0, 0.6, 0.4, 17.1, 'voće');
INSERT INTO `namirnice` (`naziv`, `kcal`, `p`, `m`, `uh`, `kategorija`) VALUES
  ('breskva', 39.0, 0.9, 0.3, 9.9, 'voće');
INSERT INTO `namirnice` (`naziv`, `kcal`, `p`, `m`, `uh`, `kategorija`) VALUES
  ('dinja', 34.0, 0.84, 0.19, 8.16, 'voće');
INSERT INTO `namirnice` (`naziv`, `kcal`, `p`, `m`, `uh`, `kategorija`) VALUES
  ('jagode', 33.0, 0.67, 0.3, 7.68, 'voće');
INSERT INTO `namirnice` (`naziv`, `kcal`, `p`, `m`, `uh`, `kategorija`) VALUES
  ('kajsija', 48.0, 1.4, 0.39, 11.12, 'voće');
INSERT INTO `namirnice` (`naziv`, `kcal`, `p`, `m`, `uh`, `kategorija`) VALUES
  ('kupina', 43.0, 1.39, 0.49, 9.61, 'voće');

INSERT INTO `namirnice` (`naziv`, `kcal`, `p`, `m`, `uh`, `kategorija`) VALUES
  ('blitva', 19.0, 1.88, 0.08, 4.13, 'povrće');
INSERT INTO `namirnice` (`naziv`, `kcal`, `p`, `m`, `uh`, `kategorija`) VALUES
  ('brokoli', 34.0, 2.8, 0.4, 6.6, 'povrće');
INSERT INTO `namirnice` (`naziv`, `kcal`, `p`, `m`, `uh`, `kategorija`) VALUES
  ('cvekla', 43.0, 1.61, 0.17, 9.56, 'povrće');
INSERT INTO `namirnice` (`naziv`, `kcal`, `p`, `m`, `uh`, `kategorija`) VALUES
  ('grašak', 81.0, 5.42, 0.4, 14.45, 'povrće');
INSERT INTO `namirnice` (`naziv`, `kcal`, `p`, `m`, `uh`, `kategorija`) VALUES
  ('karfiol', 25.0, 1.98, 0.1, 5.3, 'povrće');


INSERT INTO `namirnice` (`naziv`, `kcal`, `p`, `m`, `uh`, `kategorija`) VALUES
  ('mleko 2.8%mm', 56.0, 3.1, 2.8, 4.5, 'mleko i mlečni proizvodi');
INSERT INTO `namirnice` (`naziv`, `kcal`, `p`, `m`, `uh`, `kategorija`) VALUES
  ('mleko 3.2%mm', 60.0, 3.25, 3.25, 4.5, 'mleko i mlečni proizvodi');
INSERT INTO `namirnice` (`naziv`, `kcal`, `p`, `m`, `uh`, `kategorija`) VALUES
  ('jogurt 2.8%mm', 52.8, 2.9, 2.8, 4, 'mleko i mlečni proizvodi');
INSERT INTO `namirnice` (`naziv`, `kcal`, `p`, `m`, `uh`, `kategorija`) VALUES
  ('jogurt 3.2%mm', 61.0, 3.5, 3.2, 4.7, 'mleko i mlečni proizvodi');
INSERT INTO `namirnice` (`naziv`, `kcal`, `p`, `m`, `uh`, `kategorija`) VALUES
  ('ABC sveži krem sir', 241.0, 6.5, 22.5, 3.0, 'mleko i mlečni proizvodi');
INSERT INTO `namirnice` (`naziv`, `kcal`, `p`, `m`, `uh`, `kategorija`) VALUES
  ('maslac', 743.0, 0.65, 82.0, 0.66, 'mleko i mlečni proizvodi');


INSERT INTO `namirnice` (`naziv`, `kcal`, `p`, `m`, `uh`, `kategorija`) VALUES
  ('govedina but', 179.0, 19.6, 11.2, 0, 'meso');
INSERT INTO `namirnice` (`naziv`, `kcal`, `p`, `m`, `uh`, `kategorija`) VALUES
  ('govedina srednje masna', 214.0, 18.8, 15.4, 0, 'meso');


INSERT INTO `namirnice` (`naziv`, `kcal`, `p`, `m`, `uh`, `kategorija`) VALUES
  ('hleb ražani', 258.0, 8.5, 3.3, 48.3, 'žitarice');
INSERT INTO `namirnice` (`naziv`, `kcal`, `p`, `m`, `uh`, `kategorija`) VALUES
  ('hleb pšenični beli', 267.0, 8.2, 3.6, 49.5, 'žitarice');
INSERT INTO `namirnice` (`naziv`, `kcal`, `p`, `m`, `uh`, `kategorija`) VALUES
  ('hleb pšenični crni', 247.0, 13.0, 3.3, 41.3, 'žitarice');
INSERT INTO `namirnice` (`naziv`, `kcal`, `p`, `m`, `uh`, `kategorija`) VALUES
  ('ovsene pahuljice', 389.0, 16.9, 6.9, 66.3, 'žitarice');
INSERT INTO `namirnice` (`naziv`, `kcal`, `p`, `m`, `uh`, `kategorija`) VALUES
  ('pirinač beli', 365.0, 7.1, 0.7, 79.9, 'žitarice');



INSERT INTO recepti (`naziv`) VALUES
('mus od jabuka');
INSERT INTO recepti (`naziv`, `vreme_pripreme`, `vreme_kuvanja`) VALUES
('kolač od banana', 20, 45);



INSERT INTO `recepti_namirnice` (`recept_id`, `namirnica_id`, `kolicina_namirnice`) VALUES
(1, 1, 100.0);
INSERT INTO `recepti_namirnice` (`recept_id`, `namirnica_id`, `kolicina_namirnice`) VALUES
(1, 17, 500.0);
INSERT INTO `recepti_namirnice` (`recept_id`, `namirnica_id`, `kolicina_namirnice`) VALUES
(2, 2, 500.0);
