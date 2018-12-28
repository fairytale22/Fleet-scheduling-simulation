package View;

import Model.Grid;

/**
 * Created by zhang.youting on 2017/6/5.
 */
public class Square {
    private ColorEnum color;
    private Grid grid;

    public Square() {
        this.grid = null;
    }

    public ColorEnum getColor() {
        return color;
    }

    public void setColor(ColorEnum color) {
        this.color = color;
    }

    public Grid getGrid() {
        return grid;
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }
}
