#!/bin/bash

echo " Setting up localstack profile "
aws configure set aws_access_key_id access_key --profile=localstack
aws configure set aws_secret_access_key secret_key --profile=localstack
aws configure set region us-east-1 --profile=localstack

echo " Setting default profile "
export AWS_DEFAULT_PROFILE=localstack

#echo ### Criando Chaves no AWS Parameter Store do LocalStack...
#aws --endpoint http://localhost:4566 --profile localstack ssm put-parameter --name "/config/spring-boot-localstack_localstack/helloWorld" --value "Hello World Parameter Store" --type String
#aws --endpoint http://localhost:4566 --profile localstack ssm put-parameter --name "/config/spring-boot-localstack_localstack/sqsQueueName" --value "process-queue" --type String
#aws --endpoint http://localhost:4566 --profile localstack ssm put-parameter --name "/config/spring-boot-localstack_localstack/s3Bucket" --value "process-storage" --type String

echo ### Criando Segredos no AWS Secret Manager do LocalStack...
aws --endpoint http://localhost:4566 --profile localstack secretsmanager create-secret --name process-secrets-manager --description "Exemplo de Secrets Manager" --secret-string "{\"user\":\"guilhermeddf\",\"password\":\"1234\"}"

echo ### Criando Bucket no S3 do LocalStack...
aws --endpoint http://localhost:4566 --profile localstack s3 mb s3://process-storage

echo ### Criando Queue(Standard) no SQS do LocalStack...
aws --endpoint http://localhost:4566 --profile localstack sqs create-queue --queue-name process-queue
#aws --endpoint http://localhost:4566 --profile localstack sqs send-message --queue-url http://localhost:4566/000000000000/sqsHelloWorld --message-body "Hello World SQS!!!" --delay-seconds 1
#aws --endpoint http://localhost:4566 --profile localstack sqs receive-message --queue-url http://localhost:4566/000000000000/sqsHelloWorld

#echo ### Criando Queue(Standard) no SNS do LocalStack...
#aws --endpoint http://localhost:4566 --profile localstack sns create-topic --name snsHelloWorld
#aws --endpoint http://localhost:4566 --profile localstack sns subscribe --topic-arn arn:aws:sns:us-east-1:000000000000:snsHelloWorld --protocol sqs --notification-endpoint arn:aws:sqs:us-east-1:000000000000:sqsHelloWorld

echo ### Criando Dynamo table do LocalStack...
aws --endpoint http://localhost:4566 --profile localstack dynamodb create-table --cli-input-json '{"TableName":"Test", "KeySchema":[{"AttributeName":"id","KeyType":"HASH"}], "AttributeDefinitions":[{"AttributeName":"id","AttributeType":"S"}],"BillingMode":"PAY_PER_REQUEST"}'
