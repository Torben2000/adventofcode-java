package de.beachboys.aoc2022;

import de.beachboys.Day;
import org.javatuples.Pair;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day13 extends Day {

    private static final Pattern LIST_PATTERN = Pattern.compile("\\[([0-9,]*)]");

    private int globalIdCounter = 20;

    public Object part1(List<String> input) {
        long result = 0;
        List<Pair<Element, Element>> pairs = new ArrayList<>();
        for (int i = 0; i < input.size(); i += 3) {
            Element e1 = parsePacketString(input.get(i));
            Element e2 = parsePacketString(input.get(i + 1));
            pairs.add(Pair.with(e1, e2));
        }

        for (int i = 0; i < pairs.size(); i++) {
            Pair<Element, Element> pair = pairs.get(i);
            if (pair.getValue0().compareTo(pair.getValue1()) < 0) {
                result += i + 1;
            }
        }

        return result;
    }

    public Object part2(List<String> input) {
        List<Element> allPackets = new ArrayList<>();
        for (int i = 0; i < input.size(); i += 3) {
            allPackets.add(parsePacketString(input.get(i)));
            allPackets.add(parsePacketString(input.get(i + 1)));
        }
        Element divider1 = parsePacketString("[[2]]");
        Element divider2 = parsePacketString("[[6]]");
        allPackets.add(divider1);
        allPackets.add(divider2);

        List<Element> sortedPackets = allPackets.stream().sorted().collect(Collectors.toList());
        return (sortedPackets.indexOf(divider1) + 1) * (sortedPackets.indexOf(divider2) + 1);
    }

    private Element parsePacketString(String packetString) {
        Map<Integer, Element> elements = new HashMap<>();
        while (true) {
            Matcher m = LIST_PATTERN.matcher(packetString);
            if (m.find()) {
                Element e = new Element();
                e.id = ++globalIdCounter;
                e.list = new ArrayList<>();
                if (!m.group(1).isEmpty()) {
                    String[] split = m.group(1).split(",");
                    for (String s : split) {
                        int val = Integer.parseInt(s);
                        if (elements.containsKey(val)) {
                            e.list.add(elements.get(val));
                        } else {
                            Element subElement = new Element();
                            subElement.value = val;
                            e.list.add(subElement);
                        }
                    }
                }
                elements.put(e.id, e);
                packetString = packetString.replace(m.group(0), e.id + "");
            } else {
                break;
            }
        }
        return elements.get(globalIdCounter);
    }

    private static class Element implements Comparable<Element> {

        int id;
        Integer value = null;
        List<Element> list = new ArrayList<>();

        @Override
        public int compareTo(Element o) {
            if (o == null) {
                return 1;
            }
            if (this.value != null && o.value != null) {
                return Integer.compare(this.value, o.value);
            } else if (this.value != null && o.value == null) {
                Element tempElement = new Element();
                tempElement.list.add(this);
                return tempElement.compareTo(o);
            } else if (this.value == null && o.value != null) {
                Element tempElement = new Element();
                tempElement.list.add(o);
                return this.compareTo(tempElement);
            } else {
                for (int i = 0; i < list.size(); i++) {
                    if (o.list.size() >= i + 1) {
                        int c = list.get(i).compareTo(o.list.get(i));
                        if (c != 0) {
                            return c;
                        }
                    } else {
                        return 1;
                    }
                }
                if (o.list.size() > list.size()) {
                    return -1;
                }
            }
            return 0;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Element element = (Element) o;
            return id == element.id;
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }
    }

}
