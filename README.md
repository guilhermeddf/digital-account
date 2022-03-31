# digital-account

Esse projeto roda com docker, ele tem o posgresql e flyway para fazer as migracoes de banco, entao sera necessário rodar o docker-compose na raiz do projeto.
O projeto possui testes de sistema utilizando o rest assured, para executar esses testes é necessário ter a aplicação rodando.

Tentei utitilizar DDD na arquitetura, mas ainda existem referencias ao spring dentro do dominio, a solução para resolver esse problema está aqui: https://www.baeldung.com/hexagonal-architecture-ddd-spring,
Mas não fiz :(.

A applicaçao tem swagger, pode ser consultado atraves do caminho: localhost:8080/swagger-ui.html
