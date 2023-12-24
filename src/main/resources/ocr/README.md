# "OCR"

This folder contains mappings how to automatically "read" letters that are represented by pixels.
This reading is necessary in several puzzles and might sometimes be hard (especially if you are using strange pixel
representations).
In theory, it could even be much simpler to solve some puzzles because no manual check is needed (2018-10 went into
this direction, although it wouldn't have been applicable because of the bad test data in the puzzle description).
This whole thing is heavily inspired by https://github.com/bsoyka/advent-of-code-ocr.

The mappings include both fonts ever used in Advent of Code.

## 6.txt
Includes the mapping for the font with letters that are 6 pixels high and 4 pixels wide.
The spacing between the letters is 1 pixel.

Special case: "Y" is 5 pixels wide and does not have a spacing pixel afterward, but the mapping just contains the first
4 columns as they are enough to recognize the pattern and make handling much simpler.
Just for reference: The correct pattern is
```txt
*   *
*   *
 * * 
  *  
  *  
  *  
```
Included letters are ```ABCEFGHIJKLOPRSUYZ```. The other letters did not appear in any case that I analyzed. Also, 
https://github.com/bsoyka/advent-of-code-ocr does not mention any other letters.

This is the correct font for days 2016-8, 2016-11, 2019-11, 2021-13 and 2022-10.

## 10.txt
Includes the mapping for the font with letters that are 10 pixels high and 6 pixels wide.
The spacing between the letters is 2 pixels.

Included letters are ```ABCEFGHJKLNPRXZ```. The other letters did not appear in any case that I analyzed.

This is the correct font for day 2018-10.

## Usage
Write your own parser in the language of your choice and create a mapping between the pixels and the real letter.
If you want to use (or be inspired by) my [Java implementation](../../java/de/beachboys/OCR.java), feel free to just 
steal it, it works independently of my other stuff.
For a Python implementation, it might be worth checking out https://github.com/bsoyka/advent-of-code-ocr.
