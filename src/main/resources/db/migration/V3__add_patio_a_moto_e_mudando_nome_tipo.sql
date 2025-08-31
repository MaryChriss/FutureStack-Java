-- 1. Adiciona a nova coluna
ALTER TABLE zona
    ADD tipo_zona VARCHAR(255);

-- 2. Atualiza os valores com base na coluna 'nome' (usando ainda o valor antigo da coluna 'tipo', se quiser)
UPDATE zona
SET tipo_zona = CASE
                    WHEN nome = 'Zona A' THEN 'A'
                    WHEN nome = 'Zona B' THEN 'B'
                    ELSE tipo_zona
    END;

-- 3. Remove a coluna antiga (tipo)
ALTER TABLE zona
DROP COLUMN tipo;

-- 4. Adiciona a FK do pátio na tabela moto
ALTER TABLE moto
    ADD patio_id BIGINT;

-- 5. Atualiza o campo patio_id com base na zona relacionada
UPDATE moto
SET patio_id = (
    SELECT z.patio_id
    FROM zona z
    WHERE z.id = moto.zona_id
);

-- 6. Cria a foreign key
ALTER TABLE moto
    ADD CONSTRAINT FK_MOTO_ON_PATIO FOREIGN KEY (patio_id) REFERENCES patio (id);