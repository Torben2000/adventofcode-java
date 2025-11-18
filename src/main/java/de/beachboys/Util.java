package de.beachboys;

import com.microsoft.z3.*;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.nio.DefaultAttribute;
import org.jgrapht.nio.dot.DOTExporter;
import org.jooq.lambda.function.Function3;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple2;

import java.io.StringWriter;
import java.io.Writer;
import java.math.BigInteger;
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

    public static String paintSet(Set<Tuple2<Integer, Integer>> set) {
        return paintMap(set.stream().collect(Collectors.toMap(Function.identity(), position -> "*")));
    }

    public static String paintMap(Map<Tuple2<Integer, Integer>, String> map) {
        Map<String, String> valuesToPaint = map.values().stream().distinct().collect(Collectors.toMap(Function.identity(), Function.identity()));
        return paintMap(map, valuesToPaint);
    }

    public static String paintMap(Map<Tuple2<Integer, Integer>, String> map, Map<String, String> valuesToPaint) {
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;
        Map<Tuple2<Integer, Integer>, String> filteredMap = new HashMap<>(map);
        map.forEach((point, value) -> {
            if (valuesToPaint != null && !valuesToPaint.containsKey(value)) {
                filteredMap.remove(point);
            }
        });
        for (Tuple2<Integer, Integer> point : filteredMap.keySet()) {
            minX = Math.min(minX, point.v1);
            minY = Math.min(minY, point.v2);
            maxX = Math.max(maxX, point.v1);
            maxY = Math.max(maxY, point.v2);
        }
        int width = maxX - minX + 1;
        int height = maxY - minY + 1;
        StringBuilder imageString = new StringBuilder();
        imageString.append(" ".repeat(width*height));
        for (Tuple2<Integer, Integer> point : filteredMap.keySet()) {
            int index = width * (point.v2 - minY) + (point.v1 - minX);
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

    public static Map<Tuple2<Integer, Integer>, String> buildImageMap(String imageWithLineBreaks) {
        return buildImageMap(parseToList(imageWithLineBreaks, "\n"));
    }

    public static Map<Tuple2<Integer, Integer>, String> buildImageMap(List<String> imageLines) {
        Map<Tuple2<Integer, Integer>, String> map = new HashMap<>();
        for (int j = 0; j < imageLines.size(); j++) {
            String line = imageLines.get(j);
            for (int i = 0; i < line.length(); i++) {
                map.put(Tuple.tuple(i, j), line.substring(i, i + 1));
            }
        }
        return map;
    }

    public static Map<Tuple2<Integer, Integer>, Integer> buildIntImageMap(List<String> imageLines) {
        Map<Tuple2<Integer, Integer>, Integer> map = new HashMap<>();
        for (int j = 0; j < imageLines.size(); j++) {
            String line = imageLines.get(j);
            for (int i = 0; i < line.length(); i++) {
                map.put(Tuple.tuple(i, j), Integer.parseInt(line.substring(i, i + 1)));
            }
        }
        return map;
    }

    public static Set<Tuple2<Integer, Integer>> buildConwaySet(List<String> input, String representationOfActiveState) {
        return buildImageMap(input).entrySet().stream().filter(entry -> representationOfActiveState.equals(entry.getValue())).map(Map.Entry::getKey).collect(Collectors.toSet());
    }

    public static int getManhattanDistance(Tuple2<Integer, Integer> pos1, Tuple2<Integer, Integer> pos2) {
        return Math.abs(pos1.v1 - pos2.v1) + Math.abs(pos1.v2 - pos2.v2);
    }

    public static boolean isInRectangle(Tuple2<Integer, Integer> positionToCheck, Tuple2<Integer, Integer> cornerOfRectangle, Tuple2<Integer, Integer> oppositeCornerOfRectangle) {

        return (positionToCheck.v1 >= cornerOfRectangle.v1 || positionToCheck.v1 >= oppositeCornerOfRectangle.v1)
                && (positionToCheck.v1 <= cornerOfRectangle.v1 || positionToCheck.v1 <= oppositeCornerOfRectangle.v1)
                && (positionToCheck.v2 >= cornerOfRectangle.v2 || positionToCheck.v2 >= oppositeCornerOfRectangle.v2)
                && (positionToCheck.v2 <= cornerOfRectangle.v2 || positionToCheck.v2 <= oppositeCornerOfRectangle.v2);
    }

    public static List<Tuple2<Integer, Integer>> drawLine(Tuple2<Integer, Integer> start, Tuple2<Integer, Integer> end) {
        List<Tuple2<Integer, Integer>> line = new ArrayList<>();
        if (start.v1.equals(end.v1) || start.v2.equals(end.v2) || Math.abs(start.v1 - end.v1) == Math.abs(start.v2 - end.v2)) {
            int xIncrement = end.v1.compareTo(start.v1);
            int yIncrement = end.v2.compareTo(start.v2);
            for (Tuple2<Integer, Integer> linePoint = start; !end.equals(linePoint); linePoint = Tuple.tuple(linePoint.v1 + xIncrement, linePoint.v2 + yIncrement)) {
                line.add(linePoint);
            }
            line.add(end);
        } else {
            throw new IllegalArgumentException();
        }
        return line;
    }

    public static long getPolygonSize(List<Tuple2<Integer, Integer>> polygonPoints, boolean withLines) {
        // inner size up to middle of line
        long result = 0;
        for (int i = 0; i < polygonPoints.size(); i++) {
            Tuple2<Integer, Integer> p1 = polygonPoints.get(i);
            Tuple2<Integer, Integer> p2 = polygonPoints.get((i + 1) % polygonPoints.size());
            result += (long) p2.v2 * p1.v1 - (long) p1.v2 * p2.v1;
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

    public static long getPolygonLineLength(List<Tuple2<Integer, Integer>> polygonPoints) {
        long result = 0;
        for (int i = 0; i < polygonPoints.size(); i++) {
            Tuple2<Integer, Integer> p1 = polygonPoints.get(i);
            Tuple2<Integer, Integer> p2 = polygonPoints.get((i + 1) % polygonPoints.size());
            result += getManhattanDistance(p1, p2);
        }
        return result;
    }


    public static int getShortestPath(Set<Tuple2<Integer, Integer>> start, Set<Tuple2<Integer, Integer>> end, BiPredicate<Tuple2<Integer, Integer>, Tuple2<Integer, Integer>> canGoFromPositionToNeighbor, Function3<Integer, Tuple2<Integer, Integer>, Tuple2<Integer, Integer>, Integer> getNeighborDistanceFromPositionDistance) {
        Set<Tuple2<Integer, Integer>> seenPositions = new HashSet<>();
        Map<Integer, Set<Tuple2<Integer, Integer>>> queue = new HashMap<>();
        queue.put(0, start);
        for (int currentDistance = 0; currentDistance < Integer.MAX_VALUE; currentDistance++) {
            for (Tuple2<Integer, Integer> pos : queue.getOrDefault(currentDistance, Set.of())) {
                if (end.contains(pos)) {
                    return currentDistance;
                }
                for (Tuple2<Integer, Integer> directNeighbor : Direction.getDirectNeighbors(pos)) {
                    if (!seenPositions.contains(directNeighbor) && canGoFromPositionToNeighbor.test(pos, directNeighbor)) {
                        int neighborDistance = getNeighborDistanceFromPositionDistance.apply(currentDistance, pos, directNeighbor);
                        Set<Tuple2<Integer, Integer>> set = queue.getOrDefault(neighborDistance, new HashSet<>());
                        set.add(directNeighbor);
                        queue.put(neighborDistance, set);
                        seenPositions.add(directNeighbor);
                    }
                }
            }
        }
        return Integer.MAX_VALUE;
    }

    public static Tuple2<Integer, Integer> getNormalizedPositionOnRepeatingPattern(Tuple2<Integer, Integer> pos, int width, int height) {
        int normalizedX = modPositive(pos.v1, width);
        int normalizedY = modPositive(pos.v2, height);
        return Tuple.tuple(normalizedX, normalizedY);
    }

    public static int modPositive(int value, int mod) {
        int returnValue = value % mod;
        while (returnValue < 0) {
            returnValue += mod;
        }
        return returnValue;
    }

    public static long modPositive(long value, long mod) {
        long returnValue = value % mod;
        while (returnValue < 0) {
            returnValue += mod;
        }
        return returnValue;
    }

    public static List<Long> getPrimeFactors(long number) {
        long n = number;
        List<Long> factors = new ArrayList<>();
        for (long i = 2; i <= n; i++) {
            while (n % i == 0) {
                factors.add(i);
                n /= i;
            }
        }
        return factors;
    }

    public static long greatestCommonDivisor(long long1, long long2) {
        if (long1 == long2 || long2 == 0) {
            return long1;
        }
        return greatestCommonDivisor(long2, long1 % long2);
    }

    public static long leastCommonMultiple(long long1, long long2) {
        if (long1 == 0 || long2 == 0) {
            return 0;
        }
        return long1 * (long2 / greatestCommonDivisor(long1, long2));
    }

    /**
     * Based on
     * <a href="https://www.geeksforgeeks.org/multiplicative-inverse-under-modulo-m/">...</a>
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

    public static long chineseRemainderTheorem(List<Tuple2<Long, Long>> numbersAndRemainders) {
        long[] numbers = new long[numbersAndRemainders.size()];
        long[] remainders = new long[numbersAndRemainders.size()];
        for (int i = 0; i < numbersAndRemainders.size(); i++) {
            Tuple2<Long, Long> numberAndRemainder = numbersAndRemainders.get(i);
            numbers[i] = numberAndRemainder.v1;
            remainders[i] = numberAndRemainder.v2;
        }

        return chineseRemainderTheorem(numbers, remainders);
    }

    /**
     * Based on
     * <a href="https://www.geeksforgeeks.org/chinese-remainder-theorem-set-2-implementation/">...</a>
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

    public static Graph<String, DefaultWeightedEdge> buildGraphFromMap(Map<Tuple2<Integer, Integer>, String> map, Tuple2<Integer, Integer> startPosition) {
        return buildGraphFromMap(map, startPosition, new GraphConstructionHelper(map));
    }

    public static Graph<String, DefaultWeightedEdge> buildGraphFromMap(Map<Tuple2<Integer, Integer>, String> map, Tuple2<Integer, Integer> startPosition, GraphConstructionHelper helper) {
        Graph<String, DefaultWeightedEdge> graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
        Queue<GraphBuilderQueueElement> queue = new LinkedList<>();
        queue.add(new GraphBuilderQueueElement(startPosition, Util.findPossibleNextSteps(map, startPosition, null, helper), null, 0));
        while (!queue.isEmpty()) {
            buildGraph(queue, graph, map, queue.poll(), helper);
        }

        return graph;
    }

    private static void buildGraph(Queue<GraphBuilderQueueElement> queue, Graph<String, DefaultWeightedEdge> graph, Map<Tuple2<Integer, Integer>, String> map, GraphBuilderQueueElement queueElement, GraphConstructionHelper helper) {
        String newNodeName = helper.getNodeName(queueElement.nodePosition(), queueElement.parentNode());
        if (newNodeName == null || newNodeName.equals(queueElement.parentNode())) {
            return;
        }
        graph.addVertex(newNodeName);
        if (queueElement.parentNode() != null) {
            double newEdgeWeight = queueElement.distanceToParent() + 1.0;
            if (graph.containsEdge(queueElement.parentNode(), newNodeName)) {
                DefaultWeightedEdge existingEdge = graph.getEdge(queueElement.parentNode(), newNodeName);
                double existingEdgeWeight = graph.getEdgeWeight(existingEdge);
                if (existingEdgeWeight > newEdgeWeight) {
                    graph.setEdgeWeight(existingEdge, Math.min(existingEdgeWeight, newEdgeWeight));
                }
                //don't continue here as we already walked that path...
                return;
            }
            addEdge(graph, queueElement.parentNode(), newNodeName, newEdgeWeight);
        }
        for (Tuple2<Integer, Integer> nextStep : queueElement.nextSteps()) {
            int stepCounter = 0;
            Tuple2<Integer, Integer> previousPosition = queueElement.nodePosition();
            Tuple2<Integer, Integer> currentPosition = nextStep;

            while (true) {
                List<Tuple2<Integer, Integer>> nextNextSteps = findPossibleNextSteps(map, currentPosition, Set.of(previousPosition), helper);
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

    public static List<Tuple2<Integer, Integer>> findPossibleNextSteps(Map<Tuple2<Integer, Integer>, String> map, Tuple2<Integer, Integer> start, Set<Tuple2<Integer, Integer>> sources, GraphConstructionHelper helper) {
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

    public static List<MixedFraction> solveSystemOfLinearEquations(List<Tuple2<List<Long>, Long>> system) {
        int size = system.size();
        for (Tuple2<List<Long>, Long> line : system) {
            if (line.v1.size() != size) {
                throw new IllegalArgumentException();
            }
        }

        return switch(size) {
            case 1 -> solveSystemOfOneLinearEquation(system);
            case 2 -> solveSystemOfTwoLinearEquations(system);
            case 3 -> solveSystemOfThreeLinearEquations(system);
            default -> solveSystemOfLinearEquationsWithZ3(system, size);
        };
    }

    private static List<MixedFraction> solveSystemOfOneLinearEquation(List<Tuple2<List<Long>, Long>> system) {
        // a*x=b
        BigInteger a = BigInteger.valueOf(system.getFirst().v1.getFirst());
        BigInteger b = BigInteger.valueOf(system.getFirst().v2);
        if (BigInteger.ZERO.equals(a)) {
            return null;
        }
        return List.of(new MixedFraction(b, a));
    }

    private static List<MixedFraction> solveSystemOfTwoLinearEquations(List<Tuple2<List<Long>, Long>> system) {
        // a1*x1+b1*x2=c1
        // a2*x1+b2*x2=c2
        BigInteger a1 = BigInteger.valueOf(system.getFirst().v1.getFirst());
        BigInteger b1 = BigInteger.valueOf(system.getFirst().v1.getLast());
        BigInteger c1 = BigInteger.valueOf(system.getFirst().v2);
        BigInteger a2 = BigInteger.valueOf(system.getLast().v1.getFirst());
        BigInteger b2 = BigInteger.valueOf(system.getLast().v1.getLast());
        BigInteger c2 = BigInteger.valueOf(system.getLast().v2);
        BigInteger det = a1.multiply(b2).subtract(a2.multiply(b1));
        if (BigInteger.ZERO.equals(det)) {
            return null;
        }
        return List.of(new MixedFraction(c1.multiply(b2).subtract(c2.multiply(b1)), det),
                new MixedFraction(a1.multiply(c2).subtract(a2.multiply(c1)), det));
    }

    private static List<MixedFraction> solveSystemOfThreeLinearEquations(List<Tuple2<List<Long>, Long>> system) {
        // a1*x1+b1*x2+c1*x3=d1
        // a2*x1+b2*x2+c2*x3=d2
        // a3*x1+b3*x2+c2*x3=d3
        BigInteger a1 = BigInteger.valueOf(system.get(0).v1.get(0));
        BigInteger b1 = BigInteger.valueOf(system.get(0).v1.get(1));
        BigInteger c1 = BigInteger.valueOf(system.get(0).v1.get(2));
        BigInteger d1 = BigInteger.valueOf(system.get(0).v2);
        BigInteger a2 = BigInteger.valueOf(system.get(1).v1.get(0));
        BigInteger b2 = BigInteger.valueOf(system.get(1).v1.get(1));
        BigInteger c2 = BigInteger.valueOf(system.get(1).v1.get(2));
        BigInteger d2 = BigInteger.valueOf(system.get(1).v2);
        BigInteger a3 = BigInteger.valueOf(system.get(2).v1.get(0));
        BigInteger b3 = BigInteger.valueOf(system.get(2).v1.get(1));
        BigInteger c3 = BigInteger.valueOf(system.get(2).v1.get(2));
        BigInteger d3 = BigInteger.valueOf(system.get(2).v2);
        BigInteger det = a1.multiply(b2).multiply(c3)
                .add(b1.multiply(c2).multiply(a3))
                .add(c1.multiply(a2).multiply(b3))
                .subtract(c1.multiply(b2).multiply(a3))
                .subtract(b1.multiply(a2).multiply(c3))
                .subtract(a1.multiply(c2).multiply(b3));
        if (BigInteger.ZERO.equals(det)) {
            return null;
        }
        BigInteger x1Num = d1.multiply(b2).multiply(c3)
                .add(b1.multiply(c2).multiply(d3))
                .add(c1.multiply(d2).multiply(b3))
                .subtract(c1.multiply(b2).multiply(d3))
                .subtract(b1.multiply(d2).multiply(c3))
                .subtract(d1.multiply(c2).multiply(b3));
        BigInteger x2Num = a1.multiply(d2).multiply(c3)
                .add(d1.multiply(c2).multiply(a3))
                .add(c1.multiply(a2).multiply(d3))
                .subtract(c1.multiply(d2).multiply(a3))
                .subtract(d1.multiply(a2).multiply(c3))
                .subtract(a1.multiply(c2).multiply(d3));
        BigInteger x3Num = a1.multiply(b2).multiply(d3)
                .add(b1.multiply(d2).multiply(a3))
                .add(d1.multiply(a2).multiply(b3))
                .subtract(d1.multiply(b2).multiply(a3))
                .subtract(b1.multiply(a2).multiply(d3))
                .subtract(a1.multiply(d2).multiply(b3));
        return List.of(new MixedFraction(x1Num, det),
                new MixedFraction(x2Num, det),
                new MixedFraction(x3Num, det));
    }

    private static List<MixedFraction> solveSystemOfLinearEquationsWithZ3(List<Tuple2<List<Long>, Long>> system, int size) {
        Context z3Context = getZ3Context();
        List<RealExpr> vars = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            vars.add(z3Context.mkRealConst("x" + i));
        }

        Solver z3Solver = getZ3Solver();
        for (Tuple2<List<Long>, Long> equation : system) {
            List<ArithExpr<RealSort>> terms = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                RealExpr a = z3Context.mkReal(equation.v1.get(i));
                ArithExpr<RealSort> aX = z3Context.mkMul(a, vars.get(i));
                terms.add(aX);
            }
            @SuppressWarnings("unchecked") ArithExpr<RealSort> left = z3Context.mkAdd(terms.toArray(new ArithExpr[0]));
            RealExpr b = z3Context.mkReal(equation.v2);
            z3Solver.add(z3Context.mkEq(left, b));
        }

        if (Status.SATISFIABLE.equals(z3Solver.check())) {
            Model z3Model = z3Solver.getModel();
            List<MixedFraction> result = new ArrayList<>(size);
            for (RealExpr var : vars) {
                RatNum valueAsRationalNumber = (RatNum) z3Model.eval(var, false);
                result.add(new MixedFraction(valueAsRationalNumber.getBigIntNumerator(), valueAsRationalNumber.getBigIntDenominator()));
            }

            return result;
        } else {
            return null;
        }
    }

    private static Context z3Context;
    private static Context getZ3Context() {
        // this is not ideal because it is never closed
        // ...but reusing this and the solver improves performance
        if (z3Context == null) {
            z3Context = new Context();
        }
        return z3Context;
    }

    private static Solver z3Solver;
    private static Solver getZ3Solver() {
        if (z3Solver == null) {
            z3Solver = getZ3Context().mkSolver();
        }
        z3Solver.reset();
        return z3Solver;
    }

    private record GraphBuilderQueueElement(Tuple2<Integer, Integer> nodePosition,
                                            List<Tuple2<Integer, Integer>> nextSteps, String parentNode,
                                            int distanceToParent) {}
}
