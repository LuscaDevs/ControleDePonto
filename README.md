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
