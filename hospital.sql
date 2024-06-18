PGDMP  #    *                |            hospital    16.2    16.2 d    �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            �           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            �           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            �           1262    32840    hospital    DATABASE     {   CREATE DATABASE hospital WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Spanish_Spain.1252';
    DROP DATABASE hospital;
                postgres    false                        3079    101293    pgcrypto 	   EXTENSION     <   CREATE EXTENSION IF NOT EXISTS pgcrypto WITH SCHEMA public;
    DROP EXTENSION pgcrypto;
                   false            �           0    0    EXTENSION pgcrypto    COMMENT     <   COMMENT ON EXTENSION pgcrypto IS 'cryptographic functions';
                        false    2            �           1247    65619    telefono_domain    DOMAIN     �   CREATE DOMAIN public.telefono_domain AS character varying(11)
	CONSTRAINT telefono_domain_check CHECK (((VALUE IS NULL) OR ((VALUE)::text ~ '^[0-9]{10}$'::text)));
 $   DROP DOMAIN public.telefono_domain;
       public          postgres    false            �           1247    32842    tex1    DOMAIN     >   CREATE DOMAIN public.tex1 AS character varying(100) NOT NULL;
    DROP DOMAIN public.tex1;
       public          postgres    false            �           1247    32844    tex2    DOMAIN     =   CREATE DOMAIN public.tex2 AS character varying(20) NOT NULL;
    DROP DOMAIN public.tex2;
       public          postgres    false            �            1255    65630 �   actualizar_consulta(integer, integer, integer, text, text, date, character varying, character varying, character varying, text, integer)    FUNCTION     �  CREATE FUNCTION public.actualizar_consulta(p_id integer, p_medical_consulta_id integer, p_detalle_consulta_id integer, p_firstname text, p_lastname text, p_new_createdate date, p_new_diagnostic character varying, p_new_treatment character varying, p_new_tel character varying, p_new_ana_con text, p_new_id_doctor integer) RETURNS text
    LANGUAGE plpgsql
    AS $$
DECLARE
    paciente_id INT;
BEGIN
    -- Busca el ID del paciente utilizando su nombre y apellido
    SELECT id INTO paciente_id FROM paciente WHERE id = p_id AND firstname = p_firstname AND lastname = p_lastname;
    
    IF paciente_id IS NULL THEN
        RETURN 'Error: No se encontró al paciente con el nombre y apellido especificados';
    END IF;
    
    -- Actualiza los datos de las consultas médicas asociadas al paciente con el nuevo ID de doctor
    UPDATE medical_consulta mc
    SET createdate = p_new_createdate,
        id_doctor = p_new_id_doctor -- Actualiza el ID del doctor
    WHERE id_paciente = paciente_id AND id = p_medical_consulta_id;
    
    -- Actualiza los detalles de la consulta con el nuevo ID de doctor
    UPDATE detalle_consulta dc
    SET diagnostic = p_new_diagnostic,
        treatment = p_new_treatment,
        celular = p_new_tel,
        analisis_medico = p_new_ana_con
    WHERE id_medcon IN (SELECT id FROM medical_consulta WHERE id_paciente = paciente_id) 
        AND id = p_detalle_consulta_id;

    RETURN 'Consultas actualizadas exitosamente';
END;
$$;
 A  DROP FUNCTION public.actualizar_consulta(p_id integer, p_medical_consulta_id integer, p_detalle_consulta_id integer, p_firstname text, p_lastname text, p_new_createdate date, p_new_diagnostic character varying, p_new_treatment character varying, p_new_tel character varying, p_new_ana_con text, p_new_id_doctor integer);
       public          postgres    false            �            1255    65629 j   agregar_consulta(text, text, date, character varying, character varying, character varying, text, integer)    FUNCTION     �  CREATE FUNCTION public.agregar_consulta(p_firstname text, p_lastname text, p_createdate date, p_diagnostic character varying, p_treatment character varying, p_cel character varying, p_ana_med text, p_id_doctor integer) RETURNS text
    LANGUAGE plpgsql
    AS $$
DECLARE
    paciente_id INT;
    nueva_consulta_id INT;
BEGIN
    -- Obtiene el ID del paciente usando el nombre proporcionado
    SELECT id INTO paciente_id FROM paciente WHERE firstname = p_firstname AND lastname = p_lastname;

    -- Inserta un nuevo registro en la tabla "medical_consulta" con el ID del paciente, el ID del doctor y la fecha de consulta
    INSERT INTO medical_consulta (id_doctor, id_paciente, createdate)
    VALUES (p_id_doctor, paciente_id, p_createdate)
    RETURNING id INTO nueva_consulta_id;

    -- Inserta un nuevo registro en la tabla "detalle_consulta" con los datos proporcionados y el ID de la nueva consulta
    INSERT INTO detalle_consulta (id_medcon, diagnostic, treatment,celular,analisis_medico)
    VALUES (nueva_consulta_id, p_diagnostic, p_treatment,p_cel,p_ana_med);

    -- Devuelve un mensaje de éxito
    RETURN 'Datos de la consulta agregados exitosamente';
END;
$$;
 �   DROP FUNCTION public.agregar_consulta(p_firstname text, p_lastname text, p_createdate date, p_diagnostic character varying, p_treatment character varying, p_cel character varying, p_ana_med text, p_id_doctor integer);
       public          postgres    false                        1255    73815    buscar_consulta(date)    FUNCTION     �  CREATE FUNCTION public.buscar_consulta(p_fecha date) RETURNS TABLE(id integer, firstname text, lastname text, createdate date, diagnostic text, treatment character varying)
    LANGUAGE plpgsql
    AS $$
BEGIN
    RETURN QUERY
    SELECT p.id, p.firstname, p.lastname, mc.createdate, dc.diagnostic, dc.treatment 
    FROM paciente p 
    INNER JOIN medical_consulta mc ON p.id = mc.id_paciente 
    INNER JOIN detalle_consulta dc ON mc.id = dc.id_medcon
    WHERE mc.createdate = p_fecha;
END;
$$;
 4   DROP FUNCTION public.buscar_consulta(p_fecha date);
       public          postgres    false            �            1255    57426 #   eliminar_consulta(integer, integer)    FUNCTION       CREATE FUNCTION public.eliminar_consulta(p_id_consulta integer, p_id_detalle integer) RETURNS text
    LANGUAGE plpgsql
    AS $$
BEGIN
    

    -- Elimina el registro de la tabla medical_consulta con el ID especificado
    DELETE FROM medical_consulta WHERE id = p_id_consulta;

    -- Elimina el registro de la tabla detalle_consulta con el ID especificado
    DELETE FROM detalle_consulta WHERE id = p_id_detalle;
	-- Devuelve un mensaje de modificación exitosa
    RETURN 'Consulta eliminada exitosamente';
	
END;
$$;
 U   DROP FUNCTION public.eliminar_consulta(p_id_consulta integer, p_id_detalle integer);
       public          postgres    false            �            1255    32960    log_detalle_consulta_history()    FUNCTION     �  CREATE FUNCTION public.log_detalle_consulta_history() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
    IF TG_OP = 'INSERT' THEN
        INSERT INTO public.detalle_consulta_history (record_id, operation_state, id_medcom)
        VALUES (NEW.id, 'AÑADIDO', NEW.id_medcon);
    ELSIF TG_OP = 'DELETE' THEN
        INSERT INTO public.detalle_consulta_history (record_id, operation_state, id_medcom)
        VALUES (OLD.id, 'ELIMINADO', OLD.id_medcon);
    ELSIF TG_OP = 'UPDATE' THEN
        INSERT INTO public.detalle_consulta_history (record_id, operation_state, id_medcom)
        VALUES (NEW.id, 'EDITADO', NEW.id_medcon);
    END IF;
    RETURN NULL;
END;
$$;
 5   DROP FUNCTION public.log_detalle_consulta_history();
       public          postgres    false            �            1255    32972    log_doctor_history()    FUNCTION     �  CREATE FUNCTION public.log_doctor_history() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
    IF TG_OP = 'INSERT' THEN
        INSERT INTO public.doctor_history (record_id, operation_state, firstname, dni)
        VALUES (NEW.id, 'AÑADIDO', NEW.firstName, NEW.dni);
    ELSIF TG_OP = 'DELETE' THEN
        INSERT INTO public.doctor_history (record_id, operation_state, firstname, dni)
        VALUES (OLD.id, 'ELIMINADO', OLD.firstName, OLD.dni);
    ELSIF TG_OP = 'UPDATE' THEN
        INSERT INTO public.doctor_history (record_id, operation_state, firstname, dni)
        VALUES (NEW.id, 'EDITADO', NEW.firstName, NEW.dni);
    END IF;
    RETURN NULL;
END;
$$;
 +   DROP FUNCTION public.log_doctor_history();
       public          postgres    false            �            1255    101291    log_medical_consulta_history()    FUNCTION     �  CREATE FUNCTION public.log_medical_consulta_history() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
    IF TG_OP = 'INSERT' THEN
        INSERT INTO public.medical_consulta_history (record_id, operation_state, id_doctor, firstname_d, id_paciente, firstname_p)
        VALUES (NEW.id, 'AÑADIDO', NEW.id_doctor, (SELECT firstname FROM doctor WHERE id = NEW.id_doctor), NEW.id_paciente, (SELECT firstname FROM paciente WHERE id = NEW.id_paciente));
    ELSIF TG_OP = 'DELETE' THEN
        INSERT INTO public.medical_consulta_history (record_id, operation_state, id_doctor, firstname_d, id_paciente, firstname_p)
        VALUES (OLD.id, 'ELIMINADO', OLD.id_doctor, (SELECT firstname FROM doctor WHERE id = OLD.id_doctor), OLD.id_paciente, (SELECT firstname FROM paciente WHERE id = OLD.id_paciente));
    ELSIF TG_OP = 'UPDATE' THEN
        INSERT INTO public.medical_consulta_history (record_id, operation_state, id_doctor, firstname_d, id_paciente, firstname_p)
        VALUES (NEW.id, 'EDITADO', NEW.id_doctor, (SELECT firstname FROM doctor WHERE id = NEW.id_doctor), NEW.id_paciente, (SELECT firstname FROM paciente WHERE id = NEW.id_paciente));
    END IF;
    RETURN NULL;
END;
$$;
 5   DROP FUNCTION public.log_medical_consulta_history();
       public          postgres    false            �            1255    32936    log_paciente_history()    FUNCTION     �  CREATE FUNCTION public.log_paciente_history() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
    IF TG_OP = 'INSERT' THEN
        INSERT INTO public.paciente_history (record_id, operation_state, firstname, dni)
        VALUES (NEW.id, 'AÑADIDO', NEW.firstName, NEW.dni);
    ELSIF TG_OP = 'DELETE' THEN
        INSERT INTO public.paciente_history (record_id, operation_state, firstname, dni)
        VALUES (OLD.id, 'ELIMINADO', OLD.firstName, OLD.dni);
    ELSIF TG_OP = 'UPDATE' THEN
        INSERT INTO public.paciente_history (record_id, operation_state, firstname, dni)
        VALUES (NEW.id, 'EDITADO', NEW.firstName, NEW.dni);
    END IF;
    RETURN NULL;
END;
$$;
 -   DROP FUNCTION public.log_paciente_history();
       public          postgres    false            �            1259    57489    detalle_consulta    TABLE     �   CREATE TABLE public.detalle_consulta (
    id integer NOT NULL,
    id_medcon integer NOT NULL,
    diagnostic public.tex1,
    treatment character varying(500) NOT NULL,
    celular character varying(10),
    analisis_medico text
);
 $   DROP TABLE public.detalle_consulta;
       public         heap    postgres    false    906            �            1259    32951    detalle_consulta_history    TABLE        CREATE TABLE public.detalle_consulta_history (
    id integer NOT NULL,
    record_id integer NOT NULL,
    operation_state text NOT NULL,
    operation_time timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    id_medcom integer NOT NULL
);
 ,   DROP TABLE public.detalle_consulta_history;
       public         heap    postgres    false            �            1259    32950    detalle_consulta_history_id_seq    SEQUENCE     �   CREATE SEQUENCE public.detalle_consulta_history_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 6   DROP SEQUENCE public.detalle_consulta_history_id_seq;
       public          postgres    false    219            �           0    0    detalle_consulta_history_id_seq    SEQUENCE OWNED BY     c   ALTER SEQUENCE public.detalle_consulta_history_id_seq OWNED BY public.detalle_consulta_history.id;
          public          postgres    false    218            �            1259    57488    detalle_consulta_id_seq    SEQUENCE     �   CREATE SEQUENCE public.detalle_consulta_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 .   DROP SEQUENCE public.detalle_consulta_id_seq;
       public          postgres    false    233            �           0    0    detalle_consulta_id_seq    SEQUENCE OWNED BY     S   ALTER SEQUENCE public.detalle_consulta_id_seq OWNED BY public.detalle_consulta.id;
          public          postgres    false    232            �            1259    57442    doctor    TABLE     �   CREATE TABLE public.doctor (
    id integer NOT NULL,
    id_especialidad integer NOT NULL,
    firstname public.tex1,
    lastname public.tex1,
    dni public.tex2,
    codi public.tex2
);
    DROP TABLE public.doctor;
       public         heap    postgres    false    909    909    906    906            �            1259    32963    doctor_history    TABLE       CREATE TABLE public.doctor_history (
    id integer NOT NULL,
    record_id integer NOT NULL,
    operation_state text NOT NULL,
    operation_time timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    firstname public.tex1,
    dni public.tex2
);
 "   DROP TABLE public.doctor_history;
       public         heap    postgres    false    906    909            �            1259    32962    doctor_history_id_seq    SEQUENCE     �   CREATE SEQUENCE public.doctor_history_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 ,   DROP SEQUENCE public.doctor_history_id_seq;
       public          postgres    false    221            �           0    0    doctor_history_id_seq    SEQUENCE OWNED BY     O   ALTER SEQUENCE public.doctor_history_id_seq OWNED BY public.doctor_history.id;
          public          postgres    false    220            �            1259    57441    doctor_id_seq    SEQUENCE     �   CREATE SEQUENCE public.doctor_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 $   DROP SEQUENCE public.doctor_id_seq;
       public          postgres    false    227            �           0    0    doctor_id_seq    SEQUENCE OWNED BY     ?   ALTER SEQUENCE public.doctor_id_seq OWNED BY public.doctor.id;
          public          postgres    false    226            �            1259    41085    doctornumber_seq    SEQUENCE     �   CREATE SEQUENCE public.doctornumber_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 999900000000000
    CACHE 1
    CYCLE;
 '   DROP SEQUENCE public.doctornumber_seq;
       public          postgres    false            �            1259    57433    especialidad    TABLE     V   CREATE TABLE public.especialidad (
    id integer NOT NULL,
    nombre public.tex1
);
     DROP TABLE public.especialidad;
       public         heap    postgres    false    906            �            1259    57432    especialidad_id_seq    SEQUENCE     �   CREATE SEQUENCE public.especialidad_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 *   DROP SEQUENCE public.especialidad_id_seq;
       public          postgres    false    225            �           0    0    especialidad_id_seq    SEQUENCE OWNED BY     K   ALTER SEQUENCE public.especialidad_id_seq OWNED BY public.especialidad.id;
          public          postgres    false    224            �            1259    57472    medical_consulta    TABLE     �   CREATE TABLE public.medical_consulta (
    id integer NOT NULL,
    id_doctor integer NOT NULL,
    id_paciente integer NOT NULL,
    createdate date NOT NULL
);
 $   DROP TABLE public.medical_consulta;
       public         heap    postgres    false            �            1259    101282    medical_consulta_history    TABLE     `  CREATE TABLE public.medical_consulta_history (
    id integer NOT NULL,
    record_id integer NOT NULL,
    operation_state text NOT NULL,
    operation_time timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    id_doctor integer NOT NULL,
    firstname_d text NOT NULL,
    id_paciente integer NOT NULL,
    firstname_p text NOT NULL
);
 ,   DROP TABLE public.medical_consulta_history;
       public         heap    postgres    false            �            1259    101281    medical_consulta_history_id_seq    SEQUENCE     �   CREATE SEQUENCE public.medical_consulta_history_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 6   DROP SEQUENCE public.medical_consulta_history_id_seq;
       public          postgres    false    237            �           0    0    medical_consulta_history_id_seq    SEQUENCE OWNED BY     c   ALTER SEQUENCE public.medical_consulta_history_id_seq OWNED BY public.medical_consulta_history.id;
          public          postgres    false    236            �            1259    57471    medical_consulta_id_seq    SEQUENCE     �   CREATE SEQUENCE public.medical_consulta_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 .   DROP SEQUENCE public.medical_consulta_id_seq;
       public          postgres    false    231            �           0    0    medical_consulta_id_seq    SEQUENCE OWNED BY     S   ALTER SEQUENCE public.medical_consulta_id_seq OWNED BY public.medical_consulta.id;
          public          postgres    false    230            �            1259    41069    numberclinicalhistory_seq    SEQUENCE     �   CREATE SEQUENCE public.numberclinicalhistory_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 999900000000000
    CACHE 1
    CYCLE;
 0   DROP SEQUENCE public.numberclinicalhistory_seq;
       public          postgres    false            �            1259    57460    paciente    TABLE     �   CREATE TABLE public.paciente (
    id integer NOT NULL,
    firstname public.tex1,
    lastname public.tex1,
    dni public.tex2,
    numberclinicalhistory character varying(100) DEFAULT 'HHP0001'::character varying
);
    DROP TABLE public.paciente;
       public         heap    postgres    false    906    906    909            �            1259    32927    paciente_history    TABLE       CREATE TABLE public.paciente_history (
    id integer NOT NULL,
    record_id integer NOT NULL,
    operation_state text NOT NULL,
    operation_time timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    firstname public.tex1,
    dni public.tex2
);
 $   DROP TABLE public.paciente_history;
       public         heap    postgres    false    906    909            �            1259    32926    paciente_history_id_seq    SEQUENCE     �   CREATE SEQUENCE public.paciente_history_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 .   DROP SEQUENCE public.paciente_history_id_seq;
       public          postgres    false    217            �           0    0    paciente_history_id_seq    SEQUENCE OWNED BY     S   ALTER SEQUENCE public.paciente_history_id_seq OWNED BY public.paciente_history.id;
          public          postgres    false    216            �            1259    57459    paciente_id_seq    SEQUENCE     �   CREATE SEQUENCE public.paciente_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 &   DROP SEQUENCE public.paciente_id_seq;
       public          postgres    false    229            �           0    0    paciente_id_seq    SEQUENCE OWNED BY     C   ALTER SEQUENCE public.paciente_id_seq OWNED BY public.paciente.id;
          public          postgres    false    228            �            1259    57503    usuario    TABLE     �   CREATE TABLE public.usuario (
    id integer NOT NULL,
    id_doctor integer NOT NULL,
    password1 text NOT NULL,
    tipo_usuario character varying(20) NOT NULL
);
    DROP TABLE public.usuario;
       public         heap    postgres    false            �            1259    57502    usuario_id_seq    SEQUENCE     �   CREATE SEQUENCE public.usuario_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 %   DROP SEQUENCE public.usuario_id_seq;
       public          postgres    false    235            �           0    0    usuario_id_seq    SEQUENCE OWNED BY     A   ALTER SEQUENCE public.usuario_id_seq OWNED BY public.usuario.id;
          public          postgres    false    234            �           2604    57492    detalle_consulta id    DEFAULT     z   ALTER TABLE ONLY public.detalle_consulta ALTER COLUMN id SET DEFAULT nextval('public.detalle_consulta_id_seq'::regclass);
 B   ALTER TABLE public.detalle_consulta ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    232    233    233            �           2604    32954    detalle_consulta_history id    DEFAULT     �   ALTER TABLE ONLY public.detalle_consulta_history ALTER COLUMN id SET DEFAULT nextval('public.detalle_consulta_history_id_seq'::regclass);
 J   ALTER TABLE public.detalle_consulta_history ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    219    218    219            �           2604    57445 	   doctor id    DEFAULT     f   ALTER TABLE ONLY public.doctor ALTER COLUMN id SET DEFAULT nextval('public.doctor_id_seq'::regclass);
 8   ALTER TABLE public.doctor ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    227    226    227            �           2604    32966    doctor_history id    DEFAULT     v   ALTER TABLE ONLY public.doctor_history ALTER COLUMN id SET DEFAULT nextval('public.doctor_history_id_seq'::regclass);
 @   ALTER TABLE public.doctor_history ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    221    220    221            �           2604    57436    especialidad id    DEFAULT     r   ALTER TABLE ONLY public.especialidad ALTER COLUMN id SET DEFAULT nextval('public.especialidad_id_seq'::regclass);
 >   ALTER TABLE public.especialidad ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    225    224    225            �           2604    57475    medical_consulta id    DEFAULT     z   ALTER TABLE ONLY public.medical_consulta ALTER COLUMN id SET DEFAULT nextval('public.medical_consulta_id_seq'::regclass);
 B   ALTER TABLE public.medical_consulta ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    231    230    231            �           2604    101285    medical_consulta_history id    DEFAULT     �   ALTER TABLE ONLY public.medical_consulta_history ALTER COLUMN id SET DEFAULT nextval('public.medical_consulta_history_id_seq'::regclass);
 J   ALTER TABLE public.medical_consulta_history ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    237    236    237            �           2604    57463    paciente id    DEFAULT     j   ALTER TABLE ONLY public.paciente ALTER COLUMN id SET DEFAULT nextval('public.paciente_id_seq'::regclass);
 :   ALTER TABLE public.paciente ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    228    229    229            �           2604    32930    paciente_history id    DEFAULT     z   ALTER TABLE ONLY public.paciente_history ALTER COLUMN id SET DEFAULT nextval('public.paciente_history_id_seq'::regclass);
 B   ALTER TABLE public.paciente_history ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    217    216    217            �           2604    57506 
   usuario id    DEFAULT     h   ALTER TABLE ONLY public.usuario ALTER COLUMN id SET DEFAULT nextval('public.usuario_id_seq'::regclass);
 9   ALTER TABLE public.usuario ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    234    235    235            �          0    57489    detalle_consulta 
   TABLE DATA           j   COPY public.detalle_consulta (id, id_medcon, diagnostic, treatment, celular, analisis_medico) FROM stdin;
    public          postgres    false    233   ��       w          0    32951    detalle_consulta_history 
   TABLE DATA           m   COPY public.detalle_consulta_history (id, record_id, operation_state, operation_time, id_medcom) FROM stdin;
    public          postgres    false    219   |�                 0    57442    doctor 
   TABLE DATA           U   COPY public.doctor (id, id_especialidad, firstname, lastname, dni, codi) FROM stdin;
    public          postgres    false    227   ��       y          0    32963    doctor_history 
   TABLE DATA           h   COPY public.doctor_history (id, record_id, operation_state, operation_time, firstname, dni) FROM stdin;
    public          postgres    false    221   .�       }          0    57433    especialidad 
   TABLE DATA           2   COPY public.especialidad (id, nombre) FROM stdin;
    public          postgres    false    225   K�       �          0    57472    medical_consulta 
   TABLE DATA           R   COPY public.medical_consulta (id, id_doctor, id_paciente, createdate) FROM stdin;
    public          postgres    false    231   ��       �          0    101282    medical_consulta_history 
   TABLE DATA           �   COPY public.medical_consulta_history (id, record_id, operation_state, operation_time, id_doctor, firstname_d, id_paciente, firstname_p) FROM stdin;
    public          postgres    false    237   �       �          0    57460    paciente 
   TABLE DATA           W   COPY public.paciente (id, firstname, lastname, dni, numberclinicalhistory) FROM stdin;
    public          postgres    false    229   O�       u          0    32927    paciente_history 
   TABLE DATA           j   COPY public.paciente_history (id, record_id, operation_state, operation_time, firstname, dni) FROM stdin;
    public          postgres    false    217   ��       �          0    57503    usuario 
   TABLE DATA           I   COPY public.usuario (id, id_doctor, password1, tipo_usuario) FROM stdin;
    public          postgres    false    235   Ǖ       �           0    0    detalle_consulta_history_id_seq    SEQUENCE SET     N   SELECT pg_catalog.setval('public.detalle_consulta_history_id_seq', 55, true);
          public          postgres    false    218            �           0    0    detalle_consulta_id_seq    SEQUENCE SET     F   SELECT pg_catalog.setval('public.detalle_consulta_id_seq', 24, true);
          public          postgres    false    232            �           0    0    doctor_history_id_seq    SEQUENCE SET     D   SELECT pg_catalog.setval('public.doctor_history_id_seq', 49, true);
          public          postgres    false    220            �           0    0    doctor_id_seq    SEQUENCE SET     <   SELECT pg_catalog.setval('public.doctor_id_seq', 11, true);
          public          postgres    false    226            �           0    0    doctornumber_seq    SEQUENCE SET     >   SELECT pg_catalog.setval('public.doctornumber_seq', 5, true);
          public          postgres    false    223            �           0    0    especialidad_id_seq    SEQUENCE SET     A   SELECT pg_catalog.setval('public.especialidad_id_seq', 5, true);
          public          postgres    false    224            �           0    0    medical_consulta_history_id_seq    SEQUENCE SET     N   SELECT pg_catalog.setval('public.medical_consulta_history_id_seq', 17, true);
          public          postgres    false    236            �           0    0    medical_consulta_id_seq    SEQUENCE SET     F   SELECT pg_catalog.setval('public.medical_consulta_id_seq', 37, true);
          public          postgres    false    230            �           0    0    numberclinicalhistory_seq    SEQUENCE SET     H   SELECT pg_catalog.setval('public.numberclinicalhistory_seq', 25, true);
          public          postgres    false    222            �           0    0    paciente_history_id_seq    SEQUENCE SET     E   SELECT pg_catalog.setval('public.paciente_history_id_seq', 5, true);
          public          postgres    false    216            �           0    0    paciente_id_seq    SEQUENCE SET     =   SELECT pg_catalog.setval('public.paciente_id_seq', 2, true);
          public          postgres    false    228            �           0    0    usuario_id_seq    SEQUENCE SET     <   SELECT pg_catalog.setval('public.usuario_id_seq', 8, true);
          public          postgres    false    234            �           2606    32959 6   detalle_consulta_history detalle_consulta_history_pkey 
   CONSTRAINT     t   ALTER TABLE ONLY public.detalle_consulta_history
    ADD CONSTRAINT detalle_consulta_history_pkey PRIMARY KEY (id);
 `   ALTER TABLE ONLY public.detalle_consulta_history DROP CONSTRAINT detalle_consulta_history_pkey;
       public            postgres    false    219            �           2606    57496 &   detalle_consulta detalle_consulta_pkey 
   CONSTRAINT     d   ALTER TABLE ONLY public.detalle_consulta
    ADD CONSTRAINT detalle_consulta_pkey PRIMARY KEY (id);
 P   ALTER TABLE ONLY public.detalle_consulta DROP CONSTRAINT detalle_consulta_pkey;
       public            postgres    false    233            �           2606    57453    doctor doctor_codi_key 
   CONSTRAINT     Q   ALTER TABLE ONLY public.doctor
    ADD CONSTRAINT doctor_codi_key UNIQUE (codi);
 @   ALTER TABLE ONLY public.doctor DROP CONSTRAINT doctor_codi_key;
       public            postgres    false    227            �           2606    57451    doctor doctor_dni_key 
   CONSTRAINT     O   ALTER TABLE ONLY public.doctor
    ADD CONSTRAINT doctor_dni_key UNIQUE (dni);
 ?   ALTER TABLE ONLY public.doctor DROP CONSTRAINT doctor_dni_key;
       public            postgres    false    227            �           2606    32971 "   doctor_history doctor_history_pkey 
   CONSTRAINT     `   ALTER TABLE ONLY public.doctor_history
    ADD CONSTRAINT doctor_history_pkey PRIMARY KEY (id);
 L   ALTER TABLE ONLY public.doctor_history DROP CONSTRAINT doctor_history_pkey;
       public            postgres    false    221            �           2606    57449    doctor doctor_pkey 
   CONSTRAINT     P   ALTER TABLE ONLY public.doctor
    ADD CONSTRAINT doctor_pkey PRIMARY KEY (id);
 <   ALTER TABLE ONLY public.doctor DROP CONSTRAINT doctor_pkey;
       public            postgres    false    227            �           2606    57440    especialidad especialidad_pkey 
   CONSTRAINT     \   ALTER TABLE ONLY public.especialidad
    ADD CONSTRAINT especialidad_pkey PRIMARY KEY (id);
 H   ALTER TABLE ONLY public.especialidad DROP CONSTRAINT especialidad_pkey;
       public            postgres    false    225            �           2606    101290 6   medical_consulta_history medical_consulta_history_pkey 
   CONSTRAINT     t   ALTER TABLE ONLY public.medical_consulta_history
    ADD CONSTRAINT medical_consulta_history_pkey PRIMARY KEY (id);
 `   ALTER TABLE ONLY public.medical_consulta_history DROP CONSTRAINT medical_consulta_history_pkey;
       public            postgres    false    237            �           2606    57477 &   medical_consulta medical_consulta_pkey 
   CONSTRAINT     d   ALTER TABLE ONLY public.medical_consulta
    ADD CONSTRAINT medical_consulta_pkey PRIMARY KEY (id);
 P   ALTER TABLE ONLY public.medical_consulta DROP CONSTRAINT medical_consulta_pkey;
       public            postgres    false    231            �           2606    57470    paciente paciente_dni_key 
   CONSTRAINT     S   ALTER TABLE ONLY public.paciente
    ADD CONSTRAINT paciente_dni_key UNIQUE (dni);
 C   ALTER TABLE ONLY public.paciente DROP CONSTRAINT paciente_dni_key;
       public            postgres    false    229            �           2606    32935 &   paciente_history paciente_history_pkey 
   CONSTRAINT     d   ALTER TABLE ONLY public.paciente_history
    ADD CONSTRAINT paciente_history_pkey PRIMARY KEY (id);
 P   ALTER TABLE ONLY public.paciente_history DROP CONSTRAINT paciente_history_pkey;
       public            postgres    false    217            �           2606    57468    paciente paciente_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.paciente
    ADD CONSTRAINT paciente_pkey PRIMARY KEY (id);
 @   ALTER TABLE ONLY public.paciente DROP CONSTRAINT paciente_pkey;
       public            postgres    false    229            �           2606    57508    usuario usuario_pkey 
   CONSTRAINT     R   ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (id);
 >   ALTER TABLE ONLY public.usuario DROP CONSTRAINT usuario_pkey;
       public            postgres    false    235            �           2620    101292 1   medical_consulta medical_consulta_history_trigger    TRIGGER     �   CREATE TRIGGER medical_consulta_history_trigger AFTER INSERT OR DELETE OR UPDATE ON public.medical_consulta FOR EACH ROW EXECUTE FUNCTION public.log_medical_consulta_history();
 J   DROP TRIGGER medical_consulta_history_trigger ON public.medical_consulta;
       public          postgres    false    252    231            �           2606    57478    medical_consulta fk_doctor    FK CONSTRAINT     �   ALTER TABLE ONLY public.medical_consulta
    ADD CONSTRAINT fk_doctor FOREIGN KEY (id_doctor) REFERENCES public.doctor(id) ON UPDATE CASCADE ON DELETE CASCADE;
 D   ALTER TABLE ONLY public.medical_consulta DROP CONSTRAINT fk_doctor;
       public          postgres    false    227    4818    231            �           2606    57509    usuario fk_doctor    FK CONSTRAINT     �   ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT fk_doctor FOREIGN KEY (id_doctor) REFERENCES public.doctor(id) ON UPDATE CASCADE ON DELETE CASCADE;
 ;   ALTER TABLE ONLY public.usuario DROP CONSTRAINT fk_doctor;
       public          postgres    false    235    4818    227            �           2606    57454    doctor fk_especialidad    FK CONSTRAINT     �   ALTER TABLE ONLY public.doctor
    ADD CONSTRAINT fk_especialidad FOREIGN KEY (id_especialidad) REFERENCES public.especialidad(id) ON UPDATE CASCADE ON DELETE CASCADE;
 @   ALTER TABLE ONLY public.doctor DROP CONSTRAINT fk_especialidad;
       public          postgres    false    4812    225    227            �           2606    57497 $   detalle_consulta fk_medical_consulta    FK CONSTRAINT     �   ALTER TABLE ONLY public.detalle_consulta
    ADD CONSTRAINT fk_medical_consulta FOREIGN KEY (id_medcon) REFERENCES public.medical_consulta(id) ON UPDATE CASCADE ON DELETE CASCADE;
 N   ALTER TABLE ONLY public.detalle_consulta DROP CONSTRAINT fk_medical_consulta;
       public          postgres    false    4824    231    233            �           2606    57483    medical_consulta fk_paciente    FK CONSTRAINT     �   ALTER TABLE ONLY public.medical_consulta
    ADD CONSTRAINT fk_paciente FOREIGN KEY (id_paciente) REFERENCES public.paciente(id) ON UPDATE CASCADE ON DELETE CASCADE;
 F   ALTER TABLE ONLY public.medical_consulta DROP CONSTRAINT fk_paciente;
       public          postgres    false    229    4822    231            �   �  x��S=o�0�����4(Krb9�gs?�6[��uVX��JJ��%;t(��?֣��ҡ���{�{��!�`�������Â����+_=5d4��E8I����8�aj���:���*^�Uri3W9�Pk�-���$�R7���vU����ow���ݓ�L����JSȮ�֮����5���N��-9�$�$�@�㰤@е�'#�׋��n�֥*
�?��dcxO��;Q�o8��΢��NE'��;o�����JM(Dk�QO��*8'4������G�p��b�ka~�9!c���"X*:�/�"��ҽE������n�|`/nw�� l����e�=Rö�E<�E�g�	��n�,�f�;�v�מ6��K�O���'�u<}�ũ�6�g��a��A2�p�M�yC7B�&lZ�.4��RjE���QE�V��B�_֟hRM�C5���
��Jr������a+d�n/���J�?�Cr��}�NoV�J���;.�      w      x������ � �         �   x�]�1�0���=�Mӄ�,H�Y<d�j��==�	�������d�7\�>����m��{t�9P(�(m6�s�u��D�8<c�܀0�k\Y�A��c��ʬ?TH���t/%.1���~��6D�[�,�      y      x������ � �      }   (   x�3��MM�L��2� 2K��L9��R2�b���� ��	J      �   _   x�U���0CϰK*l�Ow��s��DJ�/<=]8�xXD٤'�uwKBD9&Z=�iUi�G�[�>��x��8t����Q����Y������~-�       �   M  x�u�=NA���)� ���f�#�#�X�PPX�`�=<�sv	h��f)����Z!x��^7�`d]���W�sJ��@����	Ώ��3���
3)�2��|o��ΩU,�2vN�ۿ��~��ZT��T?>��3����d�yX,U�j�B�Q��� :����
NQ�5w0f8.��έ&,V:�Al����Ba�>��Ö�bBf�R	���:����8zH@�ü�+l}�	���v@1�u����)���Ki�69�4oǐX���"��%4+�p	U�J��G���Ʋb�#��ڵ���+9�6�v��]�uq1/��~�I�D��yK�4�����      �   K   x�3�t-K-�,�,.H�44007471�	�t�w����2��M,�L�t��M�ⴴ0*106��t�000����� bM      u      x������ � �      �   l   x�3��442�t�O.�/��4qML�`"F�f�*F�*f*Y^!f�ƾ!�Ea��~!��Y��Qz�fU>���e9>^����U�i^^��AUn�ᜎ)��y\1z\\\ J�     