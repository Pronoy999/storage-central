alter database storage_central set search_path to storage_central;
create table if not exists storage_central.tbl_credentials
(
    id        varchar(200) primary key,
    user_guid varchar(200) not null,
    email_id  varchar(250) not null,
    password  varchar(250) not null,
    is_active bool                  default true,
    created   timestamp    not null default current_timestamp,
    updated   timestamp    not null default current_timestamp
);

alter table tbl_credentials
    add constraint
        fk_user_guid FOREIGN KEY (user_guid) references tbl_user (guid);