package View;

import Controller.ChessController;
import Model.Grid;
import Model.ObjectEnum;
import Model.AreaTypeEnum;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhang.youting on 2017/6/5.
 */
public class Board extends JPanel implements MouseListener {

    private Square[][] squares;
    private AreaTypeEnum currentColor = AreaTypeEnum.White;//当前移动方
    private AreaTypeEnum checked;//谁被将军
    private Point selectedPiece = null;//被选中的棋子
    private Map<Grid,BufferedImage> imgMap = new HashMap<>();
    private ChessController controller;

    private Button restartButton;
    private Button revokeButton;
    private Button historyButton;
    private Button lastMove4HistoryButton;
    private Button nextMove4HistoryButton;

    JPanel buttonGroup = new JPanel(new GridLayout(6,1,200,2));

    public Board(AreaTypeEnum currentColor, ChessController chessController) {
        this.setFocusable(true);
        addMouseListener(this);
        this.squares = new Square[8][8];
        this.currentColor = currentColor;
        this.selectedPiece = null;
        controller = chessController;
        init();
    }

    public void paint(Graphics g){
        super.paint(g);
        this.setBackground(Color.orange);
        g.setColor(Color.orange);
        g.fillRect(800, 0, 200, 800);
        g.setColor(Color.GREEN);
        for (int i = 0 ; i<8;i++){
            for(int j = 0; j<8;j++){
                Square square = squares[i][j];
                g.setColor(square.getColor().getColor());
                g.fillRect(i*100, j*100, 100, 100);
                if (square.getGrid()!=null){
                    BufferedImage image = imgMap.get(square.getGrid());
                    //BufferedImage image = imgMap.get(new Grid(ObjectEnum.King,AreaTypeEnum.Black));
                    int width = image.getWidth();
                    int height = image.getHeight();
                    g.drawImage(image,i*100+50-width/8,j*100+20, width/4,height/4,this);
                }
            }
        }
        if (selectedPiece!=null){
            g.setColor(Color.ORANGE);
            g.drawLine(selectedPiece.x*100,selectedPiece.y*100,selectedPiece.x*100+100,selectedPiece.y*100);
            g.drawLine(selectedPiece.x*100+100,selectedPiece.y*100,selectedPiece.x*100+100,selectedPiece.y*100+100);
            g.drawLine(selectedPiece.x*100+100,selectedPiece.y*100+100,selectedPiece.x*100,selectedPiece.y*100+100);
            g.drawLine(selectedPiece.x*100,selectedPiece.y*100+100,selectedPiece.x*100,selectedPiece.y*100);
        }
        g.setColor(Color.BLACK);
        g.setFont(new Font("黑体",Font.BOLD,20));
        g.drawString("当前玩家：", 850, 100);
        g.drawString(currentColor.getMsg(), 860, 150);

    }

    public void init(){
        //按钮初始化
        restartButton = new Button("Reset!");
        revokeButton = new Button("Revoke!");
        historyButton = new Button("Show History!");
        lastMove4HistoryButton = new Button("Last Step?");
        nextMove4HistoryButton = new Button("Next Step?");
        restartButton.setSize(50,30);

        buttonGroup.add(restartButton);
        buttonGroup.add(revokeButton);
        buttonGroup.add(historyButton);
        buttonGroup.add(lastMove4HistoryButton);
        buttonGroup.add(nextMove4HistoryButton);
        lastMove4HistoryButton.setVisible(false);
        nextMove4HistoryButton.setVisible(false);
        this.setLayout(null);
        this.add(buttonGroup);
        buttonGroup.setBounds(805,500,185,300);

        //绑定事件
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.restart();
            }
        });
        revokeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.revoke();
            }
        });
        historyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //切换至历史模式
                changeHistoryMode(controller.historyMode());
            }
        });
        lastMove4HistoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.getLastStep();
            }
        });
        nextMove4HistoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.getNextStep();
            }
        });

        for (int i = 0 ; i<8;i++){
            for(int j = 0; j<8;j++){
                squares[i][j] = new Square();
                if ((i+j)%2==0){
                    squares[i][j].setColor(ColorEnum.Black);
                }else {
                    squares[i][j].setColor(ColorEnum.White);
                }
            }
        }
        //载入图像
        String relativelyPath=System.getProperty("user.dir");
        File file=new File(relativelyPath+"\\src\\resource\\pieceImg.png");
        try {
            BufferedImage bi= ImageIO.read(file);

            //前两个值是坐标位置X、Y，后两个是长和宽
            imgMap.put(new Grid(ObjectEnum.Rook, AreaTypeEnum.Black),bi.getSubimage(80, 210, 106, 198));
            imgMap.put(new Grid(ObjectEnum.Knight, AreaTypeEnum.Black),bi.getSubimage(211, 228, 115, 187));
            imgMap.put(new Grid(ObjectEnum.Bishop, AreaTypeEnum.Black),bi.getSubimage(349, 173, 105, 241));
            imgMap.put(new Grid(ObjectEnum.Queen, AreaTypeEnum.Black),bi.getSubimage(468, 100, 110, 306));
            imgMap.put(new Grid(ObjectEnum.King, AreaTypeEnum.Black),bi.getSubimage(593, 132, 125, 280));
            imgMap.put(new Grid(ObjectEnum.Pawn, AreaTypeEnum.Black),bi.getSubimage(96, 448, 93, 154));

            imgMap.put(new Grid(ObjectEnum.Rook, AreaTypeEnum.White),bi.getSubimage(80, 700, 106, 198));
            imgMap.put(new Grid(ObjectEnum.Knight, AreaTypeEnum.White),bi.getSubimage(211, 718, 115, 187));
            imgMap.put(new Grid(ObjectEnum.Bishop, AreaTypeEnum.White),bi.getSubimage(349, 663, 105, 241));
            imgMap.put(new Grid(ObjectEnum.Queen, AreaTypeEnum.White),bi.getSubimage(468, 590, 110, 306));
            imgMap.put(new Grid(ObjectEnum.King, AreaTypeEnum.White),bi.getSubimage(593, 622, 125, 280));
            imgMap.put(new Grid(ObjectEnum.Pawn, AreaTypeEnum.White),bi.getSubimage(98, 928, 86, 150));
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public void upDate(Point p, Grid grid){
        squares[p.x][p.y].setGrid(grid);
    }

    public void setCurrentColor(AreaTypeEnum currentColor) {
        this.currentColor = currentColor;
    }
    public void setSelectedPiece(Point selectedPiece) {
        this.selectedPiece = selectedPiece;
    }
    public void setChecked(AreaTypeEnum checked) {
        this.checked = checked;
    }

    public void mouseClicked(MouseEvent e){

    }
    public void mousePressed(MouseEvent e){
        if(e.getButton()==MouseEvent.BUTTON1){
            System.out.println(e.getPoint());
            Point p = e.getPoint();
            int x = p.x/100;
            int y = p.y/100;
            if (x>=0&&x<8&&y>=0&&y<8){
                controller.clickEvent(new Point(x,y));
            }

        }
        if(e.getButton()==MouseEvent.BUTTON3){
            //System.out.println(e.getPoint());
            controller.cancelSelect();
        }
    }
    public void mouseReleased(MouseEvent e){

    }
    public void mouseEntered(MouseEvent e){

    }
    public void mouseExited(MouseEvent e){

    }


    public void changeHistoryMode(boolean historyMode){
        if (historyMode){
            restartButton.setVisible(false);
            revokeButton.setVisible(false);
            lastMove4HistoryButton.setVisible(true);
            nextMove4HistoryButton.setVisible(true);
            removeMouseListener(this);
        }else {
            restartButton.setVisible(true);
            revokeButton.setVisible(true);
            lastMove4HistoryButton.setVisible(false);
            nextMove4HistoryButton.setVisible(false);
            addMouseListener(this);
        }
    }
}
