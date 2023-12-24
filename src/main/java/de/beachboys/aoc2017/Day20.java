package de.beachboys.aoc2017;

import de.beachboys.Day;
import org.jooq.lambda.tuple.Tuple;
import org.jooq.lambda.tuple.Tuple3;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day20 extends Day {

    private static class Particle {

        private Tuple3<Integer, Integer, Integer> position;
        private Tuple3<Integer, Integer, Integer> velocity;
        private final Tuple3<Integer, Integer, Integer> acceleration;

        public Particle(Tuple3<Integer, Integer, Integer> position, Tuple3<Integer, Integer, Integer> velocity, Tuple3<Integer, Integer, Integer> acceleration) {
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
        return Math.abs(particle.acceleration.v1) + Math.abs(particle.acceleration.v2) + Math.abs(particle.acceleration.v3);
    }

    private int getTotalVelocityOffset(Particle particle) {
        int velocityX = getVelocityOffsetForDimension(particle.acceleration.v1, particle.velocity.v1);
        int velocityY = getVelocityOffsetForDimension(particle.acceleration.v2, particle.velocity.v2);
        int velocityZ = getVelocityOffsetForDimension(particle.acceleration.v3, particle.velocity.v3);
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
                Tuple3<Integer, Integer, Integer> position = Tuple.tuple(positionX, positionY, positionZ);
                int velocityX = Integer.parseInt(m.group(4));
                int velocityY = Integer.parseInt(m.group(5));
                int velocityZ = Integer.parseInt(m.group(6));
                Tuple3<Integer, Integer, Integer> velocity = Tuple.tuple(velocityX, velocityY, velocityZ);
                int accelerationX = Integer.parseInt(m.group(7));
                int accelerationY = Integer.parseInt(m.group(8));
                int accelerationZ = Integer.parseInt(m.group(9));
                Tuple3<Integer, Integer, Integer> acceleration = Tuple.tuple(accelerationX, accelerationY, accelerationZ);
                particles.add(new Particle(position, velocity, acceleration));
            }
        }
        return particles;
    }

    private Tuple3<Integer, Integer, Integer> addTriplets(Tuple3<Integer, Integer, Integer> triplet1, Tuple3<Integer, Integer, Integer> triplet2) {
        return Tuple.tuple(triplet1.v1 + triplet2.v1, triplet1.v2 + triplet2.v2, triplet1.v3 + triplet2.v3);
    }

}
