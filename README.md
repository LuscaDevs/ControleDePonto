# Sistema de Ponto Eletrônico

## Descrição

Este projeto consiste em um sistema de ponto eletrônico desenvolvido em Java, permitindo que os usuários registrem suas entradas e saídas em um ambiente de trabalho. Além disso, o sistema oferece funcionalidades para o gerenciamento completo dos usuários, incluindo operações de cadastro, atualização e exclusão. O sistema também realiza a verificação de consistência entre os registros de ponto, garantindo que cada entrada tenha uma saída correspondente e vice-versa. Além disso, mantém um contador de pontos para cada usuário, facilitando o acompanhamento do número total de entradas e saídas.

## Estrutura do Projeto

O projeto é organizado em algumas classes principais:

### `Usuario`

Representa um usuário do sistema, contendo as seguintes informações:

- Nome
- Matrícula
- Contador de pontos

### `PontoEletronico`

Classe responsável pelo gerenciamento dos registros de ponto, incluindo as seguintes funcionalidades:

- Registro de ponto de entrada e saída
- Verificação de consistência entre entradas e saídas
- Contagem de pontos para cada usuário

### `Ponto`

Representa um registro de ponto, contendo as seguintes informações:

- Usuário associado
- Tipo de ponto (entrada ou saída)
- Data e hora do registro

O sistema implementa operações de CRUD (Create, Read, Update, Delete) completas para os usuários, permitindo o cadastro, atualização e exclusão de informações. Além disso, oferece funcionalidades para o registro de pontos de entrada e saída.

## Funcionalidades

- Cadastro de Usuários: Permite adicionar novos usuários ao sistema, fornecendo nome e matrícula.
- Atualização de Usuários: Permite modificar os dados dos usuários, incluindo nome e matrícula.
- Exclusão de Usuários: Permite remover usuários do sistema, excluindo todas as informações associadas a eles.
- Registro de Pontos: Permite que os usuários registrem suas entradas e saídas, garantindo a consistência dos dados.

Este sistema oferece uma solução completa para o controle de ponto eletrônico, atendendo às necessidades de gerenciamento de usuários e registros de ponto de forma eficiente e confiável.

## Divisão SCRUM
- Product Owner: ANDRÊEI FERREIRA PESSOA DA SILVA - RA:12723128471
- Scrum Master: LUCAS DOS SANTOS SILVA – RA: 1282314493
- Devs: KETSA A DE AMAR SOUSA - RA: 32317601, LEANDRO AGUIAR - RA: 1272319129, MANOEL VINICIUS SILVA SOUZA - RA: 722315125, NATHAN ALMEIDA GOIS - RA: 722311425, PAULO SÉRGIO DOS ANJOS SILVA FILHO - RA: 722310346

- Product Owner (PO): Responsável por representar os interesses dos stakeholders, definir as funcionalidades do produto e priorizar o backlog do produto com base no valor de negócio.
- Scrum Master (SM): Responsável por garantir que a equipe de desenvolvimento siga os princípios e práticas do Scrum, remover impedimentos que estejam atrapalhando o progresso da equipe e facilitar as reuniões do Scrum.
- Development Team (Devs): Responsável por transformar os itens do backlog do produto em incrementos potencialmente entregáveis do produto a cada sprint.

A equipe de desenvolvimento consiste em cinco membros, conforme listado, liderados pelo Scrum Master. O Product Owner trabalhará em estreita colaboração com a equipe de desenvolvimento para garantir que as necessidades do cliente sejam atendidas e que o produto final seja entregue com sucesso. O Scrum Master estará presente para apoiar a equipe, facilitar os processos Scrum e remover quaisquer obstáculos que possam surgir durante o desenvolvimento.


