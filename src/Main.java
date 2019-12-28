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
        pattern = "([=+^|])";
        input = input.replaceAll(pattern, " $1 ");

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
        input = input.replaceAll("[{,}]", " ");
        input = input.trim();
        String[] token = input.split("\\s+");
        List<String> result = Arrays.asList(token);
        return result;
    }

    public static void main(String[] args) {

//        Scanner input = new Scanner(System.in);
//        String line = input.nextLine();
//
//        Tree tree = new Tree();
//        while (! line.equals("#")){
//            tree.implement(configline_tartamizer(line));
//            line = input.nextLine();
//        }
//        for (Feature a: tree.getAllFeatures()){
//            System.out.print(a.getName() + " ");
//        }
//        System.out.println();
//        tree.showData();
//
//        // among the most important lines of my code i'd like to honor:
//        tree.setRoot();
//
//        line = input.nextLine();
//        while (! line.equals("##")){
//            if (tree.isValid(testcase_tartamizer(line))){
//                System.out.println("Valid");
//            }
//            else {
//                System.out.println("Invalid");
//            }
//            line = input.nextLine();
//        }
//
        Scanner input = new Scanner(System.in);
        String line;
        line = input.nextLine();
        Tree tree = new Tree();
        List<String> result = new ArrayList<String>();
        boolean tree_created = false;
        boolean result_extended = false; // checks if some validations were added to result to timely add +++ to it.
        while (! line.equals("###")){
            while (! line.equals("##")){
                if (tree_created){
                    // handle validations
                    result.add(tree.isValid(testcase_tartamizer(line)));
                    result_extended = true;
                }
                else { // tree is not created. handle configuration
                    tree = new Tree();
                    while (! line.equals("#")){

                        tree.implement(configline_tartamizer(line));
                        line = input.nextLine();
                    }
                    // tree is done.
                    tree.setRoot();
                    tree.showData();
                    tree_created = true;
                }
                line = input.nextLine();
            }
            // this whole is done. heading to another whole!
            tree_created = false;
            if (result_extended){result.add("+++");}
            result_extended = false;
            line = input.nextLine();
        }
        for (String a : result){
            System.out.println(a);
        }
    }
}
