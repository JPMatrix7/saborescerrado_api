-- This file allow to write SQL commands that will be emitted in test and dev.
-- The commands are commented as their support depends of the database

-- Estados
INSERT INTO estado (nome, sigla)
VALUES ('Acre', 'AC'),
       ('Alagoas', 'AL'),
       ('Amapá', 'AP'),
       ('Amazonas', 'AM'),
       ('Bahia', 'BA'),
       ('Ceará', 'CE'),
       ('Distrito Federal', 'DF'),
       ('Espírito Santo', 'ES'),
       ('Goiás', 'GO'),
       ('Maranhão', 'MA'),
       ('Mato Grosso', 'MT'),
       ('Mato Grosso do Sul', 'MS'),
       ('Minas Gerais', 'MG'),
       ('Pará', 'PA'),
       ('Paraíba', 'PB'),
       ('Paraná', 'PR'),
       ('Pernambuco', 'PE'),
       ('Piauí', 'PI'),
       ('Rio de Janeiro', 'RJ'),
       ('Rio Grande do Norte', 'RN'),
       ('Rio Grande do Sul', 'RS'),
       ('Rondônia', 'RO'),
       ('Roraima', 'RR'),
       ('Santa Catarina', 'SC'),
       ('São Paulo', 'SP'),
       ('Sergipe', 'SE'),
       ('Tocantins', 'TO');

-- Cidades (estado é foreign key sem _id)
INSERT INTO cidade (nome, estado)
VALUES ('Palmas', 27),
       ('Anápolis', 9),
       ('Goiânia', 9),
       ('Araguaína', 27),
       ('Rio de Janeiro', 19),
       ('São Paulo', 25),
       ('Curitiba', 16),
       ('Porto Alegre', 21),
       ('Fortaleza', 6),
       ('Salvador', 5),
       ('Belo Horizonte', 13),
       ('Vitória', 8),
       ('Manaus', 4),
       ('Belém', 14),
       ('Recife', 17),
       ('Natal', 20),
       ('João Pessoa', 15),
       ('Maceió', 2),
       ('Aracaju', 26),
       ('Boa Vista', 23),
       ('Macapá', 3),
       ('São Luís', 10),
       ('Cuiabá', 11),
       ('Campo Grande', 12),
       ('Florianópolis', 24),
       ('Porto Velho', 22),
       ('Rio Branco', 1),
       ('Teresina', 18),
       ('Brasília', 7),
       ('Sobral', 6),
       ('Caxias do Sul', 21),
       ('Dourados', 12);

-- Categorias
INSERT INTO categoria (nome)
VALUES ('Premium'),
       ('Artesanal'),
       ('Tradicional'),
       ('Cremoso'),
       ('Especial');

-- Sabores (somente nome)
INSERT INTO sabor (nome)
VALUES ('Pequi'),
       ('Baru'),
       ('Buriti'),
       ('Cagaita'),
       ('Araticum'),
       ('Jatobá');

-- Ingredientes (nome, quantidade, unidadeMedida)
INSERT INTO ingrediente (nome, quantidade, unidademedida)
VALUES ('Cachaça Artesanal', 1000, 'ml'),
       ('Açúcar Orgânico', 500, 'g'),
       ('Essência Natural', 50, 'ml'),
       ('Leite Condensado', 395, 'g'),
       ('Creme de Leite', 200, 'ml'),
       ('Especiarias', 20, 'g'),
       ('Frutas Frescas', 500, 'g');

-- Embalagens (volume, material)
INSERT INTO embalagem (volume, material)
VALUES (750, 'Vidro'),
       (500, 'Vidro'),
       (50, 'Vidro'),
       (1000, 'Vidro'),
       (375, 'Vidro');

-- Premiações (evento, ano, medalha)
INSERT INTO premiacao (evento, ano, medalha)
VALUES ('Festival do Cerrado', 2023, 'Ouro'),
       ('Concurso Nacional de Licores', 2024, 'Ouro'),
       ('Prêmio Centro-Oeste', 2023, 'Prata'),
       ('Festival de Bebidas Artesanais', 2024, 'Bronze');

-- Parceiros Comerciais (cnpj, razaoSocial, nomeFantasia, email, telefone)
INSERT INTO parceirocomercial (cnpj, razaosocial, nomefantasia, email, telefone)
VALUES ('12.345.678/0001-90', 'Distribuidora Cerrado Ltda', 'Distribuidora Cerrado', 'contato@cerrado.com.br', '62 3333-4444'),
       ('98.765.432/0001-11', 'Fazenda Baru Orgânico ME', 'Fazenda Baru', 'fazenda@baru.com.br', '62 3555-6666'),
       ('11.222.333/0001-44', 'Cooperativa do Pequi', 'Coop Pequi', 'coop@pequi.com.br', '63 3777-8888');

-- Safras de Licor (anoSafra, fazenda, qualidade, cidade)
INSERT INTO safralicor (anosafra, fazenda, qualidade, cidade)
VALUES ('2023-01-15', 'Fazenda São José', 'Premium', 3),
       ('2024-02-20', 'Fazenda Boa Vista', 'Especial', 2),
       ('2023-06-10', 'Fazenda do Cerrado', 'Tradicional', 1),
       ('2024-03-05', 'Fazenda Santa Clara', 'Premium', 4);

-- Usuários (tabela base JOINED inheritance)
INSERT INTO usuario (id, nome, email, senha)
VALUES (1, 'João Silva', 'joao.silva@email.com', 'senha123'),
       (2, 'Maria Santos', 'maria.santos@email.com', 'senha456'),
       (3, 'Pedro Oliveira', 'pedro.oliveira@email.com', 'senha789'),
       (4, 'Ana Costa', 'ana.costa@email.com', 'senha321'),
       (5, 'Sabores do Cerrado Ltda', 'contato@saborescerrado.com.br', 'empresa123'),
       (6, 'Distribuidora Premium ME', 'vendas@premium.com.br', 'empresa456');

-- Pessoas Físicas (herança JOINED)
INSERT INTO pessoafisica (id, cpf, datanascimento, sobrenome)
VALUES (1, '111.222.333-44', '1990-05-15', 'Silva'),
       (2, '555.666.777-88', '1985-08-20', 'Santos'),
       (3, '999.000.111-22', '1992-03-10', 'Oliveira'),
       (4, '333.444.555-66', '1988-11-25', 'Costa');

-- Pessoas Jurídicas (herança JOINED)
INSERT INTO pessoajuridica (id, cnpj, razaosocial, nomefantasia)
VALUES (5, '12.345.678/0001-90', 'Sabores do Cerrado Comércio de Bebidas Ltda', 'Sabores do Cerrado'),
       (6, '98.765.432/0001-11', 'Distribuidora Premium de Bebidas ME', 'Premium Distribuidora');

-- Telefones (codigoArea, numero - SEM usuario pois não há @JoinColumn)
INSERT INTO telefone (codigoarea, numero)
VALUES ('63', '98888-1111'),
       ('62', '99999-2222'),
       ('62', '97777-3333'),
       ('61', '96666-4444'),
       ('62', '3333-5555'),
       ('61', '3444-6666');

-- Endereços (cidade é foreign key sem _id - SEM usuario)
INSERT INTO endereco (logradouro, numero, complemento, bairro, cep, cidade)
VALUES ('Rua das Flores', '123', 'Apt 101', 'Centro', '74000-100', 3);

INSERT INTO endereco (logradouro, numero, complemento, bairro, cep, cidade)
VALUES ('Av. Anhanguera', '456', null, 'Setor Central', '74000-200', 3);

INSERT INTO endereco (logradouro, numero, complemento, bairro, cep, cidade)
VALUES ('Rua do Comercio', '789', 'Sala 10', 'Jundiai', '74000-150', 2);

INSERT INTO endereco (logradouro, numero, complemento, bairro, cep, cidade)
VALUES ('Quadra 10', '15', null, 'Asa Sul', '70000-100', 29);

INSERT INTO endereco (logradouro, numero, complemento, bairro, cep, cidade)
VALUES ('Rua Comercial', '200', 'Loja 5', 'Setor Bueno', '74000-300', 3);

INSERT INTO endereco (logradouro, numero, complemento, bairro, cep, cidade)
VALUES ('Av. Principal', '300', null, 'Asa Norte', '70000-200', 29);

-- Cartões (numeroCartao, nomeTitular, validade, cvv - SEM usuario)
INSERT INTO cartao (numerocartao, nometitular, validade, cvv)
VALUES ('1111222233334444', 'JOAO SILVA', '12/2026', '123'),
       ('5555666677778888', 'MARIA SANTOS', '06/2027', '456'),
       ('9999000011112222', 'PEDRO OLIVEIRA', '03/2026', '789'),
       ('3333444455556666', 'ANA COSTA', '09/2027', '321');

-- Licores (tipo é enum ORDINAL: 0=FRUTADO, 1=MEL)
INSERT INTO licor (nome, descricao, preco, tipo, teoralcoolico, estoque, visivel, sabor, embalagem, safra, parceiro_comercial)
VALUES ('Licor de Pequi Premium', 'Licor artesanal de pequi com toque especial', 89.90, 0, 18.5, 150, true, 1, 2, 1, 1),
       ('Licor de Baru Tradicional', 'Sabor único da castanha de baru', 65.90, 0, 15.0, 200, true, 2, 1, 2, 2),
       ('Licor de Buriti Cremoso', 'Licor cremoso com polpa de buriti', 72.50, 0, 16.0, 100, true, 3, 1, 1, 1),
       ('Licor de Cagaita', 'Refrescante licor de cagaita', 58.90, 0, 14.5, 180, true, 4, 1, 3, 3),
       ('Licor de Araticum com Mel', 'Edição especial com araticum e mel', 125.00, 1, 20.0, 50, true, 5, 5, 4, 2),
       ('Licor de Jatobá', 'Sabor marcante do jatobá do cerrado', 68.00, 0, 16.5, 120, true, 6, 1, 2, 3),
       ('Miniatura Pequi', 'Miniatura perfeita para presentear', 15.90, 0, 18.5, 300, true, 1, 3, 1, 1);

-- Associação Licor-Categoria (muitos-para-muitos)
INSERT INTO licor_categoria (licor_id, categoria_id)
VALUES (1, 2),
       (2, 3),
       (3, 4),
       (4, 1),
       (5, 5),
       (5, 2),
       (6, 3),
       (7, 1);

-- Associação Licor-Ingrediente (muitos-para-muitos)
INSERT INTO licor_ingrediente (licor_id, ingrediente_id)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (2, 1),
       (2, 2),
       (2, 4),
       (3, 1),
       (3, 5),
       (3, 7),
       (4, 1),
       (4, 2),
       (5, 1),
       (5, 2),
       (5, 6);

-- Associação Licor-Premiação (muitos-para-muitos)
INSERT INTO licor_premiacao (licor_id, premiacao_id)
VALUES (1, 1),
       (1, 2),
       (2, 3),
       (5, 2),
       (5, 4);

-- Compras (status: 0=AGUARDANDO_PAGAMENTO, formaPagamento: 0=CARTAO_CREDITO)
INSERT INTO compra (datacompra, valortotal, status, formapagamento, codigorastreio, pago)
VALUES ('2024-10-15 14:30:00', 155.80, 4, 0, 'BR123456789AA', true),
       ('2024-10-20 10:15:00', 289.70, 3, 0, 'BR987654321BB', true),
       ('2024-10-25 16:45:00', 187.50, 2, 2, 'BR456789123CC', true),
       ('2024-10-28 09:20:00', 125.00, 0, 1, null, false);

-- Itens de Compra (precounitario, licor sem _id)
INSERT INTO itemcompra (quantidade, precounitario, licor)
VALUES (1, 89.90, 1),
       (1, 65.90, 2),
       (2, 72.50, 3),
       (2, 72.35, 3),
       (2, 58.90, 4),
       (1, 68.00, 6),
       (1, 125.00, 5);

-- Avaliações (datainclusao gerado automaticamente pelo @PrePersist)
-- lista_avaliacao é FK para Licor, usuario é FK para Usuario
INSERT INTO avaliacao (estrelas, comentario, usuario, lista_avaliacao)
VALUES (5, 'Excelente licor! Sabor autêntico do cerrado, recomendo!', 1, 1),
       (4, 'Muito bom, mas poderia ser um pouco mais doce.', 2, 2),
       (5, 'Cremoso e delicioso! Perfeito para presentear.', 2, 3),
       (5, 'Sabor refrescante e único. Adorei!', 3, 4),
       (5, 'Vale cada centavo! Edição especial maravilhosa.', 4, 5),
       (4, 'Ótimo licor, sabor marcante do jatobá.', 1, 6),
       (5, 'Miniatura perfeita! Excelente para degustação.', 3, 7);

