import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.List;
public class Main {

    public static List<String> configline_tartamizer (String input){
        // har xat voroodi az jens e configline ro tartamiz mikone be form e [+,a,b,c,...]
        input = input.trim();
        String[] token = input.split("\\s+");
        List<String> result = new ArrayList<String>();
        String neshangar = "";
        for (int i=0; i<token.length; i++){
            if (token[i].equals("+")
                    || token[i].equals("^")
                    || token[i].equals("|"))
                neshangar = token[i];

            if (!token[i].equals("=")
                    && !token[i].equals("+")
                    && !token[i].equals("^")
                    && !token[i].equals("|")){
                result.add(token[i]);
            }
        }
        result.add(0, neshangar);
        return result;
    }

    public static List<String> testcase_tartamizer (String input){
        // har xat voroodi az jens e configline ro tartamiz mikone be form e [+,a,b,c,...]
        input = input.replaceAll("[{}]", "");
        input = input.replace(',', ' ');
        input.trim();
        String[] token = input.split("\\s+");
        List<String> result = Arrays.asList(token);
        return result;
    }

    public static void implement_configline (Tree tree, List<String> tartamized_configline){
        Feature this_parent = new Feature(tartamized_configline.get(1), null);

        //looping through the first input line
        for (int i=2; i<tartamized_configline.size(); i++){
            if (tartamized_configline.get(0).equals("+")){
                if (tartamized_configline.get(i).charAt(0) == '?'){

                }
                else {

                }

            }
            else if (tartamized_configline.get(0).equals("^")){

            }
            else if (tartamized_configline.get(0).equals("|")){

            }
        }

    }

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
//        String line = input.nextLine();

//        implement_configline(tree, configline_tartamizer(line));
        //        while(!configline.equals("#")){
//
//        }

        Tree tree = new Tree();
        Feature A = new Feature("A", null, null, null, null);
        Feature B = new Feature("B", null, AsChildAttr.OPTIONAL, AsParentAttr.OR, null);
        Feature C = new Feature("C", null, AsChildAttr.OPTIONAL, null, null);
        Feature D = new Feature("D", null, null, null, null);
        Feature E = new Feature("E", null, null, null, null);
        tree.setRoot(A);
        A.addChild(B);
        A.addChild(C);
        B.addChild(D);
        B.addChild(E);
        tree.showData();
    }
}
