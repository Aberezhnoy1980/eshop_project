INSERT INTO `users` (`username`, `password`, `email`)
VALUES ('user', 'user', 'user@host.domain'),
	   ('admin', '$2a$10$uedJ6jkBS08x5mxZY6gV6.LAKSd202CiVutxz5VDq3TIyj9alkmIq', 'admin@host.domain'),
       ('guest', '$2a$10$uedJ6jkBS08x5mxZY6gV6.LAKSd202CiVutxz5VDq3TIyj9alkmIq', 'guest@host.domain');
GO

INSERT INTO `roles` (`name`)
VALUE ('ROLE_USER'),
	  ('ROLE_ADMIN'),
      ('ROLE_GUEST');
GO

INSERT INTO `users_roles`(`user_id`, `role_id`)
SELECT (SELECT id FROM `users` WHERE `username` = 'user'), (SELECT id FROM `roles` WHERE `name` = 'ROLE_USER')
UNION ALL
SELECT (SELECT id FROM `users` WHERE `username` = 'admin'), (SELECT id FROM `roles` WHERE `name` = 'ROLE_ADMIN')
UNION ALL
SELECT (SELECT id FROM `users` WHERE `username` = 'guest'), (SELECT id FROM `roles` WHERE `name` = 'ROLE_GUEST');
GO

INSERT INTO `categories` (`name`)
VALUES ('Laptop'),
	   ('Desktop'),
       ('Smartphone'),
       ('Tablet'),
       ('Monitors'),
       ('Printers'),
	   ('TV, audio, HI-FI'),
	   ('Foto & Video'),
       ('Soft & games'),
       ('Accessories');
GO