package View;

import java.awt.*;

/**
 * Created by zhang.youting on 2017/6/5.
 */
public enum ColorEnum {
    White(0,"白",Color.white),
    Black(1,"黑",Color.GRAY);


    private int code;
    private String msg;
    private Color color;
    ColorEnum(int code, String msg,Color color) {
        this.code = code;
        this.msg = msg;
        this.color = color;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public Color getColor() { return color; }
}

