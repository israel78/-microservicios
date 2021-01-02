INSERT INTO `usuarios` (username, password, enabled, nombre, email) VALUES ('andres','$2a$10$6NKx.ouanHDaDBtOGcxQEeQL7Xup.A8/gOBGuSeh1/TJARZm6MM96',1, 'Andres','profesor@bolsadeideas.com');
INSERT INTO `usuarios` (username, password, enabled, nombre, email) VALUES ('admin','$2a$10$fRwc0Ym47ETDfghdYkkbXOSTtfu427PbNXt9uJ/54eOjlXvMHI5N2',1, 'John','jhon.doe@bolsadeideas.com');

--los roles siempre tienen que ir precedidos del literal ROLE_
INSERT INTO `roles` (nombre) VALUES ('ROLE_USER');
INSERT INTO `roles` (nombre) VALUES ('ROLE_ADMIN');

INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (1, 1);
INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (2, 2);
INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (2, 1);


