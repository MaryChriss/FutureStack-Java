ALTER TABLE futurestack1.usuario ADD COLUMN IF NOT EXISTS id_user   BIGINT;
ALTER TABLE futurestack1.usuario ADD COLUMN IF NOT EXISTS nome_user VARCHAR(255);
ALTER TABLE futurestack1.usuario ADD COLUMN IF NOT EXISTS phone     VARCHAR(20);

DO $$
BEGIN
  IF EXISTS (SELECT 1 FROM information_schema.columns
             WHERE table_name='usuario' AND column_name='id' AND table_schema='futurestack1') THEN
UPDATE futurestack1.usuario SET id_user = id WHERE id_user IS NULL;
END IF;

  IF EXISTS (SELECT 1 FROM information_schema.columns
             WHERE table_name='usuario' AND column_name='name' AND table_schema='futurestack1') THEN
UPDATE futurestack1.usuario SET nome_user = name WHERE nome_user IS NULL;
END IF;
END$$;

DO $$
BEGIN
  IF NOT EXISTS (
    SELECT 1 FROM information_schema.sequences
    WHERE sequence_schema='futurestack1' AND sequence_name='usuario_id_user_seq'
  ) THEN
CREATE SEQUENCE futurestack1.usuario_id_user_seq START WITH 1 INCREMENT BY 1;
END IF;
END$$;

DO $$
DECLARE
v_max BIGINT;
BEGIN
SELECT COALESCE(MAX(id_user), 0) INTO v_max FROM futurestack1.usuario;

IF v_max = 0 THEN
      PERFORM setval('futurestack1.usuario_id_user_seq', 1, false);
ELSE
      PERFORM setval('futurestack1.usuario_id_user_seq', v_max, true);
END IF;
END$$;

ALTER TABLE futurestack1.usuario
    ALTER COLUMN id_user SET DEFAULT nextval('futurestack1.usuario_id_user_seq');

ALTER SEQUENCE futurestack1.usuario_id_user_seq
    OWNED BY futurestack1.usuario.id_user;

ALTER TABLE futurestack1.usuario
    ALTER COLUMN id_user SET NOT NULL;

DO $$
BEGIN
  IF NOT EXISTS (
    SELECT 1 FROM information_schema.table_constraints
    WHERE table_schema='futurestack1'
      AND table_name='usuario'
      AND constraint_name='pk_usuario'
  ) THEN
ALTER TABLE futurestack1.usuario ADD CONSTRAINT pk_usuario PRIMARY KEY (id_user);
END IF;
END$$;

ALTER TABLE futurestack1.usuario DROP COLUMN IF EXISTS avatar_url;
ALTER TABLE futurestack1.usuario DROP COLUMN IF EXISTS score;
ALTER TABLE futurestack1.usuario DROP COLUMN IF EXISTS id;
ALTER TABLE futurestack1.usuario DROP COLUMN IF EXISTS name;

ALTER TABLE futurestack1.patio ALTER COLUMN metragem_zonaa SET NOT NULL;
ALTER TABLE futurestack1.patio ALTER COLUMN metragem_zonab SET NOT NULL;

ALTER TABLE futurestack1.moto ALTER COLUMN placa TYPE VARCHAR(7);
