
/*
This class creates the pieces to play.
It doesnt create the blank piece.
 */
public class Pieces {
    private final char face;
    private int faceValue;
    public Pieces(char face){
        this.face=face;
        if(face=='a') this.faceValue=1;
        if(face=='b') this.faceValue=3;
        if(face=='c') this.faceValue=3;
        if(face=='d') this.faceValue=2;
        if(face=='e') this.faceValue=1;
        if(face=='f') this.faceValue=4;
        if(face=='g') this.faceValue=2;
        if(face=='h') this.faceValue=4;
        if(face=='i') this.faceValue=1;
        if(face=='j') this.faceValue=8;
        if(face=='k') this.faceValue=5;
        if(face=='l') this.faceValue=1;
        if(face=='m') this.faceValue=3;
        if(face=='n') this.faceValue=1;
        if(face=='o') this.faceValue=1;
        if(face=='p') this.faceValue=3;
        if(face=='q') this.faceValue=10;
        if(face=='r') this.faceValue=1;
        if(face=='s') this.faceValue=1;
        if(face=='t') this.faceValue=1;
        if(face=='u') this.faceValue=1;
        if(face=='v') this.faceValue=4;
        if(face=='w') this.faceValue=4;
        if(face=='x') this.faceValue=8;
        if(face=='y') this.faceValue=4;
        if(face=='z') this.faceValue=10;
    }
    public int getFace(){
        return face;
    }
    public int getFaceValue(){
        return faceValue;
    }
}
