create table Disease(
    id int(10) primary key auto_increment,
    name varchar(50) not null,
    pathogen varchar(50),
    precautions varchar(255)
);

create table Family(
    id int(10) primary key auto_increment,
    name varchar(30) not null
);

create table Researcher(
    id int(10) primary key auto_increment,
    name varchar(50) not null,
    username varchar(10) not null unique,
    password varchar(12) not null,
    fk_family int(10) not null,
    foreign key (fk_family) references Family(id)
);

create table Plant(
    id int(10) primary key auto_increment,
    name varchar(30) not null,
    fk_family int(10) not null,
    foreign key (fk_family) references Family(id)
);

create table Post(
    id int(10) primary key auto_increment,
    sender varchar(50) not null,
    image varchar(255) not null,
    fk_plant int(10) not null,
    foreign key (fk_plant) references Plant(id)
);

create table Diagnostic(
    id int(10) primary key auto_increment,
    fk_post int(10) not null,
    fk_researcher int(10) not null,
    fk_disease int(10) not null,
    foreign key (fk_post) references Post(id),
    foreign key (fk_researcher) references Researcher(id),
    foreign key (fk_disease) references Disease(id)
);
