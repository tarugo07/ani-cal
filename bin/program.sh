#!/bin/sh

if [ $# -ne 2 ]; then
    echo "invalid parameter"
    exit 1
fi

DATE=$1
GROUP_ID=$2

which gdate > /dev/null 2>&1
if [ $? -eq 1 ]; then
    echo "not found gdate command"
    exit 1
fi

gdate +%Y%m%d --date "${DATE}"
if [ $? -eq 1 ]; then
    echo "invalid date: ${DATE}"
    exit 1
fi

API_URL="http://localhost:9000/programs?date=${DATE}&groupId=${GROUP_ID}"

which jq > /dev/null 2>&1
if [ $? -eq 1 ]; then
    echo "not found jq command"
    exit 1
fi

curl "${API_URL}" | jq -r ".[] | \"\(.start_time|split(\"T\")|.[1])〜\(.end_time|split(\"T\")|.[1]) [\(.channel.name)] \(.name) / \(.title)\""

exit 0
