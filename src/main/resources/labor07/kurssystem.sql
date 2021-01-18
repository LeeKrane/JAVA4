/*
 * Datenbank fuer ein Kurssystem
 */

/*
DROP TABLE kurs_kunde;
DROP TABLE kurs;
DROP TABLE kurstyp;
DROP TABLE kunde;
DROP TABLE dozent;
*/


/*
 * Tabelle 'dozent'
 */
CREATE TABLE dozent (
  doz_id        serial NOT NULL,
  doz_zuname 	varchar(25),
  doz_vorname 	varchar(25),
  CONSTRAINT 	dozent_pkey PRIMARY KEY (doz_id)
);


/*
 * Daten für 'dozent'
 */

INSERT INTO dozent (doz_zuname, doz_vorname) VALUES ('Leutner','Brigitte');
INSERT INTO dozent (doz_zuname, doz_vorname) VALUES ('Gernhardt','Wolfgang');
INSERT INTO dozent (doz_zuname, doz_vorname) VALUES ('Weizenbaum','Josephine');
INSERT INTO dozent (doz_zuname, doz_vorname) VALUES ('Ludwig','Luigi');
INSERT INTO dozent (doz_zuname, doz_vorname) VALUES ('Mergel','Boris');
INSERT INTO dozent (doz_zuname, doz_vorname) VALUES ('Duffing','Julienne');
INSERT INTO dozent (doz_zuname, doz_vorname) VALUES ('Meyer','Julius');


/*
 * Tabelle 'kurs'
 */

CREATE TABLE kurs (
  kurs_id           serial NOT NULL,
/* kurs_typ          character(1),  */
  kurs_doz_id       integer,             
  kurs_bezeichnung  varchar(100),
  kurs_beginndatum  date,
  CONSTRAINT        kurs_pkey PRIMARY KEY (kurs_id),
  CONSTRAINT        kurs_fk_doz FOREIGN KEY (kurs_doz_id)
                    REFERENCES dozent(doz_id),
);


/*
 * Daten für 'kurs'
 */

INSERT INTO kurs (kurs_typ, kurs_doz_id, kurs_bezeichnung, kurs_beginndatum) 
VALUES ('P',2 ,'Objektorientierte Programmierung mit Java','2010-08-27');
INSERT INTO kurs (kurs_typ, kurs_doz_id, kurs_bezeichnung, kurs_beginndatum)
VALUES ('S',3,'JavaScript','2010-06-29');
INSERT INTO kurs (kurs_typ, kurs_doz_id, kurs_bezeichnung, kurs_beginndatum)
VALUES ('P',2,'JDBC','2010-06-30');
INSERT INTO kurs (kurs_typ, kurs_doz_id, kurs_bezeichnung, kurs_beginndatum) 
VALUES ('W',4,'HTML','2010-07-13');
INSERT INTO kurs (kurs_typ, kurs_doz_id, kurs_bezeichnung, kurs_beginndatum)
VALUES ('P',5,'GUI-Programmierung mit Java','2010-10-09');
INSERT INTO kurs (kurs_typ, kurs_doz_id, kurs_bezeichnung, kurs_beginndatum)
VALUES ('W',4,'Servlets','2010-10-10');

/*
 * Tabelle 'kunde'
 */

CREATE TABLE kunde (
  kunde_id 	        serial NOT NULL,
  kunde_zuname		varchar(25),
  kunde_vorname 	varchar(25),
  CONSTRAINT 		kunde_pkey PRIMARY KEY (kunde_id)
);


/*
 * Daten für 'kunde'
 */

INSERT INTO kunde (kunde_zuname, kunde_vorname) VALUES ('Bauer','Hannes');
INSERT INTO kunde (kunde_zuname, kunde_vorname) VALUES ('Khan','Dschingis');
INSERT INTO kunde (kunde_zuname, kunde_vorname) VALUES ('Schmidt','Lothar');
INSERT INTO kunde (kunde_zuname, kunde_vorname) VALUES ('Kunze','Sieglinde');
INSERT INTO kunde (kunde_zuname, kunde_vorname) VALUES ('Hintze','Franz');
INSERT INTO kunde (kunde_zuname, kunde_vorname) VALUES ('Kaiser','Leo');

/*
 *   Tabelle kurs_kunde
 */

CREATE TABLE  kurs_kunde (
  kunde_id     integer NOT NULL,
  kurs_id      integer NOT NULL,
  CONSTRAINT   kurs_kunde_pkey PRIMARY KEY (kunde_id, kurs_id),
  CONSTRAINT   kurs_kunde_fk1 FOREIGN KEY (kunde_id)
               REFERENCES kunde(kunde_id),
  CONSTRAINT   kurs_kunde_fk2 FOREIGN KEY (kurs_id)
               REFERENCES kurs(kurs_id)
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
