create table if not exists customer
(
    customer_id varchar not null
        constraint customer_pk
            primary key);

alter table customer
    owner to postgres;

create table if not exists account
(
    account_id  serial      not null
        constraint account_pk
            primary key,
    country     varchar(50) not null,
    customer_id varchar(50) not null
        constraint account_customer__fk
            references customer
);
-- ALTER SEQUENCE account_account_id_seq RESTART WITH 100000;

alter table account
    owner to postgres;

create table if not exists account_balance
(
    account_balance_id serial         not null
        constraint account_balance_pk
            primary key,
    available_amount   numeric(10, 2) not null,
    currency           varchar(3)     not null,
    account_id         integer        not null
        constraint account_balance_account_account_id_fk
            references account
);

alter table account_balance
    owner to postgres;

create table if not exists transaction
(
    transaction_id serial         not null
        constraint transaction_pk
            primary key,
    amount         numeric(10, 2) not null,
    currency       varchar(3)     not null,
    direction      varchar(3)     not null,
    description    text           not null,
    account_id     integer        not null
        constraint transaction_account_account_id_fk
            references account
);

alter table transaction
    owner to postgres;
