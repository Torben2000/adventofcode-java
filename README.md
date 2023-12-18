# Advent of Code
[![CircleCI build status](https://circleci.com/gh/Torben2000/adventofcode-java.svg?style=shield)](https://circleci.com/gh/Torben2000/adventofcode-java)

## Disclaimer
- Code might be ugly as it is just there to solve the puzzles of https://adventofcode.com/. :-)
- Code for "early" puzzles might evolve over time because later puzzles sometimes require refactoring for reusability or
  add some additional features to existing code

## What is in here (besides the implemented puzzle solutions)
- General framework for every day incl. unit tests
- Testing includes additional input & output values, not just the method parameters + return value
- Everything (except input) is quite type insensitive to avoid unnecessary conversions
- Command line tool to 
    - try out different inputs for the current puzzle
    - download your puzzle input
    - run your logic with your puzzle input
- Tuples (Pair, Triplet, ...)
- JSON parsing/writing etc. via json.org lib
- Simple CSV parsing (to String/int/long)
- Direction enums to help with navigating using square or hex fields
- Running lots of repeating state manipulations with speed optimization via cycle detection
- Painting and parsing of 2D ASCII images with a few different possibilities
- Calculating polygon sizes and line lengths
- "OCR" for these images that supports 6 and 10 pixel high fonts (see [details](src/main/resources/ocr/README.md))
- Math util functions for least common multiple and greatest common divisor
- Math util functions for chinese remainder theorem
- Graph support
    - Integrate JGraphT
    - Creation from 2D maps
    - Export to DOT to render it
- Support of multiple years
- Command line tool to
    - add support for other years
    - reset your implementation for a desired year to start from scratch

The general structure is based on https://github.com/dave-burke/advent-of-code-java-starter/

## Existing solutions within this repo
- Every solution for the years 2015-2022
- Solutions for 2023 up to today (or sometimes yesterday)
