
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loto;

import java.io.*;
import java.util.Scanner;
import java.util.Random;

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

        if (args.length > 0) {
            if (args[0].equals("gen")) {
                for (int i = 1; i <= 45; ++i) {
                    FileWriter fw = new FileWriter(String.format("c:/temp/loto/%1$03d.lot", i));
                    //Random rnd = ;
                    int[] nums= {0,0,0,0,0,0,0,0,0,0};
                    //нагенерить уникальные номера
                    System.out.print("generating "+i+"...");
                    for (int j = 0; j < 10; ++j) {
                        int r=0;
                        Boolean uniq=true;
                        do {
                            r=new Random().nextInt(30)+30;
                            for (int k = 0; k < 10; ++k) {
                                if (r==nums[k]) {uniq=false;}
                            } 
                        } while (!uniq);
                        nums[j]=r;
                    }
                    System.out.println("done");                    
                    for (int j = 0; j < 10; ++j) {
                        fw.write(nums[j]+"\n");
                    }
                    fw.close();
                }
                System.exit(0);
            }
        }
        Boolean showMustGoOn = true;
///// начало основного цикла        
        while (showMustGoOn) {
            File myFolder = new File("c:/temp/loto");
            //рассматриваем только файлы с расширением "*.lot"
            File[] files = myFolder.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.toLowerCase().endsWith(".lot");
                }
            });
            //проверка фильтра
            System.out.println("--- Билеты в игре:");
            int cnt = 0;
            for (File i : files) {
                FileReader fr1 = new FileReader(i.getAbsoluteFile());
                Scanner scan1 = new Scanner(fr1);
                int stars = 0;

                while (scan1.hasNextLine()) {
                    if (scan1.nextLine().contains("*")) {
                        stars++;
                    }
                }
                scan1.close();
                fr1.close();

                System.out.print(i.getName().replaceFirst(".lot", "") + "[" + stars + "]\t");
                cnt++;
            }
            System.out.println("\n--- Всего: " + cnt);

            if (cnt == 0) {
                showMustGoOn = false;
            } else {
                Scanner in = new Scanner(System.in);
                System.out.print("Введи номер бочонка: ");
                int lotoNumber = in.nextInt();
                //in.close();
                //System.out.println("entered: ["+lotoNumber+"]");
                //перебираем все файлы

                for (File i : files) {
                    //временный файл для каждого файла
                    File tempFile = new File(i.getCanonicalPath() + ".tmp");
                    FileWriter fwtemp = new FileWriter(tempFile);
                    FileReader fr = new FileReader(i.getAbsoluteFile());
                    Scanner scan = new Scanner(fr);

                    //перебираем все строки
                    while (scan.hasNextLine()) {
                        String line = scan.nextLine();
                        if (line.equalsIgnoreCase(Integer.toString(lotoNumber))) {
                            //если найдено совпадение
                            System.out.println("есть " + line + " в билете " + i.getName());
                            line = "* " + line;
                        }
                        fwtemp.write(line + "\n");
                    }
                    scan.close();
                    fr.close();
                    fwtemp.close();
                    i.delete();
                    tempFile.renameTo(i);
                }
                //снова перебираем файлы, ищем выигравшие
                for (File i : files) {
                    FileReader fr = new FileReader(i.getAbsoluteFile());
                    Scanner scan = new Scanner(fr);
                    int cntr = 0;
                    Boolean needToRename = false;
                    //перебираем все строки
                    while (scan.hasNextLine()) {
                        String line = scan.nextLine();
                        if (line.contains("*")) {
                            //если найдено совпадение
                            cntr++;
                        }
                        if (cntr >= 10) {
                            //сообщить
                            System.out.println("\n!!! Есть выигрышный билет: " + i.getName());
                            //переименовать файл
                            needToRename = true;
                        }
                    }

                    scan.close();
                    fr.close();
                    if (needToRename) {
                        //System.out.println(i.getAbsolutePath() + ".win");
                        i.renameTo(new File(i.getAbsolutePath() + ".win"));
                    }

                }
            } //else
        } //while

        System.out.println("game over");
    }
}
