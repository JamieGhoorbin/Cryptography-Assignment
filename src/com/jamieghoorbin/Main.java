package com.jamieghoorbin;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        String ex1 = readFile("./cexercise1.txt");
        String ex2 = readFile("./cexercise2.txt");
        String ex3 = readFile("./cexercise3.txt");
        String ex4 = readFile("./cexercise4.txt");
        String ex5 = readFile("./cexercise5.txt");
        String ex6 = readFile("./cexercise6.txt");
        String ex7 = readFile("./cexercise7.txt");

        /*
        Exercise 1 (2 marks)
        The plaintext comes from tess26.txt and is encoded with a Caesar cipher.
        */
        System.out.println("-------------------------------------------------------------");
        System.out.println("Exercise 1");
        CaesarDecrypt cd = new CaesarDecrypt(ex1);
        String[] plainTextAndShift = cd.decryptCaesar();
        System.out.println("Decrypted ciphertext: " + returnFirstThirtyCharacters(plainTextAndShift[0]));
        System.out.println("Number of shifts:     " + plainTextAndShift[1]);
        System.out.println("-------------------------------------------------------------");

        /*
        Exercise 2 (3 marks)
        The plaintext comes from tess26.txt and is encoded with a Vigenere cipher using
        the 21-letter key TESSOFTHEDURBERVILLES.
        */
        System.out.println("-------------------------------------------------------------");
        System.out.println("Exercise 2");
        VigenereDecrypt vd2 = new VigenereDecrypt(ex2, "TESSOFTHEDURBERVILLES");
        String[] decryptWithKey = vd2.decryptWithKey();
        if (!decryptWithKey[0].equals("Error")) {
            System.out.println("Decrypted ciphertext: " + returnFirstThirtyCharacters(decryptWithKey[0]));
            System.out.println("Key:                  " + decryptWithKey[1]);
        } else {
            System.err.println(decryptWithKey[1]);
        }
        System.out.println("-------------------------------------------------------------");

        /*
        Exercise 3 (4 marks)
        The plaintext comes from tess26.txt and is encoded with a Vigenere cipher. The
        key is an arbitrary sequence of six letters (i.e. not necessarily forming an English
        word).
        */
        System.out.println("-------------------------------------------------------------");
        System.out.println("Exercise 3");
        VigenereDecrypt vd3 = new VigenereDecrypt(ex3, 6);
        String[] decryptKeyLengthKnown1 = vd3.decryptKeyLengthKnown();
        if (!decryptKeyLengthKnown1[0].equals("Error")) {
            System.out.println("Decrypted ciphertext: " + returnFirstThirtyCharacters(decryptKeyLengthKnown1[0]));
            System.out.println("Key:                  " + decryptKeyLengthKnown1[1]);
        } else {
            System.err.println(decryptKeyLengthKnown1[1]);
        }
        System.out.println("-------------------------------------------------------------");


        /*
        Exercise 4 (5 marks)
        The plaintext comes from tess26.txt and is encoded with a Vigenere cipher. The
        key is an arbitrary sequence of between 4 and 6 letters.
        */
        System.out.println("-------------------------------------------------------------");
        System.out.println("Exercise 4");

        // Step 1: Find the length of the key using William F. Friedman's Index of Coincidence formula.
        IOC ioc = new IOC(ex4);
        HashMap<Integer, Double> listOfStuff = new HashMap<>();
        for(int i = 4; i < 7; ++i) {
            listOfStuff.put(i, ioc.calculateIC(i));
        }
        listOfStuff.forEach((key, value) -> System.out.println("Assumed Key Length: " + key + ", IOC value: " + value));
        System.out.println("-Since the assumed key length of 6 is high,\n we use this value as our candidate key length.\n");

        // Step 2: Use the key length from step 1 to decrypt the ciphertext.
        VigenereDecrypt vd4 = new VigenereDecrypt(ex4, 6);
        String[] decryptKeyLengthKnown2 = vd4.decryptKeyLengthKnown();

        if (!decryptKeyLengthKnown2[0].equals("Error")) {
            System.out.println("Decrypted ciphertext: " + returnFirstThirtyCharacters(decryptKeyLengthKnown2[0]));
            System.out.println("Key:                  " + decryptKeyLengthKnown2[1]);
        } else {
            System.err.println(decryptKeyLengthKnown2[1]);
        }
        System.out.println("-------------------------------------------------------------");


        /*
        Exercise 5 (5 marks)
        The plaintext comes from tess26.txt and is encoded with a transposition cipher,
        as follows: the plaintext is written row-wise across a certain number of columns,
        between 4 and 6. (You must figure out how many columns were used.) The
        ciphertext is formed by reading out successive columns from left to right.
        */
        System.out.println("-------------------------------------------------------------");
        System.out.println("Exercise 5");
        TranspositionDecrypt td1 = new TranspositionDecrypt(ex5);
        String[] s1 = td1.decrypt(4);
        System.out.println("Decrypted ciphertext: " + returnFirstThirtyCharacters(s1[1]));
        System.out.println("Key:                  " + s1[0]);
        System.out.println("-------------------------------------------------------------");


        /*
        Exercise 6 (5 marks)
        The plaintext comes from tess26.txt and is encoded with a transposition cipher,
        as follows: the plaintext is written row-wise across six columns. The ciphertext is
        formed by reading out successive columns in an arbitrary order (which you must
        figure out to decipher the message). Hint:look for common pairs of letters, such as
        'th'.
        */
        System.out.println("-------------------------------------------------------------");
        System.out.println("Exercise 6");
        TranspositionDecrypt td2 = new TranspositionDecrypt(ex6);
        String[] s2 = td2.decrypt(6);
        System.out.println("Decrypted ciphertext: " + returnFirstThirtyCharacters(s2[1]));
        System.out.println("Key:                  " + s2[0]);
        System.out.println("-------------------------------------------------------------");


        /*
        Exercise 7 (6 marks)
        The plaintext comes from tess27.txt and is encoded with a general substitution
        cipher, using a randomly chosen mapping from the 27-character alphabet onto
        itself. Note that normally (i.e. except by chance) a vertical bar will be mapped
        onto some other letter of the alphabet.
        */
//        GeneralSubstitutionDecrypt gsd = new GeneralSubstitutionDecrypt(ex7);
//        gsd.test();

    }

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

    /**
     * Return the first thirty characters of a given string.
     * @param str the string.
     * @return truncated string.
     */
    public static String returnFirstThirtyCharacters(String str) {
        if (!(str.length() < 30)) {
            return str.substring(0,30);
        }
        return "";
    }
}
