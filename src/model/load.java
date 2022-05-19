package model;

import java.util.ArrayList;

public class load {
    public load() {
    }
    ArrayList<Integer> firstX = new ArrayList<>();
    ArrayList<Integer> firstY = new ArrayList<>();
    ArrayList<Integer> secondX = new ArrayList<>();
    ArrayList<Integer> secondY = new ArrayList<>();
    int timeOfTurn = -1;
    public void remove(int i){

        ArrayList<Integer> firstX = new ArrayList<>();
        ArrayList<Integer> firstY = new ArrayList<>();
        ArrayList<Integer> secondX = new ArrayList<>();
        ArrayList<Integer> secondY = new ArrayList<>();
        for(int m=0;m<this.firstX.size()-1;m++){
            firstX.add(this.firstX.get(m));
            firstY.add(this.firstY.get(m));
            secondX.add(this.secondX.get(m));
            secondY.add(this.secondY.get(m));
        }
        this.firstX=firstX;
        this.firstY=firstY;
        this.secondX=secondX;
        this.secondY=secondY;
        timeOfTurn--;
    }

    public void addtime(){
        timeOfTurn++;
    }
    public void minustime(){
        timeOfTurn--;
    }
    public int getTimeOfTurn() {
        return timeOfTurn;
    }

    public int getFirstX(int i) {
        return firstX.get(i);
    }

    public void setFirstX(int  firstX) {
        timeOfTurn++;
        this.firstX.add(firstX);
    }

    public int getFirstY(int i) {
        return firstY.get(i);
    }

    public void setFirstY(int  firstY) {
        this.firstY.add(firstY);
    }

    public int  getSecondX(int i) {
        return secondX.get(i);
    }

    public void setSecondX(int secondX) {
        this.secondX.add(secondX);
    }

    public int  getSecondY(int i) {
        return secondY.get(i);
    }

    public void setSecondY(int  secondY) {
        this.secondY.add(secondY);
        for (int i=0;i<firstY.size();i++){
            System.out.printf("(%d,%d)(%d,%d)",firstX.get(i),firstY.get(i),secondX.get(i),this.secondY.get(i));
        }
        System.out.println("\n");
    }
    public void update(){
        timeOfTurn=firstX.size()-1;
    }
}
