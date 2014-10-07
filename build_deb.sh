#!/bin/bash
BIN=`which sbt`
SCRIPT_PATH=$(cd ${0%/*} && echo $PWD/${0##*/})
ROOT=`dirname "${SCRIPT_PATH}"`

RELEASE="${1}"

if [ -z "${BIN}" ]; then
    echo "sbt not found in ${PATH}"
    exit 1
fi

cd "${ROOT}"

if [ -n "${RELEASE}" ]; then
    "${BIN}" "-Dproject.version=${RELEASE}" 'debian:packageBin'
else
    "${BIN}" 'debian:packageBin'
fi
