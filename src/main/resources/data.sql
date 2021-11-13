create table if not exists user (
    id int auto_increment primary key,
    name varchar(255) not null,
    accountnumber varchar(255) not null,
    cpf varchar(255) not null,
    password varchar(255) not null,
    token varchar(255) default null
);