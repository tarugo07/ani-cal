#!/bin/sh

if [ $# -ne 2 ]; then
    echo "invalid parameter"
    exit 1
fi

DATE=$1
GROUPID=$2

which gdate > /dev/null 2>&1
if [ $? -eq 1 ]; then
    echo "not found gdate"
    exit 1
fi

gdate +%Y%m%d --date "${DATE}"
if [ $? -eq 1 ]; then
    echo "invalid date: ${DATE}"
    exit 1
fi

PROGRAM_API_URL="http://localhost:9000/programs?date=${DATE}&groupId=${GROUPID}"

which jq > /dev/null 2>&1
if [ $? -eq 1 ]; then
    echo "not found jq"
    exit 1
fi

curl "${PROGRAM_API_URL}" | jq -r ".[] | \"\(.start_time|split(\"T\")|.[1])〜\(.end_time|split(\"T\")|.[1]) [\(.channel.name)] \(.name) / \(.title)\""

exit 0
