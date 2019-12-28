import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Tree {
    private Feature root = null;
    boolean isEmpty() { return (this.root == null); }
    private List<Feature> AllFeatures = new ArrayList<Feature>();

    public List<Feature> getAllFeatures() { return AllFeatures; }

    public void setRoot(){ // important. be sure not to be forgotten to be set.
        for (Feature feature: this.getAllFeatures()){
            if (feature.getParent() == null) {
                this.root = feature;
                System.out.println("Root is feature " + feature.getName());
                return;
            }
        }
    }

    public Feature getRoot() { return root; }

    private void addFeature(Feature feature){ this.AllFeatures.add(feature); }

    // flattens our tree to be able too traverse it.
    public List<Feature> flattenBreadthFirst() {
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
            System.out.println();
            if (feature.getAsChildAttr()  != null){ System.out.println("childattr: "  + feature.getAsChildAttr().name);}
            if (feature.getAsParentAttr() != null){ System.out.println("parentattr: " + feature.getAsParentAttr().name);}
            System.out.println("\n");

        }
    }

    public void implement (List<String> configline) {
        String this_parent_name = configline.get(1);
        Feature this_parent;
        Feature traverse_result; // just to save memory! as you said.
        traverse_result = traverseTo(this_parent_name);
        if (traverse_result != null) {this_parent = traverse_result;}
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

                traverse_result = traverseTo(this_child_name);
                if (traverse_result == null){
                    this_child = new Feature(this_child_name, traverseTo(configline.get(1)));
                    this.addFeature(this_child);
                }
                else {
                    this_child = traverse_result;
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
                traverse_result = traverseTo(configline.get(i));
                if (traverse_result == null){
                    this_child = new Feature(configline.get(i), traverseTo(configline.get(1)));
                    this.addFeature(this_child);
                }
                else {
                    this_child = traverse_result;
                    this_child.setParent(this_parent);
                }
                this_parent.addChild(this_child);
            }
        }
    }

    public String isValid(List<String> model){
        // root must be included.
        if (! model.contains(this.root.getName())) {
            return ("Invalid: root is missed");
//            return false;
        }
        for (int i=0; i<model.size(); i++){
            Feature this_feature = traverseTo(model.get(i));

            // parent of each feature should exist.
            if (! this_feature.equals(this.root)){
                if (! model.contains(this_feature.getParent().getName())) {
                    return ("Invalid: parent of " + this_feature.getName() + " is missed");
//                    return false;
                }
            }

            // OR and XOR
            if (this_feature.getAsParentAttr() == AsParentAttr.OR){
                int incident_count = 0;
                for (Feature child : this_feature.getChildren()){
                    if (model.contains(child.getName()))
                        incident_count++;
                }
                if (incident_count < 1) {
                    return ("Invalid: OR error for "
                            + this_feature.getName()
                            + " - number of incidents: "
                            + incident_count);
//                    return false;
                }
            }

            if (this_feature.getAsParentAttr() == AsParentAttr.XOR){
                int incident_count = 0;
                for (Feature child : this_feature.getChildren()){
                    if (model.contains(child.getName()))
                        incident_count++;
                }
                if (incident_count != 1) {
                    return("Invalid: XOR error for "
                            + this_feature.getName()
                            + " - number of incident is: "
                            + incident_count);
//                    return false;
                }
            }

            // Mandatory children of an existing method should be in the model.
            for (Feature child: this_feature.getChildren()){
                if (child.getAsChildAttr() == AsChildAttr.MANDATORY){
                    if (! model.contains(child.getName())){
                        return("Invalid: mandatory child "
                                + child.getName()
                                + " is missed for "
                                + this_feature.getName());
//                        return false;
                    }
                }
            }
        }
        return("Valid");
    }
}
