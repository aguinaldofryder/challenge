## Neste documento descrevo a implementação do projeto.
O design pattern principal utilizado foi o DDD (Domain-Driven Design), 
que é uma abordagem de desenvolvimento de software focada na modelagem do domínio do problema.

Também segui a premissa de que estamos trabalhando com uma aplicação de grande porte e que irá escalar e receber uma quantidade significativa de requisições.


### Análise do Requisito
Antes de iniciar a implementação, analisei o requisito e o arquivo movielist.csv para entender a estrutura dos dados.
Com isso pude identificar que o projeto possui dois contextos principais:
- **Filmes**: onde são armazenados os dados dos filmes.
- **Produtores**: onde são armazenados os dados dos produtores.

Com base nisso criei uma entidade para cada contexto.
* Na entidade de Produtores, adicionei as regras de negócio necessárias para calcular o intervalo de tempo entre o primeiro e o último filme do produtor que foi o vencedor da indicação.
  * Na entidade de Filmes, adicionei o básico para salvar os dados dos filmes e emitir um evento quando um filme vencedor for adicionado.
  * Os eventos são utilizados para notificar outras partes do sistema sobre mudanças de estado, como a adição de um filme vencedor.
  * Essa abordagem possibilita a implementação de uma arquitetura orientada a eventos, onde diferentes partes do sistema podem reagir a eventos específicos sem acoplamento direto,
  possibilitando escalar o sistema de forma mais eficiente.
* Outro ponto de atenção foi no consumo de evento para atualizar o intervalo de tempo do produtor. 
  * Como o serviço pode ser executado em várias instâncias, é possível que mais de uma instância precise alterar o mesmo registro de produtor.
  * Isso abriria a possibilidade de ocorrer sobrescrita de dados. Para resolver isso, adicionei um bloqueio pessimista no serviço que busca o produtor para atualização. 
  * Com isso apenas uma instância do serviço poderá atualizar o registro de produtor por vez, evitando conflitos e garantindo a integridade dos dados.
* Com os pré-calculados e salvos no produtor, a consulta para obter o intervalo de tempo entre o primeiro e o último filme do produtor vencedor é feita de forma rápida e eficiente.
  * Além disso é possível implementar uma camada de cache para melhorar ainda mais a performance da consulta, caso necessário.
* Verifiquei que no arquivo o nome dos produtores estão concatenados na mesma coluna, separados por vírgula.
  * Para resolver isso, implementei uma lógica que separa os nomes dos produtores e cria um registro para cada um deles, garantindo que cada produtor tenha seu próprio intervalo de tempo calculado.

### Questionamentos
Aqui deixo alguns questionamentos que surgiram durante o desenvolvimento do projeto
e caso caso fosse em um projeto real seriam discutidos com a equipe de desenvolvimento e stakeholders antes de seguir com a implementação.
* **Caso o um produtor possua duas sequências com o mesmo intervalo, como por exemplo, 2010, 2012 e 2020 e 2022?**
  * Qual seria o intervalo a ser considerado? A solução foi considerar o primeiro intervalo encontrado, ou seja, o primeiro filme vencedor do produtor.

### Oportunidades de melhorias
Neste tópoico apresento algumas oportunidades de melhorias que poderiam ser implementadas no projeto, caso fosse em um ambiente real.

* **Validação de dados**: A validação dos dados do arquivo CSV é feita de forma básica. 
  * Poderia ser implementada uma validação mais robusta para garantir a integridade dos dados, como verificar se os campos obrigatórios estão preenchidos e se os dados estão no formato correto.
* **Entrada de dados**: A entrada de dados é feita através de um arquivo CSV. 
  * Poderia ser implementada um endpoint para permitir a entrada de dados de forma mais amigável com upload do arquivo.
* **Testes**: Os testes unitários foram implementados apenas para orientar o desenvolvimento mas foram removidos do código final para atender o que pede no requisito.
  * Poderia ser implementado testes unitários e de integração para garantir que todas as partes do sistema estão funcionando corretamente juntas.
* **Controle de versão**: Os commits do projeto foram feitos de forma agrupada, mas em um projeto real seria interessante seguir uma convenção de commits para facilitar o entendimento do histórico do projeto.
* **Documentação**: Não foi implementada uma documentação do projeto, como Swagger ou OpenAPI.
  * Poderia ser implementada uma documentação para facilitar o entendimento do projeto e das APIs disponíveis.
* **Monitoramento e Logs**: Não foi implementado um sistema de monitoramento e logs para acompanhar o desempenho da aplicação.
  * Poderia ser implementado um sistema de monitoramento e logs para identificar problemas e melhorar o desempenho da aplicação.
* **Maven Multi-Module**: O projeto foi implementado em um único módulo, mas poderia ser dividido em múltiplos módulos para facilitar a manutenção e escalabilidade do projeto.
  * Poderia ser implementado um projeto Maven Multi-Module para organizar melhor o código e as dependências. Como por exemplo, um módulo para a API, outro para o domínio e outro para a infraestrutura.