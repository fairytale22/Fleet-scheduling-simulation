package Model;

/**
 * Created by zhang.youting on 2017/6/5.
 */
public enum ObjectEnum {
    RegisteredTruck(0,"注册货车"),
    UnregisteredTruck(1,"未注册货车"),
    Car(2,"小汽车"),
    Human(3,"人"),
    Obstacle(4,"障碍物")
    ;


    private int code;
    private String msg;
    ObjectEnum(int code, String msg) {
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
