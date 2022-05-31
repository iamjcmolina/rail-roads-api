#!/bin/bash

echo "starting up local dynamodb docker"
docker-compose up -d
echo "sleeping 10s"
sleep 10
DYNAMODB_ENDPOINT="http://localhost:9001"
CAR_TABLE="rail-roads-car"
DESTINATION_TABLE="rail-roads-destination"
RECEIVER_TABLE="rail-roads-receiver"

create_destination_table() {
  echo "checking if table exists: ${DESTINATION_TABLE}"
  aws dynamodb describe-table --endpoint-url $DYNAMODB_ENDPOINT --table-name $DESTINATION_TABLE --output json --no-cli-pager
  if [[ $? == 254 ]]; then
      echo "creating table definition: ${DESTINATION_TABLE}"
      aws dynamodb create-table --endpoint-url $DYNAMODB_ENDPOINT --table-name $DESTINATION_TABLE --no-cli-pager \
          --attribute-definitions  AttributeName=destination,AttributeType=S \
          --key-schema AttributeName=destination,KeyType=HASH \
          --provisioned-throughput ReadCapacityUnits=10,WriteCapacityUnits=5
  fi
  aws dynamodb describe-table --endpoint-url $DYNAMODB_ENDPOINT --table-name $DESTINATION_TABLE --output json --no-cli-pager
}
populate_destination_table() {
  echo "populating destination table"
  aws dynamodb put-item \
      --endpoint-url $DYNAMODB_ENDPOINT \
      --table-name $DESTINATION_TABLE \
      --item '{
          "destination": {"S": "Houston"}
        }'
  aws dynamodb put-item \
      --endpoint-url $DYNAMODB_ENDPOINT \
      --table-name $DESTINATION_TABLE \
      --item '{
          "destination": {"S": "Chicago"}
        }'
  aws dynamodb put-item \
      --endpoint-url $DYNAMODB_ENDPOINT \
      --table-name $DESTINATION_TABLE \
      --item '{
          "destination": {"S": "LA"}
        }'
}

create_receiver_table() {
  echo "checking if table exists: ${RECEIVER_TABLE}"
  aws dynamodb describe-table --endpoint-url $DYNAMODB_ENDPOINT --table $RECEIVER_TABLE --output json --no-cli-pager
  if [[ $? == 254 ]]; then
      echo "creating table definition: ${RECEIVER_TABLE}"
      aws dynamodb create-table --endpoint-url $DYNAMODB_ENDPOINT --table-name $RECEIVER_TABLE --no-cli-pager \
          --attribute-definitions  AttributeName=receiver,AttributeType=S \
          --key-schema AttributeName=receiver,KeyType=HASH \
          --provisioned-throughput ReadCapacityUnits=10,WriteCapacityUnits=5 
  fi
  aws dynamodb describe-table --endpoint-url $DYNAMODB_ENDPOINT --table $RECEIVER_TABLE --output json --no-cli-pager
}

populate_receiver_table() {
  echo "populating receiver table"
  aws dynamodb put-item \
      --endpoint-url $DYNAMODB_ENDPOINT \
      --table-name $RECEIVER_TABLE \
      --item '{
          "receiver": {"S": "UPS"}
        }'
  aws dynamodb put-item \
      --endpoint-url $DYNAMODB_ENDPOINT \
      --table-name $RECEIVER_TABLE \
      --item '{
          "receiver": {"S": "FedEx"}
        }'
  aws dynamodb put-item \
      --endpoint-url $DYNAMODB_ENDPOINT \
      --table-name $RECEIVER_TABLE \
      --item '{
          "receiver": {"S": "Old Dominion"}
        }'
}

create_car_table() {
  echo "to delete table: aws dynamodb delete-table --endpoint-url  $DYNAMODB_ENDPOINT --table-name $CAR_TABLE --output yaml --no-cli-pager"
  echo "checking if table exists: ${CAR_TABLE}"
  aws dynamodb describe-table --endpoint-url $DYNAMODB_ENDPOINT --table $CAR_TABLE --output yaml --no-cli-pager
  if [[ $? == 254 ]]; then
      echo "creating table definition: ${CAR_TABLE}"
      aws dynamodb create-table --endpoint-url $DYNAMODB_ENDPOINT --table-name $CAR_TABLE --no-cli-pager \
          --attribute-definitions  AttributeName=nameOfCar,AttributeType=S AttributeName=destination,AttributeType=S AttributeName=receiver,AttributeType=S \
          --key-schema AttributeName=nameOfCar,KeyType=HASH AttributeName=destination,KeyType=HASH AttributeName=receiver,KeyType=HASH \
          --provisioned-throughput ReadCapacityUnits=10,WriteCapacityUnits=5
  fi
  aws dynamodb describe-table --endpoint-url $DYNAMODB_ENDPOINT --table $CAR_TABLE --output yaml --no-cli-pager
}

create_destination_table
populate_destination_table

create_receiver_table
populate_receiver_table

create_car_table