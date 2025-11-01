ALTER TABLE moto MODIFY placa VARCHAR2(255);

UPDATE moto m
SET patio_id = (SELECT z.patio_id FROM zona z WHERE z.id = m.zona_id)
WHERE m.patio_id IS NULL;

ALTER TABLE moto MODIFY (patio_id NOT NULL);

BEGIN
    INSERT INTO patio (nome, quantidade_vagas, metragem_zonaa, metragem_zonab)
    SELECT 'Filial São Paulo', 60, 200, 120 FROM dual
    WHERE NOT EXISTS (SELECT 1 FROM patio WHERE nome = 'Filial São Paulo');

    INSERT INTO patio (nome, quantidade_vagas, metragem_zonaa, metragem_zonab)
    SELECT 'Filial Rio', 40, 150, 100 FROM dual
    WHERE NOT EXISTS (SELECT 1 FROM patio WHERE nome = 'Filial Rio');
END;
/

BEGIN
    INSERT INTO zona (nome, tipo_zona, metragem, patio_id)
    SELECT 'Zona A', 'A', 200, (SELECT id FROM patio WHERE nome = 'Filial São Paulo') FROM dual
    WHERE NOT EXISTS (SELECT 1 FROM zona WHERE nome = 'Zona A');

    INSERT INTO zona (nome, tipo_zona, metragem, patio_id)
    SELECT 'Zona B', 'B', 120, (SELECT id FROM patio WHERE nome = 'Filial São Paulo') FROM dual
    WHERE NOT EXISTS (SELECT 1 FROM zona WHERE nome = 'Zona B');
END;
/
