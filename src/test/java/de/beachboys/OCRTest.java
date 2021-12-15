package de.beachboys;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class OCRTest {

    @Test
    public void testAllCharactersHeightSix() {
        String input =  " **  ***   **  **** ****  **  *  *  ***   ** *  * *     **  ***  ***   *** *  * *   *****\n" +
                        "*  * *  * *  * *    *    *  * *  *   *     * * *  *    *  * *  * *  * *    *  * *   *   *\n" +
                        "*  * ***  *    ***  ***  *    ****   *     * **   *    *  * *  * *  * *    *  *  * *   * \n" +
                        "**** *  * *    *    *    * ** *  *   *     * * *  *    *  * ***  ***   **  *  *   *   *  \n" +
                        "*  * *  * *  * *    *    *  * *  *   *  *  * * *  *    *  * *    * *     * *  *   *  *   \n" +
                        "*  * ***   **  **** *     *** *  *  ***  **  *  * ****  **  *    *  * ***   **    *  ****";
        Assertions.assertEquals("ABCEFGHIJKLOPRSUYZ", OCR.runOCR(input));
    }

    @Test
    public void testAllCharactersHeightTen() {
        String input =  "  **    *****    ****   ******  ******   ****   *    *     ***  *    *  *       *    *  *****   *****   *    *  ******\n" +
                        " *  *   *    *  *    *  *       *       *    *  *    *      *   *   *   *       **   *  *    *  *    *  *    *       *\n" +
                        "*    *  *    *  *       *       *       *       *    *      *   *  *    *       **   *  *    *  *    *   *  *        *\n" +
                        "*    *  *    *  *       *       *       *       *    *      *   * *     *       * *  *  *    *  *    *   *  *       * \n" +
                        "*    *  *****   *       *****   *****   *       ******      *   **      *       * *  *  *****   *****     **       *  \n" +
                        "******  *    *  *       *       *       *  ***  *    *      *   **      *       *  * *  *       *  *      **      *   \n" +
                        "*    *  *    *  *       *       *       *    *  *    *      *   * *     *       *  * *  *       *   *    *  *    *    \n" +
                        "*    *  *    *  *       *       *       *    *  *    *  *   *   *  *    *       *   **  *       *   *    *  *   *     \n" +
                        "*    *  *    *  *    *  *       *       *   **  *    *  *   *   *   *   *       *   **  *       *    *  *    *  *     \n" +
                        "*    *  *****    ****   ******  *        *** *  *    *   ***    *    *  ******  *    *  *       *    *  *    *  ******";
        Assertions.assertEquals("ABCEFGHJKLNPRXZ", OCR.runOCR(input));
    }

    @Test
    public void testRandomStringHeightSix() {
        String input =  "**** **** **** *   **  * **** ***  ****  ***   ** \n" +
                        "*    *    *    *   ** *  *    *  * *      *     * \n" +
                        "***  ***  ***   * * **   ***  *  * ***    *     * \n" +
                        "*    *    *      *  * *  *    ***  *      *     * \n" +
                        "*    *    *      *  * *  *    * *  *      *  *  * \n" +
                        "**** *    ****   *  *  * *    *  * *     ***  **  ";
        Assertions.assertEquals("EFEYKFRFIJ", OCR.runOCR(input));
    }

    @Test
    public void testRandomStringHeightTen() {
        String input =  "*****   ******   ****   *       *****   *    *  ******  ******\n" +
                        "*    *  *       *    *  *       *    *  **   *       *  *     \n" +
                        "*    *  *       *       *       *    *  **   *       *  *     \n" +
                        "*    *  *       *       *       *    *  * *  *      *   *     \n" +
                        "*****   *****   *       *       *****   * *  *     *    ***** \n" +
                        "*  *    *       *       *       *  *    *  * *    *     *     \n" +
                        "*   *   *       *       *       *   *   *  * *   *      *     \n" +
                        "*   *   *       *       *       *   *   *   **  *       *     \n" +
                        "*    *  *       *    *  *       *    *  *   **  *       *     \n" +
                        "*    *  ******   ****   ******  *    *  *    *  ******  ******";
        Assertions.assertEquals("RECLRNZE", OCR.runOCR(input));
    }

}
