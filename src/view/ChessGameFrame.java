package view;

import controller.GameController;
import model.ChessColor;

import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.IOException;
import java.util.Timer;

/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class ChessGameFrame extends JFrame {
    //    public final Dimension FRAME_SIZE ;
    private int WIDTH;//1000
    private int HEIGTH;//760
    public final int CHESSBOARD_SIZE;
    public static GameController gameController;
    private boolean isWhiteRound = true;
    private Clip clip;

    public GameController getGameController() {
        return gameController;
    }

    public ChessGameFrame(int width, int height) {
        setTitle("2022 CS102A Project Demo"); //设置标题
        this.WIDTH = width;
        this.HEIGTH = height;
        this.CHESSBOARD_SIZE = HEIGTH * 4 / 5;
        chessboard = new Chessboard(CHESSBOARD_SIZE, CHESSBOARD_SIZE);
        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);


        addChessboard();
        addBackground();
        addTimeLabel();
        addLabel();
        addHelloButton();
        addSaveButton();
        addUndoButton();
        addBGMButton();
        addLoadButton();
        addPlaybackButton();
        addBG();
        addComponentListener(new ComponentAdapter() {//让窗口响应大小改变事件
            @Override
            public void componentResized(ComponentEvent e) {
                int fraWidth = getWidth();//获取面板宽度
                int fraHeight = getHeight();
                labelBG.setSize(fraWidth,fraHeight);
                if (fraHeight / 76 * 100 > fraWidth) {
                    fraHeight = fraWidth / 100 * 76;
                } else {
                    fraWidth = fraHeight / 76 * 100;
                }
                chessboard.setLocation(fraHeight / 10, fraHeight / 10);
                chessboard.setCHESS_SIZE(fraHeight * 4 / 5);
                statusLabel.setLocation(fraHeight, fraHeight / 10);
                statusLabel.setSize(fraWidth / 5, fraHeight / 13);
                statusLabel.setFont(new Font("Rockwell", Font.BOLD, fraHeight / 39));
                buttonReload.setLocation(fraHeight, fraHeight / 10 + 2 * fraHeight / 13);
                buttonReload.setSize(fraWidth / 5, fraHeight / 13);
                buttonReload.setFont(new Font("Rockwell", Font.BOLD, fraHeight / 39));
                buttonBGM.setLocation(fraHeight, fraHeight / 10 + fraHeight / 13);
                buttonBGM.setSize(fraWidth / 5, fraHeight / 13);
                buttonBGM.setFont(new Font("Rockwell", Font.BOLD, fraHeight / 39));
                buttonSave.setLocation(fraHeight, fraHeight / 10 + 4 * fraHeight / 13);
                buttonSave.setSize(fraWidth / 5, fraHeight / 13);
                buttonSave.setFont(new Font("Rockwell", Font.BOLD, HEIGTH / 39));
                buttonUndo.setLocation(fraHeight, fraHeight / 10 + 3 * fraHeight / 13);
                buttonUndo.setSize(fraWidth / 5, fraHeight / 13);
                buttonUndo.setFont(new Font("Rockwell", Font.BOLD, fraHeight / 39));
                buttonPlayback.setLocation(fraHeight, fraHeight / 10 + 6* fraHeight / 13);
                buttonPlayback.setSize(fraWidth / 5, fraHeight / 13);
                buttonPlayback.setFont(new Font("Rockwell", Font.BOLD, fraHeight / 39));
                buttonLoad.setLocation(fraHeight, fraHeight / 10 + 5 * fraHeight / 13);
                buttonLoad.setSize(fraWidth / 5, fraHeight / 13);
                buttonLoad.setFont(new Font("Rockwell", Font.BOLD, fraHeight / 39));
                timeLabel.setLocation(fraHeight,fraHeight/15-20 );
                timeLabel.setSize(fraWidth / 18, fraHeight / 17);
                timeLabel.setFont(new Font("Rockwell", Font.BOLD, fraHeight / 39));
                ImageIcon picture = new ImageIcon("./images/chessboardBG.png");
                Image img=picture.getImage();
                img =img .getScaledInstance(fraHeight/10*8,fraHeight/10*8,Image.SCALE_DEFAULT);
                ImageIcon newp=new ImageIcon(img);
                labelChessBoard.setIcon(newp);
                labelChessBoard.setLocation(fraHeight / 10, fraHeight / 10);
                labelChessBoard.setSize(fraHeight/10*8,fraHeight/10*8);
                System.out.println(labelChessBoard.getIcon().getIconWidth());
                repaint();

            }
        });

        addWindowStateListener(e -> {
            repaint();
        });


    }

    Chessboard chessboard;

    /**
     * 在游戏面板中添加棋盘
     */
    private void addChessboard() {

        gameController = new GameController(chessboard);
        chessboard.setLocation(HEIGTH / 10, HEIGTH / 10);
        add(chessboard);
        chessboard.setFrame(this);
    }

    JLabel statusLabel = new JLabel("White turn");

    /**
     * 在游戏面板中添加标签
     */

    private void addLabel() {

        statusLabel.setLocation(HEIGTH, HEIGTH / 10);
        statusLabel.setSize(WIDTH / 5, HEIGTH / 13);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, HEIGTH / 39));
        add(statusLabel);
    }


    JButton buttonReload = new JButton("reload");

    /**
     * 在游戏面板中增加一个按钮，如果按下的话就会显示Hello, world!
     */

    private void addHelloButton() {

        buttonReload.setLocation(HEIGTH, HEIGTH / 10 + 2 * HEIGTH / 13);
        buttonReload.setSize(WIDTH / 5, HEIGTH / 13);
        buttonReload.setFont(new Font("Rockwell", Font.BOLD, HEIGTH / 39));
        add(buttonReload);
        buttonReload.addActionListener(e -> {
            gameController.getChessboard().reload();//相当无swapChessColor
            gameController.getChessboard().repaint();
        });
    }//reload
    JLabel labelChessBoard;
    private void addBackground(){

        ImageIcon picture = new ImageIcon("./images/chessboardBG.png");  //load a picture from computer
//        Image image = picture.getImage();  //create an Image to change the size of the picture
//        System.out.println(picture.getIconWidth()+","+picture.getIconHeight());
//        ImageIcon newpicture = new ImageIcon(image.getScaledInstance(picture.getIconWidth(), picture.getIconHeight(), Image.SCALE_SMOOTH));
        JLabel label = new JLabel(picture);
        this.labelChessBoard=label;
        labelChessBoard.setLocation(HEIGTH / 10, HEIGTH / 10);
        labelChessBoard.setSize(608,608);
        add(labelChessBoard);}//chess board
    JLabel labelBG;
    private void addBG(){

        ImageIcon picture = new ImageIcon("./images/BG.png");  //load a picture from computer
        Image image = picture.getImage();  //create an Image to change the size of the picture
        System.out.println(picture.getIconWidth()+","+picture.getIconHeight());
        ImageIcon newpicture = new ImageIcon(image.getScaledInstance(picture.getIconWidth(), picture.getIconHeight(), Image.SCALE_SMOOTH));
        JLabel label = new JLabel(picture);
        this.labelBG=label;
        labelBG.setLocation(0,0);
        labelBG.setSize(1000,760);
        add(labelBG);}//background
    Boolean BGMon = false;
    JButton buttonBGM = new JButton("BGM");

    private void addBGMButton() {

        buttonBGM.setLocation(HEIGTH, HEIGTH / 10 + HEIGTH / 13);
        buttonBGM.setSize(WIDTH / 5, HEIGTH / 13);
        buttonBGM.setFont(new Font("Rockwell", Font.BOLD, HEIGTH / 39));
        add(buttonBGM);

        buttonBGM.addActionListener(e -> {
            try {
                if (BGMon) {
                    clip.stop();
                    BGMon = false;
                } else {
//                System.out.println(new File(".").getAbsolutePath());
                    File soundFile = new File("./resource/1.wav");
                    AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
                    clip = AudioSystem.getClip();
                    clip.open(audioIn);
                    clip.loop(Integer.MAX_VALUE);
                    clip.start();
                    BGMon = true;
                }
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException f) {
                f.printStackTrace();
            }
        });
    }//music

    JButton buttonUndo = new JButton("Undo");

    private void addUndoButton() {

        buttonUndo.setLocation(HEIGTH, HEIGTH / 10 + 3 * HEIGTH / 13);
        buttonUndo.setSize(WIDTH / 5, HEIGTH / 13);
        buttonUndo.setFont(new Font("Rockwell", Font.BOLD, HEIGTH / 39));
        add(buttonUndo);
        buttonUndo.addActionListener(e -> {
            gameController.undo();
            gameController.getChessboard().repaint();
        });
    }//undo
    JButton buttonPlayback = new JButton("Playback");

    private void addPlaybackButton() {

        buttonPlayback.setLocation(HEIGTH, HEIGTH / 10 + 6 * HEIGTH / 13);
        buttonPlayback.setSize(WIDTH / 5, HEIGTH / 13);
        buttonPlayback.setFont(new Font("Rockwell", Font.BOLD, HEIGTH / 39));
        add(buttonPlayback);
        buttonPlayback.addActionListener(e -> {
            gameController.getChessboard().playback();

        });
    }










    JButton buttonLoad=new JButton("Load");
    ChessColor loadColor;
    public void setLoadColor(ChessColor color){
        loadColor=color;
    }
    private void addLoadButton() {

        buttonLoad.setLocation(HEIGTH, HEIGTH / 10+ 5 * HEIGTH / 13);
        buttonLoad.setSize(WIDTH / 5, HEIGTH / 13);
        buttonLoad.setFont(new Font("Rockwell", Font.BOLD, HEIGTH / 39));
        add(buttonLoad);
        final JTextArea msgTextArea = new JTextArea(10, 30);
        msgTextArea.setLineWrap(true);
        add(msgTextArea);
        buttonLoad.addActionListener(e -> {

            int back = 0;
            try {
                back = gameController.getChessboard().read(showFileOpenDialog(msgTextArea));


            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if (back != 100) {
                String code=String.format("错误编码：%d",back);
                JOptionPane.showConfirmDialog(null, code, "提示", JOptionPane.DEFAULT_OPTION);
            }else{
            System.out.println(gameController.getLoad().getTimeOfTurn());
            gameController.getLoad().addtime();
            gameController.getChessboard().Undo();
            gameController.getChessboard().setcolor(loadColor);
            updateUI();
            gameController.getLoad().minustime();
            gameController.getChessboard().repaint();}
        });
    }//load

    private File showFileOpenDialog(JTextArea msgTextArea) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("."));

        // 设置文件选择的模式（只选文件、只选文件夹、文件和文件均可选）
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        // 添加可用的文件过滤器（FileNameExtensionFilter 的第一个参数是描述, 后面是需要过滤的文件扩展名 可变参数）
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter(".txt",".txt"));
        // 打开文件选择框（线程将被阻塞, 直到选择框被关闭）
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            return file;
        }else if(result==JFileChooser.CANCEL_OPTION){
            fileChooser.setVisible(false);
            return null;
        }
        return null;
    }




    JButton buttonSave = new JButton("save");

    private void addSaveButton() {

        buttonSave.setLocation(HEIGTH, HEIGTH / 10 + 4 * HEIGTH / 13);
        buttonSave.setSize(WIDTH / 5, HEIGTH / 13);
        buttonSave.setFont(new Font("Rockwell", Font.BOLD, HEIGTH / 39));
        add(buttonSave);

        buttonSave.addActionListener(e -> {
            chessboard.load();
            System.out.println("Click load");
            chessboard.write();
//            String path = JOptionPane.showInputDialog(this, "Input Path here");
//            gameController.loadGameFromFile(path);
        });
    }//save
    public void updateUI() {
        statusLabel.setText(gameController.getChessboard().getCurrentColor() == ChessColor.WHITE ? "White turn" : "Black turn");
        statusLabel.repaint();
        timerTaskTest.timeReset();
        chessVoice();
    }
    public void chessVoice(){
        try {
        File soundFile = new File("./resource/录制_2022_05_17_18_47_22_193_1.wav");
        Clip clip;
        AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
        clip = AudioSystem.getClip();
        clip.open(audioIn);
        clip.start();
        }
    catch (UnsupportedAudioFileException | IOException | LineUnavailableException f) {
        f.printStackTrace();
    }
    }

    public void changeUI() {
        statusLabel.setText("White turn");
        timerTaskTest.timeReset();
    }

    Label timeLabel=new Label("3:00");
    TimerTaskTest timerTaskTest=new TimerTaskTest();
    public void addTimeLabel(){
        timeLabel.setLocation(HEIGTH,HEIGTH/15-20 );
        timeLabel.setSize(WIDTH / 18, HEIGTH / 17);
        timeLabel.setFont(new Font("Rockwell", Font.BOLD, HEIGTH / 39));

        add(timeLabel);

     Timer timer = new Timer();
     timer.schedule(timerTaskTest, 0, 1000);
     try{
         Thread.sleep(1000);
     }catch(Exception ex){
         timer.cancel();
     }
 }

    public class TimerTaskTest extends java.util.TimerTask{

        int time =32;
        public void timeReset(){
            time=31;
        }
        @Override
        public void run() {
            time--;
            if (time==0){
                time=31;
                chessboard.swapColor();
                statusLabel.setText(gameController.getChessboard().getCurrentColor() == ChessColor.WHITE ? "White turn" : "Black turn");
                statusLabel.repaint();
            }
            // TODO Auto-generated method stub
            String second= String.valueOf(this.time-this.time/60*60);
            int ten;
            int one;
            if(second.length()<2){
                ten=0;
                one= Integer.parseInt(String.valueOf(second.charAt(0)));
            }else{
                one=Integer.parseInt(String.valueOf(second.charAt(1)));
                ten= Integer.parseInt(String.valueOf(second.charAt(0)));
            }
            String time=String.format( "%d:%d%d",this.time/60,ten,one);
            timeLabel.setText(time);
            timeLabel.repaint();
        }
    }
}
