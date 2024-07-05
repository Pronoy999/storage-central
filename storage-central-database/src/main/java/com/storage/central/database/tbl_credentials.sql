alter database storagecentral set search_path to storage_central;
create table if not exists storage_central.tbl_credentials
(
    id        int          not null primary key,
    user_guid varchar(200) not null,
    email_id  varchar(250) not null,
    password  varchar(250) not null,
    is_active bool                  default true,
    created   timestamp    not null default current_timestamp,
    updated   timestamp    not null default current_timestamp
);