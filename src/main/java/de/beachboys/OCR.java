package de.beachboys;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
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
        StringBuilder returnValue = new StringBuilder();
        String[] lines = paintedString.split("\n");
        for (int i = 0; i <= lines[0].length() / 5; i++) {
            int finalI = i;
            String character = Arrays.stream(lines).map(line -> line.substring(finalI * 5, finalI * 5 + 4)).collect(Collectors.joining("\n"));
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
        return runOCRAndReturnOriginalOnError(paintedString.replaceAll(dotRepresentation, "*"));
    }

}
