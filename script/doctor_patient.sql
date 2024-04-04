-- Table: public.doctor_patient

-- DROP TABLE IF EXISTS public.doctor_patient;

CREATE TABLE IF NOT EXISTS public.doctor_patient
(
    doctor_id integer NOT NULL,
    patient_id integer NOT NULL,
    CONSTRAINT doctor_patient_pkey PRIMARY KEY (doctor_id, patient_id),
    CONSTRAINT doctor_patient_doctor_id_fkey FOREIGN KEY (doctor_id)
        REFERENCES public.doctors (doctor_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT doctor_patient_patient_id_fkey FOREIGN KEY (patient_id)
        REFERENCES public.users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.doctor_patient
    OWNER to postgres;