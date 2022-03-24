import java.util.ArrayList;

/*
This class calculates the score given the board and its co-ordinates.
If it makes the vertical and horizontal word at the same time, it
calculates both.
 */
public class score {
    public int calcScore(Board board, ArrayList<Integer> posX, ArrayList<Integer>posY){
        int score = scores(board.acrossCheck(board,posX.get(posX.size()-1),posY.get(posY.size()-1)));
        for (int i=0;i<posX.size();i++){
            int x = posX.get(i);
            int y = posY.get(i);
            if ((x - 1 > 0 && board.board.get(x - 1).get(y).hasPiece()) ||
                    (x + 1 < board.size && board.board.get(x + 1).get(y).hasPiece())) {
                score += scores(board.verticalCheck(board, x, y));
            }
        }
        if(posX.size()==7) return score+50;
        else return score;
    }
    public int scores(ArrayList<Tiles> arr){
        int score=0;
        boolean dw=false;
        boolean tw=false;
        for (int i=0;i<arr.size();i++){
            if(arr.get(i).getMultiplier().equals("DL")){
                score+= 2*(arr.get(i).getPiece().getFaceValue());
            }else if(arr.get(i).getMultiplier().equals("TL")){
                score+= 3*(arr.get(i).getPiece().getFaceValue());
            }else if(arr.get(i).getMultiplier().equals("DW")){
                score+=arr.get(i).getPiece().getFaceValue();
                dw=true;
            }else if(arr.get(i).getMultiplier().equals("TW")){
                score+=arr.get(i).getPiece().getFaceValue();
                tw=true;
            }else score+=arr.get(i).getPiece().getFaceValue();
        }
        if(dw) return 2*score;
        else if(tw) return 3*score;
        else return score;
    }
}