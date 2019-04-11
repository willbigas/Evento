create database evento;

use evento;

/** Criando Tabelas **/

CREATE TABLE participante(
id INT(11) not null auto_increment,
nome varchar(50) NOT NULL,
cpf varchar(11) NOT NULL,
email varchar(50) NOT NULL,
telefone varchar(9) NOT NULL,
ativo boolean not null,
PRIMARY KEY (id));

CREATE TABLE usuario(
id INT(11) not null auto_increment,
login varchar(25),
senha varchar(25),
PRIMARY KEY (id));


CREATE TABLE categoria(
id INT(11) not null auto_increment,
nome varchar(25),
fk_participante int,
foreign key(fk_participante) references participante(id),
PRIMARY KEY (id));


/** Criando Usuario Padrao [ADMIN] - [ADMIN] **/

INSERT INTO usuario (id, login, senha)
VALUES (1, 'admin', 'admin');

select * from usuario;

select * from categoria;

select * from participante;

