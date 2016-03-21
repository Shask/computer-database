drop schema if exists `computer-database-db`;
  create schema if not exists `computer-database-db`;
  use `computer-database-db`;

  drop table if exists computer;
  drop table if exists company;
  drop table if exists user;
  drop table if exists role;

  create table company (
    id                        bigint not null auto_increment,
    name                      varchar(255),
    constraint pk_company primary key (id))
  ;

  create table computer (
    id                        bigint not null auto_increment,
    name                      varchar(255),
    introduced                timestamp NULL,
    discontinued              timestamp NULL,
    company_id                bigint default NULL,
    constraint pk_computer primary key (id))
  ;

  create table user (
    id                        bigint not null auto_increment,
    name                        VARCHAR(255) NOT NULL ,
    password                    VARCHAR(255) NOT NULL ,
    enabled                 TINYINT not null default '1' ,
    role_id                bigint not null default '1',
    constraint pk_user primary key (id))
  ;

  create table role (
    id                        bigint not null auto_increment ,
    name                      varchar(255) NOT NULL ,
    creation TINYINT not null default '0' ,
    edition TINYINT not null default '0' ,
  deletion TINYINT not null default '0' ,
    reading TINYINT not null default '1' ,
    constraint pk_role primary key (id))
  ;


  alter table computer add constraint fk_computer_company_1 foreign key (company_id) references company (id) on delete restrict on update restrict;
  create index ix_computer_company_1 on computer (company_id);

  alter table user add constraint fk_user_role_1 foreign key (role_id) references role (id) on delete restrict on update restrict;
  create index ix_user_role_1 on user (role_id);
