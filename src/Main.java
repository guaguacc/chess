import view.ChessGameFrame;

import javax.swing.*;
import javax.sound.sampled.*;

public class Main {
    public static void main(String[] args) {


        SwingUtilities.invokeLater(() -> {
           Enter enter = new Enter(1000, 760);
            enter.setVisible(true);
        });
    }
}
