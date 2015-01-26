#!/bin/bash
# FIXME: replace with sbt-powered checker
SCRIPT_PATH=$(cd ${0%/*} && echo $PWD/${0##*/})
ROOT=`dirname "${SCRIPT_PATH}"`

if [[ -n `which jshit` ]]; then
    echo -e "jshint is required\nInstall it with\nnpm install -g jshint"
    exit 2
fi

jshint --config "$ROOT/.jshintrc" "$ROOT/public" "$ROOT/app"

if [[ $? == 0 ]];then
    echo "Ok"
else
    exit 1
fi
