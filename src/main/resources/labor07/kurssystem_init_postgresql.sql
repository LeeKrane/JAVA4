/*
 * Datenbank fuer ein Kurssystem
 */

DROP TABLE IF EXISTS kurs_kunde;
DROP TABLE IF EXISTS kurs;
DROP TABLE IF EXISTS kurstyp;
DROP TABLE IF EXISTS kunde;
DROP TABLE IF EXISTS dozent;


/*
 * Tabelle 'dozent'
 */

CREATE TABLE dozent (
  id            serial NOT NULL,
  zuname 	    varchar(25),
  vorname 	    varchar(25),
  CONSTRAINT 	dozent_pkey PRIMARY KEY (id)
);


/*
 * Daten für 'dozent'
 */

INSERT INTO dozent (zuname, vorname) VALUES ('Leutner','Brigitte');
INSERT INTO dozent (zuname, vorname) VALUES ('Gernhardt','Wolfgang');
INSERT INTO dozent (zuname, vorname) VALUES ('Weizenbaum','Josephine');
INSERT INTO dozent (zuname, vorname) VALUES ('Ludwig','Luigi');
INSERT INTO dozent (zuname, vorname) VALUES ('Mergel','Boris');
INSERT INTO dozent (zuname, vorname) VALUES ('Duffing','Julienne');
INSERT INTO dozent (zuname, vorname) VALUES ('Meyer','Julius');


/*
 * Tabelle kurstyp
 */

CREATE TABLE kurstyp (
  id            character(1) NOT NULL,
  bezeichnung   varchar(100),
  CONSTRAINT    kurstyp_pkey PRIMARY KEY (id)
);


/*
 * Daten für 'kurstyp'
 */

INSERT INTO kurstyp(id, bezeichnung) VALUES ('P', 'Programmierung');
INSERT INTO kurstyp(id, bezeichnung) VALUES ('S', 'Skriptspachen');
INSERT INTO kurstyp(id, bezeichnung) VALUES ('W', 'Webtechnologien');


/*
 * Tabelle 'kurs'
 */

CREATE TABLE kurs (
  id                serial NOT NULL,
  typ               character(1),
  doz_id            integer,             
  bezeichnung       varchar(100),
  beginndatum       date,
  CONSTRAINT        kurs_pkey PRIMARY KEY (id),
  CONSTRAINT        kurs_fk_doz FOREIGN KEY (doz_id) REFERENCES dozent(id),
  CONSTRAINT        kurs_fk_typ FOREIGN KEY (typ) REFERENCES kurstyp(id)
);


/*
 * Daten für 'kurs'
 */

INSERT INTO kurs (typ, doz_id, bezeichnung, beginndatum) 
VALUES ('P',2 ,'Objektorientierte Programmierung mit Java','2010-08-27');
INSERT INTO kurs (typ, doz_id, bezeichnung, beginndatum)
VALUES ('S',3,'JavaScript','2010-06-29');
INSERT INTO kurs (typ, doz_id, bezeichnung, beginndatum)
VALUES ('P',2,'JDBC','2010-06-30');
INSERT INTO kurs (typ, doz_id, bezeichnung, beginndatum) 
VALUES ('W',4,'HTML','2010-07-13');
INSERT INTO kurs (typ, doz_id, bezeichnung, beginndatum)
VALUES ('P',5,'GUI-Programmierung mit Java','2010-10-09');
INSERT INTO kurs (typ, doz_id, bezeichnung, beginndatum)
VALUES ('W',4,'Servlets','2010-10-10');

/*
 * Tabelle 'kunde'
 */

CREATE TABLE kunde (
  id 	            serial NOT NULL,
  zuname		    varchar(25),
  vorname 	        varchar(25),
  CONSTRAINT 		kunde_pkey PRIMARY KEY (id)
);


/*
 * Daten für 'kunde'
 */

INSERT INTO kunde (zuname, vorname) VALUES ('Bauer','Hannes');
INSERT INTO kunde (zuname, vorname) VALUES ('Khan','Dschingis');
INSERT INTO kunde (zuname, vorname) VALUES ('Schmidt','Lothar');
INSERT INTO kunde (zuname, vorname) VALUES ('Kunze','Sieglinde');
INSERT INTO kunde (zuname, vorname) VALUES ('Hintze','Franz');
INSERT INTO kunde (zuname, vorname) VALUES ('Kaiser','Leo');

/*
 *   Tabelle kurs_kunde
 */

CREATE TABLE  kurs_kunde (
  kunde_id     integer NOT NULL,
  kurs_id      integer NOT NULL,
  CONSTRAINT   kurs_kunde_pkey PRIMARY KEY (kunde_id, kurs_id),
  CONSTRAINT   kurs_kunde_fk1 FOREIGN KEY (kunde_id)
               REFERENCES kunde(id),
  CONSTRAINT   kurs_kunde_fk2 FOREIGN KEY (kurs_id)
               REFERENCES kurs(id)
);


/*
 *   Daten für 'kurs_kunde'
 */

INSERT INTO kurs_kunde VALUES (6,1);
INSERT INTO kurs_kunde VALUES (3,2);
INSERT INTO kurs_kunde VALUES (3,1);
INSERT INTO kurs_kunde VALUES (4,1);
INSERT INTO kurs_kunde VALUES (5,3);
INSERT INTO kurs_kunde VALUES (5,2);
INSERT INTO kurs_kunde VALUES (1,3);
INSERT INTO kurs_kunde VALUES (4,2);
