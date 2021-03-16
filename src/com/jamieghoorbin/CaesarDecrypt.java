package com.jamieghoorbin;

/*
  A Caesar decryption class for solving question 1 of the "CO634 Cryptography Assignment".

  @author Jamie Ghoorbin
  @version 2021.03.06
 */

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CaesarDecrypt {
    private String cipher;
    private ArrayList<String> potentialDecryptedText;
    private ArrayList<String> commonBiGrams;

    public CaesarDecrypt(String cipher) {
        this.cipher = cipher;
        this.potentialDecryptedText = new ArrayList<>();
        this.commonBiGrams = new ArrayList<>();
    }

    /**
     * Driver code to decrypt uppercase text encrypted with Caesar cipher.
     * @return the plaintext and number of shifts.
     */
    public String[] decryptCaesar() {
        populateBiGrams();
        populateListPotentialDecryptedText();

        int candidateScore = 0;
        String candidatePlainTxt = "";

        // Loop over potential plaintext list.
        for(int i = 0; i < 26; i++) {
            String plainTxt = potentialDecryptedText.get(i);
            int count = 0;

            // Loop over common bigrams list and count occurrence of each bigram in potentialDecryptedText.
            // Return the string with the highest count.
            for(String bigram : commonBiGrams) {
                Pattern p = Pattern.compile(bigram);
                Matcher m = p.matcher(plainTxt);
                while (m.find()) {
                    count++;
                }
                // Keep track of the strongest candidate.
                if(count > candidateScore) {
                    candidateScore = count;
                    candidatePlainTxt = plainTxt;
                }
            }
        }
        return new String[]{candidatePlainTxt, String.valueOf(potentialDecryptedText.indexOf(candidatePlainTxt))};
    }

    /**
     * Populate list with bigrams.
     * Credit: https://www3.nd.edu/~busiforc/handouts/cryptography/Letter%20Frequencies.html
     */
    private void populateBiGrams() {
        commonBiGrams.add("TH");
        commonBiGrams.add("HE");
        commonBiGrams.add("IN");
        commonBiGrams.add("EN");
        commonBiGrams.add("NT");
        commonBiGrams.add("RE");
        commonBiGrams.add("ER");
        commonBiGrams.add("AN");
        commonBiGrams.add("TI");
        commonBiGrams.add("ES");
        commonBiGrams.add("ON");
        commonBiGrams.add("AT");
        commonBiGrams.add("SE");
        commonBiGrams.add("ND");
        commonBiGrams.add("OR");
        commonBiGrams.add("AR");
        commonBiGrams.add("AL");
        commonBiGrams.add("TE");
        commonBiGrams.add("TE");
        commonBiGrams.add("CO");
        commonBiGrams.add("DE");
        commonBiGrams.add("TO");
        commonBiGrams.add("RA");
        commonBiGrams.add("ET");
        commonBiGrams.add("ED");
        commonBiGrams.add("IT");
        commonBiGrams.add("SA");
        commonBiGrams.add("EM");
        commonBiGrams.add("RO");



    }

    /**
     * Returns a string transposed by n caesar shift.
     * @param shift the shift amount in range (0-25).
     * @return the shifted string.
     */
    private String decryptBruteForce(int shift) {
        StringBuilder sb = new StringBuilder();
        cipher.chars()
                .forEach(ch -> sb.append((char) (Math.floorMod(((ch - 'A') - shift),26)+'A')));
        return sb.toString();
    }

    /**
     * Populate a list with potential decrypted strings.
     */
    private void populateListPotentialDecryptedText() {
        for(int i = 0; i < 26; i++) {
            potentialDecryptedText.add(decryptBruteForce(i));
        }
    }

}

