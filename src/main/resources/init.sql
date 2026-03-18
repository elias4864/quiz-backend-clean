-- Erweiterte Quiz-Daten für PostgreSQL mit MultiUser-Support
-- Einfach in DBeaver ausführen!

-- 1. Zuerst AppUser-Daten einfügen
INSERT INTO app_users (username, email, password, role) VALUES
('admin', 'admin@quiz.com', '$2a$12$V/IUNw153HLTafqZB1NWEeAZT3C2Iiw7BImsXiv86wS.SHCUBeGUq', 'ADMIN'),  -- Passwort: admin123
('teacher1', 'teacher@school.com', '$2a$12$V/IUNw153HLTafqZB1NWEeAZT3C2Iiw7BImsXiv86wS.SHCUBeGUq', 'ADMIN'), -- Passwort: admin123
('player1', 'player1@quiz.com', '$2a$12$fcmJIZwUxwvImYOa1NwpquK6ERj67aC47aMj/qg4a24p2.Fe0nFsi', 'PLAYER'), -- Passwort: player123
('player2', 'player2@quiz.com', '$2a$12$fcmJIZwUxwvImYOa1NwpquK6ERj67aC47aMj/qg4a24p2.Fe0nFsi', 'PLAYER'); -- Passwort: player123

-- 2. Questions mit created_by_user_id hinzufügen
INSERT INTO questions (question, correct_answer, category, difficulty, created_by_user_id) VALUES
-- Sports Questions (erstellt von admin)
('Welcher Fussballverein hat die meisten Champions League Titel?', 'Real Madrid', 'sports', 'medium', 1),
('Wie viele Spieler sind beim Eishockey gleichzeitig pro Team auf dem Eis?', '6', 'sports', 'easy', 1),
('In welchem Jahr fand die erste Fussball-Weltmeisterschaft statt?', '1930', 'sports', 'hard', 1),
('Welcher Sport wird auf dem größten Spielfeld gespielt?', 'Polo', 'sports', 'hard', 1),
('Wie oft finden die Olympischen Sommerspiele statt?', 'Alle 4 Jahre', 'sports', 'easy', 1),
('Welcher Tennisspieler hat die meisten Grand Slam Titel gewonnen?', 'Novak Djokovic', 'sports', 'medium', 1),

-- Games Questions (erstellt von teacher1)
('Wie heisst die Hauptfigur in The Legend of Zelda?', 'Link', 'games', 'easy', 2),
('Welches Spiel popularisierte das Battle Royale Genre?', 'PUBG', 'games', 'medium', 2),
('In welchem Jahr wurde die erste PlayStation veröffentlicht?', '1994', 'games', 'hard', 2),
('Wie heisst der Hauptcharakter in Grand Theft Auto V?', 'Michael, Franklin, Trevor', 'games', 'medium', 2),
('Welches Unternehmen entwickelte das Spiel Minecraft?', 'Mojang', 'games', 'easy', 2),
('Wie viele Pixel hat Mario in seinem ursprünglichen Design?', '16x16', 'games', 'hard', 2),

-- Movies Questions (erstellt von admin)
('Wer führte Regie bei Jurassic Park (1993)?', 'Steven Spielberg', 'movies', 'medium', 1),
('Welcher Schauspieler verkörpert Iron Man im Marvel Cinematic Universe?', 'Robert Downey Jr.', 'movies', 'easy', 1),
('Welcher Film gewann 2020 den Oscar für den besten Film?', 'Parasite', 'movies', 'hard', 1),
('Wie heisst der erste Star Wars Film chronologisch?', 'Episode I: Die dunkle Bedrohung', 'movies', 'medium', 1),
('Welcher Regisseur ist bekannt für Filme wie Inception und Interstellar?', 'Christopher Nolan', 'movies', 'easy', 1),
('In welchem Jahr wurde der erste James Bond Film veröffentlicht?', '1962', 'movies', 'hard', 1),

-- Geography Questions (erstellt von teacher1)
('Was ist die Hauptstadt der Schweiz?', 'Bern', 'geography', 'easy', 2),
('Welcher ist der längste Fluss der Welt?', 'Nil', 'geography', 'medium', 2),
('Welches Land hat die meisten Zeitzonen?', 'Frankreich', 'geography', 'hard', 2),
('Wie heisst die Hauptstadt von Australien?', 'Canberra', 'geography', 'medium', 2),
('Auf welchem Kontinent liegt Ägypten?', 'Afrika', 'geography', 'easy', 2),

-- Science Questions (erstellt von admin)
('Welches Element hat das chemische Symbol Au?', 'Gold', 'science', 'medium', 1),
('Wie viele Planeten hat unser Sonnensystem?', '8', 'science', 'easy', 1),
('Was ist die chemische Formel für Wasser?', 'H2O', 'science', 'easy', 1),
('Welche Geschwindigkeit hat Licht im Vakuum?', '299.792.458 m/s', 'science', 'hard', 1),
('Wer entwickelte die Relativitätstheorie?', 'Albert Einstein', 'science', 'medium', 1),
('Welches ist das größte Organ des menschlichen Körpers?', 'Haut', 'science', 'medium', 1),

-- History Questions (erstellt von teacher1)
('In welchem Jahr fiel die Berliner Mauer?', '1989', 'history', 'easy', 2);

-- 3. Incorrect Answers hinzufügen (bleibt unverändert)
INSERT INTO question_incorrect_answers (question_id, incorrect_answer) VALUES
-- Question 1: Real Madrid
(1, 'Bayern München'), (1, 'FC Barcelona'), (1, 'AC Mailand'),
-- Question 2: 6 Spieler  
(2, '5'), (2, '7'), (2, '8'),
-- Question 3: 1930
(3, '1928'), (3, '1932'), (3, '1934'),
-- Question 4: Polo
(4, 'Fussball'), (4, 'American Football'), (4, 'Hockey'),
-- Question 5: Alle 4 Jahre
(5, 'Alle 2 Jahre'), (5, 'Alle 3 Jahre'), (5, 'Alle 5 Jahre'),
-- Question 6: Novak Djokovic
(6, 'Roger Federer'), (6, 'Rafael Nadal'), (6, 'Pete Sampras'),

-- Question 7: Link
(7, 'Zelda'), (7, 'Ganondorf'), (7, 'Impa'),
-- Question 8: PUBG
(8, 'Fortnite'), (8, 'Apex Legends'), (8, 'Call of Duty Warzone'),
-- Question 9: 1994
(9, '1995'), (9, '1993'), (9, '1996'),
-- Question 10: Michael, Franklin, Trevor
(10, 'Niko Bellic'), (10, 'Tommy Vercetti'), (10, 'Carl Johnson'),
-- Question 11: Mojang
(11, 'Electronic Arts'), (11, 'Ubisoft'), (11, 'Activision'),
-- Question 12: 16x16
(12, '8x8'), (12, '32x32'), (12, '24x24'),

-- Question 13: Steven Spielberg
(13, 'James Cameron'), (13, 'George Lucas'), (13, 'Christopher Nolan'),
-- Question 14: Robert Downey Jr.
(14, 'Chris Evans'), (14, 'Chris Hemsworth'), (14, 'Mark Ruffalo'),
-- Question 15: Parasite
(15, '1917'), (15, 'Joker'), (15, 'Once Upon a Time in Hollywood'),
-- Question 16: Episode I
(16, 'Episode IV: Eine neue Hoffnung'), (16, 'Episode III: Die Rache der Sith'), (16, 'Rogue One'),
-- Question 17: Christopher Nolan
(17, 'Denis Villeneuve'), (17, 'Ridley Scott'), (17, 'David Fincher'),
-- Question 18: 1962
(18, '1964'), (18, '1960'), (18, '1965'),

-- Question 19: Bern
(19, 'Zürich'), (19, 'Basel'), (19, 'Genf'),
-- Question 20: Nil
(20, 'Amazonas'), (20, 'Jangtse'), (20, 'Mississippi'),
-- Question 21: Frankreich
(21, 'Russland'), (21, 'USA'), (21, 'China'),
-- Question 22: Canberra
(22, 'Sydney'), (22, 'Melbourne'), (22, 'Brisbane'),
-- Question 23: Afrika
(23, 'Asien'), (23, 'Europa'), (23, 'Australien'),

-- Question 24: Gold
(24, 'Silber'), (24, 'Aluminium'), (24, 'Argon'),
-- Question 25: 8
(25, '9'), (25, '7'), (25, '10'),
-- Question 26: H2O
(26, 'CO2'), (26, 'NaCl'), (26, 'CH4'),
-- Question 27: 299.792.458 m/s
(27, '300.000.000 m/s'), (27, '250.000.000 m/s'), (27, '350.000.000 m/s'),
-- Question 28: Albert Einstein
(28, 'Isaac Newton'), (28, 'Stephen Hawking'), (28, 'Niels Bohr'),
-- Question 29: Haut
(29, 'Leber'), (29, 'Lunge'), (29, 'Gehirn'),

-- Question 30: 1989
(30, '1990'), (30, '1987'), (30, '1991');

-- 4. Test-Queries für Verifikation
-- SELECT u.username, u.role, COUNT(q.id) as question_count 
-- FROM app_users u 
-- LEFT JOIN questions q ON u.id = q.created_by_user_id 
-- GROUP BY u.id, u.username, u.role;