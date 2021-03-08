package com.jamieghoorbin;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

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
        String ex4 = readFile("./cexercise4.txt");


/*
        Exercise 2 (3 marks)
        The plaintext comes from tess26.txt and is encoded with a Vigenere cipher using
        the 21-letter key TESSOFTHEDURBERVILLES.
*/
        VigenereDecrypt vd2 = new VigenereDecrypt(ex2, "TESSOFTHEDURBERVILLES");
        System.out.println("Exercise 2---------------");
        vd2.decryptWithKey();
        System.out.println();

/*
        Exercise 3 (4 marks)
        The plaintext comes from tess26.txt and is encoded with a Vigenere cipher. The
        key is an arbitrary sequence of six letters (i.e. not necessarily forming an English
        word).
*/
        VigenereDecrypt vd3 = new VigenereDecrypt(ex3, 6);
        System.out.println("Exercise 3---------------");
        vd3.decryptKeyLengthKnown();
        System.out.println();

/*
        Exercise 4 (5 marks)
        The plaintext comes from tess26.txt and is encoded with a Vigenere cipher. The
        key is an arbitrary sequence of between 4 and 6 letters.
*/
        IOC ioc = new IOC(ex4);
        HashMap<Integer, Double> listOfStuff = new HashMap<>();
        for(int i = 4; i < 7; ++i) {
            listOfStuff.put(i, ioc.calculateIC(i));
        }



        VigenereDecrypt vd4 = new VigenereDecrypt(ex4, 6);
        System.out.println("Exercise 4---------------");
        listOfStuff.entrySet().forEach(entry->{
            System.out.println("KeyLength: " + entry.getKey() + ", IOC value: " + entry.getValue());
        });
        System.out.println("Using key length 6 since the IOC value is the highest.");
        vd4.decryptKeyLengthKnown();
        System.out.println();






    }
}
