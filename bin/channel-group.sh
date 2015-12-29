#!/bin/sh

API_URL="http://localhost:9000/groups"

which jq > /dev/null 2>&1
if [ $? -eq 1 ]; then
    echo "not found jq command"
    exit 1
fi

curl "${API_URL}" | jq -r ".[] | \"\(.id) [\(.name)]\""

exit 0
