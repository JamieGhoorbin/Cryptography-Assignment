package com.jamieghoorbin;

/*
A class that implements William F. Friedman's Index of Coincidence formula
to estimate the key length of a Vigenère cipher.

From my understanding, if my assumed key length is correct, each group (index of a key)
should have an IOC of 0.0667 (IOC for "text written in English language") since each group is
essentially encrypted by a monoalphabetic substitution such as a Caesar cipher. Therefore, I
will plug in different assumed key lengths in the range of 4-6, hoping that one returns an IOC
score around 0.070, and this will be my candidate key length to solving question 4.

Credit for the theory: https://www.nku.edu/~christensen/1402%20Friedman%20test%202.pdf
Credit for the theory: https://www.dcode.fr/index-coincidence
Credit for calculating: https://youtu.be/9mEIbdebmY8

 */

import java.util.ArrayList;
import java.util.HashMap;

public class IOC {
    private String cipher;
    private ArrayList<HashMap<Character, Integer>> groups;

    public IOC(String cipher) {
        this.cipher = cipher;
        this.groups = new ArrayList<>();
    }

    /**
     * Initialise n groups (HashMaps) each containing an A-Z mapping and count of 0.
     *
     * @param list             the list to initialise.
     * @param assumedKeyLength the assumed key size.
     */
    private void initGroups(ArrayList<HashMap<Character, Integer>> list, int assumedKeyLength) {
        for (int i = 0; i < assumedKeyLength; i++) {
            list.add(new HashMap<>());
            for (char ch = 'A'; ch <= 'Z'; ++ch) {
                list.get(i).put(ch, 0);
            }
        }
    }

    /**
     * Populate the groups with the cipher text, incrementing the count if the mapping exists.
     *
     * @param cipher           the ciphertext to group.
     * @param list             the list to populate.
     * @param assumedKeyLength the assumed key length.
     */
    private void populateGroups(String cipher, ArrayList<HashMap<Character, Integer>> list, int assumedKeyLength) {
        for (int i = 0; i < cipher.length(); i++) {
            // Each group gets the i'th character from the cipher.
            HashMap<Character, Integer> group = groups.get(i % assumedKeyLength);
            Character c = cipher.charAt(i);
            if (group.containsKey(c)) {
                group.put(c, group.get(c) + 1);
            } else {
                group.put(c, 1);
            }
        }
    }

    /**
     * The driver method. Calculate the global Index of Coincidence.
     *
     * @param assumedKeyLength the assumed key length.
     * @return (double) the index of coincidence value; otherwise, -1.0.
     */
    public double calculateIC(int assumedKeyLength) {
        if (!(assumedKeyLength < 1)) {
            initGroups(this.groups, assumedKeyLength);
            populateGroups(this.cipher, this.groups, assumedKeyLength);

            double count = 0.0;
            // Calculate IC for each group and sum.
            for (HashMap<Character, Integer> group : groups) {
                count += calcIC(group);
            }
            int groupSize = groups.size();
            this.groups.clear();
            // return average of all groups.
            return count / groupSize;
        } else {
            return -1.0;
        }
    }

    /**
     * Auxiliary method to calculate IC for a group.
     * @param group the group to calculate.
     * @return the ic score for a group.
     */
    private double calcIC(HashMap<Character, Integer> group) {
        // Total characters in the group.
        Integer noOfLetters = group.values().stream().mapToInt(Integer::intValue).sum();

        // Sum: #a's * #a's - 1 + #b's * #b's - 1 + ...
        double sum = 0.0;
        for (char ch1 = 'A'; ch1 <= 'Z'; ++ch1) {
            double n = group.get(ch1);
            sum += (n * (n - 1));
        }
        double ic = (sum / (noOfLetters * (noOfLetters - 1)));
        return ic;
    }
}
