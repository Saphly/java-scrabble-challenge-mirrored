import java.util.ArrayList;
import java.util.Arrays;

class CharacterPoints {
    String characters;
    int point;

    public CharacterPoints(String characters, int point) {
        this.characters = characters;
        this.point = point;
    }
}


public class Scrabble {
    String scrabbleString;
    Character[] doubleLetters;
    Character[] tripleLetters;
    boolean doubleWord;
    boolean tripleWord;
    static final CharacterPoints[] charPoints = {
            new CharacterPoints("AEIOULNRST", 1),
            new CharacterPoints("DG", 2),
            new CharacterPoints("BCMP", 3),
            new CharacterPoints("FHVWY", 4),
            new CharacterPoints("K", 5),
            new CharacterPoints("JX", 8),
            new CharacterPoints("QZ", 10)
    };

    public Scrabble(String word) {
        this.scrabbleString = word;
    }

    public Scrabble(String word, Character[] doubleLetter, Character[] tripleLetter, boolean doubleWord, boolean tripleWord) {
        this.scrabbleString = word;
        this.doubleLetters = doubleLetter;
        this.tripleLetters = tripleLetter;
        this.doubleWord = doubleWord;
        this.tripleWord = tripleWord;
    }

    private boolean isNullOrEmpty() {
        if(scrabbleString == null || scrabbleString.trim().isEmpty()) {
            return true;
        }
        return false;
    };

    /**
     *
     * @param letter
     * @return array of points corresponding to each letter in the string
     */
    private int getPoints(char letter) {
        for(CharacterPoints charAndPoints : charPoints) {
            char upperCaseLetter = Character.toUpperCase(letter);

            if(charAndPoints.characters.indexOf(upperCaseLetter) != -1) {
                return charAndPoints.point;
            }
        }
        return 0;
    }

    /**
     *
     * @return array of first index of the character in the scrabbleString
     *
     *          if doubleLetters is null/length 0, return -1
     */
    private ArrayList<Integer> getDoubleLettersIndex() {
        if (doubleLetters == null || doubleLetters.length == 0 ) {
            return new ArrayList<>(Arrays.asList(-1));
        }
        ArrayList<Integer> indexes = new ArrayList<Integer> ();
        for(char c : doubleLetters) {
            indexes.add(scrabbleString.indexOf(c));
        }

        return indexes;
    };

    /**
     *
     * @return array of indexes of character in the scrabbleString
     *         if the letter is in doubleLetters, find the next index of the char in scrabbleString
     *         else, add the first index to array
     *
     *         if tripleLetter is not given / length is 0, return -1
     */
    private ArrayList<Integer> getTripleLettersIndex() {
        if (tripleLetters == null || tripleLetters.length == 0 ) {
            return new ArrayList<>(Arrays.asList(-1));
        }

        ArrayList<Integer> indexes = new ArrayList<Integer> ();

        for(char c : tripleLetters) {
            int firstInd = scrabbleString.indexOf(c);

            if(Arrays.stream(doubleLetters).anyMatch(letter -> letter == c)) {
                indexes.add(scrabbleString.indexOf(c, firstInd + 1));
                continue;
            }
            indexes.add(firstInd);
        }
        return indexes;
    }

    /**
     *
     * @return array of points corresponding to the position of the letters in scrabbleString
     */
    private ArrayList<Integer> stringToPoints() {
        ArrayList<Integer> eachCharPoint = new ArrayList<Integer>();

        for (int i = 0; i < scrabbleString.length(); i++) {
            eachCharPoint.add(getPoints(scrabbleString.charAt(i)));
        }
        return eachCharPoint;
    }

    private int calculateTotalWithBonuses() {
        ArrayList<Integer> stringToPoints = stringToPoints();
        ArrayList<Integer> doubleIndexes = getDoubleLettersIndex();
        ArrayList<Integer> tripleIndexes = getTripleLettersIndex();

        int total = 0;

        for (int i = 0; i < stringToPoints.size() ; i++) {
            if (!doubleIndexes.contains(-1) && doubleIndexes.contains(i)) {
                total += stringToPoints.get(i) * 2;
                continue;
            }
            if (!tripleIndexes.contains(-1) && tripleIndexes.contains(i)) {
                total += stringToPoints.get(i) * 3;
                continue;
            }

            total += stringToPoints.get(i);
        }

        return total;
    }


    public int score() {
        if(isNullOrEmpty()) return 0;

        scrabbleString = scrabbleString.toUpperCase();
        int totalPoint = calculateTotalWithBonuses();

        return doubleWord ? totalPoint * 2 : tripleWord ? totalPoint * 3 : totalPoint;
    }
}

