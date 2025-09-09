ALTER TABLE moto
ALTER COLUMN placa TYPE VARCHAR(255);

UPDATE moto m
SET patio_id = z.patio_id
    FROM zona z
WHERE m.patio_id IS NULL
  AND m.zona_id = z.id;

ALTER TABLE moto
    ALTER COLUMN patio_id SET NOT NULL;

INSERT INTO patio (nome, quantidade_vagas, metragem_zonaa, metragem_zonab)
VALUES ('Filial São Paulo', 60, 200.0, 120.0),
       ('Filial Rio',       40, 150.0, 100.0)
    ON CONFLICT (nome) DO NOTHING;

INSERT INTO zona (nome, tipo_zona, metragem, patio_id)
VALUES
    ('Zona A', 'A', 200.0, (SELECT id FROM patio WHERE nome = 'Filial São Paulo')),
    ('Zona B', 'B', 120.0, (SELECT id FROM patio WHERE nome = 'Filial São Paulo'))
    ON CONFLICT (nome) DO NOTHING;

INSERT INTO moto (modelo, placa, status, zona_id, patio_id)
VALUES
    ('Honda CG 160',   'ABC1234', 0,
     (SELECT id FROM zona WHERE nome = 'Zona A'),
     (SELECT patio_id FROM zona WHERE nome = 'Zona A')),
    ('Yamaha Fazer 250','XYZ5678', 1,
     (SELECT id FROM zona WHERE nome = 'Zona B'),
     (SELECT patio_id FROM zona WHERE nome = 'Zona B')),
    ('Honda Biz 125',  'JKL9123', 1,
     (SELECT id FROM zona WHERE nome = 'Zona A'),
     (SELECT patio_id FROM zona WHERE nome = 'Zona A')),
    ('Yamaha XTZ 150', 'QWE3456', 0,
     (SELECT id FROM zona WHERE nome = 'Zona B'),
     (SELECT patio_id FROM zona WHERE nome = 'Zona B')),
    ('Suzuki Burgman', 'RTY7890', 0,
     (SELECT id FROM zona WHERE nome = 'Zona A'),
     (SELECT patio_id FROM zona WHERE nome = 'Zona A')),
    ('Haojue DK 150',  'UIO4567', 0,
     (SELECT id FROM zona WHERE nome = 'Zona B'),
     (SELECT patio_id FROM zona WHERE nome = 'Zona B')),
    ('Shineray XY 50', 'MNB3210', 2,
     (SELECT id FROM zona WHERE nome = 'Zona A'),
     (SELECT patio_id FROM zona WHERE nome = 'Zona A')),
    ('Honda Pop 110i', 'ZXC9876', 1,
     (SELECT id FROM zona WHERE nome = 'Zona B'),
     (SELECT patio_id FROM zona WHERE nome = 'Zona B')),
    ('Yamaha NMax 160','ASD6543', 1,
     (SELECT id FROM zona WHERE nome = 'Zona A'),
     (SELECT patio_id FROM zona WHERE nome = 'Zona A')),
    ('Honda PCX 150',  'FGH8520', 2,
     (SELECT id FROM zona WHERE nome = 'Zona B'),
     (SELECT patio_id FROM zona WHERE nome = 'Zona B'))
    ON CONFLICT (placa) DO NOTHING;

INSERT INTO gateway (mac_address, descricao, localid_zona_id)
VALUES
    ('00:11:22:33:44:55', 'Pátio Central',      (SELECT id FROM zona WHERE nome = 'Zona A')),
    ('AA:BB:CC:DD:EE:FF', 'Manutenção Lateral', (SELECT id FROM zona WHERE nome = 'Zona B'))
    ON CONFLICT (mac_address) DO NOTHING;

INSERT INTO evento_wifi (moto_id, gateway_id_gateway, rssits_evento)
SELECT m.id, g.id_gateway, -45
FROM moto m, gateway g
WHERE m.placa = 'ABC1234'
  AND g.mac_address = '00:11:22:33:44:55'
  AND NOT EXISTS (
    SELECT 1 FROM evento_wifi ew
    WHERE ew.moto_id = m.id AND ew.gateway_id_gateway = g.id_gateway AND ew.rssits_evento = -45
);

INSERT INTO evento_wifi (moto_id, gateway_id_gateway, rssits_evento)
SELECT m.id, g.id_gateway, -60
FROM moto m, gateway g
WHERE m.placa = 'XYZ5678'
  AND g.mac_address = 'AA:BB:CC:DD:EE:FF'
  AND NOT EXISTS (
    SELECT 1 FROM evento_wifi ew
    WHERE ew.moto_id = m.id AND ew.gateway_id_gateway = g.id_gateway AND ew.rssits_evento = -60
);

INSERT INTO evento_alpr (moto_id, placa_lida, url_imagem, ts_alpr)
SELECT m.id, 'ABC1234', 'http://camera1/imagem.jpg', NOW()
FROM moto m
WHERE m.placa = 'ABC1234'
  AND NOT EXISTS (
    SELECT 1 FROM evento_alpr ea
    WHERE ea.moto_id = m.id
      AND ea.placa_lida = 'ABC1234'
      AND ea.url_imagem = 'http://camera1/imagem.jpg'
);

INSERT INTO evento_alpr (moto_id, placa_lida, url_imagem, ts_alpr)
SELECT m.id, 'XYZ5678', 'http://camera2/imagem.jpg', NOW()
FROM moto m
WHERE m.placa = 'XYZ5678'
  AND NOT EXISTS (
    SELECT 1 FROM evento_alpr ea
    WHERE ea.moto_id = m.id
      AND ea.placa_lida = 'XYZ5678'
      AND ea.url_imagem = 'http://camera2/imagem.jpg'
);
