package com.jamieghoorbin;

/*
  A Caesar decryption class for solving question 1 of the "CO634 Cryptography Assignment".

  @author Jamie Ghoorbin
  @version 2021.03.06
 */

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CaesarDecrypt {
    private String cipher;

    public CaesarDecrypt() {
        this.cipher = "";
    }

    /**
     * Calculates the potential number of shifts required to decrypt a given ciphertext.
     * @param cipher the ciphertext.
     * @return the shift.
     */
//    private int calculateShift(String cipher) {
//        // Count letter occurrence in the ciphertext.
//        Map<Character, Long> charFrequency = Arrays.asList(cipher).stream() //Stream<String>
//                .flatMap(a -> a.chars().mapToObj(c -> (char) c)) // Stream<Character>
//                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
//        charFrequency.forEach((key, value) -> System.out.println("KeyLength: " + key + ", IOC value: " + value));
//        return -1;
//    }
//
//    public String decryptCalculatingShift(String cipher) {
//        calculateShift(cipher);
//        return null;
//    }

    /**
     *
     * @param cipher the cipher text.
     * @param shift the shift amount in range (0-25).
     * @return
     */
    public String decryptBruteForce(String cipher, int shift) {
        StringBuilder sb = new StringBuilder();
        cipher.chars()
                .forEach(ch -> sb.append((char) (Math.floorMod(((ch - 'A') - shift),26)+'A')));
        return sb.toString();
    }
}

