package de.beachboys;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class OCR {

    private static final Map<String, String> characters = new HashMap<>();

    static {
        characters.put(" ** \n" +
                "*  *\n" +
                "*  *\n" +
                "****\n" +
                "*  *\n" +
                "*  *", "A");
        characters.put("*** \n" +
                "*  *\n" +
                "*** \n" +
                "*  *\n" +
                "*  *\n" +
                "*** ", "B");
        characters.put(" ** \n" +
                "*  *\n" +
                "*   \n" +
                "*   \n" +
                "*  *\n" +
                " ** ", "C");
        characters.put("****\n" +
                "*   \n" +
                "*** \n" +
                "*   \n" +
                "*   \n" +
                "****", "E");
        characters.put("****\n" +
                "*   \n" +
                "*** \n" +
                "*   \n" +
                "*   \n" +
                "*   ", "F");
        characters.put(" ** \n" +
                "*  *\n" +
                "*   \n" +
                "* **\n" +
                "*  *\n" +
                " ***", "G");
        characters.put("*  *\n" +
                "*  *\n" +
                "****\n" +
                "*  *\n" +
                "*  *\n" +
                "*  *", "H");
        characters.put(" ***\n" +
                "  * \n" +
                "  * \n" +
                "  * \n" +
                "  * \n" +
                " ***", "I");
        characters.put("  **\n" +
                "   *\n" +
                "   *\n" +
                "   *\n" +
                "*  *\n" +
                " ** ", "J");
        characters.put("*  *\n" +
                "* * \n" +
                "**  \n" +
                "* * \n" +
                "* * \n" +
                "*  *", "K");
        characters.put("*   \n" +
                "*   \n" +
                "*   \n" +
                "*   \n" +
                "*   \n" +
                "****", "L");
        characters.put(" ** \n" +
                "*  *\n" +
                "*  *\n" +
                "*  *\n" +
                "*  *\n" +
                " ** ", "O");
        characters.put("*** \n" +
                "*  *\n" +
                "*  *\n" +
                "*** \n" +
                "*   \n" +
                "*   ", "P");
        characters.put("*** \n" +
                "*  *\n" +
                "*  *\n" +
                "*** \n" +
                "* * \n" +
                "*  *", "R");
        characters.put(" ***\n" +
                "*   \n" +
                "*   \n" +
                " ** \n" +
                "   *\n" +
                "*** ", "S");
        characters.put("*  *\n" +
                "*  *\n" +
                "*  *\n" +
                "*  *\n" +
                "*  *\n" +
                " ** ", "U");
        characters.put("*   \n" +
                "*   \n" +
                " * *\n" +
                "  * \n" +
                "  * \n" +
                "  * ", "Y");
        characters.put("****\n" +
                "   *\n" +
                "  * \n" +
                " *  \n" +
                "*   \n" +
                "****", "Z");
    }

    public static String runOCR(String paintedString) {
        int characterWidth = 4;
        int spaceWidth = 1;
        int characterPlusSpaceWidth = characterWidth + spaceWidth;
        StringBuilder returnValue = new StringBuilder();
        String[] lines = paintedString.split("\n");
        int lineLength = lines[0].length();
        if (lineLength % characterPlusSpaceWidth != characterWidth && lineLength % characterPlusSpaceWidth != 0) {
            throw new IllegalArgumentException("invalid line length: " + lineLength);
        }
        for (int i = 0; i < (lineLength + spaceWidth) / characterPlusSpaceWidth; i++) {
            int finalI = i;
            String character = Arrays.stream(lines).map(line -> line.substring(finalI * characterPlusSpaceWidth, finalI * characterPlusSpaceWidth + characterWidth)).collect(Collectors.joining("\n"));
            if (!characters.containsKey(character)) {
                throw new IllegalArgumentException("Invalid character: " + character);
            }
            returnValue.append(characters.get(character));
        }
        return returnValue.toString();
    }

    public static String runOCRAndReturnOriginalOnError(String paintedString) {
        try {
            return runOCR(paintedString);
        } catch (IllegalArgumentException e) {
            return paintedString;
        }
    }

    public static String runOCRAndReturnDotVersionOnError(String paintedString, String dotRepresentation) {
        return runOCRAndReturnOriginalOnError(paintedString.replaceAll(Pattern.quote(dotRepresentation), "*").replaceAll("[^*^\\n]", " "));
    }

}
