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
  aws dynamodb delete-table --endpoint-url  $DYNAMODB_ENDPOINT --table-name $DESTINATION_TABLE --output yaml --no-cli-pager
  echo "checking if table exists: ${DESTINATION_TABLE}"
  aws dynamodb describe-table --endpoint-url $DYNAMODB_ENDPOINT --table-name $DESTINATION_TABLE --output json --no-cli-pager
  if [[ $? == 254 ]]; then
      echo "creating table definition: ${DESTINATION_TABLE}"
      aws dynamodb create-table --endpoint-url $DYNAMODB_ENDPOINT --table-name $DESTINATION_TABLE --no-cli-pager \
          --attribute-definitions  AttributeName=id,AttributeType=S \
          --key-schema AttributeName=id,KeyType=HASH \
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
          "id": {"S": "810ecfac-6248-3e14-847f-8a81c2b46be1"},
          "destination": {"S": "Houston"}
        }'
  aws dynamodb put-item \
      --endpoint-url $DYNAMODB_ENDPOINT \
      --table-name $DESTINATION_TABLE \
      --item '{
          "id": {"S": "810ecfac-6248-3e14-847f-8a81c2b46be2"},
          "destination": {"S": "Chicago"}
        }'
  aws dynamodb put-item \
      --endpoint-url $DYNAMODB_ENDPOINT \
      --table-name $DESTINATION_TABLE \
      --item '{
          "id": {"S": "810ecfac-6248-3e14-847f-8a81c2b46be3"},
          "destination": {"S": "LA"}
        }'
}

create_receiver_table() {
  aws dynamodb delete-table --endpoint-url  $DYNAMODB_ENDPOINT --table-name $RECEIVER_TABLE --output yaml --no-cli-pager
  echo "checking if table exists: ${RECEIVER_TABLE}"
  aws dynamodb describe-table --endpoint-url $DYNAMODB_ENDPOINT --table $RECEIVER_TABLE --output json --no-cli-pager
  if [[ $? == 254 ]]; then
      echo "creating table definition: ${RECEIVER_TABLE}"
      aws dynamodb create-table --endpoint-url $DYNAMODB_ENDPOINT --table-name $RECEIVER_TABLE --no-cli-pager \
          --attribute-definitions  AttributeName=id,AttributeType=S \
          --key-schema AttributeName=id,KeyType=HASH \
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
          "id": {"S": "810ecfac-6248-3e14-847f-8a81c2b46be1"},
          "receiver": {"S": "UPS"}
        }'
  aws dynamodb put-item \
      --endpoint-url $DYNAMODB_ENDPOINT \
      --table-name $RECEIVER_TABLE \
      --item '{
          "id": {"S": "810ecfac-6248-3e14-847f-8a81c2b46be2"},
          "receiver": {"S": "FedEx"}
        }'
  aws dynamodb put-item \
      --endpoint-url $DYNAMODB_ENDPOINT \
      --table-name $RECEIVER_TABLE \
      --item '{
          "id": {"S": "810ecfac-6248-3e14-847f-8a81c2b46be3"},
          "receiver": {"S": "Old Dominion"}
        }'
}

create_car_table() {
  aws dynamodb delete-table --endpoint-url  $DYNAMODB_ENDPOINT --table-name $CAR_TABLE --output yaml --no-cli-pager
  echo "checking if table exists: ${CAR_TABLE}"
  aws dynamodb describe-table --endpoint-url $DYNAMODB_ENDPOINT --table $CAR_TABLE --output yaml --no-cli-pager
  if [[ $? == 254 ]]; then
      echo "creating table definition: ${CAR_TABLE}"
      aws dynamodb create-table --endpoint-url $DYNAMODB_ENDPOINT --table-name $CAR_TABLE --no-cli-pager \
          --attribute-definitions  AttributeName=id,AttributeType=S \
          --key-schema AttributeName=id,KeyType=HASH \
          --provisioned-throughput ReadCapacityUnits=10,WriteCapacityUnits=5
  fi
  aws dynamodb describe-table --endpoint-url $DYNAMODB_ENDPOINT --table $CAR_TABLE --output yaml --no-cli-pager
}

create_destination_table
populate_destination_table

create_receiver_table
populate_receiver_table

create_car_table

echo "to delete tables"
echo "aws dynamodb delete-table --endpoint-url  $DYNAMODB_ENDPOINT --table-name $CAR_TABLE --output yaml --no-cli-pager"
echo "aws dynamodb delete-table --endpoint-url  $DYNAMODB_ENDPOINT --table-name $DESTINATION_TABLE --output yaml --no-cli-pager"
echo "aws dynamodb delete-table --endpoint-url  $DYNAMODB_ENDPOINT --table-name $RECEIVER_TABLE --output yaml --no-cli-pager"