package com.jamieghoorbin;

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
     * Initialise n groups each containing an A-Z mapping and count.
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
     * Populate the groups of n size and distribute the cipher text.
     *
     * @param cipher           the ciphertext to group.
     * @param list             the list to populate.
     * @param assumedKeyLength the assumed key length.
     */
    private void populateGroups(String cipher, ArrayList<HashMap<Character, Integer>> list, int assumedKeyLength) {
        for (int i = 0; i < cipher.length(); i++) {
            HashMap<Character, Integer> map = groups.get(i % assumedKeyLength);
            Character c = cipher.charAt(i);
            if (map.containsKey(c)) {
                map.put(c, map.get(c) + 1);
            } else {
                map.put(c, 1);
            }
        }
    }

    /**
     * Calculate the Index of Coincidence.
     *
     * @param assumedKeyLength the assumed key length.
     * @return (double) the index of coincidence value.
     */
    public double calculateIC(int assumedKeyLength) {
        if (!(assumedKeyLength < 1)) {
            initGroups(this.groups, assumedKeyLength);
            populateGroups(this.cipher, this.groups, assumedKeyLength);

            double count = 0.0;
            for (HashMap<Character, Integer> group : groups) {
                count += calcIC(group);
            }
            int groupSize = groups.size();
            this.groups.clear();

            return count / groupSize;
        } else {
            return -1.0;
        }
    }

    private double calcIC(HashMap<Character, Integer> map) {
        double sum = 0.0;
        Integer noOfLetters = map.values().stream().mapToInt(Integer::intValue).sum();

        for (char ch1 = 'A'; ch1 <= 'Z'; ++ch1) {
            double n = map.get(ch1);
            sum += (n * (n - 1));
        }
        double ic = (sum / (noOfLetters * (noOfLetters - 1)));
        return ic;
    }
}
