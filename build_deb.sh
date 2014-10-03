#!/bin/bash
BIN=`which sbt`
SCRIPT_PATH=$(cd ${0%/*} && echo $PWD/${0##*/})
ROOT=`dirname "${SCRIPT_PATH}"`

if [ -z "${BIN}" ]; then
    echo "sbt not found in ${PATH}"
    exit 1
fi

cd "${ROOT}"

"${BIN}" 'debian:packageBin'
