create database examen;
use examen;

create table Unit(
id integer not null primary key,
acronim varchar(10),
titulo varchar (35),
evaluacion varchar(10),
descripcion varchar(80)
);
create table Statement(
id integer not null primary key,
descripcion varchar(80),
dificultad enum('ALTA','MEDIA','BAJA'),
disponible boolean,
path varchar(40)
);
create table Unit_Statement(
idU integer not null ,
idS integer not null,
foreign key (idU) references Unit(id),
foreign key (idS) references Statement(id)
);
create table callForExam(
Ecall varchar (35) not null primary key,
descripcion varchar(80),
Edate date,
course varchar(10),
idS integer not null,
foreign key (idS) references Statement(id)
);

