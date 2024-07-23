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

