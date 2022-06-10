create table if not exists accident_type (
    id   bigint generated always as identity
        primary key,
    name text not null
);

create table if not exists rule (
    id   bigint generated always as identity
        primary key,
    name text not null
);

create table if not exists accident (
    id               bigint generated always as identity
        primary key,
    name             text not null,
    description      text not null,
    address          text not null,
    accident_type_id bigint
        constraint accident_type_id_fkey
            references accident_type
);

create table if not exists accident_rule (
    accident_id bigint not null
        constraint accident_id_fkey
            references accident,
    rule_id     bigint not null
        constraint rule_id_fkey
            references rule,
    primary key (accident_id, rule_id)
);