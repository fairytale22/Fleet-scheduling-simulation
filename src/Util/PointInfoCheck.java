package Util;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by zhang.youting on 2017/6/6.
 */
public class PointInfoCheck {
    private int distance = -1;//距离
    private boolean isFile = false;//直线
    private boolean isDiagonal = false;//斜线
    private boolean isKnight = false;//马
    private List<Point> pointsPass = null;

    public PointInfoCheck(Point now, Point next) {
        int dx = next.x-now.x;
        int dy = next.y-now.y;
        if(dx==0||dy==0){
            isFile =true;
            distance = Math.abs(dx)+Math.abs(dy);
        }
        if (Math.abs(dx)==Math.abs(dy)){
            isDiagonal=true;
            distance = Math.abs(dx);
        }
        if (isFile||isDiagonal){
            pointsPass = new ArrayList<>();
            int px = now.x;
            int py = now.y;
            for (int i=0;i<distance-1;i++){
                px += dx/distance;
                py += dy/distance;
                pointsPass.add(new Point(px,py));
            }
        }
        if (Math.abs(dx*dy)==2){
            isKnight=true;
        }



    }

    public int getDistance() {
        return distance;
    }
    public boolean isFile() {
        return isFile;
    }
    public boolean isDiagonal() {
        return isDiagonal;
    }
    public List<Point> getPointsPass() {
        return pointsPass;
    }
    public boolean isKnight() {
        return isKnight;
    }
}
