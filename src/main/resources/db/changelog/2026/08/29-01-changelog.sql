insert into tb_servico (nome, descricao, custo, valor, duracao)
values
    ('Troca de Óleo e Filtros', 'Substituição do óleo do motor, filtro de óleo, filtro de ar e filtro de combustível', 180.00, 300.00, 2),
    ('Alinhamento e Balanceamento', 'Ajuste da geometria da suspensão e balanceamento das quatro rodas.', 70.00, 120.00, 1),
    ('Troca de Pastilhas e Discos de Freio', 'Substituição dos componentes de atrito do sistema de freio dianteiro.', 250.00, 400.00, 2),
    ('Revisão do Sistema de Ar-Condicionado', 'Higienização do sistema, troca do filtro de cabine e recarga de gás refrigerante.', 150.00, 240.00, 2),
    ('Troca da Correia Dentada e Tensor', 'Substituição preventiva da correia de distribuição e rolamentos tensores.', 320.00, 520.00, 6),
    ('Substituição de Amortecedores Dianteiros', 'Troca dos amortecedores, batentes e coifas da suspensão dianteira', 450.00, 720.00, 6),
    ('Limpeza de Bicos Injetores', 'Limpeza ultrassônica e teste de equalização dos bicos injetores de combustível', 320.00, 200.00, 5),
    ('Retífica ou Troca da Junta do Cabeçote', 'Abertura do motor, aplainamento do cabeçote e troca das juntas de vedação.', 850.00, 1400.00, 8),
    ('Troca do Kit de Embreagem', 'Substituição do disco, plato e atuador hidráulico/rolamento da embreagem', 500.00, 800.00, 6),
    ('Diagnóstico Eletrônico (Injeção/Via Scanner)', 'Leitura de códigos de falha na ECU, análise de parâmetros e reset do sistema.', 90.00, 150.00, 2)
;

insert into tb_material (nome, descricao, custo, valor, tipo)
values
    ('Óleo do Motor Sintético 5W30 (4L)', 'Lubrificante de alta performance indicado para proteção e eficiência do motor.', 140.00, 180.00, 'PECA'),
    ('Jogo de Pastilhas de Freio Dianteiras', 'Componente de atrito responsável pela frenagem das rodas dianteiras.', 120.00, 150.00, 'PECA'),
    ('Disco de Freio Ventilado (Par)', 'Discos de aço instalados nas rodas dianteiras para atuação das pastilhas', 220.00, 280.00, 'PECA'),
    ('Filtro de Ar do Motor', 'Elemento filtrante que retém impurezas antes da entrada de ar no motor', 35.00, 50.00, 'PECA'),
    ('Filtro de Cabine (Ar-Condicionado)', 'Filtro responsável pela retenção de poeira e odores no interior do veículo', 30.00, 40.00, 'PECA'),
    ('Gás Refrigerante R134a (Carga)', 'Fluido térmico utilizado para o funcionamento do sistema de ar-condicionado', 60.00, 80.00, 'PECA'),
    ('Kit Correia Dentada e Tensor', 'Conjunto de correia sincronizadora e rolamento tensionador do motor', 210.00, 280.00, 'PECA'),
    ('Par de Amortecedores Dianteiros', 'Componentes da suspensão responsáveis por absorver impactos e manter a estabilidade', 380.00, 500.00, 'PECA'),
    ('Kit de Embreagem (Disco + Platô)', 'Conjunto de transmissão de força entre o motor e o câmbio', 420.00, 550.00, 'PECA'),
    ('Junta do Cabeçote de Aço', 'Elemento de vedação entre o bloco do motor e o cabeçote', 110.00, 150.00, 'PECA'),
    ('Pacote café Marata', 'Café matinal disponibilizado', 10.00, 10.00, 'ALIMENTO'),
    ('Kit Limpeza', 'Kit de produtos de limpeza', 100.00, 100.00, 'MATERIAL')
;

insert into tb_estoque (quantidade, minimo, material_id)
values
    (10, 5, 1),
    (10, 5, 2),
    (10, 5, 3),
    (10, 5, 4),
    (10, 5, 5),
    (10, 5, 6),
    (10, 5, 7),
    (10, 5, 8),
    (10, 5, 9),
    (10, 5, 10),
    (10, 5, 11),
    (10, 5, 12)
;