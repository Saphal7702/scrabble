import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.File;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

/*This is GUI class for scrabble game. It reads the board and create the GUI interface.
Move can be made by clicking the piece from the tray and the rectangle we want to place.
After placing the words, play button makes a move if valid and calls computer to play.
Reverse button reverse the move and pass button calls computer to make move.
 */
public class scrabbleGUI extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    /*All the required variables and hboxes and vboxes for GUI.*/
    Board board = new Board(15);
    Tray tray = new Tray();
    score Score=new score();

    BorderPane borderPane=new BorderPane();
    GridPane screen = new GridPane();
    ArrayList<Pieces> playedPieces=new ArrayList<>();
    ArrayList<Integer> posX=new ArrayList<>();
    ArrayList<Integer> posY=new ArrayList<>();

    int currentHumanScore=0;
    int totalHumanScore=0;
    int computerScore=0;
    int totalComputerScore=0;
    boolean legalPlay;

    VBox vBox = new VBox(30);

    Button play = new Button("Play");
    Button reverse = new Button("Reverse");
    Button pass = new Button("Pass");
    Button exit = new Button("EXIT");

    HBox score=new HBox(10);
    Label humanScoreUpdate=new Label();
    Label computerScoreUpdate=new Label();
    HBox display=new HBox();
    Label gameUpdate=new Label();
    HBox computerDisplay=new HBox();
    Label computerGameUpdate=new Label();

    HBox hBox1=new HBox(10);
    HBox hBox2 = new HBox();
    HBox hBox3=new HBox();

    private Pieces pieces;
    private int index;
    private boolean firstClick;
    private boolean firstPlay=true;
    /*Read and displays the human pieces rack*/
    public void displayHumanTray(){
        hBox2.getChildren().clear();
        for (int i=0;i<tray.humanTray.size();i++){
            char tem = (char) tray.humanTray.get(i).getFace();
            Image humanDeck = new Image("file:resource/"+tem+".jpg");
            ImageView humanDeckView = new ImageView(humanDeck);
            humanDeckView.setFitHeight(40);
            humanDeckView.setFitWidth(40);
            humanDeckView.setPreserveRatio(true);
            humanDeckView.setVisible(true);
            VBox temVbox= new VBox();
            temVbox.setId(Integer.toString(i));
            humanDeckView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    firstClick=true;
                    index=Integer.parseInt(temVbox.getId());
                    pieces=tray.humanTray.get(index);
                }
            });
            temVbox.getChildren().addAll(humanDeckView);
            hBox2.getChildren().addAll(temVbox);
        }
    }
    /*Read and displays the game board*/
    public void displayBoard() {
        for (int j = 0; j < boardSize; j++) {
            for (int k = 0; k < boardSize; k++) {
                final Rectangle tilesShape = new Rectangle(40, 40);
                if(board.board.get(j).get(k).hasPiece()){
                    char tem = (char) board.board.get(j).get(k).getPiece().getFace();
                    Image boardPiece = new Image("file:resource/"+tem+".jpg");
                    ImageView boardPieceView= new ImageView(boardPiece);
                    boardPieceView.setFitHeight(40);
                    boardPieceView.setFitWidth(40);
                    boardPieceView.setVisible(true);
                    screen.add(boardPieceView,k,j);
                }
                else {
                    switch (board.board.get(j).get(k).multiplier) {
                        case "DL":
                            tilesShape.setFill(Color.LIGHTBLUE);
                            break;
                        case "TL":
                            tilesShape.setFill(Color.DARKBLUE);
                            break;
                        case "DW":
                            tilesShape.setFill(Color.PINK);
                            break;
                        case "TW":
                            tilesShape.setFill(Color.RED);
                            break;
                        default:
                            tilesShape.setFill(Color.WHITE);
                            break;
                    }
                    tilesShape.setStroke(Color.BLACK);
                    screen.add(tilesShape, k, j);
                    tilesShape.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            if(firstClick) {
                                int x = GridPane.getRowIndex(tilesShape);
                                int y = GridPane.getColumnIndex(tilesShape);
                                if(firstPlay){
                                    secondClick(x, y);
                                }else {
                                    if (board.validPlace(board,x,y)){
                                        secondClick(x, y);
                                    }
                                    else {
                                        firstClick=false;
                                    }
                                }
                            }
                        }
                    });
                }
            }
        }
    }

    private void secondClick(int x, int y) {
        playedPieces.add(pieces);
        posX.add(x);
        posY.add(y);
        board.board.get(x).get(y).setPiece(pieces);
        tray.humanTray.remove(pieces);
        displayHumanTray();
        firstClick=false;
        displayBoard();
    }

    public boolean winner(){
        if(tray.tray.size()<=7){
            if(totalHumanScore>totalComputerScore){
                updateDisplay("Hurray!! You are the winner!");
            }else {
                updateDisplay("Better luck next time!! Computer rocks");
            }
            play.setDisable(true);
            pass.setDisable(true);
            reverse.setDisable(true);
            return true;
        }
        return false;
    }
    public void updateScores(){
        score.getChildren().clear();
        humanScoreUpdate.setText("Your Score: "+totalHumanScore);
        computerScoreUpdate.setText("Computer Score: "+totalComputerScore);
        score.getChildren().addAll(humanScoreUpdate,computerScoreUpdate);
    }

    public void updateDisplay(String message){
        display.getChildren().clear();
        gameUpdate.setText(message);
        display.getChildren().addAll(gameUpdate);
    }

    public void updateComputerDisplay(String message){
        computerDisplay.getChildren().clear();
        computerGameUpdate.setText(message);
        computerGameUpdate.setFont(new Font(20));
        computerDisplay.getChildren().addAll(computerGameUpdate);
    }

    int boardSize;
    @Override
    public void start(Stage primaryStage) throws Exception {
        File file = new File("resource/board.txt");
        Scanner sc = new Scanner(file);
        tray.trayInitializer();
        while (sc.hasNext()) {
            this.boardSize = Integer.parseInt(sc.next());
            board.readDictionary();
            for (int j = 0; j < boardSize; j++) {
                for (int k = 0; k < boardSize; k++) {
                    String str = sc.next();
                    Tiles tiles = new Tiles(str.charAt(0), str.charAt(1));
                    board.board.get(j).add(tiles);
                }
            }
        }

        humanScoreUpdate.setText("Your Score: " +totalHumanScore);
        humanScoreUpdate.setFont(new Font(15));
        humanScoreUpdate.setAlignment(Pos.CENTER_LEFT);
        computerScoreUpdate.setText("Computer Score: "+totalComputerScore);
        computerScoreUpdate.setFont(new Font(15));
        computerScoreUpdate.setAlignment(Pos.CENTER_RIGHT);
        score.getChildren().addAll(humanScoreUpdate,computerScoreUpdate);
        score.setAlignment(Pos.CENTER);

        gameUpdate.setText("Start Game");
        gameUpdate.setFont(new Font(20));
        gameUpdate.setAlignment(Pos.CENTER);
        display.getChildren().addAll(gameUpdate);
        display.setAlignment(Pos.CENTER);

        computerGameUpdate.setAlignment(Pos.CENTER);
        computerDisplay.getChildren().addAll(gameUpdate);
        computerDisplay.setAlignment(Pos.CENTER);

        displayBoard();
        displayHumanTray();

        /*This button checks the valid move for human and if valid makes move.
        Also calls the computer to make the move.
         */
        play.setAlignment(Pos.CENTER_LEFT);
        play.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (playedPieces.size() != 0) {
                    if (firstPlay && !board.board.get(7).get(7).hasPiece()) {
                        updateDisplay("Your first play must touch center.");
                        System.out.println("Touch center");
                    } else{
                        firstPlay = false;
                        legalPlay = true;
                        for (int i = 0; i < posX.size() - 1; i++) {
                            if (posX.get(i) != (posX.get(i + 1)) && (posY.get(i) != posY.get(i + 1))) {
                                legalPlay = false;
                                updateDisplay("Invalid Play");
                                System.out.println("invalid play");
                                break;
                            }
                        }
                        if (legalPlay) {
                            if (board.validWord(board.toStringPieces(board.acrossCheck(board,
                                    posX.get(posX.size() - 1), posY.get(posY.size() - 1))))) {
                                currentHumanScore=Score.calcScore(board,posX,posY);
                                for (int i = 0; i < playedPieces.size(); i++) {
                                    board.board.get(posX.get(i)).get(posY.get(i)).removeMultiplier();;
                                }
                                totalHumanScore += currentHumanScore;
                                updateDisplay("You played " + board.toStringPieces(board.acrossCheck(board,
                                        posX.get(posX.size() - 1), posY.get(posY.size() - 1))).toUpperCase(Locale.ROOT) +
                                        " for " + currentHumanScore + " points.");
                                humanMove();
                            } else if (board.validWord(board.toStringPieces(board.verticalCheck(board,
                                    posX.get(posX.size() - 1), posY.get(posY.size() - 1))))) {
                                currentHumanScore=Score.calcScore(board,posX,posY);
                                for (int i = 0; i < playedPieces.size(); i++) {
                                    board.board.get(posX.get(i)).get(posY.get(i)).removeMultiplier();
                                }
                                totalHumanScore += currentHumanScore;
                                updateDisplay("You played " + board.toStringPieces(board.verticalCheck(board,
                                            posX.get(posX.size() - 1), posY.get(posY.size() - 1))).toUpperCase(Locale.ROOT) +
                                        " for " + currentHumanScore + " points.");
                                System.out.println("vertical word");
                                humanMove();
                            } else {
                                updateDisplay("Invalid word");
                                System.out.println("invalid word");
                            }
                        }
                    }
                }
            }
        });
        /*It reverse the human move*/
        reverse.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (playedPieces.size() != 0) {
                    tray.humanTray.add(playedPieces.get(playedPieces.size() - 1));
                    playedPieces.remove(playedPieces.size() - 1);
                    hBox2.getChildren().clear();
                    displayHumanTray();
                    board.board.get(posX.get(posX.size() - 1)).get(posY.get(posY.size() - 1)).removePiece();
                    posX.remove(posX.size() - 1);
                    posY.remove(posY.size() - 1);
                    displayBoard();
                }
            }
        });
        pass.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(!firstPlay){
                    computerMove();
                }
            }
        });
        exit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });

        hBox1.getChildren().addAll(play,reverse,pass);
        hBox1.setAlignment(Pos.CENTER);
        hBox2.setAlignment(Pos.CENTER);
        hBox3.getChildren().addAll(exit);
        hBox3.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(score,display,computerDisplay,hBox1, hBox2,hBox3);

        borderPane.setLeft(screen);
        borderPane.setCenter(vBox);
        Scene scene = new Scene(borderPane, 1000, 624, Color.BLACK);
        primaryStage.setTitle("Scrabble");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void humanMove() {
        currentHumanScore = 0;
        playedPieces = new ArrayList<>();
        posX = new ArrayList<>();
        posY = new ArrayList<>();
        if(!winner()) {
            tray.trayReinitialize(tray.humanTray);
            displayHumanTray();
            updateScores();
            computerMove();
        }
    }

    private void computerMove() {
        Computer_Play computerPlay=new Computer_Play();
        if(computerPlay.play(board,tray.computerTray)){
            computerScore=computerPlay.maxScore;
            totalComputerScore+=computerScore;
            updateComputerDisplay("Computer played "+computerPlay.bestWord.toUpperCase(Locale.ROOT)+
                    " for "+ computerScore+" points.");
            updateScores();
            if(!winner()) tray.trayReinitialize(tray.computerTray);
            displayBoard();
            computerScore=0;
        }
    }
}