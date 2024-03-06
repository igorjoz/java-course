package com.igorjoz.lab1;

import java.util.Comparator;

public class MageComparator implements Comparator<Mage> {
    @Override
    public int compare(Mage m1, Mage m2) {
        // First compare by level
        int levelCompare = Integer.compare(m1.getLevel(), m2.getLevel());
        if (levelCompare != 0) {
            return levelCompare;
        }

        // If levels are equal, then compare by name
        int nameCompare = m1.getName().compareTo(m2.getName());
        if (nameCompare != 0) {
            return nameCompare;
        }

        // If names are also equal, finally compare by power
        return Double.compare(m1.getPower(), m2.getPower());
    }
}
