#!/bin/sh

BIN=/opt/bin
sudo [ -d $BIN ] && exit(0)
sudo [ -d $BIN ] || sudo mkdir $BIN
sudo cp -rf ./bin/* $BIN/.
sudo chmod 755 $BIN/*
