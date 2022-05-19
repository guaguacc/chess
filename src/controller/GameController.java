package controller;

import view.Chessboard;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import model.*;

public class GameController {
    private Chessboard chessboard;
    private ArrayList<load> loads=new ArrayList<>();

    private int loadTime=-1;

    public int getLoadTime() {
        return loadTime;
    }

    public void load() {
    loadTime++;
    loads.add(new load());
    }
    public void reload(){
        loadTime++;
        loads.add(new load());
    }
    public GameController(Chessboard chessboard) {
        this.chessboard = chessboard;
    }
    public load getLoad(){
        return  loads.get(loadTime);
    }
    public List<String> loadGameFromFile(String path) {

        try {

            List<String> chessData = Files.readAllLines(Path.of(path));
            chessboard.loadGame(chessData);
            return chessData;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Chessboard getChessboard(){
        return chessboard;
    }




    //new method

    private Boolean NotUndo=true;
    public Boolean getNotUndo(){
        return NotUndo;
    }
    public void undo(){

        chessboard.undo();
    }
}
