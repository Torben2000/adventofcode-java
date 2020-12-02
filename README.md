# Advent of Code 2020

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
- Simple CSV parsing (to String/int/long)
- Painting of 2D ASCII images with a few different input possibilities
- Math util functions for least common multiple and greatest common divisor
- Support of multiple years

The general structure is based on https://github.com/dave-burke/advent-of-code-java-starter/

