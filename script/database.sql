CREATE TABLE
  public.test (
    id serial NOT NULL,
    value character varying(255) NOT NULL
  );

ALTER TABLE
  public.test
ADD
  CONSTRAINT test_pkey PRIMARY KEY (id);

--

CREATE TABLE
  public.persona (
    id serial NOT NULL,
    nombre character varying(255) NOT NULL,
    genero character varying(10) NULL,
    edad smallint NULL,
    direccion character varying(255) NOT NULL,
    telefono character varying(20) NOT NULL,
    dni character varying(20) NOT NULL
  );

ALTER TABLE
  public.persona
ADD
  CONSTRAINT persona_pkey PRIMARY KEY (id);

--

CREATE TABLE
  public.cliente (
    id integer NOT NULL,
    password character varying(255) NOT NULL,
    estado boolean NOT NULL
  );

ALTER TABLE
  public.cliente
ADD
  CONSTRAINT cliente_pkey PRIMARY KEY (id);

--

CREATE TABLE
  public.cuenta (
    id serial NOT NULL,
    numero character varying(30) NOT NULL,
    tipo character varying(10) NOT NULL,
    saldo numeric NOT NULL,
    cliente_id integer NOT NULL,
    estado boolean NOT NULL
  );

ALTER TABLE
  public.cuenta
ADD
  CONSTRAINT cuenta_pkey PRIMARY KEY (id);

--

CREATE TABLE
  public.movimientos (
    id serial NOT NULL,
    fecha timestamp without time zone NOT NULL,
    valor numeric NOT NULL,
    saldo numeric NOT NULL,
    cuenta_id integer NOT NULL
  );

ALTER TABLE
  public.movimientos
ADD
  CONSTRAINT movimientos_pkey PRIMARY KEY (id);


--

ALTER TABLE
  "public"."cliente"
ADD
  CONSTRAINT "cliente_relation_persona" FOREIGN KEY ("id") REFERENCES "public"."persona" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE
  "public"."cuenta"
ADD
  CONSTRAINT "cuenta_relation_cliente" FOREIGN KEY ("cliente_id") REFERENCES "public"."cliente" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE
  "public"."movimientos"
ADD
  CONSTRAINT "movimientos_relation_cuenta" FOREIGN KEY ("cuenta_id") REFERENCES "public"."cuenta" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION;

-- 

CREATE UNIQUE INDEX "persona_index_dni" on "public"."persona" ("dni" ASC);
CREATE UNIQUE INDEX "cuenta_index_numero" on "public"."cuenta" ("numero" ASC);
CREATE INDEX "movimientos_index_cuenta" on "public"."movimientos" ("cuenta_id" ASC);