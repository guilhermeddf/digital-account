#!/usr/bin/env bash

echo "Setting up localstack profile "
aws configure set aws_access_key_id access_key --profile=localstack
aws configure set aws_secret_access_key secret_key --profile=localstack
aws configure set region us-east-1 --profile=localstack

echo "Setting default profile"
export AWS_DEFAULT_PROFILE=localstack

echo "Criando CloudFormation"
aws cloudformation deploy --stack-name stack-sample --template-file "/cloudformation/template/cloudformation-sample.yml" --endpoint-url http://localstack:4566