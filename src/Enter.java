import view.ChessGameFrame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;

public class Enter extends JFrame {
    private int WIDTH;//1000
    private int HEIGTH;//760
    JLabel label;
    public Enter(int width, int height){
        setTitle("chess"); //设置标题
        this.WIDTH = width;
        this.HEIGTH = height;
        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);
        chessButton();
        chessSea();
        chessInk();
        addBackground();
        addComponentListener(new ComponentAdapter() {//让窗口响应大小改变事件
            @Override
            public void componentResized(ComponentEvent e) {
                int fraWidth = getWidth();//获取面板宽度
                int fraHeight = getHeight();
                label.setSize(fraWidth,fraHeight);
                if(fraHeight/76*100>fraWidth){
                    fraHeight=fraWidth/100*76;
                }else {
                    fraWidth=fraHeight/76*100;
                }
                HEIGTH=fraHeight;
                WIDTH=fraWidth;
                buttonReload.setLocation(fraWidth/2, fraHeight / 2 );
                buttonReload.setSize(fraWidth/5, fraHeight/13);
                buttonReload.setFont(new Font("Rockwell", Font.BOLD, fraHeight/39));
                buttonSea.setLocation(HEIGTH/2,  4*HEIGTH/5);
                buttonSea.setSize(WIDTH/5, HEIGTH/13);
                buttonSea.setFont(new Font("Rockwell", Font.BOLD, HEIGTH/39));
                buttonInk.setLocation(HEIGTH/2-WIDTH/5,  4*HEIGTH/5);
                buttonInk.setSize(WIDTH/5, HEIGTH/13);
                buttonInk.setFont(new Font("Rockwell", Font.BOLD, HEIGTH/39));
            }
        });
    }
    ChessGameFrame mainFrame;
    JButton buttonReload = new JButton("Start");
    private void chessButton() {

        buttonReload.setLocation(HEIGTH, HEIGTH / 10 + 2*HEIGTH/13);
        buttonReload.setSize(WIDTH/5, HEIGTH/13);
        buttonReload.setFont(new Font("Rockwell", Font.BOLD, HEIGTH/39));
        add(buttonReload);
        buttonReload.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> {

                ChessGameFrame mainFrame = new ChessGameFrame(1000, 760,BGpath,PathOfChessBoardBG);
                this.mainFrame=mainFrame;
                mainFrame.setVisible(true);
                setVisible(false);
            });
        });
    }

    private void addBackground(){

        ImageIcon picture = null;  //load a picture from computer
        try {
            picture = new ImageIcon(ImageIO.read(Enter.class.getResourceAsStream("view/chessBackground.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image image = picture.getImage();  //create an Image to change the size of the picture
        System.out.println(picture.getIconWidth()+","+picture.getIconHeight());
        ImageIcon newpicture = new ImageIcon(image.getScaledInstance(picture.getIconWidth(), picture.getIconHeight(), Image.SCALE_SMOOTH));
        JLabel label = new JLabel(newpicture);
        this.label=label;
        label.setSize(1000,760);
        add(label);}


    String BGpath="BG.png";
    String PathOfChessBoardBG="chessboardBG.png";
    JButton buttonSea = new JButton("sea");
    private void chessSea() {

        buttonSea.setLocation(HEIGTH/2,  4*HEIGTH/5);
        buttonSea.setSize(WIDTH/5, HEIGTH/13);
        buttonSea.setFont(new Font("Rockwell", Font.BOLD, HEIGTH/39));
        add(buttonSea);
        buttonSea.addActionListener(e -> {
            BGpath="seaB.png";
            PathOfChessBoardBG="seaBoard.png";
        });
    }

    JButton buttonInk = new JButton("ink");
    private void chessInk() {

        buttonInk.setLocation(HEIGTH/2-WIDTH/5,  4*HEIGTH/5);
        buttonInk.setSize(WIDTH/5, HEIGTH/13);
        buttonInk.setFont(new Font("Rockwell", Font.BOLD, HEIGTH/39));
        add(buttonInk);
        buttonInk.addActionListener(e -> {
            BGpath="ink.png";
            PathOfChessBoardBG="inkChessboard.png";
        });
    }
}
