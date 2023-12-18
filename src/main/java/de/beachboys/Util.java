package de.beachboys;

import org.javatuples.Pair;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.nio.DefaultAttribute;
import org.jgrapht.nio.dot.DOTExporter;

import java.io.StringWriter;
import java.io.Writer;
import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

public final class Util {

    public static List<String> parseToList(String input, String separator) {
        return Arrays.asList(input.split(separator));
    }

    public static List<Integer> parseToIntList(String input, String separator) {
        return Arrays.stream(input.split(separator)).map(Integer::valueOf).collect(Collectors.toList());
    }

    public static List<Long> parseToLongList(String input, String separator) {
        return Arrays.stream(input.split(separator)).map(Long::valueOf).collect(Collectors.toList());
    }

    public static List<String> parseCsv(String input) {
        return parseToList(input, ",");
    }

    public static List<Integer> parseIntCsv(String input) {
        return parseToIntList(input, ",");
    }

    public static List<Long> parseLongCsv(String input) {
        return parseToLongList(input, ",");
    }

    public static String paintSet(Set<Pair<Integer, Integer>> set) {
        return paintMap(set.stream().collect(Collectors.toMap(Function.identity(), position -> "*")));
    }

    public static String paintMap(Map<Pair<Integer, Integer>, String> map) {
        Map<String, String> valuesToPaint = map.values().stream().distinct().collect(Collectors.toMap(Function.identity(), Function.identity()));
        return paintMap(map, valuesToPaint);
    }

    public static String paintMap(Map<Pair<Integer, Integer>, String> map, Map<String, String> valuesToPaint) {
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;
        Map<Pair<Integer, Integer>, String> filteredMap = new HashMap<>(map);
        map.forEach((point, value) -> {
            if (valuesToPaint != null && !valuesToPaint.containsKey(value)) {
                filteredMap.remove(point);
            }
        });
        for (Pair<Integer, Integer> point : filteredMap.keySet()) {
            minX = Math.min(minX, point.getValue0());
            minY = Math.min(minY, point.getValue1());
            maxX = Math.max(maxX, point.getValue0());
            maxY = Math.max(maxY, point.getValue1());
        }
        int width = maxX - minX + 1;
        int height = maxY - minY + 1;
        StringBuilder imageString = new StringBuilder();
        imageString.append(" ".repeat(width*height));
        for (Pair<Integer, Integer> point : filteredMap.keySet()) {
            int index = width * (point.getValue1() - minY) + (point.getValue0() - minX);
            imageString.replace(index, index + 1, filteredMap.get(point));
        }
        return formatImage(imageString.toString(), width, height, valuesToPaint);
    }

    public static String formatImage(String imageString, int width, int height, Map<String, String> valuesToPaint) {
        final StringBuilder newImageString = new StringBuilder();
        if (valuesToPaint != null) {
            imageString.chars()
                    .forEach(i -> {
                        String charAsString = String.valueOf((char) i);
                        newImageString.append(valuesToPaint.getOrDefault(charAsString, " "));
                    });
        } else {
            newImageString.append(imageString);
        }
        return formatImage(newImageString.toString(), width, height);
    }

    public static String formatImage(String imageString, int width, int height) {
        StringBuilder returnValue = new StringBuilder();
        for (int i = 0; i < height; i++) {
            returnValue.append(imageString, i*width, (i+1)*width);
            returnValue.append("\n");
        }
        return returnValue.toString();
    }

    public static Map<Pair<Integer, Integer>, String> buildImageMap(String imageWithLineBreaks) {
        return buildImageMap(parseToList(imageWithLineBreaks, "\n"));
    }

    public static Map<Pair<Integer, Integer>, String> buildImageMap(List<String> imageLines) {
        Map<Pair<Integer, Integer>, String> map = new HashMap<>();
        for (int j = 0; j < imageLines.size(); j++) {
            String line = imageLines.get(j);
            for (int i = 0; i < line.length(); i++) {
                map.put(Pair.with(i, j), line.substring(i, i + 1));
            }
        }
        return map;
    }

    public static Set<Pair<Integer, Integer>> buildConwaySet(List<String> input, String representationOfActiveState) {
        return buildImageMap(input).entrySet().stream().filter(entry -> representationOfActiveState.equals(entry.getValue())).map(Map.Entry::getKey).collect(Collectors.toSet());
    }

    public static int getManhattanDistance(Pair<Integer, Integer> pos1, Pair<Integer, Integer> pos2) {
        return Math.abs(pos1.getValue0() - pos2.getValue0()) + Math.abs(pos1.getValue1() - pos2.getValue1());
    }

    public static boolean isInRectangle(Pair<Integer, Integer> positionToCheck, Pair<Integer, Integer> cornerOfRectangle, Pair<Integer, Integer> oppositeCornerOfRectangle) {
        boolean returnValue = true;
        if (positionToCheck.getValue0() < cornerOfRectangle.getValue0() && positionToCheck.getValue0() < oppositeCornerOfRectangle.getValue0()) {
            returnValue = false;
        }
        if (positionToCheck.getValue0() > cornerOfRectangle.getValue0() && positionToCheck.getValue0() > oppositeCornerOfRectangle.getValue0()) {
            returnValue = false;
        }
        if (positionToCheck.getValue1() < cornerOfRectangle.getValue1() && positionToCheck.getValue1() < oppositeCornerOfRectangle.getValue1()) {
            returnValue = false;
        }
        if (positionToCheck.getValue1() > cornerOfRectangle.getValue1() && positionToCheck.getValue1() > oppositeCornerOfRectangle.getValue1()) {
            returnValue = false;
        }
        return returnValue;
    }

    public static List<Pair<Integer, Integer>> drawLine(Pair<Integer, Integer> start, Pair<Integer, Integer> end) {
        List<Pair<Integer, Integer>> line = new ArrayList<>();
        if (start.getValue0().equals(end.getValue0()) || start.getValue1().equals(end.getValue1()) || Math.abs(start.getValue0() - end.getValue0()) == Math.abs(start.getValue1() - end.getValue1())) {
            int xIncrement = end.getValue0().compareTo(start.getValue0());
            int yIncrement = end.getValue1().compareTo(start.getValue1());
            for (Pair<Integer, Integer> linePoint = start; !end.equals(linePoint); linePoint = Pair.with(linePoint.getValue0() + xIncrement, linePoint.getValue1() + yIncrement)) {
                line.add(linePoint);
            }
            line.add(end);
        } else {
            throw new IllegalArgumentException();
        }
        return line;
    }

    public static long getPolygonSize(List<Pair<Integer, Integer>> polygonPoints, boolean withLines) {
        // inner size up to middle of line
        long result = 0;
        for (int i = 0; i < polygonPoints.size(); i++) {
            Pair<Integer, Integer> p1 = polygonPoints.get(i);
            Pair<Integer, Integer> p2 = polygonPoints.get((i + 1) % polygonPoints.size());
            result += (long) p2.getValue1() * p1.getValue0() - (long) p1.getValue1() * p2.getValue0();
        }
        result = Math.abs(result) / 2;


        if (withLines) {
            result += getPolygonLineLength(polygonPoints) / 2;
        } else {
            result -= getPolygonLineLength(polygonPoints) / 2;
        }
        // +1 for 4 outer corners
        return result + 1;
    }

    public static long getPolygonLineLength(List<Pair<Integer, Integer>> polygonPoints) {
        long result = 0;
        for (int i = 0; i < polygonPoints.size(); i++) {
            Pair<Integer, Integer> p1 = polygonPoints.get(i);
            Pair<Integer, Integer> p2 = polygonPoints.get((i + 1) % polygonPoints.size());
            result += getManhattanDistance(p1, p2);
        }
        return result;
    }


    public static int getShortestPath(Set<Pair<Integer, Integer>> start, Set<Pair<Integer, Integer>> end, BiPredicate<Pair<Integer, Integer>, Pair<Integer, Integer>> canGoFromPositionToNeighbor, TriFunction<Integer, Pair<Integer, Integer>, Pair<Integer, Integer>, Integer> getNeighborDistanceFromPositionDistance) {
        Set<Pair<Integer, Integer>> seenPositions = new HashSet<>();
        Map<Integer, Set<Pair<Integer, Integer>>> queue = new HashMap<>();
        queue.put(0, start);
        for (int currentDistance = 0; currentDistance < Integer.MAX_VALUE; currentDistance++) {
            for (Pair<Integer, Integer> pos : queue.getOrDefault(currentDistance, Set.of())) {
                if (end.contains(pos)) {
                    return currentDistance;
                }
                for (Pair<Integer, Integer> directNeighbor : Direction.getDirectNeighbors(pos)) {
                    if (!seenPositions.contains(directNeighbor) && canGoFromPositionToNeighbor.test(pos, directNeighbor)) {
                        int neighborDistance = getNeighborDistanceFromPositionDistance.apply(currentDistance, pos, directNeighbor);
                        Set<Pair<Integer, Integer>> set = queue.getOrDefault(neighborDistance, new HashSet<>());
                        set.add(directNeighbor);
                        queue.put(neighborDistance, set);
                        seenPositions.add(directNeighbor);
                    }
                }
            }
        }
        return Integer.MAX_VALUE;
    }

    public static long greatestCommonDivisor(long long1, long long2) {
        if (long1 == long2 || long2 == 0) {
            return long1;
        }
        return greatestCommonDivisor(long2, long1 % long2);
    }

    public static long leastCommonMultiple(long long1, long long2) {
        return long1 * (long2 / greatestCommonDivisor(long1, long2));
    }

    /**
     * Based on
     * https://www.geeksforgeeks.org/multiplicative-inverse-under-modulo-m/
     */
    public static long modInverse(long a, long m)
    {
        long m0 = m;
        long y = 0;
        long x = 1;

        if (m == 1) {
            return 0;
        }

        while (a > 1) {
            // q is quotient
            long q = a / m;

            long t = m;

            // m is remainder now, process
            // same as Euclid's algo
            m = a % m;
            a = t;
            t = y;

            // Update x and y
            y = x - q * y;
            x = t;
        }

        // Make x positive
        if (x < 0) {
            x += m0;
        }

        return x;
    }

    public static long chineseRemainderTheorem(List<Pair<Long, Long>> numbersAndRemainders) {
        long[] numbers = new long[numbersAndRemainders.size()];
        long[] remainders = new long[numbersAndRemainders.size()];
        for (int i = 0; i < numbersAndRemainders.size(); i++) {
            Pair<Long, Long> numberAndRemainder = numbersAndRemainders.get(i);
            numbers[i] = numberAndRemainder.getValue0();
            remainders[i] = numberAndRemainder.getValue1();
        }

        return chineseRemainderTheorem(numbers, remainders);
    }

    /**
     * Based on
     * https://www.geeksforgeeks.org/chinese-remainder-theorem-set-2-implementation/
     */
    private static long chineseRemainderTheorem(long[] numbers, long[] remainders) {
        long product = 1;
        for (long number : numbers) {
            product *= number;
        }

        long result = 0;
        for (int i = 0; i < numbers.length; i++) {
            long partialProduct = product / numbers[i];
            result += remainders[i] * modInverse(partialProduct, numbers[i]) * partialProduct;
        }

        // the normal algorithm just returns (result % product)...but somehow we need it...
        return result % product;
    }

    public static Graph<String, DefaultWeightedEdge> buildGraphFromMap(Map<Pair<Integer, Integer>, String> map, Pair<Integer, Integer> startPosition) {
        return buildGraphFromMap(map, startPosition, new GraphConstructionHelper(map));
    }

    public static Graph<String, DefaultWeightedEdge> buildGraphFromMap(Map<Pair<Integer, Integer>, String> map, Pair<Integer, Integer> startPosition, GraphConstructionHelper helper) {
        Graph<String, DefaultWeightedEdge> graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
        Queue<GraphBuilderQueueElement> queue = new LinkedList<>();
        queue.add(new GraphBuilderQueueElement(startPosition, Util.findPossibleNextSteps(map, startPosition, null, helper), null, 0));
        while (!queue.isEmpty()) {
            buildGraph(queue, graph, map, queue.poll(), helper);
        }

        return graph;
    }

    private static void buildGraph(Queue<GraphBuilderQueueElement> queue, Graph<String, DefaultWeightedEdge> graph, Map<Pair<Integer, Integer>, String> map, GraphBuilderQueueElement queueElement, GraphConstructionHelper helper) {
        String newNodeName = helper.getNodeName(queueElement.getNodePosition(), queueElement.getParentNode());
        if (newNodeName == null || newNodeName.equals(queueElement.getParentNode())) {
            return;
        }
        graph.addVertex(newNodeName);
        if (queueElement.getParentNode() != null) {
            double newEdgeWeight = queueElement.getDistanceToParent() + 1.0;
            if (graph.containsEdge(queueElement.getParentNode(), newNodeName)) {
                DefaultWeightedEdge existingEdge = graph.getEdge(queueElement.getParentNode(), newNodeName);
                double existingEdgeWeight = graph.getEdgeWeight(existingEdge);
                if (existingEdgeWeight > newEdgeWeight) {
                    graph.setEdgeWeight(existingEdge, Math.min(existingEdgeWeight, newEdgeWeight));
                }
                //don't continue here as we already walked that path...
                return;
            }
            addEdge(graph, queueElement.getParentNode(), newNodeName, newEdgeWeight);
        }
        for (Pair<Integer, Integer> nextStep : queueElement.getNextSteps()) {
            int stepCounter = 0;
            Pair<Integer, Integer> previousPosition = queueElement.getNodePosition();
            Pair<Integer, Integer> currentPosition = nextStep;

            while (true) {
                List<Pair<Integer, Integer>> nextNextSteps = findPossibleNextSteps(map, currentPosition, Set.of(previousPosition), helper);
                if (helper.createNodeForPosition(currentPosition) || nextNextSteps.size() > 1) {
                    queue.add(new GraphBuilderQueueElement(currentPosition, nextNextSteps, newNodeName, stepCounter));
                    break;
                } else if (nextNextSteps.size() == 1) {
                    stepCounter++;
                    previousPosition = currentPosition;
                    currentPosition = nextNextSteps.getFirst();
                } else {
                    break;
                }
            }
        }
    }

    public static List<Pair<Integer, Integer>> findPossibleNextSteps(Map<Pair<Integer, Integer>, String> map, Pair<Integer, Integer> start, Set<Pair<Integer, Integer>> sources, GraphConstructionHelper helper) {
        return helper.getPossibleNavigationPositions(start)
                .stream()
                .filter(pos -> (sources == null || !sources.contains(pos)) && map.get(pos) != null && helper.isPossibleNextStep(pos))
                .collect(Collectors.toList());
    }

    public static void addEdge(Graph<String, DefaultWeightedEdge> graph, String from, String to, double weight) {
        graph.addEdge(from, to);
        graph.setEdgeWeight(from, to, weight);
    }

    public static String getOtherVertex(Graph<String, DefaultWeightedEdge> graph, DefaultWeightedEdge edge, String vertex) {
        String target = graph.getEdgeTarget(edge);
        if (vertex.equals(target)) {
            target = graph.getEdgeSource(edge);
        }
        return target;
    }

    public static String printGraphAsDOT(Graph<String, DefaultWeightedEdge> graph, Map<String, String> replacements) {
        DOTExporter<String, DefaultWeightedEdge> exporter = new DOTExporter<>(v -> {
            String vertexId = v;
            for (Map.Entry<String, String> replacement : replacements.entrySet()) {
                vertexId = vertexId.replace(replacement.getKey(), replacement.getValue());
            }
            return vertexId;
        });
        exporter.setEdgeAttributeProvider(e -> Map.of("label", DefaultAttribute.createAttribute(graph.getEdgeWeight(e))));
        Writer writer = new StringWriter();
        exporter.exportGraph(graph, writer);
        return writer.toString();
    }

    public static List<String> removeEmptyTrailingStrings(List<String> stringList) {
        int toIndex = stringList.size();
        for (int i = stringList.size() - 1; i >= 0; i--) {
            if (stringList.get(i).isEmpty()) {
                toIndex = i;
            } else {
                break;
            }
        }
        return List.copyOf(stringList.subList(0, toIndex));
    }

    private static final char[] HEX_ARRAY = "0123456789abcdef".toCharArray();
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static String getStringValueFromUser(String name, String defaultValue, IOHelper io) {
        String valueAsString = io.getInput(name + " (default " + defaultValue + "):");
        if (!valueAsString.isEmpty()) {
            return valueAsString;
        }
        return defaultValue;
    }

    public static int getIntValueFromUser(String name, int defaultValue, IOHelper io) {
        String valueAsString = io.getInput(name + " (default " + defaultValue + "):");
        if (!valueAsString.isEmpty()) {
            return Integer.parseInt(valueAsString);
        }
        return defaultValue;
    }

    public static long getLongValueFromUser(String name, long defaultValue, IOHelper io) {
        String valueAsString = io.getInput(name + " (default " + defaultValue + "):");
        if (!valueAsString.isEmpty()) {
            return Long.parseLong(valueAsString);
        }
        return defaultValue;
    }

    public static <T> T manipulateStateMultipleTimesOptimized(long totalRounds, T state, UnaryOperator<T> stateModifier) {
        Map<T, Long> history = new HashMap<>();
        for (long currentRound = 1; currentRound <= totalRounds; currentRound++) {
            state = stateModifier.apply(state);

            if (history.containsKey(state)) {
                long firstOccurrence = history.get(state);
                long cycleLength = currentRound - firstOccurrence;
                long cycles = (totalRounds - firstOccurrence) / cycleLength;
                long indexToUse = totalRounds - (cycles * cycleLength);

                return history.entrySet().stream().filter(e -> e.getValue().equals(indexToUse)).map(Map.Entry::getKey).findFirst().orElseThrow();
            }
            history.put(state, currentRound);
        }
        return state;
    }

    private static class GraphBuilderQueueElement {
        private final Pair<Integer, Integer> nodePosition;
        private final List<Pair<Integer, Integer>> nextSteps;
        private final String parentNode;
        private final int distanceToParent;

        private GraphBuilderQueueElement(Pair<Integer, Integer> nodePosition, List<Pair<Integer, Integer>> nextSteps, String parentNode, int distanceToParent) {
            this.nodePosition = nodePosition;
            this.nextSteps = nextSteps;
            this.parentNode = parentNode;
            this.distanceToParent = distanceToParent;
        }

        public Pair<Integer, Integer> getNodePosition() {
            return nodePosition;
        }

        public List<Pair<Integer, Integer>> getNextSteps() {
            return nextSteps;
        }

        public String getParentNode() {
            return parentNode;
        }

        public int getDistanceToParent() {
            return distanceToParent;
        }
    }
}
