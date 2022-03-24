import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

/*
This class reads the files from given input files and create the board
with initial conditions and tray. It calls the computer play class and make move.
 */
public class WordSolver {
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("resource/input.txt");
        Scanner sc = new Scanner(file);
        while (sc.hasNext()){
            if(sc.hasNextInt()){
                int i =Integer.parseInt(sc.next());
                /*Creates new board*/
                Board board = new Board(i);
                board.readDictionary();
                ArrayList<Pieces> playDeck = new ArrayList<>();
                for(int j=0;j<i;j++){
                    for (int k=0;k<i;k++){
                        String str = sc.next().toLowerCase(Locale.ROOT);
                        if(str.length()==1){
                            Tiles tiles=new Tiles('.','.');
                            board.board.get(j).add(tiles);
                            board.board.get(j).get(k).setPiece(new Pieces(str.charAt(0)));
                        }
                        else{
                            Tiles tiles = new Tiles(str.charAt(0), str.charAt(1));
                            board.board.get(j).add(tiles);
                        }
                    }
                }
                System.out.println("Input Board:");
                board.toStringBoard(board.board);

                String deck = sc.next();
                for (int a=0;a<deck.length();a++){
                    playDeck.add(new Pieces(deck.charAt(a)));
                }

                System.out.print("Tray: ");
                System.out.println(board.toStringTray(playDeck));
                System.out.print("Solution ");

                /*Makes computer move*/
                Computer_Play play=new Computer_Play();
                play.play(board,playDeck);

                System.out.println("Solution Board:");

                board.toStringBoard(board.board);
            }
        }
    }
}