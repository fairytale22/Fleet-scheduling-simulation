package Model;

/**
 * Created by zhang.youting on 2017/6/6.
 */
public enum DirectionEnum {

    u(0,-1),
    ur(1,-1),
    r(1,0),
    dr(1,1),
    d(0,1),
    dl(-1,1),
    l(-1,0),
    ul(-1,-1);


    private int dx;
    private int dy;

    DirectionEnum(int dx,int dy){
        this.dx = dx;
        this.dy = dy;
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }
}
