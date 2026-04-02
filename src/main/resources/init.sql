-- 1. Bestehende Daten löschen und ID-Zähler auf 1 zurücksetzen
TRUNCATE TABLE question_incorrect_answers, questions, app_users RESTART IDENTITY CASCADE;

-- 2. AppUser-Daten einfügen
INSERT INTO app_users (username, email, password, role) VALUES
('admin', 'admin@quiz.com', '$2a$12$V/IUNw153HLTafqZB1NWEeAZT3C2Iiw7BImsXiv86wS.SHCUBeGUq', 'ADMIN'),
('teacher1', 'teacher@school.com', '$2a$12$V/IUNw153HLTafqZB1NWEeAZT3C2Iiw7BImsXiv86wS.SHCUBeGUq', 'ADMIN'),
('player1', 'player1@quiz.com', '$2a$12$fcmJIZwUxwvImYOa1NwpquK6ERj67aC47aMj/qg4a24p2.Fe0nFsi', 'PLAYER'),
('player2', 'player2@quiz.com', '$2a$12$fcmJIZwUxwvImYOa1NwpquK6ERj67aC47aMj/qg4a24p2.Fe0nFsi', 'PLAYER');



INSERT INTO app_users (email, password, role)
VALUES ('admin@quiz.com', '[bcrypt_hash]', 'ADMIN');


-- 3. Questions hinzufügen (jetzt garantiert ID 1 bis 30)
INSERT INTO questions (question, correct_answer, category, difficulty, created_by_user_id) VALUES
('Welcher Fussballverein hat die meisten Champions League Titel?', 'Real Madrid', 'sports', 'medium', 1),
('Wie viele Spieler sind beim Eishockey gleichzeitig pro Team auf dem Eis?', '6', 'sports', 'easy', 1),
('In welchem Jahr fand die erste Fussball-Weltmeisterschaft statt?', '1930', 'sports', 'hard', 1),
('Welcher Sport wird auf dem größten Spielfeld gespielt?', 'Polo', 'sports', 'hard', 1),
('Wie oft finden die Olympischen Sommerspiele statt?', 'Alle 4 Jahre', 'sports', 'easy', 1),
('Welcher Tennisspieler hat die meisten Grand Slam Titel gewonnen?', 'Novak Djokovic', 'sports', 'medium', 1),
('Wie heisst die Hauptfigur in The Legend of Zelda?', 'Link', 'games', 'easy', 2),
('Welches Spiel popularisierte das Battle Royale Genre?', 'PUBG', 'games', 'medium', 2),
('In welchem Jahr wurde die erste PlayStation veröffentlicht?', '1994', 'games', 'hard', 2),
('Wie heisst der Hauptcharakter in Grand Theft Auto V?', 'Michael, Franklin, Trevor', 'games', 'medium', 2),
('Welches Unternehmen entwickelte das Spiel Minecraft?', 'Mojang', 'games', 'easy', 2),
('Wie viele Pixel hat Mario in seinem ursprünglichen Design?', '16x16', 'games', 'hard', 2),
('Wer führte Regie bei Jurassic Park (1993)?', 'Steven Spielberg', 'movies', 'medium', 1),
('Welcher Schauspieler verkörpert Iron Man im Marvel Cinematic Universe?', 'Robert Downey Jr.', 'movies', 'easy', 1),
('Welcher Film gewann 2020 den Oscar für den besten Film?', 'Parasite', 'movies', 'hard', 1),
('Wie heisst der erste Star Wars Film chronologisch?', 'Episode I: Die dunkle Bedrohung', 'movies', 'medium', 1),
('Welcher Regisseur ist bekannt für Filme wie Inception und Interstellar?', 'Christopher Nolan', 'movies', 'easy', 1),
('In welchem Jahr wurde der erste James Bond Film veröffentlicht?', '1962', 'movies', 'hard', 1),
('Was ist die Hauptstadt der Schweiz?', 'Bern', 'geography', 'easy', 2),
('Welcher ist der längste Fluss der Welt?', 'Nil', 'geography', 'medium', 2),
('Welches Land hat die meisten Zeitzonen?', 'Frankreich', 'geography', 'hard', 2),
('Wie heisst die Hauptstadt von Australien?', 'Canberra', 'geography', 'medium', 2),
('Auf welchem Kontinent liegt Ägypten?', 'Afrika', 'geography', 'easy', 2),
('Welches Element hat das chemische Symbol Au?', 'Gold', 'science', 'medium', 1),
('Wie viele Planeten hat unser Sonnensystem?', '8', 'science', 'easy', 1),
('Was ist die chemische Formel für Wasser?', 'H2O', 'science', 'easy', 1),
('Welche Geschwindigkeit hat Licht im Vakuum?', '299.792.458 m/s', 'science', 'hard', 1),
('Wer entwickelte die Relativitätstheorie?', 'Albert Einstein', 'science', 'medium', 1),
('Welches ist das größte Organ des menschlichen Körpers?', 'Haut', 'science', 'medium', 1),
('In welchem Jahr fiel die Berliner Mauer?', '1989', 'history', 'easy', 2);

    -- 4. Incorrect Answers hinzufügen
        INSERT INTO question_incorrect_answers (question_id, incorrect_answer) VALUES
        (1, 'Bayern München'), (1, 'FC Barcelona'), (1, 'AC Mailand'),
        (2, '5'), (2, '7'), (2, '8'),
        (3, '1928'), (3, '1932'), (3, '1934'),
        (4, 'Fussball'), (4, 'American Football'), (4, 'Hockey'),
        (5, 'Alle 2 Jahre'), (5, 'Alle 3 Jahre'), (5, 'Alle 5 Jahre'),
        (6, 'Roger Federer'), (6, 'Rafael Nadal'), (6, 'Pete Sampras'),
        (7, 'Zelda'), (7, 'Ganondorf'), (7, 'Impa'),
        (8, 'Fortnite'), (8, 'Apex Legends'), (8, 'Call of Duty Warzone'),
        (9, '1995'), (9, '1993'), (9, '1996'),
        (10, 'Niko Bellic'), (10, 'Tommy Vercetti'), (10, 'Carl Johnson'),
        (11, 'Electronic Arts'), (11, 'Ubisoft'), (11, 'Activision'),
        (12, '8x8'), (12, '32x32'), (12, '24x24'),
        (13, 'James Cameron'), (13, 'George Lucas'), (13, 'Christopher Nolan'),
        (14, 'Chris Evans'), (14, 'Chris Hemsworth'), (14, 'Mark Ruffalo'),
        (15, '1917'), (15, 'Joker'), (15, 'Once Upon a Time in Hollywood'),
        (16, 'Episode IV: Eine neue Hoffnung'), (16, 'Episode III: Die Rache der Sith'), (16, 'Rogue One'),
        (17, 'Denis Villeneuve'), (17, 'Ridley Scott'), (17, 'David Fincher'),
        (18, '1964'), (18, '1960'), (18, '1965'),
        (19, 'Zürich'), (19, 'Basel'), (19, 'Genf'),
        (20, 'Amazonas'), (20, 'Jangtse'), (20, 'Mississippi'),
        (21, 'Russland'), (21, 'USA'), (21, 'China'),
        (22, 'Sydney'), (22, 'Melbourne'), (22, 'Brisbane'),
        (23, 'Asien'), (23, 'Europa'), (23, 'Australien'),
        (24, 'Silber'), (24, 'Aluminium'), (24, 'Argon'),
        (25, '9'), (25, '7'), (25, '10'),
        (26, 'CO2'), (26, 'NaCl'), (26, 'CH4'),
        (27, '300.000.000 m/s'), (27, '250.000.000 m/s'), (27, '350.000.000 m/s'),
        (28, 'Isaac Newton'), (28, 'Stephen Hawking'), (28, 'Niels Bohr'),
        (29, 'Leber'), (29, 'Lunge'), (29, 'Gehirn'),
        (30, '1990'), (30, '1987'), (30, '1991');