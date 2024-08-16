alter database storage_central set search_path to storage_central;
CREATE TABLE IF NOT EXISTS storage_central.tbl_fileMaster
(
    file_id varchar(200) NOT NULL PRIMARY KEY,
    file_name varchar(500) NOT NULL,
    file_extension VARCHAR(5) ,
    sha_value varchar(256),
    user_guid varchar(200) NOT NULL,
    created    timestamp    not null default current_timestamp,
    updated    timestamp    not null default current_timestamp
)