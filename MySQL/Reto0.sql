drop database examen;
create database examen;
use examen;

create table Unit(
id integer not null primary key,
acronim varchar(10),
title varchar (35),
evaluation varchar(10),
description varchar(80)
);
create table Statement(
id integer not null primary key,
description varchar(80),
Dlevel enum('ALTA','MEDIA','BAJA'),
available boolean,
path varchar(40)
);
create table sessionE(
Esession varchar (35) not null primary key,
descripcion varchar(80),
Edate date,
course varchar(10),
id_statement int not null,
foreign key (id_statement) references Statement(id) on update cascade on delete cascade
);

create table Unit_Statement(
idU integer not null ,
idS integer not null,
foreign key (idU) references Unit(id) on update cascade on delete cascade,
foreign key (idS) references Statement(id)on update cascade on delete cascade
);


INSERT INTO unit values(1,'ADTI','data access','first','Make applications linked to databases');

INSERT INTO statement values(1,'Make a CRUD application linked to a database','MEDIA',true,'nowhere');

INSERT INTO sessionE values('primera','Make a CRUD application linked to a database','2025-05-28','first',1);

INSERT INTO unit_statement values(1,1);



