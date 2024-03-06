package com.igorjoz.lab1;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Set<Mage> mages;

        // command-line arguments - determine sorting mode
        String sortingMode = null;
        if (args.length > 0) {
            // get first argument
            sortingMode = args[0];

            if ("natural".equals(sortingMode)) { // natural ordering
                mages = new TreeSet<>();
            } else if ("alternative".equals(sortingMode)) { // alternative sorting
                mages = new TreeSet<>(new MageComparator());
            } else { // no sorting
                mages = new HashSet<>();
            }
        } else { // no arguments provided - default to no sorting
            mages = new HashSet<>();
        }

        // create main mage - 1. hierarchy level
        Mage bossMage = new Mage("Gandalf the White", 20, 2000.0);
        mages.add(bossMage);

        // create 2. hierarchy level
        Mage mage1 = new Mage("Mage1", 10, 1000.0);
        bossMage.addApprentice(mage1);

        Mage mage2 = new Mage("Mage2", 11, 1100.0);
        bossMage.addApprentice(mage2);

        // create 3. hierarchy level
        Mage apprentice1 = new Mage("Apprentice1", 1, 100.0);
        mage1.addApprentice(apprentice1);

        Mage apprentice2 = new Mage("Apprentice2", 2, 200.0);
        mage1.addApprentice(apprentice2);

        Mage apprentice3 = new Mage("Apprentice3", 2, 150.0);
        mage2.addApprentice(apprentice3);

        Mage apprentice4 = new Mage("Apprentice4", 3, 250.0);
        mage2.addApprentice(apprentice4);

        // create 4. hierarchy level
        Mage subApprentice1 = new Mage("SubApprentice1", 1, 50.0);
        apprentice1.addApprentice(subApprentice1);

        Mage subApprentice2 = new Mage("SubApprentice2", 1, 60.0);
        apprentice2.addApprentice(subApprentice2);

        Mage subApprentice3 = new Mage("SubApprentice3", 1, 55.0);
        apprentice3.addApprentice(subApprentice3);

        // display the hierarchy
        printMageHierarchy(mages, 0);

        // display the descendant statistics
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
        Map<Mage, Integer> statistics = "natural".equals(sortingMode) || "alternative".equals(sortingMode)
                ? new TreeMap<>(new MageComparator())
                : new HashMap<>();

        for (Mage mage : mages) {
            calculateDescendantStatistics(mage, statistics);
        }

        // print stats function
        statistics.forEach((mage, count) -> System.out.println(mage + " has " + count + " descendants"));
    }

    private static int calculateDescendantStatistics(Mage mage, Map<Mage, Integer> statistics) {
        int count = mage.getApprentices().size();

        for (Mage apprentice : mage.getApprentices()) {
            count += calculateDescendantStatistics(apprentice, statistics);
        }

        statistics.put(mage, count);

        return count;
    }
}







//    public static void printDescendantStatistics(Set<Mage> mages, String sortingMode) {
//        Map<Mage, Integer> statistics;
//
//        // choose the map implementation based on the sorting mode
//        if ("natural".equals(sortingMode) || "alternative".equals(sortingMode)) {
//            statistics = new TreeMap<>(new MageComparator()); // use the natural ordering if applicable
//        } else {
//            statistics = new HashMap<>();
//        }
//
//        // Populate the map with descendant counts
//        for (Mage mage : mages) {
//            statistics.put(mage, mage.countDescendants());
//            // For a more comprehensive count, consider all descendants, not just top-level ones.
//        }
//
//        // Print the map entries
//        statistics.forEach((mage, count) -> System.out.println(mage + " has " + count + " descendants"));
//    }



//    public static void printDescendantStatistics(Set<Mage> mages, String sortingMode) {
//        // Initialize a map to hold statistics for each level
//        Map<Integer, List<Mage>> levelMageMap = new HashMap<>();
//
//        // Populate the map with all mages categorized by their hierarchy levels
//        populateLevelMageMap(mages, 0, levelMageMap);
//
//        // Iterate over each level and print statistics
//        levelMageMap.keySet().stream().sorted().forEach(level -> {
//            List<Mage> magesAtLevel = levelMageMap.get(level);
//            System.out.println("Level " + level + ": " + magesAtLevel.size() + " mages");
//            // Here, you can add more detailed statistics calculations
//        });
//    }

//    private static void populateLevelMageMap(Set<Mage> mages, int currentLevel, Map<Integer, List<Mage>> levelMageMap) {
//        mages.forEach(mage -> {
//            levelMageMap.putIfAbsent(currentLevel, new ArrayList<>());
//            levelMageMap.get(currentLevel).add(mage);
//
//            // Recursively process the apprentices of the current mage
//            if (!mage.getApprentices().isEmpty()) {
//                populateLevelMageMap(mage.getApprentices(), currentLevel + 1, levelMageMap);
//            }
//        });
//    }