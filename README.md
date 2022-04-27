# Comunicação rabbit MQ

## Enunciado da prova

Você está trabalhando em uma empresa que utiliza o modelo de comunicação baseado em fila de mensagens, mais especificamente o produto RabbitMQ. Você faz parte do time que está desenvolvendo um módulo que realiza a conversão de imagens para tons de cinza com o objetivo de reduzir o espaço ocupado pelas imagens.

A arquitetura deste módulo permite que vários clientes (produtores) enviem as imagens para uma fila de imagens. Por conta do processamento e pensando na escalabilidade do sistema, vários servidores de conversão (consumidores) pegam as imagens desta fila e fazem a conversão. Feita a conversão da imagem, a imagem já em escala de cinza é enviada para uma próxima etapa para que sejam salvas nos servidores de armazenamento. Para evitar que os arquivos sejam perdidos, todos os servidores armazenam todas as imagens, ou seja, há redundância no armazenamento das imagens.

A arquitetura é exemplificada na figura 1.

![image](https://user-images.githubusercontent.com/56604262/165623433-fe9b6e94-e8ab-4860-819e-8bae0fd6b7da.png)

Lembre-se que o sistema pode ter vários clientes, vários servidores de conversão das imagens e vários servidores de armazenamento.

Utilize como exemplo a base de imagens disponibilizada na atividade da prova para os testes. Não há necessidade de utilizar todas, é apenas um conjunto de imagens para servir para os testes.

Faça com que as imagens convertidas e armazenadas nos servidores tenham o mesmo nome das imagens originais.

Deixe o seu programa minimamente testável para que seja possível executar as instâncias (clientes e servidores) e verificar o funcionamento do sistema.

Dica! Para facilitar, você pede ter uma pasta com as imagens que cada cliente vai enviar, bem como uma pasta para cada servidor de armazenamento. Isso facilita a execução e os testes na mesma máquina. Você pode fazer com que o cliente 1 leia a pasta 1 dos clientes e o cliente 2 leia a pasta 2 dos clientes. Do mesmo modo, o servidor de armazenamento 1 pode salvar as imagens na pasta 1 dos servidores e o servidor de armazenamento 2 na pasta 2 dos servidores.

No momento do envio da atividade, configure o programa para rodar na máquina local e o RabbitMQ com o usuário e a senha “guest”.
