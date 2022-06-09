CREATE TABLE accident (
    id               bigint generated always as identity
        primary key,
    name             text not null,
    description      text not null,
    address          text not null,
    accident_type_id bigint
        constraint accident_type_id_fkey
            references accident_type
);