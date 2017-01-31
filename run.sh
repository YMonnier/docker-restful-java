#!/bin/bash
# @author Ysee

ip=$1

usage () {
    echo -e "\033[31mrun.sh, version 1.0, YMonnier(https://github.com/YMonnier)\nUsage: ./run.sh [ip]\nip: using 'docker-machine ip' or put docker bridge ip\033[0m";
}

if [ $# = 0 ]; then
    usage;
    exit 1;
fi

if [[ ! $ip =~ ^[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}$ ]]; then
    echo -e "\033[31mThe parameter is not a valid ip address\n\033[0m";
    usage;
    exit 1;
fi

sub="s/http:\/\/[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}:8080\/littleapp/http:\/\/$ip:8080\/littleapp/g"
s1="./web/app/"
s2="./web/dist/"

if [ "$(uname)" == "Darwin" ]; then
    find $s1 $s2 -name "*.js" | xargs sed -i '' -E $sub
elif [ "$(expr substr $(uname -s) 1 5)" == "Linux" ]; then
    find $s1 $s2 -name "*.js" | xargs sed -i'' -r -e $sub
fi