package com.igorjoz.lab1;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Set<Mage> mages;

        // Determine the sorting mode from command-line arguments
        String sortingMode = null;
        if (args.length > 0) {
            sortingMode = args[0];

            if ("natural".equals(sortingMode)) {
                mages = new TreeSet<>(); // Use natural ordering
            } else if ("alternative".equals(sortingMode)) {
                mages = new TreeSet<>(new MageComparator()); // Use alternative sorting criterion
            } else {
                mages = new HashSet<>(); // No sorting
            }
        } else {
            // Default to no sorting if no arguments provided
            mages = new HashSet<>();
        }

        // Create mages and apprentices hierarchy
        Mage archMage = new Mage("Gandalf the White", 20, 2000.0);
        mages.add(archMage);

        Mage mage1 = new Mage("Mage1", 10, 1000.0);
        archMage.addApprentice(mage1);

        Mage mage2 = new Mage("Mage2", 11, 1100.0);
        archMage.addApprentice(mage2);

        Mage apprentice1 = new Mage("Apprentice1", 1, 100.0);
        mage1.addApprentice(apprentice1);

        Mage apprentice2 = new Mage("Apprentice2", 2, 200.0);
        mage1.addApprentice(apprentice2);

        Mage apprentice3 = new Mage("Apprentice3", 2, 150.0);
        mage2.addApprentice(apprentice3);

        // Additional mages and apprentices to meet the requirements
        Mage apprentice4 = new Mage("Apprentice4", 3, 250.0);
        mage2.addApprentice(apprentice4);

        Mage subApprentice1 = new Mage("SubApprentice1", 1, 50.0);
        apprentice1.addApprentice(subApprentice1);

        Mage subApprentice2 = new Mage("SubApprentice2", 1, 60.0);
        apprentice2.addApprentice(subApprentice2);

        Mage subApprentice3 = new Mage("SubApprentice3", 1, 55.0);
        apprentice3.addApprentice(subApprentice3);

        // Display the hierarchy
        printMageHierarchy(mages, 0);

        printDescendantStatistics(mages, sortingMode);
    }

    private static void printMageHierarchy(Set<Mage> mages, int indent) {
        String indentation = " ".repeat(indent);
        for (Mage mage : mages) {
            System.out.println(indentation + mage);
            printMageHierarchy(mage.getApprentices(), indent + 4);
        }
    }

    public static void printDescendantStatistics(Set<Mage> mages, String sortingMode) {
        Map<Mage, Integer> statistics;

        // Choose the map implementation based on the sorting mode
        if ("natural".equals(sortingMode) || "alternative".equals(sortingMode)) {
            statistics = new TreeMap<>(new MageComparator()); // Or use the natural ordering if applicable
        } else {
            statistics = new HashMap<>();
        }

        // Populate the map with descendant counts
        for (Mage mage : mages) {
            statistics.put(mage, mage.countDescendants());
            // For a more comprehensive count, consider all descendants, not just top-level ones.
        }

        // Print the map entries
        statistics.forEach((mage, count) -> System.out.println(mage + " has " + count + " descendants"));
    }
}
