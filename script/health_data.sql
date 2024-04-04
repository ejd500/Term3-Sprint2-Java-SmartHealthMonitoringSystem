-- Table: public.health_data

-- DROP TABLE IF EXISTS public.health_data;

CREATE TABLE IF NOT EXISTS public.health_data
(
    health_data_id integer NOT NULL DEFAULT nextval('health_data_id_seq'::regclass),
    user_id integer NOT NULL,
    weight numeric(5,2) NOT NULL,
    height numeric(5,2) NOT NULL,
    steps integer NOT NULL,
    heart_rate integer NOT NULL,
    date character varying(24) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT health_data_pkey PRIMARY KEY (health_data_id),
    CONSTRAINT health_data_user_id_fkey FOREIGN KEY (user_id)
        REFERENCES public.users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.health_data
    OWNER to postgres;