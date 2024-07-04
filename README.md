# AfroPay
AfroPay é um sistema bancário que permite a criação e gestão de contas, incluindo funcionalidades como depósitos, saques, transferências, pagamentos e PIX. O sistema diferencia entre contas correntes e contas de pagamento, com regras específicas para cada tipo.

## Introdução
AfroPay é um sistema bancário projetado para fornecer serviços financeiros robustos e eficientes. Este projeto é uma demonstração de um banco digital, onde usuários podem criar contas, atualizar informações pessoais, e realizar diversas operações financeiras como depósitos, saques, transferências e pagamentos. A plataforma diferencia entre contas correntes e contas de pagamento, aplicando regras específicas para cada tipo de conta.

## Funcionalidades
Criação de Usuários e Contas: Criação automática de contas correntes ou contas de pagamento baseadas na renda salarial do usuário.
Depósitos: Adicionar fundos à conta.
Saques: Retirar fundos da conta, com controle de taxas adicionais após um certo número de saques gratuitos.
Transferências: Transferir fundos entre contas, com limites específicos para contas de pagamento.
Pagamentos: Realizar pagamentos usando o saldo da conta ou cheque especial (para contas correntes).
PIX: Realizar pagamentos instantâneos (funcionalidade similar ao pagamento).
## Estrutura do Projeto
Entity: Contém as classes que representam as tabelas do banco de dados.
Repository: Interfaces que estendem JpaRepository para acessar dados.
Service: Contém a lógica de negócios e as regras de negócio.
Controller: Define os endpoints da API.
## Endpoints da API

### Usuário
- Criar Usuário: POST /usuario
- Ver Usuário: GET /usuario
- Consultar Usuário por ID: GET /conta/{id}
- Atualizar Usuário: PUT /usuario/{id}
- Deletar Usuário: DELETE /{id}/delete

### Conta
- Depositar: POST /banco/depositar
- Sacar: POST /banco/sacar
- Transferir: POST /banco/transferir
- Realizar Pagamento: POST /banco/pagamento
- Realizar PIX: POST /banco/pix

## Considerações Finais
Este projeto demonstra a implementação básica de um sistema bancário com funcionalidades essenciais. É altamente recomendável adicionar mais validações, tratamentos de exceção e testes para garantir a robustez e a confiabilidade do sistema.
