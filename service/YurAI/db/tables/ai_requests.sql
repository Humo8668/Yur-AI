CREATE TABLE public.ai_requests
(
    request_id serial NOT NULL,
    base64_file text NOT NULL,
    request_time time without time zone NOT NULL DEFAULT now(),
    PRIMARY KEY (request_id)
);

ALTER TABLE IF EXISTS public.ai_requests
    OWNER to postgres;