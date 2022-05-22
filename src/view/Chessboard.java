package view;


import controller.ClickController;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.List;
import java.util.Timer;

/**
 * 这个类表示面板上的棋盘组件对象
 */
public class Chessboard extends JComponent {
    /**
     * CHESSBOARD_SIZE： 棋盘是8 * 8的
     * <br>
     * BACKGROUND_COLORS: 棋盘的两种背景颜色
     * <br>
     * chessListener：棋盘监听棋子的行动
     * <br>
     * chessboard: 表示8 * 8的棋盘
     * <br>
     * currentColor: 当前行棋方
     */
    private static final int CHESSBOARD_SIZE = 8;

    private ChessComponent[][] chessComponents = new ChessComponent[CHESSBOARD_SIZE][CHESSBOARD_SIZE];
    ChessComponent[][] original = new ChessComponent[8][8];
    private ChessColor currentColor = ChessColor.WHITE;
    //all chessComponents in this chessboard are shared only one model controller
    private final ClickController clickController = new ClickController(this);
    private int CHESS_SIZE;
    private ChessGameFrame frame;

    public void setCHESS_SIZE(int width) {
        setLayout(null); // Use absolute layout.
        setSize(width, width);
        CHESS_SIZE = width / 8;
        updateOrigin();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                remove(chessComponents[i][j]);
                if (chessComponents[i][j] instanceof KingChessComponent) {
                    chessComponents[i][j] = new KingChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), original[i][j].getChessColor(), clickController, CHESS_SIZE);
                } else if (chessComponents[i][j] instanceof QueenChessComponent) {
                    chessComponents[i][j] = new QueenChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), original[i][j].getChessColor(), clickController, CHESS_SIZE);
                } else if (chessComponents[i][j] instanceof RookChessComponent) {
                    chessComponents[i][j] = new RookChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), original[i][j].getChessColor(), clickController, CHESS_SIZE);
                } else if (chessComponents[i][j] instanceof BishopChessComponent) {
                    chessComponents[i][j] = new BishopChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), original[i][j].getChessColor(), clickController, CHESS_SIZE);
                } else if (chessComponents[i][j] instanceof blackPawnChessComponent) {
                    chessComponents[i][j] = new blackPawnChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), original[i][j].getChessColor(), clickController, CHESS_SIZE);
                } else if (chessComponents[i][j] instanceof whitePawnChessComponent) {
                    chessComponents[i][j] = new whitePawnChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), original[i][j].getChessColor(), clickController, CHESS_SIZE);
                } else if (chessComponents[i][j] instanceof KnightChessComponent) {
                    chessComponents[i][j] = new KnightChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), original[i][j].getChessColor(), clickController, CHESS_SIZE);
                } else {
                    chessComponents[i][j] = new EmptySlotComponent(new ChessboardPoint(i, j), calculatePoint(i, j), clickController, CHESS_SIZE);
                }
                chessComponents[i][j].setVisible(true);
                chessComponents[i][j].repaint();
                add(chessComponents[i][j]);
            }

        }

        repaint();
    }

    public ClickController getClickController() {
        return clickController;
    }

    public int getCHESS_SIZE() {
        return CHESS_SIZE;
    }

    public void setChessComponents(ChessComponent[][] chessComponents) {
        this.chessComponents = chessComponents;
    }

    public Chessboard(int width, int height) {
        setLayout(null); // Use absolute layout.
        setSize(width, height);
        CHESS_SIZE = width / 8;


        initiateEmptyChessboard();

        // FIXME: Initialize chessboard for testing only.
        initRookOnBoard(0, 0, ChessColor.BLACK);
        initRookOnBoard(0, CHESSBOARD_SIZE - 1, ChessColor.BLACK);
        initRookOnBoard(CHESSBOARD_SIZE - 1, 0, ChessColor.WHITE);
        initRookOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 1, ChessColor.WHITE);
        initBishopOnBoard(0, 2, ChessColor.BLACK);
        initBishopOnBoard(0, CHESSBOARD_SIZE - 3, ChessColor.BLACK);
        initBishopOnBoard(CHESSBOARD_SIZE - 1, 2, ChessColor.WHITE);
        initBishopOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 3, ChessColor.WHITE);
        initQueenOnBoard(0, CHESSBOARD_SIZE - 4, ChessColor.BLACK);
        initQueenOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 4, ChessColor.WHITE);
        initKingOnBoard(0, CHESSBOARD_SIZE - 5, ChessColor.BLACK);
        initKingOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 5, ChessColor.WHITE);
        initKnightOnBoard(0, 1, ChessColor.BLACK);
        initKnightOnBoard(0, CHESSBOARD_SIZE - 2, ChessColor.BLACK);
        initKnightOnBoard(CHESSBOARD_SIZE - 1, 1, ChessColor.WHITE);
        initKnightOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 2, ChessColor.WHITE);
        for (int i = 0; i < CHESSBOARD_SIZE; i++) {
            initWhitePawnOnBoard(CHESSBOARD_SIZE - 2, i, ChessColor.WHITE);
            initBlackPawnOnBoard(1, i, ChessColor.BLACK);
        }
        updateOrigin();
    }

    public void updateOrigin() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (chessComponents[i][j] instanceof KingChessComponent) {
                    original[i][j] = new KingChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), chessComponents[i][j].getChessColor(), clickController, CHESS_SIZE);
                } else if (chessComponents[i][j] instanceof QueenChessComponent) {
                    original[i][j] = new QueenChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), chessComponents[i][j].getChessColor(), clickController, CHESS_SIZE);
                } else if (chessComponents[i][j] instanceof RookChessComponent) {
                    original[i][j] = new RookChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), chessComponents[i][j].getChessColor(), clickController, CHESS_SIZE);
                } else if (chessComponents[i][j] instanceof BishopChessComponent) {
                    original[i][j] = new BishopChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), chessComponents[i][j].getChessColor(), clickController, CHESS_SIZE);
                } else if (chessComponents[i][j] instanceof blackPawnChessComponent) {
                    original[i][j] = new blackPawnChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), chessComponents[i][j].getChessColor(), clickController, CHESS_SIZE);
                } else if (chessComponents[i][j] instanceof whitePawnChessComponent) {
                    original[i][j] = new whitePawnChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), chessComponents[i][j].getChessColor(), clickController, CHESS_SIZE);
                } else if (chessComponents[i][j] instanceof KnightChessComponent) {
                    original[i][j] = new KnightChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), chessComponents[i][j].getChessColor(), clickController, CHESS_SIZE);
                } else {
                    original[i][j] = new EmptySlotComponent(new ChessboardPoint(i, j), calculatePoint(i, j), clickController, CHESS_SIZE);

                }
            }

        }
    }

    public void initChessBoardToOrigin() {
        initiateEmptyChessboard();

        // FIXME: Initialize chessboard for testing only.
        initRookOnBoard(0, 0, ChessColor.BLACK);
        initRookOnBoard(0, CHESSBOARD_SIZE - 1, ChessColor.BLACK);
        initRookOnBoard(CHESSBOARD_SIZE - 1, 0, ChessColor.WHITE);
        initRookOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 1, ChessColor.WHITE);
        initBishopOnBoard(0, 2, ChessColor.BLACK);
        initBishopOnBoard(0, CHESSBOARD_SIZE - 3, ChessColor.BLACK);
        initBishopOnBoard(CHESSBOARD_SIZE - 1, 2, ChessColor.WHITE);
        initBishopOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 3, ChessColor.WHITE);
        initQueenOnBoard(0, CHESSBOARD_SIZE - 4, ChessColor.BLACK);
        initQueenOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 4, ChessColor.WHITE);
        initKingOnBoard(0, CHESSBOARD_SIZE - 5, ChessColor.BLACK);
        initKingOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 5, ChessColor.WHITE);
        initKnightOnBoard(0, 1, ChessColor.BLACK);
        initKnightOnBoard(0, CHESSBOARD_SIZE - 2, ChessColor.BLACK);
        initKnightOnBoard(CHESSBOARD_SIZE - 1, 1, ChessColor.WHITE);
        initKnightOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 2, ChessColor.WHITE);
        for (int i = 0; i < CHESSBOARD_SIZE; i++) {
            initWhitePawnOnBoard(CHESSBOARD_SIZE - 2, i, ChessColor.WHITE);
            initBlackPawnOnBoard(1, i, ChessColor.BLACK);
        }
        setCurrentColor(ChessColor.WHITE);
        frame.changeUI();
    }

    public void setFrame(ChessGameFrame f) {
        frame = f;
        frame.getGameController().load();
    }

    public ChessComponent[][] getChessComponents() {
        return chessComponents;
    }

    public ChessColor getCurrentColor() {
        return currentColor;
    }

    public void setCurrentColor(ChessColor currentColor) {
        this.currentColor = currentColor;
    }

    public void putChessOnBoard(ChessComponent chessComponent) {
        int row = chessComponent.getChessboardPoint().getX(), col = chessComponent.getChessboardPoint().getY();

        if (chessComponents[row][col] != null) {
            remove(chessComponents[row][col]);
        }
        add(chessComponents[row][col] = chessComponent);
    }

    public void swapChessComponents(ChessComponent chess1, ChessComponent chess2) {
        // Note that chess1 has higher priority, 'destroys' chess2 if exists.
//        if ((swapFirst==0)){
//            updateUI();
//        }
//        if(frame.getGameController().getNotUndo())
//        {frame.getGameController().getLoad().setFirst(chess1);
//        frame.getGameController().getLoad().setSecond(chess2);}
        if (!(chess2 instanceof EmptySlotComponent)) {
            remove(chess2);
            add(chess2 = new EmptySlotComponent(chess2.getChessboardPoint(), chess2.getLocation(), clickController, CHESS_SIZE));
        }
        chess1.swapLocation(chess2);
        int row1 = chess1.getChessboardPoint().getX(), col1 = chess1.getChessboardPoint().getY();
        chessComponents[row1][col1] = chess1;
        int row2 = chess2.getChessboardPoint().getX(), col2 = chess2.getChessboardPoint().getY();
        chessComponents[row2][col2] = chess2;

        chess1.repaint();
        chess2.repaint();


    }

    public ChessGameFrame getFrame() {
        return frame;
    }

    public void initiateEmptyChessboard() {
        for (int i = 0; i < chessComponents.length; i++) {
            for (int j = 0; j < chessComponents[i].length; j++) {
                putChessOnBoard(new EmptySlotComponent(new ChessboardPoint(i, j), calculatePoint(i, j), clickController, CHESS_SIZE));
            }
        }
    }

    //    int swapFirst=0;//be used to debug problem that the first UI is wrong
    public void swapColor() {
////        swapFirst++;
        currentColor = currentColor == ChessColor.BLACK ? ChessColor.WHITE : ChessColor.BLACK;
        frame.updateUI();
    }

    private void initRookOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new RookChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initBishopOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new BishopChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initQueenOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new QueenChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initKingOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new KingChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initKnightOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new KnightChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initWhitePawnOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new whitePawnChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initBlackPawnOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new blackPawnChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }


    private Point calculatePoint(int row, int col) {
        return new Point(col * CHESS_SIZE, row * CHESS_SIZE);
    }

    public void loadGame(List<String> chessData) {
        chessData.forEach(System.out::println);
    }

    public void reload() {
        initChessBoardToOrigin();
        frame.getGameController().reload();
        repaint();

    }

//    public void SwapChessComponents(ChessComponent chess1, ChessComponent chess2) {
//        // Note that chess1 has higher priority, 'destroys' chess2 if exists.
//
////        if ((swapFirst==0)){
////            updateUI();
////        }
//        if (!(chess2 instanceof EmptySlotComponent)) {
//            remove(chess2);
//            add(chess2 = new EmptySlotComponent(chess2.getChessboardPoint(), chess2.getLocation(), clickController, CHESS_SIZE));
//        }
//        chess1.swapLocation(chess2);
//        int row1 = chess1.getChessboardPoint().getX(), col1 = chess1.getChessboardPoint().getY();
//        chessComponents[row1][col1] = chess1;
//        int row2 = chess2.getChessboardPoint().getX(), col2 = chess2.getChessboardPoint().getY();
//        chessComponents[row2][col2] = chess2;
//        chess1.repaint();
//        chess2.repaint();
//    }


    public boolean isUndo = false;

    public void undo() {
        isUndo = true;
//        for (int i = 0; i < 8; i++) {
//            System.out.println(chessComponents[i][0]);
//        }
//        System.out.println(" ");

        initChessBoardToOrigin();
        for (int i = 0; i < frame.getGameController().getLoad().getTimeOfTurn(); i++) {

            clickController.setFirst(chessComponents[frame.getGameController().getLoad().getFirstX(i)][frame.getGameController().getLoad().getFirstY(i)]);
            clickController.onClick(chessComponents[frame.getGameController().getLoad().getSecondX(i)][frame.getGameController().getLoad().getSecondY(i)]);
        }
        frame.getGameController().getLoad().remove(frame.getGameController().getLoad().getTimeOfTurn() - 1);
//        for (int i = 0; i < 8; i++) {
//            System.out.println(chessComponents[i][0]);
//        }
//        System.out.println(" ");

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                add(chessComponents[i][j]);
            }
        }


        isUndo = false;
    }

    public void Undo() {
        isUndo = true;
//        for (int i = 0; i < 8; i++) {
//            System.out.println(chessComponents[i][0]);
//        }
//        System.out.println(" ");

        initChessBoardToOrigin();
        for (int i = 0; i < frame.getGameController().getLoad().getTimeOfTurn(); i++) {

            clickController.setFirst(chessComponents[frame.getGameController().getLoad().getFirstX(i)][frame.getGameController().getLoad().getFirstY(i)]);
            clickController.onClick(chessComponents[frame.getGameController().getLoad().getSecondX(i)][frame.getGameController().getLoad().getSecondY(i)]);
        }
//        for (int i = 0; i < 8; i++) {
//            System.out.println(chessComponents[i][0]);
//        }
//        System.out.println(" ");

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                add(chessComponents[i][j]);
            }
        }


        isUndo = false;
    }

    int loadTime = 0;

    public void load() {
        loadTime++;
    }

    public void write() {
        try {
            File dir = new File("./load");
            if(!dir.exists()){
                dir.mkdirs();
            }
            String path = String.format("./load/load%d.txt", loadTime % 8);
            File file = new File(path);
            Writer fw = new FileWriter(path);
            BufferedWriter bw = new BufferedWriter(fw);
            String[] s = judge();
            for (int i = 0; i < 8; i++) {
                bw.write(s[i] + "\n");
            }
            if (currentColor == ChessColor.WHITE) {
                bw.write("w\n");
            } else {
                bw.write("b\n");
            }
            String FX = "";
            String FY = "";
            String SX = "";
            String SY = "";
            for (int i = 0; i < frame.getGameController().getLoad().getTimeOfTurn() + 1; i++) {
                FX += frame.getGameController().getLoad().getFirstX(i);
                FY += frame.getGameController().getLoad().getFirstY(i);
                SX += frame.getGameController().getLoad().getSecondX(i);
                SY += frame.getGameController().getLoad().getSecondY(i);
            }
            bw.write(FX + "\n");
            bw.write(FY + "\n");
            bw.write(SX + "\n");
            bw.write(SY + "\n");

            bw.flush();
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String[] judge() {
        String[] s = new String[8];
        for (int i = 0; i < 8; i++) {
            s[i] = "";
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (chessComponents[i][j] instanceof KingChessComponent) {
                    if (chessComponents[i][j].getChessColor() == ChessColor.WHITE) {
                        s[i] += "k";
                    } else {
                        s[i] += "K";
                    }
                } else if (chessComponents[i][j] instanceof QueenChessComponent) {
                    if (chessComponents[i][j].getChessColor() == ChessColor.WHITE) {
                        s[i] += "q";
                    } else {
                        s[i] += "Q";
                    }
                } else if (chessComponents[i][j] instanceof RookChessComponent) {
                    if (chessComponents[i][j].getChessColor() == ChessColor.WHITE) {
                        s[i] += "r";
                    } else {
                        s[i] += "R";
                    }
                } else if (chessComponents[i][j] instanceof BishopChessComponent) {
                    if (chessComponents[i][j].getChessColor() == ChessColor.WHITE) {
                        s[i] += "b";
                    } else {
                        s[i] += "B";
                    }
                } else if (chessComponents[i][j] instanceof blackPawnChessComponent) {

                    s[i] += "P";

                } else if (chessComponents[i][j] instanceof whitePawnChessComponent) {

                    s[i] += "p";
                } else if (chessComponents[i][j] instanceof KnightChessComponent) {
                    if (chessComponents[i][j].getChessColor() == ChessColor.WHITE) {
                        s[i] += "n";
                    } else {
                        s[i] += "N";
                    }
                } else {

                    s[i] += "_";
                }
                add(chessComponents[i][j]);
            }

        }
        return s;
    }

    public int read(File file) throws IOException {
        String path = file.getAbsolutePath();
        String[] Bf = path.split("\\.");

        if (!Bf[Bf.length - 1].equals("txt")) {
            return 104;
        }
        FileReader input = new FileReader(file);
        BufferedReader bf = new BufferedReader(input);
        String[] s = new String[8];

        for (int i = 0; i < 8; i++) {
            s[i] = bf.readLine();
        }

        String color = bf.readLine();
        if (!color.equals("w") && !color.equals("b")) {
            return 103;
        }
        for (int i = 0; i < 8; i++) {//check8*8
            if (s[i].length() != 8) {
                return 101;
            }
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                char c = s[i].charAt(j);
                if (c != 'p' && c != 'P' && c != 'n' && c != 'N' && c != 'k' && c != 'K' && c != 'r' && c != 'R' && c != 'q' && c != 'Q' && c != 'b' && c != 'B' && c != '_') {
                    return 102;
                }
            }
        }//check chess
        String FX = bf.readLine();
        String FY = bf.readLine();
        String SX = bf.readLine();
        String SY = bf.readLine();
        frame.getGameController().load();
        for (int i = 0; i < FX.length(); i++) {
            frame.getGameController().getLoad().setFirstX(Integer.parseInt(String.valueOf(FX.charAt(i))));
            frame.getGameController().getLoad().setFirstY(Integer.parseInt(String.valueOf(FY.charAt(i))));
            frame.getGameController().getLoad().setSecondX(Integer.parseInt(String.valueOf(SX.charAt(i))));
            frame.getGameController().getLoad().setSecondY(Integer.parseInt(String.valueOf(SY.charAt(i))));
        }
        frame.getGameController().getLoad().update();
        if (color.equals("w")) {
            frame.setLoadColor(ChessColor.WHITE);
        } else {
            frame.setLoadColor((ChessColor.BLACK));
        }
        return 100;

    }

    public void setcolor(ChessColor color) {
        currentColor = color;
    }

    java.util.Timer timer;

    public void playback() {
        isUndo = true;
        initChessBoardToOrigin();
        java.util.Timer timer = new Timer();
        this.timer = timer;
        TimerTaskTest timerTaskTest = new TimerTaskTest();
        this.timer.schedule(timerTaskTest, 500, 500);
        try {
            Thread.sleep(500);
        } catch (Exception ex) {
            timer.cancel();
        }
//        for (int i = 0; i < 8; i++) {
//            System.out.println(chessComponents[i][0]);
//        }
//        System.out.println(" ");
    }

    public class TimerTaskTest extends java.util.TimerTask {

        int time = -1;

        @Override
        public void run() {

            if (time == -1) {
                initChessBoardToOrigin();
                time++;
                repaint();
                return;
            }
            if (time > frame.getGameController().getLoad().getTimeOfTurn()) {
                timer.cancel();

                return;
            }
            isUndo = true;
            clickController.setFirst(chessComponents[frame.getGameController().getLoad().getFirstX(time)][frame.getGameController().getLoad().getFirstY(time)]);
            clickController.onClick(chessComponents[frame.getGameController().getLoad().getSecondX(time)][frame.getGameController().getLoad().getSecondY(time)]);
            isUndo = false;
            for (int I = 0; I < 8; I++) {
                for (int j = 0; j < 8; j++) {
                    add(chessComponents[I][j]);
                }
            }
            time++;
            repaint();
            return;
        }
    }


//    private boolean check(String[] s,String FX,String FY,String SX,String SY){
//        for(int i=0;i<8;i++){//check8*8
//            if (s[i].length()!=8){
//                return false;
//            }
//        }
//        for (int i = 0; i < 8; i++) {
//            for (int j = 0; j < 8; j++) {
//            char c=s[i].charAt(j);
//            if(c!='p'&&c!='P'&&c!='n'&&c!='N'&&c!='k'&&c!='K'&&c!='r'&&c!='R'&&c!='q'&&c!='Q'&&c!='b'&&c!='B'&&c!='_'){
//                return false;
//            }
//            }
//        }//check chess
////        char[][] chessboard=new char[8][8];
////        String or="RNBKQBNRPPPPPPPP________________________________pppppppprnbkqbnr";
////        int time=0;
////        for (int i = 0; i < 8; i++) {
////            for (int j = 0; j < 8; j++) {
////            chessboard[i][j]=or.charAt(time);
////            }
////        }
////        for (int i=0;i<FX.length();i++){
////            int fx=Integer.parseInt(String.valueOf(FX.charAt(i)));
////            int fy=Integer.parseInt(String.valueOf(FY.charAt(i)));
////            int sx=Integer.parseInt(String.valueOf(SX.charAt(i)));
////            int sy=Integer.parseInt(String.valueOf(SY.charAt(i)));
////            char current=chessboard[fx][fy];
////            chessboard[fx][fy]=chessboard[sx][sy];
////            chessboard[sx][sy]=current;
////        }
////        String[] New=new String[8];
////        for (int i = 0; i < 8; i++) {
////            New[i] = "";
////        }
////        for(int i=0;i<8;i++){
////            for (int j=0;j<8;j++){
////                New[i]+=chessboard[i][j];
////            }
////
////        }
////        for(int i=0;i<8;i++){
////            if(!s[i].equals(New[i])){
////                return false;
////            }
////        }//check can to move;
//        return true;
//    }
}
