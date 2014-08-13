#!/bin/bash
SCRIPT_PATH=$(cd ${0%/*} && echo $PWD/${0##*/})
ROOT=`dirname "${SCRIPT_PATH}"`

cd "${ROOT}"

echo -ne "\033]2;zaglushka.py for hedgehog\007"
echo -e "Run zaglushka.py ..."
zaglushka.py --ports=8000 "--config=${ROOT}/api_stubs/config.json"
