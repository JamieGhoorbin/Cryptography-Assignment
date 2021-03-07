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
        String cipher = readFile("./cexercise3.txt");

        VigenereDecrypt ex = new VigenereDecrypt(cipher, 6);
        ex.decrypt();


//         System.out.println(cipher.length());

    }
}
