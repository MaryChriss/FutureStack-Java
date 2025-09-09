ALTER TABLE zona
    ADD tipo_zona VARCHAR(255);

UPDATE zona
SET tipo_zona = CASE
                    WHEN nome = 'Zona A' THEN 'A'
                    WHEN nome = 'Zona B' THEN 'B'
                    ELSE tipo_zona
    END;

ALTER TABLE zona
DROP COLUMN tipo;

ALTER TABLE moto
    ADD patio_id BIGINT;

UPDATE moto
SET patio_id = (
    SELECT z.patio_id
    FROM zona z
    WHERE z.id = moto.zona_id
);

ALTER TABLE moto
    ADD CONSTRAINT FK_MOTO_ON_PATIO FOREIGN KEY (patio_id) REFERENCES patio (id);