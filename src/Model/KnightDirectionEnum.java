package Model;

/**
 * Created by zhang.youting on 2017/6/6.
 */
public enum KnightDirectionEnum {

    u1(-1,-2),
    u2(1,-2),
    r1(2,-1),
    r2(2,1),
    d1(1,2),
    d2(-1,2),
    l1(-2,1),
    l2(-2,-1);


    private int dx;
    private int dy;

    KnightDirectionEnum(int dx, int dy){
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
