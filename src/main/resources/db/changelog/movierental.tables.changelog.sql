--liquibase formatted sql

-- changeset shilpashree:customertable
-- preconditions onFail:MARK_RAN onError:MARK_RAN
-- precondition-sql-check expectedResult:0 select count(*) from information_schema.tables where lower(table_name) = 'customer';
CREATE TABLE public.customer (customer_id varchar(10) not null, created_by varchar(24) not null, created_timestamp TIMESTAMP WITHOUT TIME ZONE NOT NULL, updated_by varchar(24), updated_timestamp TIMESTAMP WITHOUT TIME ZONE NOT NULL, customer_name varchar(24), CONSTRAINT customer_pk PRIMARY KEY (customer_id));
-- rollback drop table customer;

-- changeset shilpashree:customerdata
-- preconditions onFail:MARK_RAN onError:MARK_RAN
-- precondition-sql-check expectedResult:0 select count(*) from public.CUSTOMER;
insert into public.customer(customer_id, created_by, created_timestamp, updated_by, updated_timestamp, customer_name) values ('CUStomer','MovieRentalProviderApp',current_timestamp,'MovieRentalProviderApp',current_timestamp,'C. U. Stomer');
insert into public.customer(customer_id, created_by, created_timestamp, updated_by, updated_timestamp, customer_name) values ('SLShilpa','MovieRentalProviderApp',current_timestamp,'MovieRentalProviderApp',current_timestamp,'S L Shilpashree');

-- changeset shilpashree:movietable
-- preconditions onFail:MARK_RAN onError:MARK_RAN
-- precondition-sql-check expectedResult:0 select count(*) from information_schema.tables where lower(table_name) = 'movie';

CREATE TABLE public.movie (movie_id varchar(10) not null, created_by varchar(24) not null, created_timestamp TIMESTAMP WITHOUT TIME ZONE NOT NULL, updated_by varchar(24), updated_timestamp TIMESTAMP WITHOUT TIME ZONE NOT NULL, movie_code varchar(24), movie_title varchar(24), CONSTRAINT movie_pk PRIMARY KEY(movie_id));
-- rollback drop table movie;

-- changeset shilpashree:moviedata
-- preconditions onFail:MARK_RAN onError:MARK_RAN
-- precondition-sql-check expectedResult:0 select count(*) from public.MOVIE;
insert into public.movie (movie_id, created_by, created_timestamp, updated_by, updated_timestamp, movie_code, movie_title) values
('F001','MovieRentalProviderApp',current_timestamp,'MovieRentalProviderApp',current_timestamp,'regular','You''ve Got Mail');
insert into public.movie (movie_id, created_by, created_timestamp, updated_by, updated_timestamp, movie_code, movie_title) values
('F002','MovieRentalProviderApp',current_timestamp,'MovieRentalProviderApp',current_timestamp,'regular','Matrix');
insert into public.movie (movie_id, created_by, created_timestamp, updated_by, updated_timestamp, movie_code, movie_title) values
('F003','MovieRentalProviderApp',current_timestamp,'MovieRentalProviderApp',current_timestamp,'childrens','Cars');
insert into public.movie (movie_id, created_by, created_timestamp, updated_by, updated_timestamp, movie_code, movie_title) values
('F004','MovieRentalProviderApp',current_timestamp,'MovieRentalProviderApp',current_timestamp,'new','Fast & Furious X');

-- changeset shilpashree:movierentaltable
-- preconditions onFail:MARK_RAN onError:MARK_RAN
-- precondition-sql-check expectedResult:0 select count(*) from information_schema.tables where lower(table_name) = 'movie_rental';

CREATE TABLE public.movie_rental (rental_id bigint not null, created_by varchar(24) not null, created_timestamp TIMESTAMP WITHOUT TIME ZONE NOT NULL, updated_by varchar(24), updated_timestamp TIMESTAMP WITHOUT TIME ZONE NOT NULL, customer_id varchar(10), movie_id varchar(10), rental_period integer, CONSTRAINT movierental_pk PRIMARY KEY (rental_id));

--changeset shilpashree:movierentalfk1
-- preconditions onFail:MARK_RAN onError:MARK_RAN
-- precondition-sql-check expectedResult:0 select count(*) from information_schema.constraint_column_usage where lower(constraint_name) = 'movie_rental_fk1';
ALTER TABLE public.movie_rental ADD CONSTRAINT movie_rental_fk1 FOREIGN KEY (customer_id) REFERENCES public.customer (customer_id) ON UPDATE RESTRICT ON DELETE RESTRICT;

--changeset shilpashree:movierentalfk2
-- preconditions onFail:MARK_RAN onError:MARK_RAN
-- precondition-sql-check expectedResult:0 select count(*) from information_schema.constraint_column_usage where lower(constraint_name) = 'movie_rental_fk2';
ALTER TABLE public.movie_rental ADD CONSTRAINT movie_rental_fk2 FOREIGN KEY (movie_id) REFERENCES public.movie (movie_id) ON UPDATE RESTRICT ON DELETE RESTRICT;


