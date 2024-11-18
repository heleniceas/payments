# Autorizador de benefícios da Caju

### Tecnologias
<ul>
    <li>Kotlin</li>
    <li>Spring Boot</li>
    <li>Gradle</li>
    <li>JUnit</li>
    <li>Swagger</li>
</ul>

### Guia Rápido

 Exemplo CURL
```bash
$ curl --location 'localhost:8080/transfer' \
--data '{
"account": "1234567890",
"totalAmount": 1.17,
"mcc": "5832",
"merchant": "TAXI"
}'
```

#### Comandos do Gradle

- `./gradlew dependencies`: download dependencies
- `./gradlew build`: build the whole project (including dependencies download)
- `./gradlew bootRun`: start and serve the Spring application

## Subindo a aplicação com Docker Compose

No diretorio  onde foi baixado o arquivo execute o comando abaixo

    $ docker-compose up -d


### Accounts Data

Esta tabela apresenta os dados das contas, incluindo saldos em diferentes categorias (cash, meal e food).

| **Número da Conta** | **Saldo (Cash)** | **Saldo (Meal)** | **Saldo (Food)** |
|----------------------|------------------|------------------|------------------|
| 1234567890           | 1500.00          | 200.00           | 100.00           |
| 9876543210           | 2500.50          | 300.00           | 150.00           |
| 1122334455           | 1200.75          | 180.00           | 90.00            |
| 9988776655           | 5000.00          | 450.00           | 200.00           |
| 2233445566           | 3200.00          | 400.00           | 220.00           |

### Establishments Data

Esta tabela apresenta os dados dos estabelecimentos, incluindo seus nomes e o tipo de saldo associado.

| **Nome do Estabelecimento** | **Tipo de Saldo**       |
|-----------------------------|-------------------------|
| UBER EATS                   | FOOD                   |
| UBER TRIP                   | *Não especificado*     |
| PAG*JoseDaSilva             | MEAL                   |
| PICPAY*BILHETEUNICO         | *Não especificado*     |

### Explicação dos Campos
- **Nome do Estabelecimento**: Identificação do estabelecimento associado à transação.
- **Tipo de Saldo**: Categoria do saldo associado à transação. Pode ser:
    - **FOOD**: Saldo destinado a alimentos.
    - **MEAL**: Saldo destinado a refeições.
    - **Não especificado**: Tipo de saldo não definido para o estabelecimento.

### Utilização
Os dados acima podem ser usados para associar transações a tipos específicos de saldos ou identificar estabelecimentos que não possuem um tipo de saldo definido.

### Acesso a Documentação (Em Construção/ Não Implementado)

Os endpoints estão documentos pelo Swagger. Após iniciar a aplicação, a documentação roda em:

``http://localhost:8080/swagger-ui/index.html``



## ## L4. Questão aberta

Optei por trabalhar com um banco de dados relacional, que já oferece gerenciamento nativo para evitar que duas transações modifiquem os mesmos dados simultaneamente. Além disso, ele permite configurar os níveis de isolamento, garantindo consistência e integridade nas operações.

Para a atualização do saldo, estou utilizando transações. Assim, em caso de qualquer problema durante o processamento, é realizado o rollback automático. Atualmente, a configuração estabelece um timeout de 1 segundo, que é o tempo mínimo permitido pela configuração padrão.

Mais informações sobre o nivel de isolamento do banco : https://www.devmedia.com.br/transacoes-no-postgresql-niveis-de-isolamento/32464

Para melhorar a performance uma alternativa seria o uso do Redis como cache. 
Visto que ele é um banco em memória extremamente rápido, vale salientar que uma soluçao amplamente adotada por bancos e sistemas financeiros devido à sua capacidade de realizar operações com baixa latência e armazenamento em memória.

### Funcionamento da Leitura de Saldo com Redis

1. Armazenamento de saldo no Redis:

O saldo de cada conta é armazenado como um par chave-valor.
A chave pode ter o formato saldo:<conta_id>, enquanto o valor representa o saldo atual da conta.
Esse saldo é atualizado em tempo real sempre que ocorre uma transação.

2. Consulta de saldo:

Ao consultar o saldo, o sistema verifica primeiramente o Redis.
Cache hit: Se o saldo estiver no Redis, o retorno é imediato, geralmente em microssegundos.
Cache miss: Caso o saldo não esteja no Redis, o sistema consulta o banco de dados relacional, armazena o resultado no Redis e retorna ao cliente.

### Vantagens do Uso de Redis para Gerenciar Saldo
1. Alta performance:

O Redis pode processar milhões de operações por segundo com latência extremamente baixa, permitindo consultas quase instantâneas.
Ideal para atender SLAs rigorosos, como tempos de resposta abaixo de 100 ms.

2. Redução da carga no banco relacional:

Em sistemas bancários, as consultas de saldo são operações muito frequentes. Usar o Redis reduz significativamente o número de acessos ao banco relacional, aliviando a carga sobre o sistema principal.

3. Sincronização em tempo real:

Após cada transação  o saldo é atualizado diretamente no Redis, garantindo dados sempre consistentes e atualizados.

4. Escalabilidade para picos de acesso:

Durante períodos de alta demanda, como Black Friday ou fechamento de mês, o Redis é capaz de lidar com volumes elevados de consultas sem degradação no desempenho.
Essa abordagem combina a robustez dos bancos relacionais para consistência transacional com a alta velocidade do Redis, oferecendo uma solução híbrida eficiente e escalável para sistemas financeiros.


## Considerações e melhorias
- Faltou implementar a camada de testes do controller
- Adicionar o swagger
- Criaçao de teste de componente 
- No readme adicionar como rodar os testes
- Na aplication usar um argumento para carregar os dados do banco de dados

