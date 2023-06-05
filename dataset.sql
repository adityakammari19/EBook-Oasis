show databases; 
use elibrary_db; 
show tables; 
select * from book; 
select * from users; 
select * from user_role; 
select * from publications; 
select * from roles; 
select * from subscriptions; 
insert into users values (1,"aditya@gmail.com","aditya",1,"kammari","$2a$10$9b2tqIeJxp1POvX8jpwEZuD3sSE67EUmg86GqjBDwRTH9PBiHJMmG","9876543210","aditya"),(2,"jatin@gmail.com","jatin",1,"lekkala","$2a$10$9b2tqIeJxp1POvX8jpwEZuD3sSE67EUmg86GqjBDwRTH9PBiHJMmG","9123456789","jatin"); 
insert into book value (1,"Stan Lee","www.example.com","Friendly Neibourhood Super Hero","12345",350,"localdiskC/books","Spider Man"),(2,"A.G Riddle","www.link.com","Mystry through time","6789",260,"localdiskC/books/LostInTime","Lost In Time"); 
insert into roles values (1,"ADMIN"),(2,"USER"); 
insert into user_role value (1,1),(1,2),(2,2); 
insert into publications values (101,'2010-10-29',1,1),(102,'2020-05-25',2,2); 
insert into subscriptions values (201,'2022-03-15',2,1),(202,'2023-01-01',1,2); 
delete from users where user_id = 4; 
INSERT INTO book
VALUES
  (1, 'John Smith', 'CoverImages/TheSecretCode.jpg', 'A thrilling mystery novel that keeps you on the edge of your seat', '978-123456789', 320, 'Books/TheSecretCode.pdf', 'The Secret Code'),
  (2, 'J.K. Rowling', 'CoverImages/HarryPotter1.jpg', 'The first book in the Harry Potter series', '978-0439554930', 320, 'Books/HarryPotter1.pdf', 'Harry Potter and the Sorcerer\'s Stone'),
  (3, 'Dan Brown', 'CoverImages/TheDaVinciCode.jpg', 'A captivating thriller with hidden secrets', '978-0307474278', 592, 'Books/TheDaVinciCode.pdf', 'The Da Vinci Code'),
  (4, 'Harper Lee', 'CoverImages/ToKillAMockingbird.jpg', 'A classic novel about racial injustice', '978-0060935467', 336, 'Books/ToKillAMockingbird.pdf', 'To Kill a Mockingbird'),
  (5, 'George Orwell', 'CoverImages/1984.jpg', 'A dystopian novel depicting a totalitarian society', '978-0451524935', 328, 'Books/1984.pdf', '1984'),
  (6, 'J.R.R. Tolkien', 'CoverImages/TheHobbit.jpg', 'An adventurous tale set in Middle-earth', '978-0547928227', 400, 'Books/TheHobbit.pdf', 'The Hobbit'),
  (7, 'Jane Austen', 'CoverImages/PrideAndPrejudice.jpg', 'A classic romance novel exploring societal norms', '978-0141439518', 480, 'Books/PrideAndPrejudice.pdf', 'Pride and Prejudice'),
  (8, 'Stephen King', 'CoverImages/TheShining.jpg', 'A horror novel about a haunted hotel', '978-0307743657', 688, 'Books/TheShining.pdf', 'The Shining'),
  (9, 'Jules Verne', 'CoverImages/AroundTheWorld.jpg', 'A science fiction adventure circumnavigating the globe', '978-0812504202', 352, 'Books/AroundTheWorld.pdf', 'Around the World in 80 Days'),
  (10, 'Stan Lee', 'CoverImages/SpiderMan.jpg', 'The web-slinging superhero\'s thrilling adventures', '978-1302919374', 120, 'Books/SpiderMan.pdf', 'Spider-Man'),
  (11, 'Leo Tolstoy', 'CoverImages/WarAndPeace.jpg', 'A panoramic novel depicting Russian society during the Napoleonic era', '978-0140447934', 1392, 'Books/WarAndPeace.pdf', 'War and Peace'),
  (12, 'Gillian Flynn', 'CoverImages/GoneGirl.jpg', 'A gripping psychological thriller with shocking twists and turns', '978-0307588364', 432, 'Books/GoneGirl.pdf', 'Gone Girl'),
  (13, 'Ernest Cline', 'CoverImages/ReadyPlayerOne.jpg', 'A science fiction adventure set in a virtual reality world', '978-0307887443', 384, 'Books/ReadyPlayerOne.pdf', 'Ready Player One'),
  (14, 'Agatha Christie', 'CoverImages/MurderOnTheOrientExpress.jpg', 'A classic murder mystery aboard a luxurious train', '978-0062073501', 256, 'Books/MurderOnTheOrientExpress.pdf', 'Murder on the Orient Express'),
  (15, 'Suzanne Collins', 'CoverImages/TheHungerGames.jpg', 'A dystopian series about survival and rebellion', '978-0439023481', 384, 'Books/TheHungerGames.pdf', 'The Hunger Games'),
  (16, 'J.D. Salinger', 'CoverImages/TheCatcherInTheRye.jpg', 'A coming-of-age story of a teenager in New York City', '978-0316769488', 288, 'Books/TheCatcherInTheRye.pdf', 'The Catcher in the Rye'),
  (17, 'F. Scott Fitzgerald', 'CoverImages/TheGreatGatsby.jpg', 'An exploration of the American Dream in the 1920s', '978-0743273565', 180, 'Books/TheGreatGatsby.pdf', 'The Great Gatsby'),
  (18, 'Haruki Murakami', 'CoverImages/NorwegianWood.jpg', 'A nostalgic love story set in 1960s Tokyo', '978-0375704024', 296, 'Books/NorwegianWood.pdf', 'Norwegian Wood'),
  (19, 'Emily Bronte', 'CoverImages/WutheringHeights.jpg', 'A haunting tale of love and revenge on the Yorkshire Moors', '978-0141439556', 416, 'Books/WutheringHeights.pdf', 'Wuthering Heights'),
  (20, 'George R.R. Martin', 'CoverImages/AGameOfThrones.jpg', 'An epic fantasy series of political intrigue and warfare', '978-0553593716', 848, 'Books/AGameOfThrones.pdf', 'A Game of Thrones');

INSERT INTO publications VALUES
  (1, '2019-06-15', 1, 2),
  (2, '2018-11-03', 2, 1),
  (3, '2016-07-22', 3, 3),
  (4, '2017-09-10', 4, 4),
  (5, '2015-12-18', 5, 2),
  (6, '2018-03-09',  6,1),
  (7, '2021-08-27',  7,3),
  (8, '2016-11-14',  8,4),
  (9, '2017-05-01',  9,2),
  (10, '2019-09-26',  10,1),
  (11, '2016-12-05',  11,3),
  (12, '2020-02-18',  12,4),
  (13, '2017-10-28',  13,2),
  (14, '2018-08-13',  14,1),
  (15, '2022-01-07',  15,3),
  (16, '2019-03-02',  16,4),
  (17, '2015-11-09',  17,2),
  (18, '2016-05-24',  18,1),
  (19, '2020-10-12',  19,3),
  (20, '2017-06-30',  20,4);
  INSERT INTO subscriptions
VALUES
  (1, '2022-03-15',  10,2),
  (2, '2021-11-27',  14,5),
  (3, '2023-02-06',  7,1),
  (4, '2021-12-19',  17,6),
  (5, '2022-08-01',  5,3),
  (6, '2021-10-22',  9,4),
  (7, '2022-09-10',  11,2),
  (8, '2023-05-02',  16,5),
  (9, '2021-09-09',  2,1),
  (10, '2022-06-11',  20,6),
  (11, '2021-07-04',  12,3),
  (12, '2023-03-25',  18,4),
  (13, '2022-01-28',  8,2),
  (14, '2022-08-23',  4,5),
  (15, '2023-04-07',  15,1),
  (16, '2021-11-14',  13,6),
  (17, '2022-02-05',  19,3),
  (18, '2022-07-24',  3,4),
  (19, '2023-01-17',  6,2),
  (20, '2021-09-27',  1,5),
  (21, '2022-04-30',  10,1),
  (22, '2021-08-10',  14,6),
  (23, '2022-10-12',  7,3),
  (24, '2021-12-05',  17,4),
  (25, '2022-09-18',  5,2),
  (26, '2021-10-08',  9,5),
  (27, '2023-05-21',  11,1),
  (28, '2021-09-25',  16,6),
  (29, '2022-06-26',  2,3),
  (30, '2021-07-19',  20,4),
  (31, '2023-04-01',  12,2),
  (32, '2022-02-19',  18,5),
  (33, '2022-08-12',  8,1),
  (34, '2023-02-14',  4,6),
  (35, '2021-11-07',  15,3);
INSERT INTO users
VALUES
  (1, 'aditya@gmail.com', 'Aditya', 1, 'Kammari', '$2a$10$9b2tqIeJxp1POvX8jpwEZuD3sSE67EUmg86GqjBDwRTH9PBiHJMmG', '9876543210', 'aditya'),
  (2, 'john@example.com', 'John', 1, 'Smith', '$2a$10$9b2tqIeJxp1POvX8jpwEZuD3sSE67EUmg86GqjBDwRTH9PBiHJMmG', '1234567890', 'johnsmith'),
  (3, 'michael@example.com', 'Michael', 1, 'Johnson', '$2a$10$9b2tqIeJxp1POvX8jpwEZuD3sSE67EUmg86GqjBDwRTH9PBiHJMmG', '4567890123', 'michaelj'),
  (4, 'david@example.com', 'David', 1, 'Williams', '$2a$10$9b2tqIeJxp1POvX8jpwEZuD3sSE67EUmg86GqjBDwRTH9PBiHJMmG', '7890123456', 'davewill'),
  (5, 'james@example.com', 'James', 1, 'Brown', '$2a$10$9b2tqIeJxp1POvX8jpwEZuD3sSE67EUmg86GqjBDwRTH9PBiHJMmG', '2345678901', 'jamesb'),
  (6, 'william@example.com', 'William', 1, 'Taylor', '$2a$10$9b2tqIeJxp1POvX8jpwEZuD3sSE67EUmg86GqjBDwRTH9PBiHJMmG', '5678901234', 'willtay');
insert into user_role values (1,1),(1,2),(2,2),(3,2),(4,2),(5,2),(6,2); 
