#!/bin/bash
PORT=9010
BIN=`which activator`
SCRIPT_PATH=$(cd ${0%/*} && echo $PWD/${0##*/})
ROOT=`dirname "${SCRIPT_PATH}"`

TYPE="${1}"
CONFIG="${2}"
cd "${ROOT}"

echo -ne "\033]2;keemun play app\007"
echo -e "Run app in ${ROOT}, port ${PORT}, play=${BIN}\n"
if [ -z "${CONFIG}" ]; then
    if [ "${TYPE}" == "real" ]; then
        echo "With real data (application.conf)"
        "${BIN}" \
            "~run ${PORT}"
    else
        echo "With stubbed data (dev.conf)"
        "${BIN}" \
            "-Dconfig.file=${ROOT}/conf/dev.conf" \
            "~run ${PORT}"
    fi
else
    echo "With config ${CONFIG}"
    "${BIN}" \
        "-Dconfig.file=${CONFIG}" \
        "~run ${PORT}"
fi
