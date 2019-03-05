/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loto;
import java.io.*;
import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author dim
 */
public class Loto {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
              
        if (args.length>0){
            if (args[0].equals("gen")) {
                for (int i=0;i<50;++i) {
                    FileWriter fw = new FileWriter(String.format("c:/temp/loto/%1$03d.lot",i));
                    Random rnd = new Random();
                    //нагенерить уникальные номера
                    ArrayList<Integer> s = new ArrayList<Integer>();
                    
                    for (int j=0;j<10;++j){
                      
                    }
                    
                    //записать их в файл
                    for (String[10] s:nums){
                        fw.write(s[i]);
                    }
                    fw.close();
                }
            System.exit(0);
            }
        }
        
        File myFolder = new File("c:/temp/loto");
        File[] files = myFolder.listFiles();
        //System.out.println("--- Files found:");
        //for (File i:files) {
//            System.out.println(i.getName());
//        }
        //System.out.println("---");
        
        int lotoNumber =0;
        Scanner in = new Scanner(System.in);
        System.out.print("Enter loto number: ");
        lotoNumber = in.nextInt();
        in.close();
        //System.out.println("entered: ["+lotoNumber+"]");
        for (File i:files) {
            FileReader fr = new FileReader(i.getAbsoluteFile());
            System.out.println(i.getName());
            Scanner scan = new Scanner(fr);
            while (scan.hasNextLine()) {
               String line=scan.nextLine();
               if (line.equalsIgnoreCase(Integer.toString(lotoNumber))) {System.out.print("* ");}
               System.out.println(line);
            }
            scan.close();
            fr.close();
        }
    }
}
