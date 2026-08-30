insert into tb_usuario (id, nome, email, cpfcnpj, telefone, perfil)
values (1, 'admin', 'admin@example.com', '0123456789', '123456789', 'ADMINISTRADOR');
insert into tb_credencial (id, login, senha, usuario_id)
values (1, '0123456789', '$argon2id$v=19$m=16384,t=2,p=1$GvjNajcA+jx7VjPWdEnH7w$whhB95n/qls9HmQDNa8PtBjouoqqtiGFgTtPmBoXeuU', 1);
