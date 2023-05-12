#!/bin/bash

echo " Setting up localstack profile "
aws configure set aws_access_key_id access_key --profile=localstack
aws configure set aws_secret_access_key secret_key --profile=localstack
aws configure set region us-east-1 --profile=localstack

echo " Setting default profile "
export AWS_DEFAULT_PROFILE=localstack

echo " Setting SQS names as env variables "
export TEST_SQS=my-queue

echo " Creating queues "
aws --endpoint-url=http://localstack:4566 sqs create-queue --queue-name $TEST_SQS

echo " Listing queues "
aws --endpoint-url=http://localhost:4566 sqs list-queues







