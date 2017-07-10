INSERT INTO author VALUES
  (1, 'Chuck', 'Palahniuk', 'MALE', '1962-02-21'),
  (2, 'J.D.', 'Salinger', 'MALE', '1919-01-01'),
  (3, 'F. Scott', 'Fitzgerald', 'MALE', '1896-09-24'),
  (4, 'Franz', 'Kafka', 'MALE', '1883-06-03'),
  (5, 'Agatha', 'Christie', 'FEMALE', '1890-09-15');

INSERT INTO reward VALUES
  (1, 1997,'Pacific Northwest Booksellers Association Award',1),
  (2, 2010,'The 100 best novels: No 72.', 4),
  (3, 2009, 'USC Scripter Award', 3);

INSERT INTO book VALUES
  (1, 'Murder on the Orient Express', '9788370231743', 'CRIME'),
  (2, 'Fight Club', '9788360979426', 'SATIRE'),
  (3, 'Before the Law', '978544671809', 'NOVEL'),
  (4, 'The Catcher in the Rye', '978544671809', 'NOVEL'),
  (5, 'The Great Gatsby', '9780230035287','HISTORICAL_FICTION');

INSERT INTO author_book VALUES
  (5, 1),
  (1, 2),
  (4, 3),
  (2, 4),
  (3, 5);

