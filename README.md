Project-3 Scrabble.

//Trie: Game starts with storing the dictionary in a trie data structure. It makes easier to 
search and store the data in a node. Here each node has 26 other nodes and so on till it forms
the word in a dictionary.

//Pieces: It creates and stores the given varities of pieces(called tiles in project)
It has face (a,b,c...) and face value(1,2,3....).

//Tiles: It is like a checker box of a chess board. It holds the pieces and have
multiplier as a properties. It sets, removes the pieces in the board.

//Tray: This class initialize the tray. i.e. creates 98 pieces accoding to standard
scrabble game and distributes 7 each to human an computer rack.
Note: There is no blank piece.

//Board: This class creates the 2x2 arraylist board to store the game board of the given size.
Also this reads and store the dictionary file. It also has several methods like toStrings and transpose
required to play the game. It checks valid palce in the board and make across and vertical check 
after placing the pieces. 

//Computer play: This is the main idea behind the game. It implements trie data structure and
"Brute force" approch to find the best possible words.
Logic: It scans the whole board and find piece to play. If the is a valid place to make a move,
it places the piece and check if it make a valid word or prefix, if not it back tracks.
Tray loop: It makes all the possible combinations of the given tray and pass it to left and right tray.
i.e. if there are 7 pieces, it makes 7! permutaions. After this left and right play are called.
Right play: Here for all the possible combinations of tray we checks for valid place in right direction
and if found place the piece and check for legal word or prefix. If valid word or prefix moves forward and
add another piece and store the valid word pieces and coordinates, if not remove the placed piece.
Left play: Here pieces are placed in the left of the board and for the rest of the tray right play is called,
which helps to form word in both left and right of the letter if possible. 
We rotate the board the do the same for any valid vertical word. 
For each word its coordinates and score is stored and for the highest score, it place the piece and a move is made.

//Word solver: This class reads the files from given input files and create the board 
with initial conditions and tray. It calls the computer play class and make move. This class
is responsible for the console version of the game. It creats the required tiles and pieces
for the initial board.

//Scrabble GUI: This class creates the required GUI interface for the game. Human moves are tracked and scores are calculated
here. It implements grid pane and border pane and several hboxes and vboxes for the layout.
Several buttons and lables are implemented to display and play game.

//Game play: //Right click on the pieces from the tray we want to play and right click the position,
we want to play on the board. //First word must touch the center. After placing the pieces, we must hit play button
to play the game. //Reverse button reverse the move and pass button pass the move to computer.
Note: There is no implementation of a blank tile.