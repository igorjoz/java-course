package com.igorjoz.lab1;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Mage implements Comparable<Mage> {
    private String name;
    private int level;
    private double power;
    private Set<Mage> apprentices;

    public Mage(String name, int level, double power) {
        this.name = name;
        this.level = level;
        this.power = power;
        this.apprentices = new HashSet<>();
    }

    // Methods to add and remove apprentices
    public void addApprentice(Mage apprentice) {
        apprentices.add(apprentice);
    }

    public void removeApprentice(Mage apprentice) {
        apprentices.remove(apprentice);
    }

    @Override
    public int compareTo(Mage other) {
        // Natural ordering based on name, then level, then power
        int nameCompare = this.name.compareTo(other.name);
        if (nameCompare != 0) return nameCompare;

        int levelCompare = Integer.compare(this.level, other.level);
        if (levelCompare != 0) return levelCompare;

        return Double.compare(this.power, other.power);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Mage mage = (Mage) o;

        if (level != mage.level) return false;
        if (Double.compare(mage.power, power) != 0) return false;
        return name.equals(mage.name);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = name.hashCode();
        result = 31 * result + level;
        temp = Double.doubleToLongBits(power);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Mage{" +
                "name='" + name + '\'' +
                ", level=" + level +
                ", power=" + power +
                ", apprentices=" + apprentices.size() +
                '}';
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public double getPower() {
        return power;
    }

    public Set<Mage> getApprentices() {
        return Collections.unmodifiableSet(apprentices); // Protect internal set
    }

    public int countDescendants() {
        int count = 0;
        for (Mage apprentice : apprentices) {
            count += 1 + apprentice.countDescendants();
        }
        return count;
    }
}
