package de.beachboys.aoc2025;

import de.beachboys.Day;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateXY;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Polygon;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Day09 extends Day {

    public Object part1(List<String> input) {
        long result = 0;
        List<Tuple2<Integer, Integer>> redTiles = parseInput(input);
        for (int i = 0; i < redTiles.size(); i++) {
            Tuple2<Integer, Integer> t1 = redTiles.get(i);
            for (int j = i + 1; j < redTiles.size(); j++) {
                Tuple2<Integer, Integer> t2 = redTiles.get(j);
                long area = getBoxArea(t1, t2);
                if (area > result) {
                    result = area;
                }
            }
        }
        return result;
    }

    public Object part2(List<String> input) {
        long result = 0;
        List<Tuple2<Integer, Integer>> redTiles = parseInput(input);

        GeometryFactory f = new GeometryFactory();
        Coordinate[] polygonCoordinates = new Coordinate[redTiles.size()+1];
        for (int i = 0; i < redTiles.size(); i++) {
            polygonCoordinates[i] = new CoordinateXY(redTiles.get(i).v1, redTiles.get(i).v2);
        }
        polygonCoordinates[redTiles.size()] = new CoordinateXY(redTiles.getFirst().v1, redTiles.getFirst().v2);
        Polygon polygon = f.createPolygon(polygonCoordinates);

        for (int i = 0; i < redTiles.size(); i++) {
            Tuple2<Integer, Integer> t1 = redTiles.get(i);
            for (int j = i + 1; j < redTiles.size(); j++) {
                Tuple2<Integer, Integer> t2 = redTiles.get(j);
                if (polygon.contains(getRectangle(t1, t2, f))) {
                    long area = getBoxArea(t1, t2);
                    if (area > result) {
                        result = area;
                    }
                }
            }
        }
        return result;
    }

    private static long getBoxArea(Tuple2<Integer, Integer> t1, Tuple2<Integer, Integer> t2) {
        return (long) (Math.abs(t1.v1 - t2.v1) + 1) * (Math.abs(t1.v2 - t2.v2) + 1);
    }

    private static Polygon getRectangle(Tuple2<Integer, Integer> t1, Tuple2<Integer, Integer> t2, GeometryFactory f) {
        Coordinate[] rectangleCoordinates = new Coordinate[5];
        rectangleCoordinates[0] = new CoordinateXY(t1.v1, t1.v2);
        rectangleCoordinates[1] = new CoordinateXY(t1.v1, t2.v2);
        rectangleCoordinates[2] = new CoordinateXY(t2.v1, t2.v2);
        rectangleCoordinates[3] = new CoordinateXY(t2.v1, t1.v2);
        rectangleCoordinates[4] = new CoordinateXY(t1.v1, t1.v2);
        return f.createPolygon(rectangleCoordinates);
    }

    private static List<Tuple2<Integer, Integer>> parseInput(List<String> input) {
        List<Tuple2<Integer, Integer>> redTiles = new ArrayList<>();
        for (String line : input) {
            String[] split = line.split(Pattern.quote(","));
            redTiles.add(Tuple.tuple(Integer.parseInt(split[0]), Integer.parseInt(split[1])));
        }
        return redTiles;
    }


}
