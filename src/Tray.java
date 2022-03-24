import java.util.ArrayList;
import java.util.Collections;
/*
This class creates the tray and initialize tray for human and computer.
 */
public class Tray {
    public ArrayList<Pieces> tray = new ArrayList<>();
    public ArrayList<Pieces> humanTray = new ArrayList<>();
    public ArrayList<Pieces> computerTray = new ArrayList<>();

    public void trayInitializer() {
        for (int i = 0; i < 12; i++) {
            tray.add(new Pieces('e'));
        }
        for (int i = 0; i < 9; i++) {
            tray.add(new Pieces('a'));
            tray.add(new Pieces('i'));
        }
        for (int i = 0; i < 8; i++) {
            tray.add(new Pieces('o'));
        }
        for (int i = 0; i < 6; i++) {
            tray.add(new Pieces('n'));
            tray.add(new Pieces('r'));
            tray.add(new Pieces('t'));
        }
        for (int i = 0; i < 4; i++) {
            tray.add(new Pieces('l'));
            tray.add(new Pieces('s'));
            tray.add(new Pieces('u'));
            tray.add(new Pieces('d'));

        }
        for (int i = 0; i < 3; i++) {
            tray.add(new Pieces('g'));
        }
        for (int i = 0; i < 2; i++) {
            tray.add(new Pieces('b'));
            tray.add(new Pieces('c'));
            tray.add(new Pieces('m'));
            tray.add(new Pieces('p'));
            tray.add(new Pieces('f'));
            tray.add(new Pieces('h'));
            tray.add(new Pieces('v'));
            tray.add(new Pieces('w'));
            tray.add(new Pieces('y'));
        }
        tray.add(new Pieces('k'));
        tray.add(new Pieces('j'));
        tray.add(new Pieces('x'));
        tray.add(new Pieces('q'));
        tray.add(new Pieces('z'));
        Collections.shuffle(tray);

        for (int i = 0; i<7; i++){
            humanTray.add(i,tray.get(i));
            tray.remove(i);
        }
        for (int j = 0; j<7; j++){
            computerTray.add(j, tray.get(j));
            tray.remove(j);
        }
    }
    public void trayReinitialize(ArrayList<Pieces> Tray){
        for(int i=Tray.size();i<7;i++){
            Tray.add(i, tray.get(i));
            tray.remove(i);
        }
    }
}
