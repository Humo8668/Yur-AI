CREATE TABLE public."Users"
(
    id serial NOT NULL,
    login character varying(30) NOT NULL,
    full_name character varying(100) NOT NULL,
    email character varying(150),
    profile_pic text,
    password character varying(400) NOT NULL,
    CONSTRAINT "Users_PK" PRIMARY KEY (id),
    CONSTRAINT "Users_U1" UNIQUE (login)
);

ALTER TABLE IF EXISTS public."Users"
    OWNER to postgres;