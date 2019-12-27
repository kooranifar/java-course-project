import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Tree {
    private Feature root = null;
    boolean isEmpty() { return (this.root == null); }
    private List<Feature> AllFeatures = new ArrayList<Feature>();

    public List<Feature> getAllFeatures() { return AllFeatures; }

    public void setRoot(Feature root){
        this.root = root;
        System.out.println("now the root is: " + root.getName());

    }
    public Feature getRoot() { return root; }

    private void addFeature(Feature feature){ this.AllFeatures.add(feature); }

    // flattens our tree to be able too traverse it.
    private List<Feature> flattenBreadthFirst() {
//        System.out.println("flattenBreadthFirst is called");
        List list = new ArrayList<Feature>();
        Queue<Feature> queue = new LinkedList<Feature>();
        queue.add(this.root);
//        System.out.println(this.root.getName() + " added to list");
        while (queue.peek() != null) {
            Feature currentFeature = queue.remove();
            list.add(currentFeature);
//            System.out.println(currentFeature.getName() + " added to list");
            for (Feature child : currentFeature.getChildren()){
                queue.add(child);
            }
        }
        return list;
    }

    private Feature traverseTo(String name){ // returns null if it doesn't exits in our tree
        for (Feature feature : this.AllFeatures){
            if (name.equals(feature.getName())){ return feature; }
        }
        return null;
    }

    // a method to print our tree. this will show all data of all nodes in BF order
    public void showData (){
        List<Feature> allFeatures = this.getAllFeatures();
        for (Feature feature : allFeatures){
            System.out.println("name: " + feature.getName());
            if (feature.getParent()!= null) {System.out.println("parent: " + feature.getParent().getName());}
            else {System.out.println("parent: null");}

            System.out.print("children: ");
            for (Feature child : feature.getChildren()){
                System.out.print(child.getName() + " ");
            }
            System.out.println(feature.getChildren().size());
            if (feature.getAsChildAttr()  != null){ System.out.println("childattr: "  + feature.getAsChildAttr().name);}
            if (feature.getAsParentAttr() != null){ System.out.println("parentattr: " + feature.getAsParentAttr().name);}
            System.out.println("\n");

        }
    }

    public void implement (List<String> configline) {
        String this_parent_name = configline.get(1);
        Feature this_parent;
        if (traverseTo(this_parent_name) != null) {this_parent = traverseTo(this_parent_name);}
        else { // generate this new feature.
            this_parent = new Feature(this_parent_name, null);
            this.addFeature(this_parent);
        }
        /*
         if this_parent is a new feature, it's parent and AsChildAttr cannot be set yet.
         if it has existed in our tree then, it's parent, AsChildAttr and name was set before.
         we set the name for the feature if it is new.
         now we should set AsParentAttr and children for it. these, depends on the operator in configline. so...
        */
        if (configline.get(0).equals("+")) {
            // AsChildAttr cannot be declared here. AsParentAttr is null.
            // looping through the children to addChild them:
            for (int i=2; i<configline.size();i++){
                /*
                 each child may or may not be seen yet. name, parent, AsChildAttr should be set for them. so:
                 here we can see why i named it AsChildAttr
                */
                Feature this_child;
                String this_child_name;
                if (configline.get(i).charAt(0)=='?')
                    this_child_name = configline.get(i).substring(1);
                else
                    this_child_name = configline.get(i);

                if (traverseTo(this_child_name) == null){
                    this_child = new Feature(this_child_name, traverseTo(configline.get(1)));
                    this.addFeature(this_child);
                }
                else {
                    this_child = traverseTo(this_child_name);
                    this_child.setParent(this_parent);
                }
                this_child.setAsChildAttr(
                        configline.get(i).charAt(0)=='?' ? AsChildAttr.OPTIONAL : AsChildAttr.MANDATORY);
                // now that we implemented data for children we shall add them to this_parent
                this_parent.addChild(this_child);
            }
        } else if (configline.get(0).equals("|") || configline.get(0).equals("^")){
            this_parent.setAsParentAttr(configline.get(0).equals("|") ? AsParentAttr.OR : AsParentAttr.XOR);
            for (int i = 2; i < configline.size() ; i++) {
                Feature this_child;
                if (traverseTo(configline.get(i)) == null){
                    this_child = new Feature(configline.get(i), traverseTo(configline.get(1)));
                    this.addFeature(this_child);
                }
                else {
                    this_child = traverseTo(configline.get(i));
                    this_child.setParent(this_parent);
                }
                this_parent.addChild(this_child);
            }
        }
    }
}
