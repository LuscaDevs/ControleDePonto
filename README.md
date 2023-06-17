# Sistema de Ponto Eletrônico

## Descrição

Este projeto consiste em um sistema de ponto eletrônico, onde os usuários podem realizar o registro de entradas e saídas em um ambiente de trabalho. O sistema foi desenvolvido em Java e oferece as seguintes funcionalidades:

- Registro de ponto: Os usuários podem registrar suas entradas e saídas no sistema, indicando a data e hora em que ocorreram.
- Verificação de consistência: O sistema verifica se um usuário possui uma entrada sem uma saída correspondente e vice-versa, evitando registros inválidos.
- Contagem de pontos: O sistema mantém um contador de pontos para cada usuário, permitindo o acompanhamento do número total de entradas e saídas.

## Estrutura do projeto

O projeto é dividido em algumas classes principais:

- `Usuario`: Representa um usuário do sistema, contendo informações como nome, matrícula e contador de pontos.
- `PontoEletronico`: Classe responsável por gerenciar os registros de pontos, verificando a consistência entre entradas e saídas.
- `Ponto`: Representa um registro de ponto, contendo informações como usuário, tipo (entrada ou saída) e data/hora.

