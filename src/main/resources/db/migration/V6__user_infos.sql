-- 1️⃣ Remover a PK antiga (descobrir o nome exato da constraint antes)
DECLARE
    v_pk_name VARCHAR2(100);
BEGIN
    SELECT constraint_name
    INTO v_pk_name
    FROM user_constraints
    WHERE table_name = 'USUARIO'
      AND constraint_type = 'P';

    EXECUTE IMMEDIATE 'ALTER TABLE usuario DROP CONSTRAINT ' || v_pk_name;
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        NULL; -- se não tiver PK, apenas ignora
END;
/

-- 2️⃣ Adicionar novas colunas
ALTER TABLE usuario ADD (id_user NUMBER, nome_user VARCHAR2(255), phone VARCHAR2(20));

-- 3️⃣ Copiar dados antigos
UPDATE usuario SET id_user = id WHERE id_user IS NULL;
UPDATE usuario SET nome_user = name WHERE nome_user IS NULL;

-- 4️⃣ Criar sequence para novos registros
CREATE SEQUENCE usuario_id_user_seq START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;

-- 5️⃣ Preencher com valores novos caso id_user esteja nulo
UPDATE usuario SET id_user = usuario_id_user_seq.NEXTVAL WHERE id_user IS NULL;

-- 6️⃣ Definir id_user como NOT NULL e chave primária
ALTER TABLE usuario MODIFY (id_user NOT NULL);
ALTER TABLE usuario ADD CONSTRAINT pk_usuario_id_user PRIMARY KEY (id_user);

-- 7️⃣ Remover colunas antigas
ALTER TABLE usuario DROP COLUMN avatar_url;
ALTER TABLE usuario DROP COLUMN score;
ALTER TABLE usuario DROP COLUMN id;
ALTER TABLE usuario DROP COLUMN name;

-- 8️⃣ Ajustar colunas obrigatórias em outras tabelas
ALTER TABLE patio MODIFY (metragem_zonaa NOT NULL);
ALTER TABLE patio MODIFY (metragem_zonab NOT NULL);
ALTER TABLE moto MODIFY (placa VARCHAR2(7));
