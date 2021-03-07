package com.jamieghoorbin;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class VigenereDecrypt {
    private final int keyLength;
    private ArrayList<HashMap<Character, Double>> groups;
    private Map<Character, Double> englishLetterFreq;
    private String key;
    private String cipher;

    public VigenereDecrypt(String cipher, int keyLength) {
        this.keyLength = keyLength;
        this.groups = new ArrayList<>(keyLength);
        englishLetterFreq = new HashMap<>();
        this.cipher = cipher;
        this.key = "";
    }

    // Overload constructor when doing without key length. Key length is init when known.
    public VigenereDecrypt(String cipher, String key) {
        this.cipher = cipher;
        this.key = key;
        this.keyLength = key.length();
        this.groups = null;
        englishLetterFreq = null;
    }

    public void decryptKeyLengthKnown() {
        if (!(this.groups == null || this.englishLetterFreq == null)) {
            // Initialise n (keyLength) groups
            initGroups();
            populateGroups();
            populateEnglishLetterFreq();
            calculateFreqForGroups();
            calculateKey();
            printKey();
            decryptCipher(cipher, key);
        } else {
            System.err.println("Error: Unable to perform this operation...");
        }

    }

    public void decryptWithKey() {
        if (!getKey().equals("")) {
            printKey();
            decryptCipher(cipher, key);
        } else {
            System.err.println("Error: No key provided...");
        }

    }

    private void populateEnglishLetterFreq() {
        englishLetterFreq.put('A', 8.2);
        englishLetterFreq.put('B', 1.5);
        englishLetterFreq.put('C', 2.8);
        englishLetterFreq.put('D', 4.3);
        englishLetterFreq.put('E', 13.0);
        englishLetterFreq.put('F', 2.2);
        englishLetterFreq.put('G', 2.0);
        englishLetterFreq.put('H', 6.1);
        englishLetterFreq.put('I', 7.0);
        englishLetterFreq.put('J', 0.15);
        englishLetterFreq.put('K', 0.77);
        englishLetterFreq.put('L', 4.0);
        englishLetterFreq.put('M', 2.4);
        englishLetterFreq.put('N', 6.7);
        englishLetterFreq.put('O', 7.5);
        englishLetterFreq.put('P', 1.9);
        englishLetterFreq.put('Q', 0.095);
        englishLetterFreq.put('R', 6.0);
        englishLetterFreq.put('S', 6.3);
        englishLetterFreq.put('T', 9.1);
        englishLetterFreq.put('U', 2.8);
        englishLetterFreq.put('V', 0.98);
        englishLetterFreq.put('W', 2.4);
        englishLetterFreq.put('X', 0.15);
        englishLetterFreq.put('Y', 2.0);
        englishLetterFreq.put('Z', 0.074);
    }

    public HashMap<Character, Double> getGroup(int groupIndex) {
        return groups.get(groupIndex);
    }

    public void printGroup(int groupIndex) {
        getGroup(groupIndex).entrySet().forEach(entry -> {
            System.out.println(entry.getKey() + " " + entry.getValue());
        });
    }

    public void printMaxGroup(int groupIndex) {
        Character x = getGroup(groupIndex).entrySet().stream().max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1).get().getKey();
        System.out.println(x);
    }

    private void initGroups() {
        for (int i = 0; i < keyLength; i++) {
            groups.add(new HashMap<Character, Double>());
            for (char ch = 'A'; ch <= 'Z'; ++ch) {
                groups.get(i).put(ch, 0.0);
            }
        }
    }

    private void populateGroups() {
        for (int i = 0; i < getCipher().length(); i++) {
            HashMap<Character, Double> map = groups.get(i % keyLength);
            Character c = getCipher().charAt(i);
            if (map.containsKey(c)) {
                map.put(c, map.get(c) + 1);
            } else {
                map.put(c, 1.0);
            }
        }
    }

    private void calculateFreqForGroups() {
        for (int i = 0; i < keyLength; i++) {
            HashMap<Character, Double> group = groups.get(i);
            Double sumOfGroupValues = group.values().stream().reduce(0.0, Double::sum);
            for (char ch = 'A'; ch <= 'Z'; ++ch) {
                Double freqForCipher = group.get(ch);
                if (freqForCipher != null) {
                    Double calcFreqToAdd = (freqForCipher / sumOfGroupValues);
                    group.replace(ch, calcFreqToAdd);
                } else {
                    System.err.println("Error: character not in group...");
                }
            }
        }
    }

    private void calculateKey() {
        StringBuilder keySb = new StringBuilder();
        ArrayList<Double> tempList = new ArrayList<>();
        Double count = 0.0;

        for (int i = 0; i < keyLength; i++) { // 6 groups
            HashMap<Character, Double> group = groups.get(i);
            for (char ch1 = 'A'; ch1 <= 'Z'; ++ch1) {
                for (char ch2 = 'A'; ch2 <= 'Z'; ++ch2) { // shift
                    Double engFreqVal = englishLetterFreq.get(ch2);
                    Double freqVal = group.get((char) (((ch1 + ch2) % 26) + 65));
                    if (engFreqVal != null && freqVal != null) {
                        count += engFreqVal * freqVal;
                    } else {
                        System.err.println("Error: character not in group...");
                    }
                }
                tempList.add(count);
                count = 0.0;
            }
            // Get the highest product from the shift calculation.
            Integer indexOfMaxValue = IntStream.range(0, tempList.size()).boxed()
                    .max(Comparator.comparing(tempList::get)).orElse(-1);

            keySb.append((char) (indexOfMaxValue + 65));
            // clear arrayList and do next group
            tempList.clear(); // empty arraylist
        }
        updateKey(keySb.toString());
    }

    private void printKey() {
        System.out.println("Key: " + key);
    }

    private void decryptCipher(String ciphertext, String key) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < ciphertext.length(); i++) {
            // Using floorMod as Java returns remainder not mod on negative calculations.
            int cipherModKey = Math.floorMod(ciphertext.charAt(i) - key.charAt(i % key.length()), 26);
            String s = String.valueOf((char) (cipherModKey + 'A'));
            sb.append(s);
        }
        System.out.println("Decrypted ciphertext: " + sb.toString());
    }

    public String getCipher() {
        return cipher;
    }

    public String getKey() {
        return key;
    }

    private void updateKey(String key) {
        this.key = key;
    }
}