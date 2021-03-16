package com.jamieghoorbin;

 /*
  A Transposition decryption class for solving questions 5 and 6 of the
  "CO634 Cryptography Assignment". The class is capable of decrypting
  uppercase ciphertext when the key is known and when the key is unknown.

 Bigram data obtained from: http://norvig.com/mayzner.html
 */

import java.util.*;

public class TranspositionDecrypt {
    String cipher;
    HashMap<String, Double> bigramMap;

    public TranspositionDecrypt(String cipher) {
        this.cipher = cipher;
        this.bigramMap = new HashMap<>();
        initBigramMap();
    }

    /**
     * Initialise the bigram frequency map.
     */
    private void initBigramMap() {
        bigramMap.put("TH", 3.56);
        bigramMap.put("HE", 3.07);
        bigramMap.put("IN", 2.43);
        bigramMap.put("ER", 2.05);
        bigramMap.put("AN", 1.99);
        bigramMap.put("RE", 1.85);
        bigramMap.put("ON", 1.76);
        bigramMap.put("AT", 1.49);
        bigramMap.put("EN", 1.45);
        bigramMap.put("ND", 1.35);
        bigramMap.put("TI", 1.34);
        bigramMap.put("ES", 1.34);
        bigramMap.put("OR", 1.28);
        bigramMap.put("TE", 1.20);
        bigramMap.put("OF", 1.17);
        bigramMap.put("ED", 1.17);
        bigramMap.put("IS", 1.13);
        bigramMap.put("IT", 1.12);
        bigramMap.put("AL", 1.09);
        bigramMap.put("AR", 1.07);
        bigramMap.put("ST", 1.05);
        bigramMap.put("TO", 1.04);
        bigramMap.put("NT", 1.04);
        bigramMap.put("NG", 0.95);
        bigramMap.put("SE", 0.93);
        bigramMap.put("HA", 0.93);
        bigramMap.put("AS", 0.87);
        bigramMap.put("OU", 0.87);
        bigramMap.put("IO", 0.83);
        bigramMap.put("LE", 0.83);
        bigramMap.put("VE", 0.83);
        bigramMap.put("CO", 0.79);
        bigramMap.put("ME", 0.79);
        bigramMap.put("DE", 0.76);
        bigramMap.put("HI", 0.76);
        bigramMap.put("RI", 0.73);
        bigramMap.put("RO", 0.73);
        bigramMap.put("IC", 0.70);
        bigramMap.put("NE", 0.69);
        bigramMap.put("EA", 0.69);
        bigramMap.put("RA", 0.69);
        bigramMap.put("CE", 0.65);
        bigramMap.put("LI", 0.62);
        bigramMap.put("CH", 0.60);
        bigramMap.put("LL", 0.58);
        bigramMap.put("BE", 0.58);
        bigramMap.put("MA", 0.57);
        bigramMap.put("SI", 0.55);
        bigramMap.put("OM", 0.55);
        bigramMap.put("UR", 0.54);
    }

    /**
     *
     * Split a string from the beginning index (inclusive) of a given length (exclusive).
     * @param str the string to split.
     * @param stringLen the ending index (exclusive).
     * @return a string array of size 2 containing the substring string and the rest.
     */
    private String[] substringAndTheRest(String str, int stringLen) {
        // Defensive approach in the case of IndexOutOfBoundsException.
        if(str.length() <= stringLen) {
            return new String[]{str, ""};
        }
        return new String[]{str.substring(0, stringLen), str.substring(stringLen)};
    }

    /**
     * Returns the ciphertext.
     * @return the ciphertext.
     */
    public String getCipher() {
        return cipher;
    }

    /**
     * The driver code to decrypt the ciphertext.
     * @param noOfColumns the number of columns used to encrypt the text.
     * @return the key and decrypted text.
     */
    public String[] decrypt(int noOfColumns) {
        ArrayList<String> columnsOfText = new ArrayList<>();
        HashMap<Pair<Integer,Integer>, Double> scores = new HashMap<>();
        populateGroups(noOfColumns, columnsOfText);
        permuteAndScoreColumns(noOfColumns, columnsOfText, scores);

        String key = formatKey(noOfColumns, scores);
        String text = decryptWithKey(key, columnsOfText);

        return new String[]{key, text};
    }

    /**
     * Decrypts the text with the given key.
     * @param key the key.
     * @param columnsOfText a list containing the each column of text.
     * @return the decrypted text.
     */
    private String decryptWithKey(String key, ArrayList<String> columnsOfText) {
        int longestColumn = 0;
        for(String str: columnsOfText) {
            if (str.length() > longestColumn) {
                longestColumn = str.length();
            }
        }
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < longestColumn; i++) {
            for(int j = 0; j < key.length(); j++) {
                int x = Integer.parseInt(String.valueOf(key.charAt(j)));
                String str = columnsOfText.get(x);
                if(j <= str.length()) {
                    sb.append(str.charAt(i));
                }
            }
        }
        return sb.toString();
    }

    /**
     * Permutes each column of encrypted text with another column and scores based on a column pair forming a bigram.
     * @param noOfColumns the number of columns used.
     * @param columnsOfText the columns of ciphertext.
     * @param scores the score map - stores column pair (index) and the associated score.
     */
    private void permuteAndScoreColumns(int noOfColumns, ArrayList<String> columnsOfText, HashMap<Pair<Integer,Integer>, Double> scores) {
        // Take columnsOfText and pass to method that returns the highest permute score

        for (int i = 0; i < noOfColumns; i++) {
            Pair<Integer, Integer> tempPair = new Pair<>(0 ,0);
            Double tempScore = 0.0;
            for (int j = 0; j < noOfColumns; j++) {
                if (!(i==j)) {
                    double sc = scoreColumnPair(columnsOfText.get(i), columnsOfText.get(j));
                    // check for highest, add to scores (and clear temp?)
                    if (sc > tempScore) {
                        tempPair.setLeft(i);
                        tempPair.setRight(j);
                        tempScore = sc;
                    }
                }
            }
            scores.put(new Pair<>(tempPair.getLeft(), tempPair.getRight()), tempScore);
        }
    }

    /**
     * Forms a key using the scored column pair.
     * Note to oneself: this algorithm needs improving, for example when a key can not be formed.
     * @param noOfColumns the number of columns.
     * @param scores the scores map.
     * @return the key.
     */
    private String formatKey(int noOfColumns, HashMap<Pair<Integer,Integer>, Double> scores) {
        // List to store Key Pair in string format.
        LinkedList<String> keys = new LinkedList<>();

        // Sort by score and add the keys in string format to the (linked)list.
        scores.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(e -> keys.add(e.getKey().toString()));

        StringBuilder _result = new StringBuilder();
        // remove first key and add to string builder.
        _result.append(keys.remove());
        // remove last item - left for now, can be used to check end key value.
        String last = String.valueOf(keys.removeLast().charAt(0));

        while (_result.length() != noOfColumns) {
            String second = keys.remove();

            if (_result.charAt(0) == second.charAt(1)) { // outer match
                _result.insert(0, second.charAt(0));
            } else if (_result.charAt(_result.length() - 1) == second.charAt(0)) { // inner match
                _result.append(second.charAt(1));
            } else {
                keys.add(second); // otherwise add back the key to the end of list.
            }
        }
        return _result.toString();
    }

    /**
     * Business logic of dividing the ciphertext and populating the columns.
     * @param noOfColumns the number of columns to divide the string by.
     * @param list the list to populate.
     */
    private void populateGroups(int noOfColumns, ArrayList<String> list) {
        // e.g. noOfColumns = 5 & cipherLen = 31
        String cipherText = getCipher();
        final int cipherLen = getCipher().length(); // 31
        int oddCols = cipherLen%noOfColumns; // 31 mod 5 = 1, therefore 1 odd col
        int equalCols = ((cipherLen - (cipherLen%noOfColumns))/noOfColumns); // 31 - (31 mod 5) = 30
        boolean equallyDivides = (oddCols)==0; // false

        for(int col = 0; col < noOfColumns; col++) {
            if(equallyDivides) {
                // split equally
                String[] stringAndTheRest = substringAndTheRest(cipherText, equalCols);
                cipherText = stringAndTheRest[1];
                list.add(stringAndTheRest[0]);
                // could do string.split by number of columns and loop and add.
            } else {
                // do imbalanced/odd case
                String[] stringAndTheRest = substringAndTheRest(cipherText, equalCols+1);
                cipherText = stringAndTheRest[1];
                list.add(stringAndTheRest[0]);
                oddCols--;
                if(oddCols == 0) {
                    equallyDivides = true;
                }
            }
        }
    }

    /**
     * Iterates through the given strings in parallel and increase the score if
     * the combined string of a row is present in the bigramMap.
     * @param s1 string one.
     * @param s2 string two.
     * @return the score.
     */
    private double scoreColumnPair(String s1, String s2) {
        double score = 0;
        // Get the shortest string length - a unigram need not be scored.
        int shortestColumnLen = 0;
        if(s1.length() <= s2.length()) {
            shortestColumnLen = s1.length();
        } else {
            shortestColumnLen = s2.length();
        }
        // Loop over both strings
        // score does not increment if char in one row out of bounds.
        for (int i = 0; i < shortestColumnLen; i++) {
            Character s1Row = s1.charAt(i);
            Character s2Row = s2.charAt(i);
            if (!(s1Row == null && s2Row == null)) { // needed ???? since char at would throw error?
                Double val = bigramMap.get(String.valueOf(s1Row) + s2Row);
                if (val != null) {
                    score += val;
                }
            }
        }
        return score;
    }


}
