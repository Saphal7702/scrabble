import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/*
This class creates the board of the given size and implements several method handy for
implementing it.
 */
public class Board {
    int size;
    ArrayList<ArrayList<Tiles>> board = new ArrayList<>(size);
    Trie trie = new Trie();

    public Board(int size){
        this.size=size;
        for (int i=0; i<size;i++){
            ArrayList<Tiles> rows = new ArrayList<>(size);
            board.add(rows);
        }
    }

    public void readDictionary() throws FileNotFoundException {
        File file = new File("resource/sowpods.txt");
        Scanner sc = new Scanner(file);
        while (sc.hasNext()){
            String str =sc.next().toLowerCase(Locale.ROOT);//.replaceAll("\\s","");
            trie.insert(str);
        }
    }

    public boolean validWord(String word){
        return trie.search(word);
    }
    public boolean validPrefix(String word){
        return trie.startsWith(word);
    }

    public ArrayList<ArrayList<Tiles>> transposeMatrix(ArrayList<ArrayList<Tiles>> list){
        ArrayList<ArrayList<Tiles>> temp = new ArrayList<>();
        int firstListSize = list.get(0).size();
        for (int i = 0; i < firstListSize; i++) {
            ArrayList<Tiles> tempList = new ArrayList<>();
            for (ArrayList<Tiles> row : list) {
                tempList.add(row.get(i));
            }
            temp.add(tempList);
        }
        return temp;
    }

    public void toStringBoard(ArrayList<ArrayList<Tiles>> boardOrginal){
        for(int a=0;a<boardOrginal.size();a++){
            for (int b=0;b<boardOrginal.size();b++){
                char temp1;
                char temp2;
                if(boardOrginal.get(a).get(b).hasPiece()){
                    temp1=' ';
                    temp2 = (char) boardOrginal.get(a).get(b).getPiece().getFace();
                }else {
                    temp1 = boardOrginal.get(a).get(b).getFirstValue();
                    temp2 = boardOrginal.get(a).get(b).getSecondValue();
                }
                System.out.print(temp1);
                System.out.print(temp2);
                System.out.print(" ");
            }
            System.out.println();
        }
    }
    public String toStringTray(ArrayList<Pieces> arr){
        String tem="";
        for (int i=0;i<arr.size();i++) {
            char c = (char) arr.get(i).getFace();
            tem += c;
        }
        return tem;
    }
    public String toStringPieces(ArrayList<Tiles> arr){
        String tem="";
        for (int i=0;i<arr.size();i++) {
            if (arr.get(i).hasPiece()) {
                char c = (char) arr.get(i).getPiece().getFace();
                tem += c;
            }
        }
        return tem;
    }

    /*Checks for the valid place in the board, considering the ends of the board.
    A valid place must be one which doesnt have piece and connected to the other played positions
    of the board.
     */
    public boolean validPlace(Board board,int x,int y){
        if(x<0 || x>=board.board.size() || y< 0 || y>=board.board.size()){
            return false;
        }
        else if(!board.board.get(x).get(y).hasPiece()){
            if(x==0){
                if(y==0){
                return board.board.get(x).get(y + 1).hasPiece() ||
                        board.board.get(x + 1).get(y).hasPiece();
                }else if(y==board.size-1){
                    return board.board.get(x).get(y - 1).hasPiece() ||
                            board.board.get(x + 1).get(y).hasPiece();
                }
                else {
                    return board.board.get(x).get(y - 1).hasPiece() ||
                            board.board.get(x + 1).get(y).hasPiece() ||
                            board.board.get(x).get(y + 1).hasPiece();
                }
            }
            else if(y==0){
                if(x==board.size-1) {
                    return board.board.get(x).get(y + 1).hasPiece() ||
                            board.board.get(x - 1).get(y).hasPiece();
                }else {
                    return board.board.get(x).get(y + 1).hasPiece() ||
                            board.board.get(x - 1).get(y).hasPiece()||
                            board.board.get(x + 1).get(y).hasPiece();
                }
            }
            else if(x== board.size-1 ){
                if(y==board.size-1) {
                    return board.board.get(x).get(y - 1).hasPiece() ||
                            board.board.get(x - 1).get(y).hasPiece();
                }else {
                    return board.board.get(x).get(y - 1).hasPiece() ||
                            board.board.get(x - 1).get(y).hasPiece()||
                            board.board.get(x).get(y+1).hasPiece();
                }
            }else if(y==board.size-1){
                 return board.board.get(x).get(y - 1).hasPiece() ||
                        board.board.get(x + 1).get(y).hasPiece() ||
                        board.board.get(x-1).get(y).hasPiece();
            }
            else {
                return board.board.get(x).get(y+1).hasPiece() || board.board.get(x).get(y-1).hasPiece() ||
                        board.board.get(x+1).get(y).hasPiece() || board.board.get(x-1).get(y).hasPiece();
            }
        }else return false;
    }

    /*This checks for the valid across word played finding the reference to check with*/
    int count;
    int pos;
    int pos1;
    String prefix;
    boolean flag;
    public ArrayList<Tiles> acrossCheck(Board board,int x, int y){
        ArrayList<Tiles> wordTiles=new ArrayList<>();
        pos=y;
        prefix="";
        flag=true;
        count=0;
        while (flag) {
            if (pos == 0 || pos>=board.size) {
                flag=false;
            }
            else
                {
                    pos--;
                    if (board.board.get(x).get(pos).hasPiece()) {
                        count++;
                    }
                    else {
                        flag = false;
                    }
                }
        }
        pos1=y-count;
            while (pos1< board.size) {
                if(board.board.get(x).get(pos1).hasPiece()){
                    wordTiles.add(board.board.get(x).get(pos1));
                    pos1++;
                }else break;
        }
         return wordTiles;
    }
    /*This checks for the valid vertical word*/
    public ArrayList<Tiles> verticalCheck(Board board,int x, int y){
        ArrayList<Tiles> wordTiles=new ArrayList<>();
        pos=x;
        prefix="";
        flag=true;
        count=0;
        while (flag) {
            if (pos <= 0 || pos>=board.size) {
                flag=false;
            }
            else
            {
                pos--;
                if (board.board.get(pos).get(y).hasPiece()) {
                    count++;
                }
                else {
                    flag = false;
                }
            }
        }
        pos1=x-count;
        while (pos1< board.size) {
            if(board.board.get(pos1).get(y).hasPiece()){
                wordTiles.add(board.board.get(pos1).get(y));
                pos1++;
            }else break;
        }
        return wordTiles;
    }
}
