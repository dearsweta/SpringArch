create table profiles
(
    id             bigint auto_increment,
    bio            varchar(255) null,
    phone_number   int          null,
    date_of_birth  int          null,
    loyalty_points int UNSIGNED not null,
    constraint profiles_users_id_fk
        foreign key (id) references users (id)
);

