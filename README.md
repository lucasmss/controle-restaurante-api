# 🍽️ Controle Restaurante API

API REST para gerenciamento de um restaurante, desenvolvida com Java e Spring Boot.

O projeto simula o fluxo principal de atendimento de um restaurante:

**Mesa → Consumo → Pedido → Itens → Produtos → Fechamento → Pagamento**

Atualmente o backend já possui o fluxo principal funcionando e está entrando na etapa de testes finais antes do desenvolvimento do frontend.

---

## 🚀 Tecnologias

- Java
- Spring Boot
- Spring Data JPA
- Hibernate
- PostgreSQL
- Maven
- REST API
- Jakarta Persistence (JPA)

---

## 🧠 Funcionalidades

### Mesas

- Listar mesas disponíveis
- Verificar se uma mesa está ocupada
- Abrir um consumo para uma mesa
- Liberar a mesa após o pagamento

### Consumo

- Abrir consumo
- Associar consumo a uma mesa
- Registrar data de abertura
- Registrar pedidos do consumo
- Calcular valor consumido
- Calcular taxa de serviço
- Calcular valor total
- Fechar consumo
- Controlar status do consumo

### Pedidos

- Criar pedido para um consumo
- Associar pedido ao consumo
- Adicionar múltiplos itens ao pedido
- Associar produtos aos itens
- Registrar quantidade
- Registrar o preço unitário do produto no momento do pedido
- Impedir pedidos em consumos fechados

### Produtos

- Cadastro de produtos
- Código do produto
- Nome
- Preço
- Controle de produto ativo/inativo

### Pagamentos

- Associar pagamento ao consumo
- Registrar valor pago
- Registrar data do pagamento
- Permitir pagamento com taxa de serviço
- Permitir pagamento sem taxa de serviço
- Fechar o consumo após o pagamento
- Liberar a mesa após o pagamento

---

## 🔄 Fluxo principal

### 1. Ver mesas disponíveis

http
GET /mesas/disponiveis

Retorna as mesas que não possuem um consumo com status ABERTO.

2. Abrir um consumo
POST /mesas/{mesaId}/consumos

Exemplo:

POST /mesas/5/consumos

A mesa passa a possuir um consumo aberto.

3. Criar um pedido
POST /consumos/{consumoId}/pedidos

Exemplo:

POST /consumos/1/pedidos

O pedido recebe os produtos e suas respectivas quantidades.

Exemplo de JSON:

{
  "itens": [
    {
      "produto": {
        "id": 1
      },
      "quantidade": 2
    },
    {
      "produto": {
        "id": 3
      },
      "quantidade": 1
    }
  ]
}

O sistema busca o preço atual do produto e salva esse valor no ItemPedido.

4. Fechar/calcular o consumo
PUT /consumos/{mesaId}

Exemplo:

PUT /consumos/5

Nesse momento o sistema calcula:

Valor consumido
+ Taxa de serviço
= Valor total

A taxa de serviço atualmente é calculada como 10% quando aceita.

O fechamento/calculo da conta prepara os valores do consumo. A decisão final de pagar com ou sem taxa acontece no pagamento.

5. Realizar pagamento
POST /consumos/{consumoId}/pagamento

O corpo da requisição recebe diretamente um Boolean.

Com taxa:

true

Sem taxa:

false
Com taxa

O cliente paga:

valorTotal
Sem taxa

O cliente paga:

valorConsumido

Após o pagamento:

o consumo recebe status FECHADO;
a data de fechamento é registrada;
o pagamento é persistido;
a mesa volta a ficar disponível.
🗃️ Modelo de entidades
Mesa
 │
 └── Consumo
      │
      ├── Pedidos
      │    │
      │    └── ItensPedido
      │         │
      │         └── Produto
      │
      └── Pagamentos
Mesa

Representa uma mesa física do restaurante.

Consumo

Representa o atendimento aberto para uma mesa.

Pedido

Representa um pedido realizado durante um consumo.

ItemPedido

Representa cada produto e sua quantidade dentro de um pedido.

Produto

Representa os produtos disponíveis no restaurante.

Pagamento

Representa o pagamento realizado para um consumo.

📊 Status
Status do Consumo
ABERTO
FECHADO
Status do Pedido

O pedido possui um status próprio, atualmente iniciado como:

FEITO
💰 Regra da taxa de serviço

A taxa de serviço é calculada em 10% do valor consumido.

Exemplo:

Consumo:       R$ 100,00
Taxa 10%:      R$  10,00
Total:         R$ 110,00

Caso o cliente opte por não pagar a taxa:

Consumo:       R$ 100,00
Taxa:          R$   0,00
Total pago:    R$ 100,00

A decisão é enviada no momento do pagamento através de:

true

ou

false
🛡️ Regras de negócio

O sistema já possui algumas validações importantes:

Não é possível abrir um consumo em uma mesa que já possui consumo aberto.
Não é possível realizar pedido em consumo fechado.
O preço do produto é copiado para o ItemPedido no momento da criação do pedido.
O consumo mantém os valores calculados:
valorConsumido
valorTaxaServico
valorTotal
O pagamento determina se a taxa de serviço será efetivamente cobrada.
Após o pagamento, o consumo é fechado.
Após o fechamento, a mesa volta a aparecer entre as mesas disponíveis.
🗄️ Banco de dados

O projeto utiliza PostgreSQL.

As entidades são persistidas através do Spring Data JPA/Hibernate.

Principais relacionamentos:

Mesa 1:N Consumo

Consumo 1:N Pedido

Pedido 1:N ItemPedido

Produto 1:N ItemPedido

Consumo 1:N Pagamento
🧪 Testes manuais

O fluxo principal pode ser validado através do Postman ou outra ferramenta de requisições HTTP.

Fluxo recomendado:

1. GET mesas disponíveis
        ↓
2. POST abrir consumo
        ↓
3. POST criar pedido
        ↓
4. PUT fechar/calcular consumo
        ↓
5. POST pagamento
        ↓
6. GET mesas disponíveis

Ao final do fluxo, a mesa utilizada deve voltar para a lista de mesas disponíveis.

Também devem ser testados cenários inválidos, como:

Abrir uma mesa já ocupada;
Fazer pedido em consumo fechado;
Buscar consumo inexistente;
Realizar pagamento com consumo inexistente.
📌 Estado atual do projeto
Backend

🟢 Fluxo principal implementado

🟢 Persistência PostgreSQL

🟢 Relacionamentos JPA

🟢 Regras básicas de negócio

🟢 Pedidos e itens

🟢 Cálculo de consumo

🟢 Taxa de serviço

🟢 Pagamento

🟢 Liberação da mesa após pagamento

🟡 Testes finais e refinamento das exceções

Frontend

🔴 Ainda não iniciado

Próxima grande etapa do projeto: desenvolver a interface para o usuário consumir a API.

🛣️ Próximos passos
Backend
Finalizar testes dos principais fluxos
Criar/tratar exceções de forma padronizada
Melhorar respostas HTTP
Validar dados recebidos pela API
Revisar endpoints
Criar documentação da API
Frontend

Após a estabilização do backend:

Tela de mesas
Visualização de mesas disponíveis/ocupadas
Abertura de consumo
Cadastro/seleção de produtos
Criação de pedidos
Visualização da conta
Escolha da taxa de serviço
Pagamento
Liberação da mesa
👨‍💻 Objetivo do projeto

Projeto desenvolvido para praticar e consolidar conhecimentos em:

Java
Spring Boot
APIs REST
JPA/Hibernate
PostgreSQL
Relacionamentos entre entidades
Regras de negócio
Transações
Tratamento de exceções
Arquitetura em camadas
Integração entre backend e frontend

O projeto está sendo desenvolvido de forma incremental, priorizando o entendimento das regras de negócio e da arquitetura antes da implementação da interface.
