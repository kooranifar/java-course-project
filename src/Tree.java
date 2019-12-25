import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Tree {
    private Feature root = null;

    boolean isEmpty() { return (this.root == null); }

    void setRoot(Feature root){
        this.root = root;
        System.out.println("now the root is: " + root.getName());

    }
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

    // a method to print our tree. this will show all data of all nodes in BF order
    public void showData (){
        List<Feature> flattenTree = this.flattenBreadthFirst();
        for (Feature feature : flattenTree){
            System.out.println("name: " + feature.getName());
            if (! feature.equals(this.root)){ System.out.println("parent: " + feature.getParent().getName()); }
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

}
