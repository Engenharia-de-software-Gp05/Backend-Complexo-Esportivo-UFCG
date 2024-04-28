#!/bin/bash

function usage() {
  echo "Usage: ./deploy.sh -v <version> -ddl <ddl_auto>"
  echo "Options:"
  echo "  -v, --version   : Specify the version of the Docker image to deploy."
  echo "  -ddl, --ddl_auto: Specify the ddl_auto value (update, create, validate). Default is validate."
}

# Default values
VERSION=""
DDL_AUTO="validate"

# Parse command-line arguments
while [[ "$#" -gt 0 ]]; do
  case $1 in
    -v|--version) VERSION="$2"; shift ;;
    -ddl|--ddl_auto) DDL_AUTO="$2"; shift ;;
    *) echo "Unknown parameter passed: $1"; usage; exit 1 ;;
  esac
  shift
done

# Check if version is provided
if [ -z "$VERSION" ]; then
  echo "Error: Version not specified!"
  usage
  exit 1
fi

# Variables
DOCKER_IMAGE="ratorock/joga-junto-api:$VERSION"
DOCKER_CONTAINER="jogajunto"

# SSH to server
ssh ubuntu@35.174.62.157 << EOF
  docker rm -f $DOCKER_CONTAINER
  docker pull $DOCKER_IMAGE
  docker run -d --name $DOCKER_CONTAINER -e ddl=$DDL_AUTO --env-file .env --network host $DOCKER_IMAGE
EOF
