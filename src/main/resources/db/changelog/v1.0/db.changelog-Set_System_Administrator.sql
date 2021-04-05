INSERT INTO accounts(login, password, status) VALUES('admin', '$2y$12$MNnPiHcfaOn9aqlG8EZWeemYqxSwBlw.I.evhF6ugqQIc2AoYRh0m', 'ACTIVE');
INSERT INTO accounts(login, password, status) VALUES('developer', '$2y$12$SF61ug6IteRe4MWYDCiqQ.j0TwraRGgDWi6y1p8p36/Jd3ja5XrYq', 'ACTIVE');
INSERT INTO roles(name) VALUES ('ADMIN');
INSERT INTO roles(name) VALUES ('MODERATOR');
INSERT INTO roles(name) VALUES ('USER');
INSERT INTO roles(name) VALUES ('DEVELOPER');
INSERT INTO accounts_roles VALUES(1, 1);
INSERT INTO accounts_roles VALUES(2, 4);