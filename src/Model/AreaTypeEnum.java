package Model;

/**
 * Created by zhang.youting on 2017/6/5.
 */
public enum AreaTypeEnum {
    Road(0,"道路"),
    Intersection(1,"路口"),
    Container(2,"集装箱")
    ;



    private int code;
    private String msg;
    AreaTypeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
