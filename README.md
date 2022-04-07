# Minesweeper
The original minesweeper for shell with three available difficulties: Easy, Medium and Hard.

# Play Environments
The minesweeper works for every kind of Unix OS (Mac or Linux), but not for Windows.

# Installation
In order to play the Minsweeper is neccesary to have install java 11.0.0.0, if you don't have it, you could install it from here: https://www.java.com/es/download/

When you have the appropiate java version, create a directory called Games4Shell in your home directory, then download or clone the repository and put it in the directory Games4Shell.
1. Go to the $HOME: $ cd
2. Make Games4Shell directory: $ mkdir Games4Shell

# How to play
1. Open the Shell (Terminal).
2. Go to the $HOME: $ cd
3. Go to the Minesweeper directory that you have just clone or download: $c d Games4Shell/Minesweeper/
4. Give Minesweeper.sh execution permission: $ chmod +x Minesweeper.sh
5. Now you can play typing in the shell (Terminal): $ ./Minesweeper.sh

Extra:
You can acces the quickest games records typing: $ ./Minesweeper.sh -t

# Suggestions
Create and export an alias in order to play from anywhere in your shell withoot having to type the path to Minesweeper.sh:
1. $ alias Minesweeper=$HOME/Games4Shell/Minesweeper/Minesweeper.sh
2. Now when you type Minesweeper the game should start

Create and export an alias in order to see the top from anywhere in your shell withoot having to type the path to Minesweeper.sh -t:
1. $ alias MinesweeperTop=$HOME/Games4Shell/Minesweeper/Minesweeper.sh -t
2. Now when you type MinesweeperTop the top interface should start.

!!!!Remember, you would need to do this every time you close the Shell or the Terminal!!!!
