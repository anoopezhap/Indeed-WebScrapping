package indeed;

import java.util.LinkedHashSet;
import java.util.Set;

public class demo {
    public static void main(String[] args) {
        String temp = "\"Posted\n" +
                "Posted 2 days ago\"\n";
        String data2 = temp.replace("\"","");
        String input = data2.replace("\n"," ");
        System.out.println(input);

        System.out.println("######");

        String split[] = input.split(" ");

       for(String s :split)
       {
           System.out.println(s);
           System.out.println("***");
       }

       LinkedHashSet<String> set = new LinkedHashSet<>();

       for(String s : split)
       {
           set.add(s);
       }

       String a = set.toString();
       String b = a.replace("[","").replace("]","").replace(",","");
        System.out.println(b);
















    }
}
