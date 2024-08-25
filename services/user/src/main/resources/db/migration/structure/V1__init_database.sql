
/*------------------------------roles table----------------------------------*/

    create table if not exists tb_roles (
        role_id uuid not null primary key,
        name character varying(63) not null,
        created_at timestamp without time zone not null,
        modified_at timestamp without time zone
    );

create table tb_authorities (
    role_id uuid not null,
    authority character varying check (authority in (
        'UPDATE_USER','DELETE_USER','READ_USERS',
        'READ_USER','BLOCK_USER','WRITE_ROLE',
        'DELETE_ROLE','READ_ROLES','READ_ROLE',
        'UPDATE_PASS','RESET_PASS')
        ));

/*----------------------------password table---------------------------------*/

create sequence tb_password_seq start with 101 increment by 8;

create table tb_passwords (
    created_at timestamp without time zone not null,
    expired_at timestamp without time zone not null,
    password_id bigint not null,
    password character varying not null,
    primary key (password_id));

/*------------------------------user table----------------------------------*/

create table tb_users (
    enabled boolean not null,
    created_at timestamp without time zone not null,
    modified_at timestamp without time zone,
    password bigint not null unique,
    phone character varying(15) not null unique,
    role uuid not null,
    user_id uuid not null,
    first_name character varying(127) not null,
    last_name character varying(255) not null,
    primary key (user_id));

/*----------------------------customer table---------------------------------*/

create table tb_customers (
    created_at timestamp without time zone not null,
    modified_at timestamp without time zone,
    nid character varying(10) not null unique check (length(nid) = 10),
    customer_id uuid not null,
    user_id uuid not null unique,
    gender character varying check (gender in ('MALE','FEMALE')),
    loyalty_status character varying check (loyalty_status in ('NEW_USER','LOYAL')),
    bio text,
    primary key (customer_id));

/*----------------------------indexes---------------------------------*/
alter table if exists tb_authorities add constraint fk_role_id_authorities foreign key (role_id) references tb_roles;
alter table if exists tb_customers add constraint fk_user_id_customers foreign key (user_id) references tb_users;
alter table if exists tb_users add constraint fk_password_users foreign key (password) references tb_passwords;
alter table if exists tb_users add constraint fk_role_users foreign key (role) references tb_roles;