INSERT INTO AUTHORS VALUES (1, 'Ильф', 'Илья', 'Арнольдович');
INSERT INTO AUTHORS VALUES (2, 'Петров', 'Евгений', 'Петрович');
INSERT INTO AUTHORS VALUES (3, 'Брэдбери', 'Рэй', '');
INSERT INTO AUTHORS VALUES (4, 'Донцова', 'Дарья', '');
INSERT INTO AUTHORS VALUES (5, 'Кастанеда', 'Карлос', '');
INSERT INTO AUTHORS VALUES (6, 'Гослинг', 'Джеймс', '');
INSERT INTO AUTHORS VALUES (7, 'Джой', 'Билл', '');
INSERT INTO AUTHORS VALUES (8, 'Стил', 'Гай', '');
INSERT INTO AUTHORS VALUES (9, 'Брача', 'Гилард', '');
INSERT INTO AUTHORS VALUES (10, 'Баксли', 'Алекс', '');

INSERT INTO BOOKS VALUES (1, 'Золотой теленок', 10);
INSERT INTO BOOKS VALUES (2, 'Двенадцать стульев', 8);
INSERT INTO BOOKS VALUES (3, 'О скитаньях вечных и о Земле', 2);
INSERT INTO BOOKS VALUES (4, 'Покер с акулой', 20);
INSERT INTO BOOKS VALUES (5, 'Язык программирования Java SE 8', 4);
INSERT INTO BOOKS VALUES (6, 'Учение дона Хуана: Путь знания индейцев — яки', 6);

INSERT INTO GENRES VALUES (1, 'Сатира');
INSERT INTO GENRES VALUES (2, 'Детектив');
INSERT INTO GENRES VALUES (3, 'Рассказы');
INSERT INTO GENRES VALUES (4, 'Фантастика');
INSERT INTO GENRES VALUES (5, 'Философский роман');
INSERT INTO GENRES VALUES (6, 'Эзотерика');
INSERT INTO GENRES VALUES (7, 'Компьютерная литература');

INSERT INTO BOOK_AUTHOR VALUES (1, 1,1);
INSERT INTO BOOK_AUTHOR VALUES (2, 2,1);
INSERT INTO BOOK_AUTHOR VALUES (3, 1,2);
INSERT INTO BOOK_AUTHOR VALUES (4, 2,2);
INSERT INTO BOOK_AUTHOR VALUES (5, 3,3);
INSERT INTO BOOK_AUTHOR VALUES (6, 4,4);
INSERT INTO BOOK_AUTHOR VALUES (7, 5,6);
INSERT INTO BOOK_AUTHOR VALUES (8, 6,5);
INSERT INTO BOOK_AUTHOR VALUES (9, 7,5);
INSERT INTO BOOK_AUTHOR VALUES (10, 8,5);
INSERT INTO BOOK_AUTHOR VALUES (11, 9,5);
INSERT INTO BOOK_AUTHOR VALUES (12, 10,5);

INSERT INTO BOOK_GENRE VALUES (1, 1,1);
INSERT INTO BOOK_GENRE VALUES (2, 2,1);
INSERT INTO BOOK_GENRE VALUES (3, 3,3);
INSERT INTO BOOK_GENRE VALUES (4, 3,4);
INSERT INTO BOOK_GENRE VALUES (5, 4,2);
INSERT INTO BOOK_GENRE VALUES (6, 5,7);
INSERT INTO BOOK_GENRE VALUES (7, 6,6);
INSERT INTO BOOK_GENRE VALUES (8, 6,5);

INSERT INTO acl_sid VALUES (1, 1, 'admin');
INSERT INTO acl_sid VALUES (2, 1, 'user');

INSERT INTO acl_class VALUES (1, 'main.domain.Author');
INSERT INTO acl_class VALUES (2, 'main.domain.Book');
INSERT INTO acl_class VALUES (3, 'main.domain.Genre');
INSERT INTO acl_class VALUES (4, 'main.domain.Comment');

INSERT INTO acl_object_identity (id, 
								 object_id_class, 
								 object_id_identity,
								 parent_object, 
								 owner_sid, 
								 entries_inheriting) VALUES
(1, 1, 1, NULL, 1, 0),
(2, 1, 2, NULL, 1, 0),
(3, 1, 3, NULL, 1, 0),
(4, 1, 4, NULL, 1, 0),
(5, 1, 5, NULL, 1, 0),
(6, 1, 6, NULL, 1, 0),
(7, 1, 7, NULL, 1, 0),
(8, 1, 8, NULL, 1, 0),
(9, 1, 9, NULL, 1, 0),
(10, 1, 10, NULL, 1, 0),
(11, 2, 1, NULL, 1, 0),
(12, 2, 2, NULL, 1, 0),
(13, 2, 3, NULL, 1, 0),
(14, 2, 4, NULL, 1, 0),
(15, 2, 5, NULL, 1, 0),
(16, 2, 6, NULL, 1, 0),
(17, 3, 1, NULL, 1, 0),
(18, 3, 2, NULL, 1, 0),
(19, 3, 3, NULL, 1, 0),
(20, 3, 4, NULL, 1, 0),
(21, 3, 5, NULL, 1, 0),
(22, 3, 6, NULL, 1, 0),
(23, 3, 7, NULL, 1, 0);

INSERT INTO acl_entry (id,
					   acl_object_identity, 
					   ace_order, 
					   sid, 
					   mask, 
					   granting, 
					   audit_success, 
					   audit_failure) VALUES
(1, 1, 1, 1, 1, 1, 1, 1),
(2, 1, 2, 1, 2, 1, 1, 1),
(3, 1, 3, 2, 1, 1, 1, 1),
(4, 2, 1, 1, 1, 1, 1, 1),
(5, 2, 2, 1, 2, 1, 1, 1),
(6, 2, 3, 2, 1, 1, 1, 1),
(7, 3, 1, 1, 1, 1, 1, 1),
(8, 3, 2, 1, 2, 1, 1, 1),
(9, 3, 3, 2, 1, 1, 1, 1),
(10, 4, 1, 1, 1, 1, 1, 1),
(11, 4, 2, 1, 2, 1, 1, 1),
(12, 4, 3, 2, 1, 1, 1, 1),
(13, 5, 1, 1, 1, 1, 1, 1),
(14, 5, 2, 1, 2, 1, 1, 1),
(15, 5, 3, 2, 1, 1, 1, 1),
(16, 6, 1, 1, 1, 1, 1, 1),
(17, 6, 2, 1, 2, 1, 1, 1),
(18, 6, 3, 2, 1, 1, 1, 1),
(19, 7, 1, 1, 1, 1, 1, 1),
(20, 7, 2, 1, 2, 1, 1, 1),
(21, 7, 3, 2, 1, 1, 1, 1),
(22, 8, 1, 1, 1, 1, 1, 1),
(23, 8, 2, 1, 2, 1, 1, 1),
(24, 8, 3, 2, 1, 1, 1, 1),
(25, 9, 1, 1, 1, 1, 1, 1),
(26, 9, 2, 1, 2, 1, 1, 1),
(27, 9, 3, 2, 1, 1, 1, 1),
(28, 10, 1, 1, 1, 1, 1, 1),
(29, 10, 2, 1, 2, 1, 1, 1),
(30, 10, 3, 2, 1, 1, 1, 1),
(31, 11, 1, 1, 1, 1, 1, 1),
(32, 11, 2, 1, 2, 1, 1, 1),
(33, 11, 3, 2, 1, 1, 1, 1),
(34, 12, 1, 1, 1, 1, 1, 1),
(35, 12, 2, 1, 2, 1, 1, 1),
(36, 12, 3, 2, 1, 1, 1, 1),
(37, 13, 1, 1, 1, 1, 1, 1),
(38, 13, 2, 1, 2, 1, 1, 1),
(39, 13, 3, 2, 1, 1, 1, 1),
(40, 14, 1, 1, 1, 1, 1, 1),
(41, 14, 2, 1, 2, 1, 1, 1),
(42, 14, 3, 2, 1, 1, 1, 1),
(43, 15, 1, 1, 1, 1, 1, 1),
(44, 15, 2, 1, 2, 1, 1, 1),
(45, 15, 3, 2, 1, 1, 1, 1),
(46, 16, 1, 1, 1, 1, 1, 1),
(47, 16, 2, 1, 2, 1, 1, 1),
(48, 16, 3, 2, 1, 1, 1, 1),
(49, 17, 1, 1, 1, 1, 1, 1),
(50, 17, 2, 1, 2, 1, 1, 1),
(51, 17, 3, 2, 1, 1, 1, 1),
(52, 18, 1, 1, 1, 1, 1, 1),
(53, 18, 2, 1, 2, 1, 1, 1),
(54, 18, 3, 2, 1, 1, 1, 1),
(55, 19, 1, 1, 1, 1, 1, 1),
(56, 19, 2, 1, 2, 1, 1, 1),
(57, 19, 3, 2, 1, 1, 1, 1),
(58, 20, 1, 1, 1, 1, 1, 1),
(59, 20, 2, 1, 2, 1, 1, 1),
(60, 20, 3, 2, 1, 1, 1, 1),
(61, 21, 1, 1, 1, 1, 1, 1),
(62, 21, 2, 1, 2, 1, 1, 1),
(63, 21, 3, 2, 1, 1, 1, 1),
(64, 22, 1, 1, 1, 1, 1, 1),
(65, 22, 2, 1, 2, 1, 1, 1),
(66, 22, 3, 2, 1, 1, 1, 1),
(67, 23, 1, 1, 1, 1, 1, 1),
(68, 23, 2, 1, 2, 1, 1, 1),
(69, 23, 3, 2, 1, 1, 1, 1);