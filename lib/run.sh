#!/bin/sh

BIN=/opt/bin
[ -d $BIN ] && exit 0
[ -d $BIN ] || sudo mkdir $BIN
cp -rf ./bin/* $BIN/.
chmod 755 $BIN/*
