package Controller;

import Model.AreaTypeEnum;
import Model.BoardModel;
import Model.Grid;
import Util.PointInfoCheck;
import View.Board;

import javax.swing.*;

import java.awt.*;
import java.util.List;


/**
 * Created by zhang.youting on 2017/6/5.
 */
public class ChessController{
    private Board board;
    private BoardModel boardModel;
    private AreaTypeEnum currentColor = AreaTypeEnum.White;//当前移动方
    private Point selectPoint;
    private AreaTypeEnum checked;
    private BoardModel recentBoard4HistoryMode;
    private boolean historyMode = false;
    JFrame frame;


    public ChessController(){
        frame = new JFrame();
        frame.setBounds(10,10,1000,828);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        init();

    }

    public void init(){
        currentColor = AreaTypeEnum.White;
        boardModel = new BoardModel();
        this.board = new Board(AreaTypeEnum.White,this);
        selectPoint = null;
        checked = null;
        board.setOpaque(false);
        frame.add(board);
        frame.setVisible(true);
        postData();
    }

    public void postData(){
        Grid[][] grids = boardModel.getBoard();
        for (int i = 0 ; i<8;i++) {
            for (int j = 0; j < 8; j++) {
                Point p = new Point(i,j);
                board.upDate(p, grids[i][j]);
            }
        }
        board.setSelectedPiece(selectPoint);
        board.setCurrentColor(currentColor);
        board.setChecked(checked);
        board.repaint();
    }

    public void clickEvent(Point p){
        Grid grid = boardModel.getOneOnBoard(p);
        //尚未选择的情况下
        if (selectPoint == null){
            //选择的位置无效
            if (grid ==null|| grid.getAreaType()!=currentColor){
                return;
            }
            //有效
            selectPoint = p;
        }
        //已经选择了的情况下
        else {
            Grid currentGrid = boardModel.getOneOnBoard(selectPoint);
            //选择其他己方棋子
            if (grid !=null&& grid.getAreaType()==currentColor){
                selectPoint = p;
            }
            //移动到空地或者吃子
            else {
                PointInfoCheck pointInfoCheck = new PointInfoCheck(selectPoint,p);
                List<Point> pointsPass = pointInfoCheck.getPointsPass();
                switch (currentGrid.getObject()){
                    case King:
                        if (pointInfoCheck.getDistance()!=1){
                            return;
                        }
                        break;
                    //需要判断路径的
                    case Queen:
                        if (!pointInfoCheck.isDiagonal()&&!pointInfoCheck.isFile()){
                            return;
                        }
                        for (Point pass: pointsPass) {
                            if (boardModel.getOneOnBoard(pass)!=null){
                                return;
                            }
                        }
                        break;
                    case Rook:
                        if (!pointInfoCheck.isFile()){
                            return;
                        }
                        for (Point pass: pointsPass) {
                            if (boardModel.getOneOnBoard(pass)!=null){
                                return;
                            }
                        }
                        break;
                    case Bishop:
                        if (!pointInfoCheck.isDiagonal()){
                            return;
                        }
                        for (Point pass: pointsPass) {
                            if (boardModel.getOneOnBoard(pass)!=null){
                                return;
                            }
                        }
                        break;
                    case Knight:
                        if (!pointInfoCheck.isKnight())
                            return;
                        break;
                    case Pawn:
                        //吃子 斜着一格
                        if (grid !=null&& grid.getAreaType()!=currentColor){
                            if (pointInfoCheck.isDiagonal()&&pointInfoCheck.getDistance()==1){
                                if (currentColor== AreaTypeEnum.Black&&p.y>selectPoint.y){
                                    break;
                                }
                                if (currentColor== AreaTypeEnum.White&&p.y<selectPoint.y){
                                    break;
                                }
                            }
                        }else if (pointInfoCheck.isFile()&&pointInfoCheck.getDistance()<=2){
                            //黑方
                            if (currentColor== AreaTypeEnum.Black){
                                if (selectPoint.x==p.x&&p.y>selectPoint.y){
                                    //走一格
                                    if (pointInfoCheck.getDistance()==1){
                                        break;
                                    }
                                    //第一次移动走两格
                                    if (selectPoint.y==1&&pointInfoCheck.getDistance()==2){
                                        if (boardModel.getOneOnBoard(new Point(selectPoint.x,selectPoint.y+1))==null){
                                            break;
                                        }
                                    }
                                }
                            }
                            //白方
                            if (currentColor== AreaTypeEnum.White){
                                if (selectPoint.x==p.x&&p.y<selectPoint.y){
                                    //走一格
                                    if (pointInfoCheck.getDistance()==1){
                                        break;
                                    }
                                    //第一次移动走两格
                                    if (selectPoint.y==6&&pointInfoCheck.getDistance()==2){
                                        if (boardModel.getOneOnBoard(new Point(selectPoint.x,selectPoint.y-1))==null){
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                        return;
                    default:
                        return;
                }//switch end
                //操作合法 执行操作
                boardModel = boardModel.movement(selectPoint,p);
                //移动后是否会被将军
                if(boardModel.isChecked(currentColor)){
                    //如果被将军 那么撤回
                    boardModel=boardModel.getLastStep();
                    boardModel.setNextStep(null);
                    return;
                }
                //解除将军状态
                if (checked == currentColor){
                    checked = null;
                }
                selectPoint = null;
                changePlayer();
                //判断对方是否被将军
                if(boardModel.isChecked(currentColor)){
                    checked = currentColor;
                }
            }

        }
        postData();
    }

    public void cancelSelect(){
        selectPoint = null;
        postData();
    }

    public void changePlayer(){
        if (currentColor == AreaTypeEnum.White)
            this.currentColor= AreaTypeEnum.Black;
        else
            this.currentColor= AreaTypeEnum.White;
    }

    public void restart(){
        frame.remove(board);
        init();
    }

    public void revoke(){
        if (boardModel.getLastStep() == null){
            return;
        }
        boardModel = boardModel.getLastStep();
        changePlayer();
        postData();
    }

    public boolean historyMode(){
        if (historyMode){
            boardModel = recentBoard4HistoryMode;
            recentBoard4HistoryMode = null;
            postData();
        }else {
            recentBoard4HistoryMode = boardModel;
        }
        historyMode = !historyMode;
        return historyMode;
    }

    public void getLastStep(){
        if (boardModel.getLastStep() == null){
            return;
        }
        boardModel = boardModel.getLastStep();
        postData();
    }
    public void getNextStep(){
        if (boardModel.getNextStep() == null){
            return;
        }
        boardModel = boardModel.getNextStep();
        postData();
    }

}
