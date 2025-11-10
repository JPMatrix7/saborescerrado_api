-- This file allow to write SQL commands that will be emitted in test and dev.
-- The commands are commented as their support depends of the database

-- Estados
INSERT INTO estado (nome, sigla, ativo, datainclusao)
VALUES ('Acre', 'AC', true, CURRENT_TIMESTAMP),
       ('Alagoas', 'AL', true, CURRENT_TIMESTAMP),
       ('Amapá', 'AP', true, CURRENT_TIMESTAMP),
       ('Amazonas', 'AM', true, CURRENT_TIMESTAMP),
       ('Bahia', 'BA', true, CURRENT_TIMESTAMP),
       ('Ceará', 'CE', true, CURRENT_TIMESTAMP),
       ('Distrito Federal', 'DF', true, CURRENT_TIMESTAMP),
       ('Espírito Santo', 'ES', true, CURRENT_TIMESTAMP),
       ('Goiás', 'GO', true, CURRENT_TIMESTAMP),
       ('Maranhão', 'MA', true, CURRENT_TIMESTAMP),
       ('Mato Grosso', 'MT', true, CURRENT_TIMESTAMP),
       ('Mato Grosso do Sul', 'MS', true, CURRENT_TIMESTAMP),
       ('Minas Gerais', 'MG', true, CURRENT_TIMESTAMP),
       ('Pará', 'PA', true, CURRENT_TIMESTAMP),
       ('Paraíba', 'PB', true, CURRENT_TIMESTAMP),
       ('Paraná', 'PR', true, CURRENT_TIMESTAMP),
       ('Pernambuco', 'PE', true, CURRENT_TIMESTAMP),
       ('Piauí', 'PI', true, CURRENT_TIMESTAMP),
       ('Rio de Janeiro', 'RJ', true, CURRENT_TIMESTAMP),
       ('Rio Grande do Norte', 'RN', true, CURRENT_TIMESTAMP),
       ('Rio Grande do Sul', 'RS', true, CURRENT_TIMESTAMP),
       ('Rondônia', 'RO', true, CURRENT_TIMESTAMP),
       ('Roraima', 'RR', true, CURRENT_TIMESTAMP),
       ('Santa Catarina', 'SC', true, CURRENT_TIMESTAMP),
       ('São Paulo', 'SP', true, CURRENT_TIMESTAMP),
       ('Sergipe', 'SE', true, CURRENT_TIMESTAMP),
       ('Tocantins', 'TO', true, CURRENT_TIMESTAMP);

-- Cidades (estado é foreign key sem _id)
INSERT INTO cidade (nome, estado, ativo, datainclusao)
VALUES ('Palmas', 27, true, CURRENT_TIMESTAMP),
       ('Anápolis', 9, true, CURRENT_TIMESTAMP),
       ('Goiânia', 9, true, CURRENT_TIMESTAMP),
       ('Araguaína', 27, true, CURRENT_TIMESTAMP),
       ('Rio de Janeiro', 19, true, CURRENT_TIMESTAMP),
       ('São Paulo', 25, true, CURRENT_TIMESTAMP),
       ('Curitiba', 16, true, CURRENT_TIMESTAMP),
       ('Porto Alegre', 21, true, CURRENT_TIMESTAMP),
       ('Fortaleza', 6, true, CURRENT_TIMESTAMP),
       ('Salvador', 5, true, CURRENT_TIMESTAMP),
       ('Belo Horizonte', 13, true, CURRENT_TIMESTAMP),
       ('Vitória', 8, true, CURRENT_TIMESTAMP),
       ('Manaus', 4, true, CURRENT_TIMESTAMP),
       ('Belém', 14, true, CURRENT_TIMESTAMP),
       ('Recife', 17, true, CURRENT_TIMESTAMP),
       ('Natal', 20, true, CURRENT_TIMESTAMP),
       ('João Pessoa', 15, true, CURRENT_TIMESTAMP),
       ('Maceió', 2, true, CURRENT_TIMESTAMP),
       ('Aracaju', 26, true, CURRENT_TIMESTAMP),
       ('Boa Vista', 23, true, CURRENT_TIMESTAMP),
       ('Macapá', 3, true, CURRENT_TIMESTAMP),
       ('São Luís', 10, true, CURRENT_TIMESTAMP),
       ('Cuiabá', 11, true, CURRENT_TIMESTAMP),
       ('Campo Grande', 12, true, CURRENT_TIMESTAMP),
       ('Florianópolis', 24, true, CURRENT_TIMESTAMP),
       ('Porto Velho', 22, true, CURRENT_TIMESTAMP),
       ('Rio Branco', 1, true, CURRENT_TIMESTAMP),
       ('Teresina', 18, true, CURRENT_TIMESTAMP),
       ('Brasília', 7, true, CURRENT_TIMESTAMP),
       ('Sobral', 6, true, CURRENT_TIMESTAMP),
       ('Caxias do Sul', 21, true, CURRENT_TIMESTAMP),
       ('Dourados', 12, true, CURRENT_TIMESTAMP);

-- Categorias
INSERT INTO categoria (nome, ativo, datainclusao)
VALUES ('Premium', true, CURRENT_TIMESTAMP),
       ('Artesanal', true, CURRENT_TIMESTAMP),
       ('Tradicional', true, CURRENT_TIMESTAMP),
       ('Cremoso', true, CURRENT_TIMESTAMP),
       ('Especial', true, CURRENT_TIMESTAMP);

-- Sabores (somente nome)
INSERT INTO sabor (nome, ativo, datainclusao)
VALUES ('Pequi', true, CURRENT_TIMESTAMP),
       ('Baru', true, CURRENT_TIMESTAMP),
       ('Buriti', true, CURRENT_TIMESTAMP),
       ('Cagaita', true, CURRENT_TIMESTAMP),
       ('Araticum', true, CURRENT_TIMESTAMP),
       ('Jatobá', true, CURRENT_TIMESTAMP);

-- Ingredientes (nome, quantidade, unidadeMedida)
INSERT INTO ingrediente (nome, quantidade, unidademedida, ativo, datainclusao)
VALUES ('Cachaça Artesanal', 1000, 'ml', true, CURRENT_TIMESTAMP),
       ('Açúcar Orgânico', 500, 'g', true, CURRENT_TIMESTAMP),
       ('Essência Natural', 50, 'ml', true, CURRENT_TIMESTAMP),
       ('Leite Condensado', 395, 'g', true, CURRENT_TIMESTAMP),
       ('Creme de Leite', 200, 'ml', true, CURRENT_TIMESTAMP),
       ('Especiarias', 20, 'g', true, CURRENT_TIMESTAMP),
       ('Frutas Frescas', 500, 'g', true, CURRENT_TIMESTAMP);

-- Embalagens (volume, material)
INSERT INTO embalagem (volume, material, ativo, datainclusao)
VALUES (750, 'Vidro', true, CURRENT_TIMESTAMP),
       (500, 'Vidro', true, CURRENT_TIMESTAMP),
       (50, 'Vidro', true, CURRENT_TIMESTAMP),
       (1000, 'Vidro', true, CURRENT_TIMESTAMP),
       (375, 'Vidro', true, CURRENT_TIMESTAMP);

-- Premiações (evento, ano, medalha)
INSERT INTO premiacao (evento, ano, medalha, ativo, datainclusao)
VALUES ('Festival do Cerrado', 2023, 'Ouro', true, CURRENT_TIMESTAMP),
       ('Concurso Nacional de Licores', 2024, 'Ouro', true, CURRENT_TIMESTAMP),
       ('Prêmio Centro-Oeste', 2023, 'Prata', true, CURRENT_TIMESTAMP),
       ('Festival de Bebidas Artesanais', 2024, 'Bronze', true, CURRENT_TIMESTAMP);

-- Parceiros Comerciais (cnpj, razaoSocial, nomeFantasia, email, telefone)
INSERT INTO parceirocomercial (cnpj, razaosocial, nomefantasia, email, telefone, ativo, datainclusao)
VALUES ('12.345.678/0001-90', 'Distribuidora Cerrado Ltda', 'Distribuidora Cerrado', 'contato@cerrado.com.br', '62 3333-4444', true, CURRENT_TIMESTAMP),
       ('98.765.432/0001-11', 'Fazenda Baru Orgânico ME', 'Fazenda Baru', 'fazenda@baru.com.br', '62 3555-6666', true, CURRENT_TIMESTAMP),
       ('11.222.333/0001-44', 'Cooperativa do Pequi', 'Coop Pequi', 'coop@pequi.com.br', '63 3777-8888', true, CURRENT_TIMESTAMP);

-- Safras de Licor (anoSafra, fazenda, qualidade, cidade)
INSERT INTO safralicor (anosafra, fazenda, qualidade, cidade, ativo, datainclusao)
VALUES ('2023-01-15', 'Fazenda São José', 'Premium', 3, true, CURRENT_TIMESTAMP),
       ('2024-02-20', 'Fazenda Boa Vista', 'Especial', 2, true, CURRENT_TIMESTAMP),
       ('2023-06-10', 'Fazenda do Cerrado', 'Tradicional', 1, true, CURRENT_TIMESTAMP),
       ('2024-03-05', 'Fazenda Santa Clara', 'Premium', 4, true, CURRENT_TIMESTAMP);

-- Usuários (tabela base JOINED inheritance)
INSERT INTO usuario (id, nome, email, senha, ativo, datainclusao)
VALUES (1, 'João Silva', 'joao.silva@email.com', 'senha123', true, CURRENT_TIMESTAMP),
       (2, 'Maria Santos', 'maria.santos@email.com', 'senha456', true, CURRENT_TIMESTAMP),
       (3, 'Pedro Oliveira', 'pedro.oliveira@email.com', 'senha789', true, CURRENT_TIMESTAMP),
       (4, 'Ana Costa', 'ana.costa@email.com', 'senha321', true, CURRENT_TIMESTAMP),
       (5, 'Sabores do Cerrado Ltda', 'contato@saborescerrado.com.br', 'empresa123', true, CURRENT_TIMESTAMP),
       (6, 'Distribuidora Premium ME', 'vendas@premium.com.br', 'empresa456', true, CURRENT_TIMESTAMP);

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

-- Perfis dos Usuários (0=USER, 1=ADMIN)
INSERT INTO usuario_perfil (usuario_id, perfis)
VALUES (1, 0),
       (2, 0),
       (3, 1),
       (4, 0),
       (5, 1),
       (6, 0);

-- Telefones (codigoArea, numero - SEM usuario pois não há @JoinColumn)
INSERT INTO telefone (codigoarea, numero, ativo, datainclusao)
VALUES ('63', '98888-1111', true, CURRENT_TIMESTAMP),
       ('62', '99999-2222', true, CURRENT_TIMESTAMP),
       ('62', '97777-3333', true, CURRENT_TIMESTAMP),
       ('61', '96666-4444', true, CURRENT_TIMESTAMP),
       ('62', '3333-5555', true, CURRENT_TIMESTAMP),
       ('61', '3444-6666', true, CURRENT_TIMESTAMP);

-- Endereços (cidade é foreign key sem _id - SEM usuario)
INSERT INTO endereco (logradouro, numero, complemento, bairro, cep, cidade, ativo, datainclusao)
VALUES ('Rua das Flores', '123', 'Apt 101', 'Centro', '74000-100', 3, true, CURRENT_TIMESTAMP),
       ('Av. Anhanguera', '456', null, 'Setor Central', '74000-200', 3, true, CURRENT_TIMESTAMP),
       ('Rua do Comercio', '789', 'Sala 10', 'Jundiai', '74000-150', 2, true, CURRENT_TIMESTAMP),
       ('Quadra 10', '15', null, 'Asa Sul', '70000-100', 29, true, CURRENT_TIMESTAMP),
       ('Rua Comercial', '200', 'Loja 5', 'Setor Bueno', '74000-300', 3, true, CURRENT_TIMESTAMP),
       ('Av. Principal', '300', null, 'Asa Norte', '70000-200', 29, true, CURRENT_TIMESTAMP);

-- Cartões (numeroCartao, nomeTitular, validade, cvv - SEM usuario)
INSERT INTO cartao (numerocartao, nometitular, validade, cvv, ativo, datainclusao)
VALUES ('1111222233334444', 'JOAO SILVA', '12/2026', '123', true, CURRENT_TIMESTAMP),
       ('5555666677778888', 'MARIA SANTOS', '06/2027', '456', true, CURRENT_TIMESTAMP),
       ('9999000011112222', 'PEDRO OLIVEIRA', '03/2026', '789', true, CURRENT_TIMESTAMP),
       ('3333444455556666', 'ANA COSTA', '09/2027', '321', true, CURRENT_TIMESTAMP);

-- Licores (tipo é enum ORDINAL: 0=FRUTADO, 1=MEL)
INSERT INTO licor (nome, descricao, preco, tipo, teoralcoolico, estoque, visivel, sabor, embalagem, safra, parceiro_comercial, ativo, datainclusao)
VALUES ('Licor de Pequi Premium', 'Licor artesanal de pequi com toque especial', 89.90, 0, 18.5, 150, true, 1, 2, 1, 1, true, CURRENT_TIMESTAMP),
       ('Licor de Baru Tradicional', 'Sabor único da castanha de baru', 65.90, 0, 15.0, 200, true, 2, 1, 2, 2, true, CURRENT_TIMESTAMP),
       ('Licor de Buriti Cremoso', 'Licor cremoso com polpa de buriti', 72.50, 0, 16.0, 100, true, 3, 1, 1, 1, true, CURRENT_TIMESTAMP),
       ('Licor de Cagaita', 'Refrescante licor de cagaita', 58.90, 0, 14.5, 180, true, 4, 1, 3, 3, true, CURRENT_TIMESTAMP),
       ('Licor de Araticum com Mel', 'Edição especial com araticum e mel', 125.00, 1, 20.0, 50, true, 5, 5, 4, 2, true, CURRENT_TIMESTAMP),
       ('Licor de Jatobá', 'Sabor marcante do jatobá do cerrado', 68.00, 0, 16.5, 120, true, 6, 1, 2, 3, true, CURRENT_TIMESTAMP),
       ('Miniatura Pequi', 'Miniatura perfeita para presentear', 15.90, 0, 18.5, 300, true, 1, 3, 1, 1, true, CURRENT_TIMESTAMP);

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
INSERT INTO compra (datacompra, valortotal, status, formapagamento, codigorastreio, pago, ativo, datainclusao)
VALUES ('2024-10-15 14:30:00', 155.80, 4, 0, 'BR123456789AA', true, true, CURRENT_TIMESTAMP),
       ('2024-10-20 10:15:00', 289.70, 3, 0, 'BR987654321BB', true, true, CURRENT_TIMESTAMP),
       ('2024-10-25 16:45:00', 187.50, 2, 2, 'BR456789123CC', true, true, CURRENT_TIMESTAMP),
       ('2024-10-28 09:20:00', 125.00, 0, 1, null, false, true, CURRENT_TIMESTAMP);

-- Itens de Compra (precounitario, licor sem _id)
INSERT INTO itemcompra (quantidade, precounitario, licor, ativo, datainclusao)
VALUES (1, 89.90, 1, true, CURRENT_TIMESTAMP),
       (1, 65.90, 2, true, CURRENT_TIMESTAMP),
       (2, 72.50, 3, true, CURRENT_TIMESTAMP),
       (2, 72.35, 3, true, CURRENT_TIMESTAMP),
       (2, 58.90, 4, true, CURRENT_TIMESTAMP),
       (1, 68.00, 6, true, CURRENT_TIMESTAMP),
       (1, 125.00, 5, true, CURRENT_TIMESTAMP);

-- Avaliações (datainclusao gerado automaticamente pelo @PrePersist)
-- lista_avaliacao é FK para Licor, usuario é FK para Usuario
INSERT INTO avaliacao (estrelas, comentario, usuario, lista_avaliacao, ativo, datainclusao)
VALUES (5, 'Excelente licor! Sabor autêntico do cerrado, recomendo!', 1, 1, true, CURRENT_TIMESTAMP),
       (4, 'Muito bom, mas poderia ser um pouco mais doce.', 2, 2, true, CURRENT_TIMESTAMP),
       (5, 'Cremoso e delicioso! Perfeito para presentear.', 2, 3, true, CURRENT_TIMESTAMP),
       (5, 'Sabor refrescante e único. Adorei!', 3, 4, true, CURRENT_TIMESTAMP),
       (5, 'Vale cada centavo! Edição especial maravilhosa.', 4, 5, true, CURRENT_TIMESTAMP),
       (4, 'Ótimo licor, sabor marcante do jatobá.', 1, 6, true, CURRENT_TIMESTAMP),
       (5, 'Miniatura perfeita! Excelente para degustação.', 3, 7, true, CURRENT_TIMESTAMP);

