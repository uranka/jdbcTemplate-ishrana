DROP DATABASE IF EXISTS ishrana2;
CREATE DATABASE ishrana2 CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE ishrana2;

CREATE TABLE namirnice (
namirnica_id INT NOT NULL AUTO_INCREMENT,
naziv VARCHAR(40) NOT NULL,
kcal DOUBLE(4,1) NOT NULL,
p DOUBLE(4,1) NOT NULL,
m DOUBLE(4,1) NOT NULL,
uh DOUBLE(4,1) NOT NULL,
kategorija VARCHAR(40) NOT NULL,
PRIMARY KEY (`namirnica_id`)
);

CREATE TABLE recepti (
recept_id INT NOT NULL AUTO_INCREMENT,
naziv VARCHAR(40) NOT NULL,
uputstvo TEXT NULL,
slika BLOB NULL,
vreme_pripreme SMALLINT NULL,
vreme_kuvanja SMALLINT NULL,
PRIMARY KEY (`recept_id`)
);

CREATE TABLE recepti_namirnice (
recept_id INT NOT NULL,
namirnica_id INT NOT NULL,
kolicina_namirnice DOUBLE(5,1) NOT NULL,
PRIMARY KEY (`recept_id`,`namirnica_id`),  
FOREIGN KEY (recept_id) REFERENCES recepti(recept_id),
FOREIGN KEY (namirnica_id) REFERENCES namirnice(namirnica_id)
);
