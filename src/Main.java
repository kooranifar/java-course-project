import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.List;
public class Main {

    public static List<String> configline_tartamizer (String input){
        // har xat voroodi az jens e configline ro tartamiz mikone be form e [+,a,b,c,...]
        input = input.trim();
        // to remove whitespaces between question mark and optional features
        String pattern = "([?])(\\s+)(\\w)";
        input = input.replaceAll(pattern, "$1$3");

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

        if (neshangar != ""){ result.add(0, neshangar); }
        // to handle a configline like: b = c
        else { result.add(0, "+"); }

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

    public static void main(String[] args) {
        Tree tree = new Tree();

        Scanner input = new Scanner(System.in);
        String line = input.nextLine();
        while (! line.equals("#")){
            System.out.println(configline_tartamizer(line));
            tree.implement(configline_tartamizer(line));
            tree.showData();
            for (Feature a: tree.getAllFeatures()){
                System.out.print(a.getName() + " ");
            }
            System.out.println();
            line = input.nextLine();
        }

//                while(!configline.equals("#")){

//        }
        /*
        * the tree is:
        *
        * a
        * | \
        * |  \
        * b   c
        * |\
        * d e
        *   fgh
        *
        *
k        *  */
//        Feature A = new Feature("A", null, null, null, null);
//        Feature B = new Feature("B", null, AsChildAttr.OPTIONAL, AsParentAttr.OR, null);
//        Feature C = new Feature("C", null, AsChildAttr.OPTIONAL, null, null);
//        Feature D = new Feature("D", null, null, null, null);
//        Feature E = new Feature("E", null, null, null, null);
//        Feature F = new Feature("F", E, null, null, null);
//        Feature G = new Feature("G", E, null, null, null);
//        Feature H = new Feature("H", E, null, null, null);
//        tree.setRoot(A);
//        A.addChild(B);
//        E.addChild(F);
//        E.addChild(G);
//        B.addChild(D);
//        E.addChild(H);
//        A.addChild(C);
//        B.addChild(E);

    }
}
