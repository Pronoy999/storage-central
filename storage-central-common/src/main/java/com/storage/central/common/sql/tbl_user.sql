alter database storage_central set search_path to storage_central;
CREATE TABLE if not exists storage_central.tbl_user
(
    guid       varchar(200) NOT NULL PRIMARY KEY,
    first_name varchar(200) not null,
    last_name  varchar(200) not null,
    created    timestamp    not null default current_timestamp,
    updated    timestamp    not null default current_timestamp
);