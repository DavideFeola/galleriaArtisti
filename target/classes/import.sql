
INSERT INTO credenziali (username, password, ruolo) VALUES ('fabio', '$2a$10$nzhy39f6/RGj/6ho.uuFX.a8FrptPT8n4eRym9HHpJTbAASsBk7Zu', 'ARTISTA');
INSERT INTO credenziali (username, password, ruolo) VALUES ('carlotta', '$2a$10$ADbLeIfSX9KAQATJhaNvOOCoLjncnAiaEshDk8fSj3M8O7nDrxkHO', 'ARTISTA');
INSERT INTO credenziali (username, password, ruolo) VALUES ('davide', '$2a$10$re8ldQc1c25aTxujp6zcjOiXg4A9UgbN9PawkSbeFJ98bgTFmFYFy', 'ARTISTA');
INSERT INTO credenziali (username, password, ruolo) VALUES ('carlo', '$2a$10$re8ldQc1c25aTxujp6zcjOiXg4A9UgbN9PawkSbeFJ98bgTFmFYFy', 'UTENTE');

-- UTENTE

INSERT INTO utente (nome, cognome, email, credenziali_id) VALUES ('Carlo', 'Cardini', 'carlo.gmail9', 4);

-- ARTISTA

INSERT INTO artista (nome, cognome, email, voto_totale, credenziali_id) VALUES ('Fabio Massimo', 'Di Marco', 'fabio9.gmail', 0, 1);
INSERT INTO artista (nome, cognome, email, voto_totale, credenziali_id) VALUES ('Carlotta', 'Mariani', 'totta9.gmail', 7, 2);
INSERT INTO artista (nome, cognome, email, voto_totale, credenziali_id) VALUES ('Davide', 'Feola', 'davide9.gmail', 8, 3);

-- Inserimento di dati nella tabella Opere

INSERT INTO opera (titolo, descrizione, voto, artista_id, anno_creazione, corrente_artistica) VALUES ('Tramonto sul Nilo', '"Tramonto Sul Nilo" cattura la bellezza effimera della luce che sfuma dolcemente sul fiume, con pennellate rapide e colori morbidi che evocano l’atmosfera serena e vibrante del crepuscolo. ', 0, 1,  '2000', 'Impressionismo');
INSERT INTO opera (titolo, descrizione, voto, artista_id, anno_creazione, corrente_artistica) VALUES ('Pomodori Rossi', '"Pomodori Rossi" trasforma il frutto in pura emozione, con rossi vibranti e forme distorte che evocano passione e fragilità.', 8, 3, '2023', 'Espressionismo' );
INSERT INTO opera (titolo, descrizione, voto, artista_id, anno_creazione, corrente_artistica) VALUES ('In riva al mare', '"In riva al mare" esplode di emozioni intense e contrastanti, con colori accesi e forme distorte che trasmettono la forza tumultuosa del mare e l’anima inquieta del paesaggio.', 7, 2,  '2025', 'Espressionismo');


--Inserimento di dati nella tabella immagine

INSERT INTO immagine (nome_file, path, opera_id) VALUES ('tramonto.jpg', '/images/tramonto.jpg', 1); 
INSERT INTO immagine (nome_file, path, opera_id) VALUES ('pomodori.jpg', '/images/pomodori.jpg', 2);
INSERT INTO immagine (nome_file, path, opera_id) VALUES ('riva.jpg', '/images/riva.jpg', 3);


-- Inserimento di dati nella tabella Votazione

INSERT INTO votazione (mittente_id, destinatario_id, opera_id, voto) VALUES (3, 2, 3, 7);
INSERT INTO votazione (mittente_id, destinatario_id, opera_id, voto) VALUES (1, 3, 2, 8);
