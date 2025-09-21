ALTER TABLE usuario ADD COLUMN IF NOT EXISTS id_user   BIGINT;
ALTER TABLE usuario ADD COLUMN IF NOT EXISTS nome_user VARCHAR(255);
ALTER TABLE usuario ADD COLUMN IF NOT EXISTS phone     VARCHAR(20);

DO $$
BEGIN
  IF EXISTS (SELECT 1 FROM information_schema.columns
             WHERE table_name='usuario' AND column_name='id') THEN
UPDATE usuario SET id_user = id WHERE id_user IS NULL;
END IF;

  IF EXISTS (SELECT 1 FROM information_schema.columns
             WHERE table_name='usuario' AND column_name='name') THEN
UPDATE usuario SET nome_user = name WHERE nome_user IS NULL;
END IF;
END$$;

DO $$
BEGIN
  IF NOT EXISTS (
    SELECT 1 FROM information_schema.sequences
    WHERE sequence_schema='public' AND sequence_name='usuario_id_user_seq'
  ) THEN
CREATE SEQUENCE public.usuario_id_user_seq START WITH 1 INCREMENT BY 1;
END IF;
END$$;

DO $$
DECLARE
v_max BIGINT;
BEGIN
SELECT COALESCE(MAX(id_user), 0) INTO v_max FROM usuario;

IF v_max = 0 THEN
    PERFORM setval('public.usuario_id_user_seq', 1, false);
ELSE
    PERFORM setval('public.usuario_id_user_seq', v_max, true);
END IF;
END$$;

ALTER TABLE public.usuario
    ALTER COLUMN id_user SET DEFAULT nextval('public.usuario_id_user_seq');

ALTER SEQUENCE public.usuario_id_user_seq
    OWNED BY public.usuario.id_user;

ALTER TABLE public.usuario
    ALTER COLUMN id_user SET NOT NULL;

DO $$
BEGIN
  IF NOT EXISTS (
    SELECT 1 FROM information_schema.table_constraints
    WHERE table_name='usuario' AND constraint_name='pk_usuario'
  ) THEN
ALTER TABLE usuario ADD CONSTRAINT pk_usuario PRIMARY KEY (id_user);
END IF;
END$$;

ALTER TABLE usuario DROP COLUMN IF EXISTS avatar_url;
ALTER TABLE usuario DROP COLUMN IF EXISTS score;
ALTER TABLE usuario DROP COLUMN IF EXISTS id;
ALTER TABLE usuario DROP COLUMN IF EXISTS name;

ALTER TABLE patio ALTER COLUMN metragem_zonaa SET NOT NULL;
ALTER TABLE patio ALTER COLUMN metragem_zonab SET NOT NULL;

ALTER TABLE moto ALTER COLUMN placa TYPE VARCHAR(7);
