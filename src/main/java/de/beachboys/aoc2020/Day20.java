package de.beachboys.aoc2020;

import de.beachboys.Day;
import de.beachboys.Util;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Day20 extends Day {

    enum Edge {
        NORTH, EAST, SOUTH, WEST, NORTH_BACK, EAST_BACK, SOUTH_BACK, WEST_BACK
    }

    public static final int TILE_DIMENSION = 10;
    public static final int MAX_INDEX_IN_TILE = TILE_DIMENSION - 1;
    public static final int TILE_DIMENSION_WITHOUT_BORDER = TILE_DIMENSION - 2;
    private int tilesPerDimension = 0;

    private final Map<Integer, Map<Tuple2<Integer, Integer>, String>> tileImageMaps = new HashMap<>();
    private final Map<Tuple2<Integer, Edge>, Integer> tileEdgeIds = new HashMap<>();
    private final Map<Integer, Set<Tuple2<Integer, Edge>>> edgeIdToTiles = new HashMap<>();
    private final Map<Integer, Integer> fittingEdgeIds = new HashMap<>();

    public static final List<String> MONSTER_IMAGE = List.of("                  # ",
            "#    ##    ##    ###",
            " #  #  #  #  #  #   ");
    public static final Map<Tuple2<Integer, Integer>, String> MONSTER_MAP = Util.buildImageMap(MONSTER_IMAGE);
    public static final int MONSTER_WIDTH = MONSTER_IMAGE.getFirst().length();
    public static final int MONSTER_HEIGHT = MONSTER_IMAGE.size();

    public Object part1(List<String> input) {
        prepareData(input);

        List<Integer> possibleOuterEdges = edgeIdToTiles.values().stream()
                .filter(tiles -> tiles.size() == 1)
                .map(tiles -> tiles.stream().findFirst().orElseThrow().v1)
                .toList();

        return possibleOuterEdges.stream()
                .collect(Collectors.groupingBy(e -> e, Collectors.counting()))
                .entrySet().stream()
                .filter(e -> e.getValue() == 4)
                .mapToLong(Map.Entry::getKey)
                .reduce(1, (a, b) -> a * b);
    }

    public Object part2(List<String> input) {
        prepareData(input);
        Map<Tuple2<Integer, Integer>, Tuple2<Integer, Edge>> tilesPositions = solvePuzzle(tilesPerDimension);
        Map<Tuple2<Integer, Integer>, String> bigImageMap = buildBigImageMap(tilesPositions);
        return findMonsterAndCountMovingWater(bigImageMap);
    }

    private long findMonsterAndCountMovingWater(Map<Tuple2<Integer, Integer>, String> bigImageMap) {
        int bigMapWidthHeight = TILE_DIMENSION_WITHOUT_BORDER * tilesPerDimension;
        Map<Tuple2<Integer, Integer>, String> imageMapToPaint = bigImageMap;
        long minMovingWater = bigImageMap.values().stream().filter(this::isMovingWater).count();
        for (Edge dir : Edge.values()) {
            Map<Tuple2<Integer, Integer>, String> imageMapToUse = rotateMap(bigImageMap, dir, bigMapWidthHeight);
            for (int x = 0; x < bigMapWidthHeight - MONSTER_WIDTH; x++) {
                for (int y = 0; y < bigMapWidthHeight - MONSTER_HEIGHT; y++) {
                    final int finalX = x;
                    final int finalY = y;
                    Map<Tuple2<Integer, Integer>, String> monsterMapAtPos = MONSTER_MAP.entrySet().stream()
                            .collect(Collectors.toMap(e -> Tuple.tuple(e.getKey().v1 + finalX, e.getKey().v2 + finalY), Map.Entry::getValue));
                    if (monsterMapAtPos.entrySet().stream().filter(e -> isMovingWater(e.getValue())).filter(e -> isMovingWater(imageMapToUse.get(e.getKey()))).count() == 15) {
                        monsterMapAtPos.keySet().stream().filter(pos -> isMovingWater(monsterMapAtPos.get(pos))).forEach(p -> imageMapToUse.put(p, "O"));
                    }
                }
            }
            long movingWater = imageMapToUse.values().stream().filter(this::isMovingWater).count();
            if (movingWater < minMovingWater) {
                minMovingWater = movingWater;
                imageMapToPaint = imageMapToUse;
            }

        }
        io.logInfo(Util.paintMap(imageMapToPaint));
        return minMovingWater;
    }

    private Map<Tuple2<Integer, Integer>, String> buildBigImageMap(Map<Tuple2<Integer, Integer>, Tuple2<Integer, Edge>> tilesPositions) {
        Map<Tuple2<Integer, Integer>, String> bigImageMap = new HashMap<>();
        for (int tileX = 0; tileX < tilesPerDimension; tileX++) {
            for (int tileY = 0; tileY < tilesPerDimension; tileY++) {
                Tuple2<Integer, Edge> tile = tilesPositions.get(Tuple.tuple(tileX, tileY));
                Map<Tuple2<Integer, Integer>, String> imageMap = tileImageMaps.get(tile.v1);
                Map<Tuple2<Integer, Integer>, String> rotatedMap = rotateMap(imageMap, tile.v2, TILE_DIMENSION);
                for (Tuple2<Integer, Integer> position : rotatedMap.keySet()) {
                    if (isBorderPositionInTile(position)) {
                        continue;
                    }
                    int bigImageX = tileX * TILE_DIMENSION_WITHOUT_BORDER + position.v1 - 1;
                    int bigImageY = tileY * TILE_DIMENSION_WITHOUT_BORDER + position.v2 - 1;
                    bigImageMap.put(Tuple.tuple(bigImageX, bigImageY), rotatedMap.get(position));
                }
            }
        }
        return bigImageMap;
    }

    private boolean isBorderPositionInTile(Tuple2<Integer, Integer> position) {
        return position.v1 == 0 || position.v2 == 0 || position.v1 == MAX_INDEX_IN_TILE || position.v2 == MAX_INDEX_IN_TILE;
    }

    private Map<Tuple2<Integer, Integer>, Tuple2<Integer, Edge>> solvePuzzle(int tilesPerDimension) {
        Map<Tuple2<Integer, Integer>, Tuple2<Integer, Edge>> tilesPositions = new HashMap<>();
        for (int tileX = 0; tileX < tilesPerDimension; tileX++) {
            for (int tileY = 0; tileY < tilesPerDimension; tileY++) {
                Tuple2<Integer, Edge> northNeighbor = tilesPositions.get(Tuple.tuple(tileX, tileY - 1));
                Tuple2<Integer, Edge> westNeighbor = tilesPositions.get(Tuple.tuple(tileX - 1, tileY));
                Set<Tuple2<Integer, Edge>> northPossibilities = getFittingTileEdges(northNeighbor, this::getOppositeEdge);
                Set<Tuple2<Integer, Edge>> westPossibilities = getFittingTileEdges(westNeighbor, this::getEastEdgeBasedOnNorthEdge);
                Tuple2<Integer, Edge> currentTileNorth = findNorthEdge(northPossibilities, westPossibilities);
                tilesPositions.put(Tuple.tuple(tileX, tileY), currentTileNorth);
            }
        }
        return tilesPositions;
    }

    private Set<Tuple2<Integer, Edge>> getFittingTileEdges(Tuple2<Integer, Edge> neighbor, Function<Edge, Edge> getAdjacentEdgeFromNorthEdge) {
        Set<Tuple2<Integer, Edge>> possibilities;
        if (neighbor != null) {
            int fittingEdgeId = fittingEdgeIds.get(tileEdgeIds.get(Tuple.tuple(neighbor.v1, getAdjacentEdgeFromNorthEdge.apply(neighbor.v2))));
            possibilities = edgeIdToTiles.get(fittingEdgeId).stream().filter(e -> !e.v1.equals(neighbor.v1)).collect(Collectors.toSet());
        } else {
            possibilities = edgeIdToTiles.values().stream().filter(s -> s.size() == 1).map(s -> s.stream().findFirst().orElseThrow()).collect(Collectors.toSet());
        }
        return possibilities;
    }

    private Map<Tuple2<Integer, Integer>, String> rotateMap(Map<Tuple2<Integer, Integer>, String> imageMap, Edge northEdge, int dimension) {
        Map<Tuple2<Integer, Integer>, String> rotatedMap = new HashMap<>();
        for (Tuple2<Integer, Integer> position : imageMap.keySet()) {
            rotatedMap.put(Tuple.tuple(convertXValue(position, northEdge, dimension - 1), convertYValue(position, northEdge, dimension - 1)), imageMap.get(position));
        }
        return rotatedMap;
    }

    private int convertXValue(Tuple2<Integer, Integer> positionForNorth, Edge northEdge, int maxWidth) {
        return switch (northEdge) {
            case EAST, WEST_BACK -> positionForNorth.v2;
            case NORTH, SOUTH_BACK -> positionForNorth.v1;
            case SOUTH, NORTH_BACK -> maxWidth - positionForNorth.v1;
            case WEST, EAST_BACK -> maxWidth - positionForNorth.v2;
        };
    }

    private int convertYValue(Tuple2<Integer, Integer> positionForNorth, Edge northEdge, int maxHeight) {
        return switch (northEdge) {
            case EAST, EAST_BACK -> maxHeight - positionForNorth.v1;
            case NORTH, NORTH_BACK -> positionForNorth.v2;
            case SOUTH, SOUTH_BACK -> maxHeight - positionForNorth.v2;
            case WEST, WEST_BACK -> positionForNorth.v1;
        };
    }

    private Tuple2<Integer, Edge> findNorthEdge(Set<Tuple2<Integer, Edge>> northPossibilities, Set<Tuple2<Integer, Edge>> westPossibilities) {
        for (Tuple2<Integer, Edge> north : northPossibilities) {
            for (Tuple2<Integer, Edge> west : westPossibilities) {
                if (north.v1.equals(west.v1) && north.v2 == getEastEdgeBasedOnNorthEdge(west.v2)) {
                    // first match is fine as there are only multiple matches for the very first tile to place
                    return north;
                }
            }
        }
        throw new IllegalArgumentException();
    }

    private Edge getOppositeEdge(Edge edge) {
        return switch (edge) {
            case EAST -> Edge.WEST;
            case NORTH -> Edge.SOUTH;
            case SOUTH -> Edge.NORTH;
            case WEST -> Edge.EAST;
            case EAST_BACK -> Edge.WEST_BACK;
            case NORTH_BACK -> Edge.SOUTH_BACK;
            case WEST_BACK -> Edge.EAST_BACK;
            case SOUTH_BACK -> Edge.NORTH_BACK;
        };
    }

    private Edge getEastEdgeBasedOnNorthEdge(Edge northEdge) {
        return switch (northEdge) {
            case EAST -> Edge.SOUTH;
            case NORTH -> Edge.EAST;
            case SOUTH -> Edge.WEST;
            case WEST -> Edge.NORTH;
            case EAST_BACK -> Edge.NORTH_BACK;
            case NORTH_BACK -> Edge.WEST_BACK;
            case WEST_BACK -> Edge.SOUTH_BACK;
            case SOUTH_BACK -> Edge.EAST_BACK;
        };
    }

    private void prepareData(List<String> input) {
        Map<Integer, List<String>> imageInput = getImageInputForTiles(input);
        fillTileImageMaps(imageInput);
        calculateEdgeIds();
        for (Map.Entry<Tuple2<Integer, Edge>, Integer> edge : tileEdgeIds.entrySet()) {
            edgeIdToTiles.putIfAbsent(edge.getValue(), new HashSet<>());
            edgeIdToTiles.get(edge.getValue()).add(Tuple.tuple(edge.getKey().v1, edge.getKey().v2));
        }
        tilesPerDimension = (int) Math.sqrt(tileImageMaps.keySet().size());
    }

    private void calculateEdgeIds() {
        for (Integer tileId : tileImageMaps.keySet()) {
            Map<Tuple2<Integer, Integer>, String> imageMap = tileImageMaps.get(tileId);
            int northEdgeId = getEdgeId(imageMap, this::isNorthInTile, Tuple2::v1, true);
            int westEdgeId = getEdgeId(imageMap, this::isWestInTile, Tuple2::v2, false);
            int southEdgeId = getEdgeId(imageMap, this::isSouthInTile, Tuple2::v1, false);
            int eastEdgeId = getEdgeId(imageMap, this::isEastInTile, Tuple2::v2, true);
            int northBackEdgeId = getEdgeId(imageMap, this::isNorthInTile, Tuple2::v1, false);
            int westBackEdgeId = getEdgeId(imageMap, this::isWestInTile, Tuple2::v2, true);
            int southBackEdgeId = getEdgeId(imageMap, this::isSouthInTile, Tuple2::v1, true);
            int eastBackEdgeId = getEdgeId(imageMap, this::isEastInTile, Tuple2::v2, false);

            tileEdgeIds.put(Tuple.tuple(tileId, Edge.NORTH), northEdgeId);
            tileEdgeIds.put(Tuple.tuple(tileId, Edge.SOUTH), southEdgeId);
            tileEdgeIds.put(Tuple.tuple(tileId, Edge.WEST), westEdgeId);
            tileEdgeIds.put(Tuple.tuple(tileId, Edge.EAST), eastEdgeId);
            tileEdgeIds.put(Tuple.tuple(tileId, Edge.NORTH_BACK), northBackEdgeId);
            tileEdgeIds.put(Tuple.tuple(tileId, Edge.SOUTH_BACK), southBackEdgeId);
            tileEdgeIds.put(Tuple.tuple(tileId, Edge.WEST_BACK), westBackEdgeId);
            tileEdgeIds.put(Tuple.tuple(tileId, Edge.EAST_BACK), eastBackEdgeId);

            fittingEdgeIds.put(northEdgeId, northBackEdgeId);
            fittingEdgeIds.put(northBackEdgeId, northEdgeId);
            fittingEdgeIds.put(southEdgeId, southBackEdgeId);
            fittingEdgeIds.put(southBackEdgeId, southEdgeId);
            fittingEdgeIds.put(westEdgeId, westBackEdgeId);
            fittingEdgeIds.put(westBackEdgeId, westEdgeId);
            fittingEdgeIds.put(eastEdgeId, eastBackEdgeId);
            fittingEdgeIds.put(eastBackEdgeId, eastEdgeId);
        }
    }

    private int getEdgeId(Map<Tuple2<Integer, Integer>, String> imageMap, Predicate<Tuple2<Integer, Integer>> borderTest, Function<Tuple2<Integer, Integer>, Integer> dimensionProvider, boolean backwardsValue) {
        return imageMap.entrySet().stream()
                .filter(e -> borderTest.test(e.getKey()))
                .filter(e -> isMovingWater(e.getValue()))
                .mapToInt(e -> getEdgeIdForPosition(dimensionProvider.apply(e.getKey()), backwardsValue))
                .sum();
    }

    private Map<Integer, List<String>> getImageInputForTiles(List<String> input) {
        Map<Integer, List<String>> imageInput = new HashMap<>();
        int currentTileId = -1;
        List<String> currentTileLines = new ArrayList<>();
        for (String line : input) {
            if (line.startsWith("Tile")) {
                currentTileId = Integer.parseInt(line.substring(5, line.length() - 1));
            } else if (line.isEmpty()) {
                imageInput.put(currentTileId, currentTileLines);
                currentTileLines = new ArrayList<>();
            } else {
                currentTileLines.add(line);
            }
        }
        imageInput.put(currentTileId, currentTileLines);
        return imageInput;
    }

    private void fillTileImageMaps(Map<Integer, List<String>> imageInput) {
        for (Integer tileId : imageInput.keySet()) {
            tileImageMaps.put(tileId, Util.buildImageMap(imageInput.get(tileId)));
        }
    }

    private int getEdgeIdForPosition(int position, boolean backwardsValue) {
        int positionToUse = position;
        if (backwardsValue) {
            positionToUse = MAX_INDEX_IN_TILE - position;
        }
        return (int) Math.pow(2, positionToUse);
    }

    private boolean isNorthInTile(Tuple2<Integer, Integer> position) {
        return position.v2 == 0;
    }

    private boolean isSouthInTile(Tuple2<Integer, Integer> position) {
        return position.v2 == MAX_INDEX_IN_TILE;
    }

    private boolean isWestInTile(Tuple2<Integer, Integer> position) {
        return position.v1 == 0;
    }

    private boolean isEastInTile(Tuple2<Integer, Integer> position) {
        return position.v1 == MAX_INDEX_IN_TILE;
    }

    private boolean isMovingWater(String mapValue) {
        return "#".equals(mapValue);
    }

}
