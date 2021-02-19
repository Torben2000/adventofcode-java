package de.beachboys.aoc2017;

import de.beachboys.Day;
import org.javatuples.Triplet;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day20 extends Day {

    private static class Particle {

        private Triplet<Integer, Integer, Integer> position;
        private Triplet<Integer, Integer, Integer> velocity;
        private final Triplet<Integer, Integer, Integer> acceleration;

        public Particle(Triplet<Integer, Integer, Integer> position, Triplet<Integer, Integer, Integer> velocity, Triplet<Integer, Integer, Integer> acceleration) {
            this.position = position;
            this.velocity = velocity;
            this.acceleration = acceleration;
        }

    }

    public Object part1(List<String> input) {
        List<Particle> particles = parseParticleList(input);

        final int minTotalAcceleration = particles.stream().mapToInt(this::getTotalAcceleration).min().orElseThrow();
        List<Particle> particlesWithMinAcceleration = particles.stream().filter(particle -> minTotalAcceleration == getTotalAcceleration(particle)).collect(Collectors.toList());

        final int minTotalVelocity = particlesWithMinAcceleration.stream().mapToInt(this::getTotalVelocityOffset).min().orElseThrow();
        List<Particle> particlesWithMinAccelerationAndMinVelocity = particlesWithMinAcceleration.stream().filter(particle -> minTotalVelocity == getTotalVelocityOffset(particle)).collect(Collectors.toList());

        if (particlesWithMinAccelerationAndMinVelocity.size() > 1) {
            return "more than one match";
        }

        return particles.indexOf(particlesWithMinAccelerationAndMinVelocity.get(0));
    }

    public Object part2(List<String> input) {
        List<Particle> particles = parseParticleList(input);

        for (int round = 0; round < 100; round++) {
            moveParticles(particles);
            Set<Integer> indicesToRemove = getIndicesOfDuplicatePositions(particles);
            indicesToRemove.stream().sorted(Comparator.reverseOrder()).mapToInt(Integer::intValue).forEachOrdered(particles::remove);
        }
        return particles.size();
    }

    private int getTotalAcceleration(Particle particle) {
        return Math.abs(particle.acceleration.getValue0()) + Math.abs(particle.acceleration.getValue1()) + Math.abs(particle.acceleration.getValue2());
    }

    private int getTotalVelocityOffset(Particle particle) {
        int velocityX = getVelocityOffsetForDimension(particle.acceleration.getValue0(), particle.velocity.getValue0());
        int velocityY = getVelocityOffsetForDimension(particle.acceleration.getValue1(), particle.velocity.getValue1());
        int velocityZ = getVelocityOffsetForDimension(particle.acceleration.getValue2(), particle.velocity.getValue2());
        return velocityX + velocityY + velocityZ;
    }

    private int getVelocityOffsetForDimension(int acceleration, int velocity) {
        int factor = 1;
        if (acceleration != 0) {
            factor = acceleration / Math.abs(acceleration);
        } else if (velocity != 0){
            factor = velocity / Math.abs(velocity);
        }
        return velocity * factor;
    }

    private void moveParticles(List<Particle> particles) {
        for (Particle particle : particles) {
            particle.velocity = addTriplets(particle.velocity, particle.acceleration);
            particle.position = addTriplets(particle.position, particle.velocity);
        }
    }

    private Set<Integer> getIndicesOfDuplicatePositions(List<Particle> particles) {
        Set<Integer> indicesToRemove = new HashSet<>();
        for (int i = 0; i < particles.size(); i++) {
            Particle particle1 = particles.get(i);
            for (int j = i + 1; j < particles.size(); j++) {
                Particle particle2 = particles.get(j);
                if (particle1.position.equals(particle2.position)) {
                    indicesToRemove.add(i);
                    indicesToRemove.add(j);
                }
            }
        }
        return indicesToRemove;
    }

    private List<Particle> parseParticleList(List<String> input) {
        Pattern p = Pattern.compile("p=<(-?[0-9]+),(-?[0-9]+),(-?[0-9]+)>, v=<(-?[0-9]+),(-?[0-9]+),(-?[0-9]+)>, a=<(-?[0-9]+),(-?[0-9]+),(-?[0-9]+)>");
        List<Particle> particles = new ArrayList<>();
        for (String particle : input) {
            Matcher m = p.matcher(particle);
            if (m.matches()) {
                int positionX = Integer.parseInt(m.group(1));
                int positionY = Integer.parseInt(m.group(2));
                int positionZ = Integer.parseInt(m.group(3));
                Triplet<Integer, Integer, Integer> position = Triplet.with(positionX, positionY, positionZ);
                int velocityX = Integer.parseInt(m.group(4));
                int velocityY = Integer.parseInt(m.group(5));
                int velocityZ = Integer.parseInt(m.group(6));
                Triplet<Integer, Integer, Integer> velocity = Triplet.with(velocityX, velocityY, velocityZ);
                int accelerationX = Integer.parseInt(m.group(7));
                int accelerationY = Integer.parseInt(m.group(8));
                int accelerationZ = Integer.parseInt(m.group(9));
                Triplet<Integer, Integer, Integer> acceleration = Triplet.with(accelerationX, accelerationY, accelerationZ);
                particles.add(new Particle(position, velocity, acceleration));
            }
        }
        return particles;
    }

    private Triplet<Integer, Integer, Integer> addTriplets(Triplet<Integer, Integer, Integer> triplet1, Triplet<Integer, Integer, Integer> triplet2) {
        return Triplet.with(triplet1.getValue0() + triplet2.getValue0(), triplet1.getValue1() + triplet2.getValue1(), triplet1.getValue2() + triplet2.getValue2());
    }

}
