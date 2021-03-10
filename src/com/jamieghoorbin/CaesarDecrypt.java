package com.jamieghoorbin;

/*
  A Caesar decryption class for solving question 1 of the "CO634 Cryptography Assignment".

  @author Jamie Ghoorbin
  @version 2021.03.06
 */

public class CaesarDecrypt {
    private String cipher;

    public CaesarDecrypt(String cipher) {
        this.cipher = cipher;
    }

    /**
     * Returns a string of n caesar shifts.
     *
     * @param shift the shift amount in range (0-25).
     * @return the shifted string.
     */
    public String decryptBruteForce(int shift) {
        StringBuilder sb = new StringBuilder();
        cipher.chars()
                .forEach(ch -> sb.append((char) (Math.floorMod(((ch - 'A') - shift),26)+'A')));
        return sb.toString();
    }
}

