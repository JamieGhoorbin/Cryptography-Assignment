package com.jamieghoorbin;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class Main {

    /**
     * A simple method to read a file.
     * @param filename the file.
     * @return the file content.
     */
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
        String ex1 = readFile("./cexercise1.txt");
        String ex2 = readFile("./cexercise2.txt");
        String ex3 = readFile("./cexercise3.txt");
        String ex4 = readFile("./cexercise4.txt");
//        String ex5 = readFile("./cexercise5.txt");
//        String ex6 = readFile("./cexercise6.txt");
//        String ex7 = readFile("./cexercise7.txt");

/*
        Exercise 1 (2 marks)
        The plaintext comes from tess26.txt and is encoded with a Caesar cipher.
*/
        CaesarDecrypt cd = new CaesarDecrypt();
        System.out.println("Exercise 1---------------");
        for(int i = 0 ; i < 26; i++) {
            if(i==0) {
                System.out.println("Original Ciphertext:             " + cd.decryptBruteForce(ex1,i));
            } else {
                if(i<10) {
                    System.out.println("Potential plaintext [shift:" + i + "]:   " + cd.decryptBruteForce(ex1,i));
                } else {
                    System.out.println("Potential plaintext [shift:" + i + "]:  " + cd.decryptBruteForce(ex1,i));
                }
            }
        }
        System.out.println();

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
        listOfStuff.forEach((key, value) -> System.out.println("KeyLength: " + key + ", IOC value: " + value));
        System.out.println("Since key length of 6 is the highest and closest IOC value to the monoalphabetic " +
                "distribution, we use this value as our assumed key length.");
        vd4.decryptKeyLengthKnown();
        System.out.println();






    }
}
