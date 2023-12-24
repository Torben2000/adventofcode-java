package de.beachboys;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OCR {

    private static final Map<Integer, OCRConfig> configs = new HashMap<>();

    static {
        try {
            configs.put(6, new OCRConfig(6, 4, 1, "6.txt"));
            configs.put(10, new OCRConfig(10, 6, 2, "10.txt"));
        } catch (IOException | URISyntaxException e) {
            // don't fail here, just show the exception
            e.printStackTrace();
        }
    }

    public static String runOCR(String paintedString) {
        String[] lines = paintedString.split("\n");
        OCRConfig config = configs.get(lines.length);
        if (config == null) {
            throw new IllegalArgumentException("invalid character height: " + lines.length);
        }
        int lineLength = lines[0].length();
        if (lineLength % config.getCharacterPlusSpaceWidth() != config.getCharacterWidth() && lineLength % config.getCharacterPlusSpaceWidth() != 0) {
            throw new IllegalArgumentException("invalid line length: " + lineLength);
        }

        StringBuilder returnValue = new StringBuilder();
        for (int i = 0; i < (lineLength + config.getSpaceWidth()) / config.getCharacterPlusSpaceWidth(); i++) {
            int finalI = i;
            String characterRepresentation = Arrays.stream(lines)
                    .map(line -> line.substring(
                            finalI * config.getCharacterPlusSpaceWidth(),
                            finalI * config.getCharacterPlusSpaceWidth() + config.getCharacterWidth()))
                    .collect(Collectors.joining("\n"));
            String character = config.getCharacter(characterRepresentation);
            if (character == null) {
                throw new IllegalArgumentException("Invalid character: " + characterRepresentation);
            }
            returnValue.append(character);
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

    private static class OCRConfig {

        private final Map<String, String> characters = new HashMap<>();

        private final int characterWidth;
        private final int spaceWidth;
        private final int height;

        public OCRConfig(int height, int characterWidth, int spaceWidth, String mappingFileName) throws IOException, URISyntaxException {
            this.characterWidth = characterWidth;
            this.spaceWidth = spaceWidth;
            this.height = height;

            try (Stream<String> f = Files.lines(Path.of(Objects.requireNonNull(getClass().getClassLoader().getResource("ocr/" + mappingFileName)).toURI()))) {
                List<String> mapping = f.toList();
                int currentLineIndex = 0;
                while (currentLineIndex < mapping.size()) {
                    StringBuilder characterRepresentation = new StringBuilder();
                    for (int i = 0; i < height; i++) {
                        if (!characterRepresentation.isEmpty()) {
                            characterRepresentation.append("\n");
                        }
                        characterRepresentation.append(String.format("%-" + characterWidth + "s", mapping.get(currentLineIndex + i)));
                    }
                    currentLineIndex += height;
                    characters.put(characterRepresentation.toString(), mapping.get(currentLineIndex));
                    currentLineIndex += 2;
                }
            }
        }

        public int getCharacterWidth() {
            return characterWidth;
        }

        public int getSpaceWidth() {
            return spaceWidth;
        }

        public int getCharacterPlusSpaceWidth() {
            return characterWidth + spaceWidth;
        }

        public int getHeight() {
            return height;
        }

        public String getCharacter(String characterRepresentation) {
            return characters.get(characterRepresentation);
        }

    }

}
