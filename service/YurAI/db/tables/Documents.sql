CREATE TABLE IF NOT EXISTS public."Documents"
(
    id integer NOT NULL DEFAULT nextval('"Documents_id_seq"'::regclass),
    file_name character varying(400) COLLATE pg_catalog."default" NOT NULL,
    file_full_url character varying(1000) COLLATE pg_catalog."default",
    base64_source text COLLATE pg_catalog."default",
    uploaded_by integer,
    uploaded_at timestamp without time zone NOT NULL DEFAULT now(),
    CONSTRAINT "Documents_PK" PRIMARY KEY (id)
)

ALTER TABLE IF EXISTS public."Documents"
    OWNER to postgres;