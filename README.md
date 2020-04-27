# message-broker-kafka-services
Projeto de um Message Broker com Kafka + Zookeeper e micro serviços para consumo e envio de mensagens

## docker-compose-kafka-single-node

Docker-Compose para a instalação de um single node do Zookeeper + Kafka num ambiente docker.

## spring-boot-kafka-example

Módulo Spring Boot Kafka Client para processamento de mensagens no Kafka. Responsável pela configuração, produção e consumo. Dispõe de uma API REST para permitir o disparo de mensagens a partir de outros componentes. Possui camada de persistência em base Oracle. Pode ser implantado via Docker.

## core-message-sender-example

Módulo Spring Boot Core Message Sender para o processamento e envio de mensagens para um componente final, podendo este ser um client SMTP ou até mesmo outra API. As mensagens são consumidas via API REST. Tem o papel de validar e consolidar todas as mensagens recebidas, garantindo seu destino e atualizando o status na base. Possui camada de persistência em base Oracle. Pode ser implantado via Docker.
