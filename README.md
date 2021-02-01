# Advent of Code
[![CircleCI build status](https://circleci.com/gh/Torben2000/adventofcode2020.svg?style=shield)](https://circleci.com/gh/Torben2000/adventofcode2020)

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
- Direction enums to help with navigating using square or hex fields
- Painting of 2D ASCII images with a few different input possibilities
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
### 2020
| Day | Part 1 | Part 2 |
| ---:|:---:|:---:|
|1|✔|✔|
|2|✔|✔|
|3|✔|✔|
|4|✔|✔|
|5|✔|✔|
|6|✔|✔|
|7|✔|✔|
|8|✔|✔|
|9|✔|✔|
|10|✔|✔|
|11|✔|✔|
|12|✔|✔|
|13|✔|✔|
|14|✔|✔|
|15|✔|✔|
|16|✔|✔|
|17|✔|✔|
|18|✔|✔|
|19|✔|✔|
|20|✔|✔|
|21|✔|✔|
|22|✔|✔|
|23|✔|✔|
|24|✔|✔|
|25|✔|✔|

### 2019
| Day | Part 1 | Part 2 |
| ---:|:---:|:---:|
|1|✔|✔|
|2|✔|✔|
|3|✔|✔|
|4|✔|✔|
|5|✔|✔|
|6|✔|✔|
|7|✔|✔|
|8|✔|✔|
|9|✔|✔|
|10|✔|✔|
|11|✔|✔|
|12|✔|✔|
|13|✔|✔|
|14|✔|✔|
|15|✔|✔|
|16|✔|✔|
|17|✔|✔|
|18|✔|✔|
|19|✔|✔|
|20|✔|✔|
|21|✔|✔|
|22|✔|✔|
|23|✔|✔|
|24|✔|✔|
|25|✔|✔|

### 2017
| Day | Part 1 | Part 2 |
| ---:|:---:|:---:|
|1|✔|✔|
|2|✔|✔|
|3|✔|✔|
|4|✔|✔|
|5|✔|✔|
|6|✔|✔|
|7|✔|✔|
|8|✔|✔|
|9|✔|✔|
|10|✔|✔|
|11|✔|✔|
|12|✔|✔|
|13|✔|✔|
|14|❌|❌|
|15|❌|❌|
|16|❌|❌|
|17|❌|❌|
|18|❌|❌|
|19|❌|❌|
|20|❌|❌|
|21|❌|❌|
|22|❌|❌|
|23|❌|❌|
|24|❌|❌|
|25|❌|❌|

### 2016
| Day | Part 1 | Part 2 |
| ---:|:---:|:---:|
|1|✔|✔|
|2|✔|✔|
|3|✔|✔|
|4|✔|✔|
|5|✔|✔|
|6|✔|✔|
|7|✔|✔|
|8|✔|✔|
|9|✔|✔|
|10|✔|✔|
|11|✔|✔|
|12|✔|✔|
|13|✔|✔|
|14|✔|✔|
|15|✔|✔|
|16|✔|✔|
|17|✔|✔|
|18|✔|✔|
|19|✔|✔|
|20|✔|✔|
|21|✔|✔|
|22|✔|✔|
|23|✔|✔|
|24|✔|✔|
|25|✔|✔|


### 2015
| Day | Part 1 | Part 2 |
| ---:|:---:|:---:|
|1|✔|✔|
|2|✔|✔|
|3|✔|✔|
|4|✔|✔|
|5|✔|✔|
|6|✔|✔|
|7|✔|✔|
|8|✔|✔|
|9|✔|✔|
|10|✔|✔|
|11|✔|✔|
|12|✔|✔|
|13|✔|✔|
|14|✔|✔|
|15|✔|✔|
|16|✔|✔|
|17|✔|✔|
|18|✔|✔|
|19|✔|✔|
|20|✔|✔|
|21|✔|✔|
|22|✔|✔|
|23|✔|✔|
|24|✔|✔|
|25|✔|✔|
