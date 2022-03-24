import java.util.ArrayList;
import java.util.Collections;

/*
This class is the idea behind the computer move.
It places the possible combination of letters to the left and right end
of the letters in the board and if valid words store the pieces and co-ordinates of
the played position.
 */
public class Computer_Play {
    ArrayList<String> words = new ArrayList<>();
    ArrayList<String> prefix = new ArrayList<>();
    ArrayList<Pieces> tempTray = new ArrayList<>();
    ArrayList<Integer> posX = new ArrayList<>();
    ArrayList<Integer> temposX = new ArrayList<>();

    ArrayList<Integer> posY = new ArrayList<>();
    ArrayList<Integer> temposY = new ArrayList<>();

    ArrayList<Integer> scoress = new ArrayList<>();
    ArrayList<ArrayList<Integer>> storeX = new ArrayList<>();
    ArrayList<ArrayList<Integer>> storeY = new ArrayList<>();
    ArrayList<ArrayList<Pieces>> storePieces= new ArrayList<>();
    ArrayList<Pieces> temptray = new ArrayList<>();
    ArrayList<Pieces> clonetemtray=new ArrayList<>();
    score score = new score();
    boolean right;
    int lScore = 0;
    int newScore = 0;
    int horScore;
    int verScore;
    int horScoreIndex;
    int verScoreIndex;
    public int maxScore;
    public String bestWord;

    /*Start the game by scanning the board and finding valid places to play*/
    public boolean play(Board board, ArrayList<Pieces> tray) {
        for (int i = 0; i < board.size; i++) {
            for (int j = 0; j < board.size; j++) {
                if (board.board.get(i).get(j).hasPiece()) {
                    scanBoard(board, tray, i, j);
                    trayLoop(board, tray, 0, i - 1, j, "left", 0);
                    trayLoop(board, tray, 0, i - 1, j, "left", 0);
                }
            }
        }

        horScoreIndex=highestScore(scoress);
        horScore=scoress.get(highestScore(scoress));

        /*Here we transpose board to check for possible vertical move*/
        board.board=board.transposeMatrix(board.board);
        for (int i = 0; i < board.size; i++) {
            for (int j = 0; j < board.size; j++) {
                if (board.board.get(i).get(j).hasPiece()) {
                    scanBoard(board, tray, i, j);
                }
            }
        }
        board.board=board.transposeMatrix(board.board);
        verScoreIndex=highestScore(scoress);
        verScore=scoress.get(highestScore(scoress));

        /*Comparing the best move on vertical and horizontal board and make a move
        in a certain direction.
         */
        if(horScore>=verScore){
            detectBestPlay(board, tray, horScore, horScoreIndex, storeX, storeY);
        }else {
            detectBestPlay(board, tray, verScore, verScoreIndex, storeY, storeX);
        }
        if(tray.size()!=7){
            return true;
        }
        else return false;
    }

    private void detectBestPlay(Board board, ArrayList<Pieces> tray, int horScore,
                                int horScoreIndex, ArrayList<ArrayList<Integer>> storeX,
                                ArrayList<ArrayList<Integer>> storeY) {
        maxScore= horScore;
        bestWord=words.get(horScoreIndex);
        System.out.print(bestWord+ " has ");
        for (int i = 0; i<storeX.get(horScoreIndex).size(); i++){
            Tiles tile = new Tiles('.','.');
            tile.setPiece(storePieces.get(horScoreIndex).get(i));
            board.board.get(storeX.get(horScoreIndex).get(i)).set(storeY.get(horScoreIndex).get(i),tile);
        }
        String tem1=board.toStringTray(storePieces.get(horScoreIndex));
        for(int i = storeX.get(horScoreIndex).size()-1; i>=0; i--){
            String tem2=""+(char) storePieces.get(horScoreIndex).get(i).getFace();
            if(tem1.contains(tem2)){
                tray.remove(i);
            }
        }
        System.out.println(horScore + " points");
    }

    private void scanBoard(Board board, ArrayList<Pieces> tray, int i, int j) {
        trayLoop(board, tray, 0, i, j, "right", 0);
        trayLoop(board, tray, 0, i, j, "left", 0);
        trayLoop(board, tray, 0, i + 1, j, "right", 0);
        trayLoop(board, tray, 0, i + 1, j, "left", 0);
        trayLoop(board, tray, 0, i - 1, j, "right", 0);
        trayLoop(board, tray, 0, i-1, j, "left", 0);
    }

    public int highestScore(ArrayList<Integer> list){
        int num1 = 0;
        int num2 = 0;
        int y = 1;
        int high = 0;
        for (int i = 0; i< list.size(); i++){
            if (y<= list.size()-1) {
                num1 = list.get(i);
                num2 = list.get(y);
                if ((high < num1) || (high < num2)) {
                    if (num1 > num2) {
                        high = num1;
                    } else if (num2 > num1) {
                        high = num2;
                    }
                }
            }
            y++;
        }
        return list.indexOf(high);
    }

    /*Making a move on a left of the piece on the board*/
    public boolean leftPlay(Board board,ArrayList<Pieces> tray,int x, int y, int countL){
        if(y<0 || countL<0){
            temptray.clear();
            posX.clear();
            posY.clear();
            return true;
        }
        else if(!board.validPlace(board,x,y)){
            leftPlay(board,tray,x,y-1,countL);
        }
        else if(board.validPlace(board,x,y)) {
            board.board.get(x).get(y).setPiece(tray.get(countL));
            /*If there is piece above checking for valid vertical place*/
            if ((x+1<board.size && board.board.get(x + 1).get(y).hasPiece()) ||
                    ((x-1>=0)  && board.board.get(x - 1).get(y).hasPiece())) {
                if (board.validWord(board.toStringPieces(board.verticalCheck(board, x, y)))) {
                    if (board.validWord(board.toStringPieces(board.acrossCheck(board, x, y)))) {
                        temptray.add(tray.get(countL));
                        tempTray.remove(tray.get(countL));
                        countL--;
                        posX.add(x);
                        posY.add(y);
                        if (!words.contains(board.toStringPieces(board.acrossCheck(board, x, y)))) {
                            temposX= (ArrayList<Integer>) posX.clone();
                            temposY= (ArrayList<Integer>) posY.clone();
                            clonetemtray= (ArrayList<Pieces>) temptray.clone();
                            storeX.add(temposX);
                            storeY.add(temposY);
                            storePieces.add(clonetemtray);
                            temptray.add(tray.get(countL));
                            words.add(board.toStringPieces(board.acrossCheck(board, x, y)));
                            lScore=score.calcScore(board, posX, posY);
                            if (countL >= 0) rightPlay(board, tempTray, x, y, 0);
                            if(!right) scoress.add(lScore);
                        }
                        leftPlay(board, tray, x, y - 1, countL);
                        board.board.get(x).get(y).removePiece();
                    } else if (board.validPrefix(board.toStringPieces(board.acrossCheck(board, x, y)))) {
                        leftMove(board, tray, x, y, countL);
                    } else {
                        tempTray.remove(tray.get(countL));
                        posX.add(x);
                        posY.add(y);
                        countL--;
                        leftPlay(board, tray, x, y - 1, countL);
                        board.board.get(x).get(y).removePiece();
                    }
                }
                else {
                    temptray.remove(tray.get(countL));
                    board.board.get(x).get(y).removePiece();
                }
            }
            else {
                if (board.validWord(board.toStringPieces(board.acrossCheck(board, x, y)))) {
                    temptray.add(tray.get(countL));
                    tempTray.remove(tray.get(countL));
                    countL--;
                    posX.add(x);
                    posY.add(y);
                    if (!words.contains(board.toStringPieces(board.acrossCheck(board, x, y)))) {
                        temposX= (ArrayList<Integer>) posX.clone();
                        temposY= (ArrayList<Integer>) posY.clone();
                        clonetemtray= (ArrayList<Pieces>) temptray.clone();
                        storeX.add(temposX);
                        storeY.add(temposY);
                        storePieces.add(clonetemtray);
                        words.add(board.toStringPieces(board.acrossCheck(board, x, y)));
                        lScore=score.calcScore(board, posX, posY);
                        if (countL >= 0) rightPlay(board, tempTray, x, y, 0);
                        if(!right) scoress.add(lScore);
                    }
                    leftPlay(board, tray, x, y - 1, countL);
                    board.board.get(x).get(y).removePiece();
                } else if (board.validPrefix(board.toStringPieces(board.acrossCheck(board, x, y)))) {
                    leftMove(board, tray, x, y, countL);
                } else {
                    temptray.add(tray.get(countL));
                    tempTray.remove(tray.get(countL));
                    countL--;
                    posX.add(x);
                    posY.add(y);
                    leftPlay(board, tray, x, y - 1, countL);
                    board.board.get(x).get(y).removePiece();
                }
            }
        }
        return false;
    }

    private void leftMove(Board board, ArrayList<Pieces> tray, int x, int y, int countL) {
        temptray.add(tray.get(countL));
        tempTray.remove(tray.get(countL));
        countL--;
        posX.add(x);
        posY.add(y);
        if (!prefix.contains(board.toStringPieces(board.acrossCheck(board, x, y)))) {
            prefix.add(board.toStringPieces(board.acrossCheck(board, x, y)));
            if (countL >= 0) rightPlay(board, tempTray, x, y, 0);
        }
        leftPlay(board, tray, x, y - 1, countL);
        board.board.get(x).get(y).removePiece();
    }

    /*Making all the possible combination of a tray and passing it to left and right positions*/
    public void trayLoop(Board board, ArrayList<Pieces> tray, int k, int x, int y, String pos, int tracker) {
        for (int i = k; i < tray.size(); i++) {
            Collections.swap(tray, i, k);
            trayLoop(board, tray, k + 1, x, y, pos, tracker);
            Collections.swap(tray, k, i);
        }
        if (k == tray.size() - 1) {
            if (pos.equals("right")) {
                rightPlay(board, tray, x, y, 0);
            } else if (pos.equals("left")) {
                posX = new ArrayList<>();
                posY = new ArrayList<>();
                temptray=new ArrayList<>();
                tempTray = (ArrayList<Pieces>) tray.clone();
                leftPlay(board, tray, x, y, tray.size() - 1);
            }
        }
    }

    /*Makes possible moves on the right end if the letter*/
    public boolean rightPlay(Board board,ArrayList<Pieces> tray,int x, int y,int counter){
        if(y>=board.size || counter==tray.size()){
            posX.clear();
            posY.clear();
            temptray.clear();
            return true;
        }
        else if(!board.validPlace(board,x,y)){
            rightPlay(board,tray,x,y+1,counter);
        }
        else if(board.validPlace(board,x,y)) {
            board.board.get(x).get(y).setPiece(tray.get(counter));
            if ((x+1<board.size && board.board.get(x + 1).get(y).hasPiece()) ||
                    (x-1>=0 && board.board.get(x - 1).get(y).hasPiece())) {
                if (board.validWord(board.toStringPieces(board.verticalCheck(board, x, y)))) {
                    if (board.validWord(board.toStringPieces(board.acrossCheck(board, x, y)))) {
                        rightMove(board, tray, x, y, counter);
                    } else if (board.validPrefix(board.toStringPieces(board.acrossCheck(board, x, y)))) {
                        temptray.add(tray.get(counter));
                        posX.add(x);
                        posY.add(y);
                        if (!prefix.contains(board.toStringPieces(board.acrossCheck(board, x, y))))
                            prefix.add(board.toStringPieces(board.acrossCheck(board, x, y)));
                        counter++;
                        rightPlay(board, tray, x, y + 1, counter);
                        board.board.get(x).get(y).removePiece();
                    } else {
                        posX.clear();
                        posY.clear();
                        temptray.clear();
                        right=false;
                        board.board.get(x).get(y).removePiece();
                    }
                } else {
                    posX.clear();
                    posY.clear();
                    temptray.clear();
                    right=false;
                    board.board.get(x).get(y).removePiece();
                }
            }else {
                if (board.validWord(board.toStringPieces(board.acrossCheck(board, x, y)))) {
                    rightMove(board, tray, x, y, counter);
                } else if(board.validPrefix(board.toStringPieces(board.acrossCheck(board, x, y)))) {
                    if (!prefix.contains(board.toStringPieces(board.acrossCheck(board, x, y))))
                        prefix.add(board.toStringPieces(board.acrossCheck(board, x, y)));
                    temptray.add(tray.get(counter));
                    counter++;
                    posX.add(x);
                    posY.add(y);
                    rightPlay(board, tray, x, y + 1, counter);
                    board.board.get(x).get(y).removePiece();
                } else {
                    posX.clear();
                    posY.clear();
                    temptray.clear();
                    right=false;
                    board.board.get(x).get(y).removePiece();
                }
            }
        }
        return false;
    }
    private void rightMove(Board board, ArrayList<Pieces> tray, int x, int y, int counter) {
        temptray.add(tray.get(counter));
        counter++;
        posX.add(x);
        posY.add(y);
        if (!words.contains(board.toStringPieces(board.acrossCheck(board, x, y)))) {
            temposX= (ArrayList<Integer>) posX.clone();
            temposY= (ArrayList<Integer>) posY.clone();
            clonetemtray= (ArrayList<Pieces>) temptray.clone();
            storeX.add(temposX);
            storeY.add(temposY);
            storePieces.add(clonetemtray);
            words.add(board.toStringPieces(board.acrossCheck(board, x, y)));
            newScore= score.calcScore(board, posX, posY);
            scoress.add(newScore);
            right=true;
        }
        rightPlay(board, tray, x, y + 1, counter);
        board.board.get(x).get(y).removePiece();
    }
}