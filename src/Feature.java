import java.util.List;
import java.util.ArrayList;


public class Feature {

    private String          Name;
    private Feature         Parent;
    private AsParentAttr    AsParentAttr;
    private AsChildAttr     AsChildAttr;
    private List<Feature>   Children;

    Feature(String Name, Feature Parent){
        this.Parent = Parent;
        this.Name = Name;
        this.AsChildAttr = null;
        this.AsParentAttr = null;
        this.Children =  new ArrayList<Feature>();
    }

    Feature(String Name, Feature Parent, AsChildAttr AsChildattr, AsParentAttr AsParentAttr, List<Feature> Children){
        this.Parent = Parent;
        this.Name = Name;
        this.AsChildAttr = AsChildattr;
        this.AsParentAttr = AsParentAttr;
        this.Children =  new ArrayList<Feature>();
    }

    public String getName(){ return this.Name;}

    public void setParent(Feature parent){ this.Parent = parent; }

    public Feature getParent(){return this.Parent;}

    public void addChild(Feature child){
        this.Children.add(child);
        child.setParent(this);
    }
    public List<Feature> getChildren(){ return this.Children; }

    public void setAsChildAttr(AsChildAttr asChildAttr) { this.AsChildAttr = asChildAttr; }

    public AsChildAttr getAsChildAttr() { return AsChildAttr; }

    public AsParentAttr getAsParentAttr() { return AsParentAttr; }

    public void setAsParentAttr(AsParentAttr asParentAttr) { this.AsParentAttr = asParentAttr; }


}
