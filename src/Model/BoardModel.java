package Model;

import java.awt.*;

/**
 * Created by zhang.youting on 2017/6/6.
 */
public class BoardModel {
    private Grid[][] board ;
    private BoardModel nextStep = null;//建立下一步执行后的状态
    private BoardModel lastStep = null;//上一步的状态

    private final static int WIDTH_OF_BOARD = 151;
    private final static int HEIGHT_OF_BOARD = 47;


    public BoardModel(BoardModel boardModel){ //替换当前模型用
        this.board = new Grid[WIDTH_OF_BOARD][HEIGHT_OF_BOARD];
        for (int i = 0 ; i<WIDTH_OF_BOARD;i++) {
            for (int j = 0; j < HEIGHT_OF_BOARD; j++) {
                this.board[i][j] = boardModel.getBoard()[i][j];
            }
        }
    }
    public BoardModel() { //初始化用
            this.board = new Grid[WIDTH_OF_BOARD][HEIGHT_OF_BOARD];
            for (int i = 0 ; i<WIDTH_OF_BOARD;i++) {
                for (int j = 0; j < HEIGHT_OF_BOARD; j++) {
                    board[i][j] = null;
                }
            }
        //安排每个格子具体的内容


    }

    public Grid getOneOnBoard(Point p){
        return board[p.x][p.y];
    }
    public Grid[][] getBoard() {
        return board;
    }
    public BoardModel getNextStep() {
        return nextStep;
    }
    public void setNextStep(BoardModel nextStep) {
        this.nextStep = nextStep;
    }
    public BoardModel getLastStep() {
        return lastStep;
    }
    public void setLastStep(BoardModel lastStep) {
        this.lastStep = lastStep;
    }
    public void setBoard(Grid[][] board) {
        this.board = board;
    }
    public void setOneOnBoard(Point p, Grid grid){
        this.board[p.x][p.y]= grid;
    }


    public Point getNextPoint(Point p,DirectionEnum directionEnum){
        int x = p.x+directionEnum.getDx();
        int y = p.y+directionEnum.getDy();
        if (x<0||x>7||y<0||y>7){
            return null;
        }
        return new Point(x,y);
    }
    public Point getNextPoint(Point p,KnightDirectionEnum directionEnum){
        int x = p.x+directionEnum.getDx();
        int y = p.y+directionEnum.getDy();
        if (x<0||x>7||y<0||y>7){
            return null;
        }
        return new Point(x,y);
    }

    public BoardModel movement(Point now,Point next){
        BoardModel nextBoard = new BoardModel(this);
        nextBoard.setLastStep(this);
        this.setNextStep(nextBoard);
        nextBoard.setOneOnBoard(next,nextBoard.getBoard()[now.x][now.y]);
        nextBoard.setOneOnBoard(now,null);
        return nextBoard;
    }
}
