/*
This class is the tiles of the board.
It stores the pieces in it and have multiplier value.
 */
public class Tiles {
    public  char firstValue;
    public  char secondValue;
    public Pieces piece;
    public String multiplier;

    public Tiles(char firstValue, char secondValue){
        this.firstValue=firstValue;
        this.secondValue=secondValue;
        if(firstValue-'2'==0) this.multiplier ="DW";
        else if(firstValue-'3'==0) this.multiplier ="TW";
        else if(secondValue-'2'==0) this.multiplier ="DL";
        else if(secondValue-'3'==0) this.multiplier ="TL";
        else this.multiplier ="PL";
    }

    public char getFirstValue() {
        return firstValue;
    }
    public char getSecondValue() {
        return secondValue;
    }

    public boolean hasPiece(){
        return piece!=null;
    }
    public Pieces getPiece(){
        return piece;
    }
    public void setPiece(Pieces piece)
    {
        this.piece=piece;
    }
    public void removePiece(){
        this.piece=null;
    }

    public String getMultiplier(){
        return multiplier;
    }
    public void removeMultiplier(){
        this.multiplier="PL";
    }
}
