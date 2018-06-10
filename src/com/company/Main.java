package com.company;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {

        try {
            String content = new String(Files.readAllBytes(Paths.get("src\\com\\company\\file.html")),  Charset.forName("windows-1251"));
            //System.out.println(content);

            Stream<String> sentences = Pattern.compile("[\\.!?]+[^а-яА-Яa-zA-Z]\\D").splitAsStream(content);
            //sentences.forEach(System.out::println);
            String[] sentencesArray = sentences.toArray(size -> new String[size]);

            String s = "";
            String[] ris = new String[sentencesArray.length];
            int j =0;
            String risn = "";


            for (int i =0; i<sentencesArray.length; i++) {
                s=sentencesArray[i];
                //System.out.println(sentencesArray[i]);


                Pattern p = Pattern.compile("(Рис\\.\\s\\d{1,2})+");
                Matcher m = p.matcher(s);
                while (m.find()) {
                    //System.out.println(m.group(0));
                    ris[j] = m.group(0);
                    j++;
                    System.out.println("Здесь есть ссылка на рисунок" + sentencesArray[i]);
                }
                //System.out.println(ris);
            }

            int[] risnum = new int[ris.length];
            boolean b=false;

            for (int i =0; i<ris.length; i++) {
                //System.out.println(ris[i]);
                Pattern pn = Pattern.compile("\\d{1,2}");
                if (ris[i]!=null) {
                    Matcher mn = pn.matcher(ris[i]);
                    while (mn.find()) {
                        risn = mn.group(0);
                        //System.out.println("Номер рисунка" + risn);
                    }

                    risnum[i] = Integer.parseInt(risn);
                    //System.out.println(risnum[i]);
                }
            }

            for (int i=1; i<risnum.length; i++) {
                if (risnum[i]<risnum[i-1]) b=true;
                System.out.println();
                System.out.println("Автор ссылается на рисунки не по порядку");
                break;

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}