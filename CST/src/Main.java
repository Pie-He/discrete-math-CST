import parse.Parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        File file = new File("testunit.txt");

        List<String> strs = new LinkedList<String>();
        if (file.exists()) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String str;
                while ((str = br.readLine()) != null) {
                    try {
                        System.out.println(str);
                        Parser p = new Parser();
                        strs.add(str);
                    } catch (Exception e) {
                        System.out.println("not well-defined!");
                    }
                }
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Tree.constructTree(strs);
        }
    }
}
