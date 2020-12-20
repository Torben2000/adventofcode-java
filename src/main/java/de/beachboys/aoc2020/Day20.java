package de.beachboys.aoc2020;

import de.beachboys.Day;
import de.beachboys.Util;
import org.javatuples.Pair;

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

    Map<Integer, Map<Pair<Integer, Integer>, String>> tileImageMaps = new HashMap<>();
    Map<Pair<Integer, Edge>, Integer> tileEdgeIds = new HashMap<>();
    Map<Integer, Set<Pair<Integer, Edge>>> edgeIdToTiles = new HashMap<>();
    Map<Integer, Integer> fittingEdgeIds = new HashMap<>();

    public static final List<String> MONSTER_IMAGE = List.of("                  # ",
            "#    ##    ##    ###",
            " #  #  #  #  #  #   ");
    public static final Map<Pair<Integer, Integer>, String> MONSTER_MAP = Util.buildImageMap(MONSTER_IMAGE);
    public static final int MONSTER_WIDTH = MONSTER_IMAGE.get(0).length();
    public static final int MONSTER_HEIGHT = MONSTER_IMAGE.size();

    public Object part1(List<String> input) {
        prepareData(input);

        List<Integer> possibleOuterEdges = edgeIdToTiles.values().stream()
                .filter(tiles -> tiles.size() == 1)
                .map(tiles -> tiles.stream().findFirst().orElseThrow().getValue0())
                .collect(Collectors.toList());

        return possibleOuterEdges.stream()
                .collect(Collectors.groupingBy(e -> e, Collectors.counting()))
                .entrySet().stream()
                .filter(e -> e.getValue() == 4)
                .mapToLong(Map.Entry::getKey)
                .reduce(1, (a, b) -> a * b);
    }

    public Object part2(List<String> input) {
        prepareData(input);
        Map<Pair<Integer, Integer>, Pair<Integer, Edge>> tilesPositions = solvePuzzle(tilesPerDimension);
        Map<Pair<Integer, Integer>, String> bigImageMap = buildBigImageMap(tilesPositions);
        return findMonsterAndCountMovingWater(bigImageMap);
    }

    private long findMonsterAndCountMovingWater(Map<Pair<Integer, Integer>, String> bigImageMap) {
        int bigMapWidthHeight = TILE_DIMENSION_WITHOUT_BORDER * tilesPerDimension;
        Map<Pair<Integer, Integer>, String> imageMapToPaint = bigImageMap;
        long minMovingWater = bigImageMap.values().stream().filter(this::isMovingWater).count();
        for (Edge dir : Edge.values()) {
            Map<Pair<Integer, Integer>, String> imageMapToUse = rotateMap(bigImageMap, dir, bigMapWidthHeight);
            for (int x = 0; x < bigMapWidthHeight - MONSTER_WIDTH; x++) {
                for (int y = 0; y < bigMapWidthHeight - MONSTER_HEIGHT; y++) {
                    final int finalX = x;
                    final int finalY = y;
                    Map<Pair<Integer, Integer>, String> monsterMapAtPos = MONSTER_MAP.entrySet().stream()
                            .collect(Collectors.toMap(e -> Pair.with(e.getKey().getValue0() + finalX, e.getKey().getValue1() + finalY), Map.Entry::getValue));
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

    private Map<Pair<Integer, Integer>, String> buildBigImageMap(Map<Pair<Integer, Integer>, Pair<Integer, Edge>> tilesPositions) {
        Map<Pair<Integer, Integer>, String> bigImageMap = new HashMap<>();
        for (int tileX = 0; tileX < tilesPerDimension; tileX++) {
            for (int tileY = 0; tileY < tilesPerDimension; tileY++) {
                Pair<Integer, Edge> tile = tilesPositions.get(Pair.with(tileX, tileY));
                Map<Pair<Integer, Integer>, String> imageMap = tileImageMaps.get(tile.getValue0());
                Map<Pair<Integer, Integer>, String> rotatedMap = rotateMap(imageMap, tile.getValue1(), TILE_DIMENSION);
                for (Pair<Integer, Integer> position : rotatedMap.keySet()) {
                    if (isBorderPositionInTile(position)) {
                        continue;
                    }
                    int bigImageX = tileX * TILE_DIMENSION_WITHOUT_BORDER + position.getValue0() - 1;
                    int bigImageY = tileY * TILE_DIMENSION_WITHOUT_BORDER + position.getValue1() - 1;
                    bigImageMap.put(Pair.with(bigImageX, bigImageY), rotatedMap.get(position));
                }
            }
        }
        return bigImageMap;
    }

    private boolean isBorderPositionInTile(Pair<Integer, Integer> position) {
        return position.getValue0() == 0 || position.getValue1() == 0 || position.getValue0() == MAX_INDEX_IN_TILE || position.getValue1() == MAX_INDEX_IN_TILE;
    }

    private Map<Pair<Integer, Integer>, Pair<Integer, Edge>> solvePuzzle(int tilesPerDimension) {
        Map<Pair<Integer, Integer>, Pair<Integer, Edge>> tilesPositions = new HashMap<>();
        for (int tileX = 0; tileX < tilesPerDimension; tileX++) {
            for (int tileY = 0; tileY < tilesPerDimension; tileY++) {
                Pair<Integer, Edge> northNeighbor = tilesPositions.get(Pair.with(tileX, tileY - 1));
                Pair<Integer, Edge> westNeighbor = tilesPositions.get(Pair.with(tileX - 1, tileY));
                Set<Pair<Integer, Edge>> northPossibilities = getFittingTileEdges(northNeighbor, this::getOppositeEdge);
                Set<Pair<Integer, Edge>> westPossibilities = getFittingTileEdges(westNeighbor, this::getEastEdgeBasedOnNorthEdge);
                Pair<Integer, Edge> currentTileNorth = findNorthEdge(northPossibilities, westPossibilities);
                tilesPositions.put(Pair.with(tileX, tileY), currentTileNorth);
            }
        }
        return tilesPositions;
    }

    private Set<Pair<Integer, Edge>> getFittingTileEdges(Pair<Integer, Edge> neighbor, Function<Edge, Edge> getAdjacentEdgeFromNorthEdge) {
        Set<Pair<Integer, Edge>> possibilities;
        if (neighbor != null) {
            int fittingEdgeId = fittingEdgeIds.get(tileEdgeIds.get(Pair.with(neighbor.getValue0(), getAdjacentEdgeFromNorthEdge.apply(neighbor.getValue1()))));
            possibilities = edgeIdToTiles.get(fittingEdgeId).stream().filter(e -> !e.getValue0().equals(neighbor.getValue0())).collect(Collectors.toSet());
        } else {
            possibilities = edgeIdToTiles.values().stream().filter(s -> s.size() == 1).map(s -> s.stream().findFirst().orElseThrow()).collect(Collectors.toSet());
        }
        return possibilities;
    }

    private Map<Pair<Integer, Integer>, String> rotateMap(Map<Pair<Integer, Integer>, String> imageMap, Edge northEdge, int dimension) {
        Map<Pair<Integer, Integer>, String> rotatedMap = new HashMap<>();
        for (Pair<Integer, Integer> position : imageMap.keySet()) {
            rotatedMap.put(Pair.with(convertXValue(position, northEdge, dimension - 1), convertYValue(position, northEdge, dimension - 1)), imageMap.get(position));
        }
        return rotatedMap;
    }

    private int convertXValue(Pair<Integer, Integer> positionForNorth, Edge northEdge, int maxWidth) {
        switch (northEdge) {
            case EAST:
            case WEST_BACK:
                return positionForNorth.getValue1();
            case NORTH:
            case SOUTH_BACK:
                return positionForNorth.getValue0();
            case SOUTH:
            case NORTH_BACK:
                return maxWidth - positionForNorth.getValue0();
            case WEST:
            case EAST_BACK:
                return maxWidth - positionForNorth.getValue1();
        }
        throw new IllegalArgumentException();
    }

    private int convertYValue(Pair<Integer, Integer> positionForNorth, Edge northEdge, int maxHeight) {
        switch (northEdge) {
            case EAST:
            case EAST_BACK:
                return maxHeight - positionForNorth.getValue0();
            case NORTH:
            case NORTH_BACK:
                return positionForNorth.getValue1();
            case SOUTH:
            case SOUTH_BACK:
                return maxHeight - positionForNorth.getValue1();
            case WEST:
            case WEST_BACK:
                return positionForNorth.getValue0();
        }
        throw new IllegalArgumentException();
    }

    private Pair<Integer, Edge> findNorthEdge(Set<Pair<Integer, Edge>> northPossibilities, Set<Pair<Integer, Edge>> westPossibilities) {
        for (Pair<Integer, Edge> north : northPossibilities) {
            for (Pair<Integer, Edge> west : westPossibilities) {
                if (north.getValue0().equals(west.getValue0()) && north.getValue1() == getEastEdgeBasedOnNorthEdge(west.getValue1())) {
                    // first match is fine as there are only multiple matches for the very first tile to place
                    return north;
                }
            }
        }
        throw new IllegalArgumentException();
    }

    private Edge getOppositeEdge(Edge edge) {
        switch (edge) {
            case EAST:
                return Edge.WEST;
            case NORTH:
                return Edge.SOUTH;
            case SOUTH:
                return Edge.NORTH;
            case WEST:
                return Edge.EAST;
            case EAST_BACK:
                return Edge.WEST_BACK;
            case NORTH_BACK:
                return Edge.SOUTH_BACK;
            case WEST_BACK:
                return Edge.EAST_BACK;
            case SOUTH_BACK:
                return Edge.NORTH_BACK;
        }
        throw new IllegalArgumentException();
    }

    private Edge getEastEdgeBasedOnNorthEdge(Edge northEdge) {
        switch (northEdge) {
            case EAST:
                return Edge.SOUTH;
            case NORTH:
                return Edge.EAST;
            case SOUTH:
                return Edge.WEST;
            case WEST:
                return Edge.NORTH;
            case EAST_BACK:
                return Edge.NORTH_BACK;
            case NORTH_BACK:
                return Edge.WEST_BACK;
            case WEST_BACK:
                return Edge.SOUTH_BACK;
            case SOUTH_BACK:
                return Edge.EAST_BACK;
        }
        throw new IllegalArgumentException();
    }

    private void prepareData(List<String> input) {
        Map<Integer, List<String>> imageInput = getImageInputForTiles(input);
        fillTileImageMaps(imageInput);
        calculateEdgeIds();
        for (Map.Entry<Pair<Integer, Edge>, Integer> edge : tileEdgeIds.entrySet()) {
            edgeIdToTiles.putIfAbsent(edge.getValue(), new HashSet<>());
            edgeIdToTiles.get(edge.getValue()).add(Pair.with(edge.getKey().getValue0(), edge.getKey().getValue1()));
        }
        tilesPerDimension = (int) Math.sqrt(tileImageMaps.keySet().size());
    }

    private void calculateEdgeIds() {
        for (Integer tileId : tileImageMaps.keySet()) {
            Map<Pair<Integer, Integer>, String> imageMap = tileImageMaps.get(tileId);
            int northEdgeId = getEdgeId(imageMap, this::isNorthInTile, Pair::getValue0, true);
            int westEdgeId = getEdgeId(imageMap, this::isWestInTile, Pair::getValue1, false);
            int southEdgeId = getEdgeId(imageMap, this::isSouthInTile, Pair::getValue0, false);
            int eastEdgeId = getEdgeId(imageMap, this::isEastInTile, Pair::getValue1, true);
            int northBackEdgeId = getEdgeId(imageMap, this::isNorthInTile, Pair::getValue0, false);
            int westBackEdgeId = getEdgeId(imageMap, this::isWestInTile, Pair::getValue1, true);
            int southBackEdgeId = getEdgeId(imageMap, this::isSouthInTile, Pair::getValue0, true);
            int eastBackEdgeId = getEdgeId(imageMap, this::isEastInTile, Pair::getValue1, false);

            tileEdgeIds.put(Pair.with(tileId, Edge.NORTH), northEdgeId);
            tileEdgeIds.put(Pair.with(tileId, Edge.SOUTH), southEdgeId);
            tileEdgeIds.put(Pair.with(tileId, Edge.WEST), westEdgeId);
            tileEdgeIds.put(Pair.with(tileId, Edge.EAST), eastEdgeId);
            tileEdgeIds.put(Pair.with(tileId, Edge.NORTH_BACK), northBackEdgeId);
            tileEdgeIds.put(Pair.with(tileId, Edge.SOUTH_BACK), southBackEdgeId);
            tileEdgeIds.put(Pair.with(tileId, Edge.WEST_BACK), westBackEdgeId);
            tileEdgeIds.put(Pair.with(tileId, Edge.EAST_BACK), eastBackEdgeId);

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

    private int getEdgeId(Map<Pair<Integer, Integer>, String> imageMap, Predicate<Pair<Integer, Integer>> borderTest, Function<Pair<Integer, Integer>, Integer> dimensionProvider, boolean backwardsValue) {
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

    private boolean isNorthInTile(Pair<Integer, Integer> position) {
        return position.getValue1() == 0;
    }

    private boolean isSouthInTile(Pair<Integer, Integer> position) {
        return position.getValue1() == MAX_INDEX_IN_TILE;
    }

    private boolean isWestInTile(Pair<Integer, Integer> position) {
        return position.getValue0() == 0;
    }

    private boolean isEastInTile(Pair<Integer, Integer> position) {
        return position.getValue0() == MAX_INDEX_IN_TILE;
    }

    private boolean isMovingWater(String mapValue) {
        return "#".equals(mapValue);
    }

}
