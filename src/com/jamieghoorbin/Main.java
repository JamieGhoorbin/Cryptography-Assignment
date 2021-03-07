package com.jamieghoorbin;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    public static String readFile (String filename) {
        StringBuilder result = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String str;
            while ((str = br.readLine()) != null) {
                result.append(str);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return result.toString();
    }

    public static void main(String[] args) {
        String ex2 = readFile("./cexercise2.txt");
        String ex3 = readFile("./cexercise3.txt");


        VigenereDecrypt vd1 = new VigenereDecrypt(ex2, "TESSOFTHEDURBERVILLES");
        System.out.println("Exercise 2---------------");
        vd1.decryptWithKey();

        VigenereDecrypt vd2 = new VigenereDecrypt(ex3, 6);
        System.out.println("Exercise 3---------------");
        vd2.decryptKeyLengthKnown();




    }
}
