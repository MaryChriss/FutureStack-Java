ALTER TABLE zona ADD tipo_zona VARCHAR2(255);

UPDATE zona
SET tipo_zona = CASE
                    WHEN nome = 'Zona A' THEN 'A'
                    WHEN nome = 'Zona B' THEN 'B'
                    ELSE tipo_zona
    END;

ALTER TABLE zona DROP COLUMN tipo;

ALTER TABLE moto ADD patio_id NUMBER;

UPDATE moto m
SET patio_id = (SELECT z.patio_id FROM zona z WHERE z.id = m.zona_id);

ALTER TABLE moto ADD CONSTRAINT fk_moto_patio FOREIGN KEY (patio_id) REFERENCES patio(id);
