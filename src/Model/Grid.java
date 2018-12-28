package Model;

public class Grid {
    private ObjectEnum object;
    private AreaTypeEnum areaType;

    public Grid(ObjectEnum object, AreaTypeEnum areaType) {
        this.object = object;
        this.areaType = areaType;
    }

    public ObjectEnum getObject() {
        return object;
    }

    public void setObject(ObjectEnum object) {
        this.object = object;
    }

    public AreaTypeEnum getAreaType() {
        return areaType;
    }

    public void setAreaType(AreaTypeEnum areaType) {
        this.areaType = areaType;
    }

    public boolean equals(Object obj){
        Grid grid = (Grid)obj;
        if (this.object == grid.getObject()&&this.areaType == grid.getAreaType()){
            return true;
        }
        else
            return false;
    }

    public int hashCode(){
        return object.hashCode()+ areaType.hashCode();
    }
}
