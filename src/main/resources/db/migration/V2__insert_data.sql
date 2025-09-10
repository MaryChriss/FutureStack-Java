-- Patios
INSERT INTO patio (nome, quantidade_vagas, metragem_zonaa, metragem_zonab)
VALUES ('Filial São Paulo', 60, 200.0, 120.0),
       ('Filial Rio', 40, 150.0, 100.0);

-- Zonas
INSERT INTO zona (nome, tipo, metragem, patio_id)
VALUES
    ('Zona A', 'A', 200.0, (SELECT id FROM patio WHERE nome = 'Filial São Paulo')),
    ('Zona B', 'B', 150.0, (SELECT id FROM patio WHERE nome = 'Filial São Paulo'));

-- Motos
INSERT INTO moto (modelo, placa, status, zona_id)
VALUES
    ('Honda CG 160', 'ABC1234', 0, (SELECT id FROM zona WHERE nome = 'Zona A')),
    ('Yamaha Fazer 250', 'XYZ5678', 1, (SELECT id FROM zona WHERE nome = 'Zona B')),
    ('Honda Biz 125', 'JKL9123', 1, (SELECT id FROM zona WHERE nome = 'Zona A')),
    ('Yamaha XTZ 150', 'QWE3456', 0, (SELECT id FROM zona WHERE nome = 'Zona B')),
    ('Suzuki Burgman', 'RTY7890', 0, (SELECT id FROM zona WHERE nome = 'Zona A')),
    ('Haojue DK 150', 'UIO4567', 0, (SELECT id FROM zona WHERE nome = 'Zona B')),
    ('Shineray XY 50', 'MNB3210', 2, (SELECT id FROM zona WHERE nome = 'Zona A')),
    ('Honda Pop 110i', 'ZXC9876', 1, (SELECT id FROM zona WHERE nome = 'Zona B')),
    ('Yamaha NMax 160', 'ASD6543', 1, (SELECT id FROM zona WHERE nome = 'Zona A')),
    ('Honda PCX 150', 'FGH8520', 2, (SELECT id FROM zona WHERE nome = 'Zona B'));

INSERT INTO gateway (mac_address, descricao, localid_zona_id)
VALUES
    ('00:11:22:33:44:55', 'Pátio Central', (SELECT id FROM zona WHERE nome = 'Zona A')),
    ('AA:BB:CC:DD:EE:FF', 'Manutenção Lateral', (SELECT id FROM zona WHERE nome = 'Zona B'));

-- Eventos Wi-Fi
INSERT INTO evento_wifi (moto_id, gateway_id_gateway, rssits_evento)
VALUES
    (
        (SELECT id FROM moto WHERE placa = 'ABC1234'),
        (SELECT id_gateway FROM gateway WHERE mac_address = '00:11:22:33:44:55'),
        -45
    ),
    (
        (SELECT id FROM moto WHERE placa = 'XYZ5678'),
        (SELECT id_gateway FROM gateway WHERE mac_address = 'AA:BB:CC:DD:EE:FF'),
        -60
    );

-- Eventos ALPR
INSERT INTO evento_alpr (moto_id, placa_lida, url_imagem, ts_alpr)
VALUES
    (
        (SELECT id FROM moto WHERE placa = 'ABC1234'),
        'ABC1234',
        'http://camera1/imagem.jpg',
        NOW()
    ),
    (
        (SELECT id FROM moto WHERE placa = 'XYZ5678'),
        'XYZ5678',
        'http://camera2/imagem.jpg',
        NOW()
    );
