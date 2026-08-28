insert into tb_usuario (id, nome, email, cpfcnpj, telefone, perfil)
values (1, 'admin', 'admin@example.com', '12345678901', '1234567890', 'ADMIN');
insert into tb_credencial (id, login, senha, usuario_id)
values (1, 'admin', '$argon2id$v=19$m=16384,t=2,p=1$VJSq/0iSJWwZLoa62Xz3Qw$o3Un5TeSLvUbY/MizNKCvw6qX2t5RRtM0LRdrDS+8k8', 1);
