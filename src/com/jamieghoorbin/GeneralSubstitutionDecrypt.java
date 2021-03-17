package com.jamieghoorbin;

import java.util.*;
import java.util.stream.Collectors;

public class GeneralSubstitutionDecrypt {
    String cipher;
    private HashMap<Character, Integer> letterCount;
    private Map<Character, Double> englishLetterFreq;
    private Map<Character, Character> characterMapping;


    public GeneralSubstitutionDecrypt(String cipher) {
        this.cipher = cipher;
        this.letterCount = new HashMap<>();
        this.englishLetterFreq = new HashMap<>();
        this.characterMapping = new HashMap<>();
    }

    private void setup() {
        initMap();
        countLetterOccurrence(cipher);
        populateEnglishLetterFreq();
        populateCharacterMapping();
    }

    public void test() {
        // Initial setup
        setup();



        // Cracking the encrypted text.

        // A threshold of 4 produced words such as TAE which indicates 3 is the best threshold.
        updateCharacterMappingWithThreshold(3);
        // |*| Can be A or I. 'A' seems to fit the bill :)
        updateSingleCharacterMapping('T', 'A');
        // |T*E|, tried replacing * with H to form THE
        updateSingleCharacterMapping('V', 'H');
        // |HA*|, tried replacing * with D to form HAD
        updateSingleCharacterMapping('C', 'D');
        // |A*D|, tried replacing * with N to form AND
        updateSingleCharacterMapping('Z', 'N');
        // |THE*E| |HE*E|, tried placing * with R to form THERE HERE
        updateSingleCharacterMapping('Q', 'R');
        // |HEA*EN|, guessing its the word HEAVEN
        updateSingleCharacterMapping('O', 'V');
        // |*H**DREN|, guessing its the word children, which would mean |*ENTRE| becomes CENTRE
        updateSingleCharacterMapping('S', 'C');
        updateSingleCharacterMapping('P', 'I');
        updateSingleCharacterMapping('J', 'L');
        // Assumption of CHILDREN is correct since |*EARNT| became |LEARNT| and |*T| became |IT|
        // Many word can now be guessed
        // |C*NTIN*ED|, guessing its the word 'continued'
        updateSingleCharacterMapping('R', 'O');
        updateSingleCharacterMapping('E', 'U');
        // Since R has been discovered, I guess |*ELIEVE| is the word 'believe' and not 'relieve'
        updateSingleCharacterMapping('G', 'B');
        // |*CHOOL|, guessing this is the word 'school', and |*ECOND| would then become 'second'
        updateSingleCharacterMapping('K', 'S');
        // |*HAT| |*ITHOUT| leads me to believe H is W
        updateSingleCharacterMapping('H', 'W');
        // |STREN*THENED| M is G
        updateSingleCharacterMapping('M', 'G');
        // |SU**ER| |GRIE*| |*OUR| D is F
        updateSingleCharacterMapping('D', 'F');
        // |*UESTION| B is Q
        updateSingleCharacterMapping('B', 'Q');
        // |FLIC*ERING| W is K
        updateSingleCharacterMapping('W', 'K');
        // |*AIN| |*ERSONS| |*ART| U is P
        updateSingleCharacterMapping('U', 'P');
        // |ONL*| F is Y
        updateSingleCharacterMapping('F', 'Y');
        // |*ISTAKE| |*EET| L is M
        updateSingleCharacterMapping('L', 'M');

        // A, N, X are unsolved since they do not appear in the encrypted sample text.
        // They map to J X Z but more sample text would be needed to map these characters.


        System.out.println(characterMapping.toString());

        // Print out text using mapping.
        decrypt();
    }

    private void updateSingleCharacterMapping(Character oldValue, Character newValue) {
        characterMapping.replace(oldValue, newValue);
    }

    /**
     * Sorts the englishLetterFrequency and encrypted text by value (descending), mapping the highest English
     * letter frequency to the highest encrypted text count.
     * The threshold limit assigns the first n characters to the encrypted/plain text mapping.
     * @param threshold the threshold limit.
     */
    private void updateCharacterMappingWithThreshold(int threshold) {
        // add limit for threshold
        if (threshold < 0 || threshold > 27) {
            threshold = 27;
        }

        // sort engLetterFreq by val, descending and store into a List
        List<Character> orderedEnglishLetterFreqKeyOnly =
                englishLetterFreq.entrySet().stream()
                        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                        .limit(threshold)
                        .map(Map.Entry::getKey)
                        .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);

        // sort letterCount by val, descending and store into a List
        List<Character> orderedLetterCountKeyOnly =
                letterCount.entrySet().stream()
                        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                        .limit(threshold)
                        .map(Map.Entry::getKey)
                        .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);

        // based on threshold get characterMapping key using letterCount key and update
        // with engLetterFreq key as characterMapping value.
        for (int i = 0; i < orderedLetterCountKeyOnly.size(); i++) {
            characterMapping.replace(orderedLetterCountKeyOnly.get(i), orderedEnglishLetterFreqKeyOnly.get(i));
        }
    }

    /**
     * Prints the cipher using the characterMapping.
     */
    private void decrypt() {
        System.out.println(cipher);
        cipher.chars().forEach(ch -> System.out.print(characterMapping.get((char) ch)));
        System.out.println();
    }

    /**
     * Populates the character map with A - Z values as keys and '*' as unknowns.
     */
    private void populateCharacterMapping() {
        characterMapping.put('A','*' );
        characterMapping.put('B','*' );
        characterMapping.put('C','*' );
        characterMapping.put('D','*' );
        characterMapping.put('E','*' );
        characterMapping.put('F','*' );
        characterMapping.put('G','*' );
        characterMapping.put('H','*' );
        characterMapping.put('I','*' );
        characterMapping.put('J','*' );
        characterMapping.put('K','*' );
        characterMapping.put('L','*' );
        characterMapping.put('M','*' );
        characterMapping.put('N','*' );
        characterMapping.put('O','*' );
        characterMapping.put('P','*' );
        characterMapping.put('Q','*' );
        characterMapping.put('R','*' );
        characterMapping.put('S','*' );
        characterMapping.put('T','*' );
        characterMapping.put('U','*' );
        characterMapping.put('V','*' );
        characterMapping.put('W','*' );
        characterMapping.put('X','*' );
        characterMapping.put('Y','*' );
        characterMapping.put('Z','*' );
        characterMapping.put('|','*' );
    }

    /**
     * Initialise the map with an A-Z mapping and count.
     */
    private void initMap() {
            for (char ch = 'A'; ch <= 'Z'; ++ch) {
                letterCount.put(ch, 0);
            }
            letterCount.put('|', 0);
    }

    /**
     * Populates the map of english letter frequencies.
     * Values obtained from: https://en.wikipedia.org/wiki/Letter_frequency
     */
    private void populateEnglishLetterFreq() {
        // Assumption that space (pipe |) will carry highest freq.
        englishLetterFreq.put('|', Double.valueOf(Integer.MAX_VALUE));
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

    /**
     * Initialise the map with an A-Z mapping and count.
     * @param cipher the encrypted text/cipher text.
     */
    private void countLetterOccurrence(String cipher) {
        for (int i = 0; i < cipher.length(); i++) {
            if (!letterCount.containsKey(cipher.charAt(i))) {
                letterCount.put(cipher.charAt(i), 1);
            } else {
                letterCount.put(cipher.charAt(i), letterCount.get(cipher.charAt(i)) +1);
            }
        }
    }
}
