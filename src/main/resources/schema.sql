create table if not exists ingredient (
  id   varchar(4) not null,
  name varchar(25) not null,
  type varchar(10) not null
);

create table if not exists ingredient_ref (
  ingredient varchar(4) not null,
  taco       bigint not null,
  taco_key   bigint not null
);

create table if not exists taco (
  id             integer primary key generated by default as identity,
  name           varchar(50) not null,
  taco_order     bigint not null,
  taco_order_key bigint not null,
  create_date    timestamp not null
);

create table if not exists taco_order (
  id              bigint primary key generated by default as identity,
  delivery_name   varchar(50) not null,
  delivery_street varchar(50) not null,
  delivery_city   varchar(50) not null,
  delivery_state  varchar(50) not null,
  delivery_zip    varchar(50) not null,
  cc_number       varchar(16) not null,
  cc_expiration   varchar(5) not null,
  cc_cvv          varchar(3) not null,
  create_date     timestamp not null
);

alter table taco add foreign key (taco_order) references taco_Order (id);