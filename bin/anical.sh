#!/bin/sh

PROGRAM_API_URL="http://localhost:9000/programs?groupId=1"

which jq > /dev/null 2>&1
if [ $? -eq 1 ]; then
    echo "not found jq"
    exit 1
fi

curl "${PROGRAM_API_URL}" | jq -r ".[] | \"\(.start_time|split(\"T\")|.[1])〜\(.end_time|split(\"T\")|.[1]) [\(.channel.name)] \(.name) / \(.title)\""

exit 0
