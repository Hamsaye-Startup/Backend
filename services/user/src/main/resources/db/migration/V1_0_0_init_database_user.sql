
/*------------------------------roles table----------------------------------*/
    create table if not exists tb_roles (
        role_id uuid not null primary key,
        name character varying(63) not null,
        created_at timestamp without time zone not null,
        modified_at timestamp without time zone
    );

    create table if not exists tb_authorities (
        role_id uuid not null references tb_roles (role_id),
        authority character varying(127)
    );

    create index if not exists tb_role_pkey on tb_roles using btree (role_id);
    create unique index if not exists tb_role_name_unique on tb_roles using btree (name);

    insert into tb_roles (role_id, name, created_at) values ('eaf0e7fb-b22d-45a8-b948-15b4a3026741', 'role_default', now());
    insert into tb_roles (role_id, name, created_at) values ('908419e2-c7a2-4039-a37d-a7b289c5dd78', 'role_admin', now());

/*----------------------------password table---------------------------------*/
    create table if not exists tb_passwords (
        password_id bigint not null primary key,
        password character varying(1000) not null,
        created_at timestamp without time zone not null,
        modified_at timestamp without time zone
    );

/*------------------------------user table----------------------------------*/
    create table if not exists tb_users (
        user_id uuid not null primary key,
        phone character varying(12) not null,
        password bigint not null references tb_passwords (password_id),
        enabled boolean not null default true,
        created_at timestamp without time zone not null,
        modified_at timestamp without time zone,
        role uuid not null references tb_roles (role_id)
    );

    create index if not exists tb_user_pkey on tb_users using btree (user_id);
    create unique index if not exists tb_user_phone_unique on tb_users using btree (phone);

    update tb_users set role = '908419e2-c7a2-4039-a37d-a7b289c5dd78' where phone = '+989120137406';

/*----------------------------customer table---------------------------------*/
create table if not exists tb_customers (
                                            customer_id uuid not null primary key,
                                            nid character varying(10) not null,
                                            user_id uuid not null,
                                            created_at timestamp without time zone not null,
                                            modified_at timestamp without time zone,
                                            become_host boolean not null,
                                            status character varying(31) not null,
                                            bio text
);

create index if not exists tb_customers_pkey on tb_customers using btree (customer_id);
create unique index if not exists tb_customers_nid_unique on tb_customers using btree (nid);
create unique index if not exists tb_customers_user_id_unique on tb_customers using btree (user_id);

