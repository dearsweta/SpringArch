alter table profiles
    modify bio TEXT null;

alter table profiles
    modify phone_number VARCHAR(15) null;

alter table profiles
    modify date_of_birth date null;

alter table profiles
    alter column loyalty_points set default 0;

alter table profiles
    add constraint profiles_pk
        primary key (id);

