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

    private int getPoints(char letter) {
        for(CharacterPoints charAndPoints : charPoints) {
            char upperCaseLetter = Character.toUpperCase(letter);

            if(charAndPoints.characters.indexOf(upperCaseLetter) != -1) {
                return charAndPoints.point;
            }
        }
        return 0;
    }

    private boolean isNullOrEmpty() {
        if(scrabbleString == null || scrabbleString.trim().isEmpty()) {
            return true;
        }
        return false;
    };

    public int score() {
        if(isNullOrEmpty()) return 0;

        int totalPoint = 0;

        for (int i = 0; i < scrabbleString.length(); i++) {
            totalPoint += getPoints(scrabbleString.charAt(i));
        }

        return totalPoint;
    }
}

