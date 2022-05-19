package controller;


import model.ChessComponent;
import view.ChessGameFrame;
import view.Chessboard;

public class ClickController {
    private Chessboard chessboard;
    private ChessComponent first;

    public ClickController(Chessboard chessboard) {
        this.chessboard = chessboard;
    }

    public void setChessboard(Chessboard chessboard) {
        this.chessboard = chessboard;
    }

    public void setFirst(ChessComponent first) {
        this.first = first;
    }
    public void OnClick(ChessComponent chessComponent) {
        chessboard.swapChessComponents(first, chessComponent);
        chessboard.swapColor();
        first.setSelected(false);
        first = null;
    }
    public void onClick(ChessComponent chessComponent) {
        if (first == null) {
            if (handleFirst(chessComponent)) {
                chessComponent.setSelected(true);
                first = chessComponent;
                first.repaint();
            }
        } else {
            if (first == chessComponent) { // 再次点击取消选取
                chessComponent.setSelected(false);
                ChessComponent recordFirst = first;
                first = null;
                recordFirst.repaint();
            } else if (handleSecond(chessComponent)) {



                //repaint in swap chess method.
                if (!chessboard.isUndo)
                {chessboard.getFrame().getGameController().getLoad().setSecondX(chessComponent.getChessboardPoint().getX());
                chessboard.getFrame().getGameController().getLoad().setSecondY(chessComponent.getChessboardPoint().getY());
                chessboard.getFrame().getGameController().getLoad().setFirstX(first.getChessboardPoint().getX());
                chessboard.getFrame().getGameController().getLoad().setFirstY(first.getChessboardPoint().getY());}
                chessboard.swapChessComponents(first, chessComponent);
                chessboard.swapColor();
                first.setSelected(false);
                first = null;
            }
        }
    }

    /**
     * @param chessComponent 目标选取的棋子
     * @return 目标选取的棋子是否与棋盘记录的当前行棋方颜色相同
     */

    private boolean handleFirst(ChessComponent chessComponent) {
        return chessComponent.getChessColor() == chessboard.getCurrentColor();
    }

    /**
     * @param chessComponent first棋子目标移动到的棋子second
     * @return first棋子是否能够移动到second棋子位置
     */

    private boolean handleSecond(ChessComponent chessComponent) {

        return chessComponent.getChessColor() != chessboard.getCurrentColor() &&
                first.canMoveTo(chessboard.getChessComponents(), chessComponent.getChessboardPoint());
    }
}
