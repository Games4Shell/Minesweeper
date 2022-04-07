#!/bin/sh

usage(){
	echo "usage: Minesweeper.sh [-t]" 1>&2
	exit 1
}

if [ $# -gt 1 ]; then
	usage
fi

cwd=$(pwd)

cd $HOME/Games4Shell/Minesweeper/

if ! [ $(ls | grep -E 'topEasy\.txt') ]; then touch topEasy.txt; fi
if ! [ $(ls | grep -E 'topMedium\.txt') ]; then touch topMedium.txt; fi
if ! [ $(ls | grep -E 'topHard\.txt') ]; then touch topHard.txt; fi


javac -encoding utf-8 HumanPlayer.java

if [ $# -eq 0 ]; then
        java HumanPlayer
        exit 0
fi

if [ "$1" != -t  ]; then
	usage
fi

if [ "$1" = -t ]; then
	echo "\t\tTop Display\n"
	java Top
	exit 0
fi

cd $cwd
