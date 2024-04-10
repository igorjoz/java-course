package com.igorjoz.lab4;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ETI");
        EntityManager em = emf.createEntityManager();
        Scanner scanner = new Scanner(System.in);

        addTower(em, "Stare ETI", 100);
        addTower(em, "Nowe ETI", 60);

        addMage(em, "Marek", 80, "Stare ETI");
        addMage(em, "Andrzej", 40, "Stare ETI");
        addMage(em, "Mały", 50, "Stare ETI");
        addMage(em, "Magnificencja", 60, "Nowe ETI");

        boolean running = true;

        while (running) {
            System.out.println("Choose an option: ");
            System.out.println("1. Add Tower");
            System.out.println("2. Add Mage");
            System.out.println("3. Delete Mage");
            System.out.println("4. List Mages");
            System.out.println("5. List Mages with Level Greater Than");
            System.out.println("6. List Towers with Height Less Than");
            System.out.println("7. List Mages with Level Greater Than Tower Height");
            System.out.println("8. List All Towers");
            System.out.println("9. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Enter tower name:");
                    String towerName = scanner.nextLine();
                    System.out.println("Enter tower height:");
                    int height = scanner.nextInt();
                    addTower(em, towerName, height);
                    break;
                case 2:
                    System.out.println("Enter mage name:");
                    String mageName = scanner.nextLine();
                    System.out.println("Enter mage level:");
                    int level = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Enter tower name for mage:");
                    String mageTowerName = scanner.nextLine();
                    addMage(em, mageName, level, mageTowerName);
                    break;
                case 3:
                    System.out.println("Enter mage name to delete:");
                    String delMageName = scanner.nextLine();
                    deleteMage(em, delMageName);
                    break;
                case 4:
                    listMages(em);
                    break;
                case 5:
                    System.out.println("Enter level:");
                    int lvl = scanner.nextInt();
                    listMagesWithLevelGreaterThan(em, lvl);
                    break;
                case 6:
                    System.out.println("Enter height:");
                    int ht = scanner.nextInt();
                    listTowersWithHeightLessThan(em, ht);
                    break;
                case 7:
                    System.out.println("Enter tower name:");
                    String towerForMages = scanner.nextLine();
                    listMagesWithLevelGreaterThanTowerHeight(em, towerForMages);
                    break;
                case 8:
                    listTowers(em);
                    break;
                case 9:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option, please try again.");
            }
        }

        em.close();
        emf.close();
        scanner.close();
    }

    private static void addTower(EntityManager em, String name, int height) {
        Tower tower = new Tower(name, height);
        em.getTransaction().begin();
        em.persist(tower);
        em.getTransaction().commit();
    }

    private static void addMage(EntityManager em, String name, int level, String towerName) {
        Tower tower = em.find(Tower.class, towerName);
        if (tower == null) {
            System.out.println("Tower not found: " + towerName);
            return;
        }
        Mage mage = new Mage(name, level, tower);
        em.getTransaction().begin();
        em.persist(mage);
        em.getTransaction().commit();
    }

    private static void deleteMage(EntityManager em, String name) {
        Mage mage = em.find(Mage.class, name);
        if (mage != null) {
            em.getTransaction().begin();
            em.remove(mage);
            em.getTransaction().commit();
        }
    }

    private static void listMages(EntityManager em) {
        List<Mage> mages = em.createQuery("SELECT m FROM Mage m", Mage.class).getResultList();
        for (Mage mage : mages) {
            System.out.println(mage.getName() + ", Level: " + mage.getLevel());
        }
    }

    private static void listMagesWithLevelGreaterThan(EntityManager em, int level) {
        List<Mage> mages = em.createQuery("SELECT m FROM Mage m WHERE m.level > :level", Mage.class)
                .setParameter("level", level)
                .getResultList();
        for (Mage mage : mages) {
            System.out.println("Mage: " + mage.getName() + ", Level: " + mage.getLevel());
        }
    }

    private static void listTowers(EntityManager em) {
        List<Tower> towers = em.createQuery("SELECT t FROM Tower t", Tower.class).getResultList();
        for (Tower tower : towers) {
            System.out.println("Tower: " + tower.getName() + ", Height: " + tower.getHeight());
        }
    }

    private static void listTowersWithHeightLessThan(EntityManager em, int height) {
        List<Tower> towers = em.createQuery("SELECT t FROM Tower t WHERE t.height < :height", Tower.class)
                .setParameter("height", height)
                .getResultList();
        for (Tower tower : towers) {
            System.out.println("Tower: " + tower.getName() + ", Height: " + tower.getHeight());
        }
    }

    private static void listMagesWithLevelGreaterThanTowerHeight(EntityManager em, String towerName) {
        List<Mage> mages = em.createQuery("SELECT m FROM Mage m WHERE m.level > (SELECT t.height FROM Tower t WHERE t.name = :towerName)", Mage.class)
                .setParameter("towerName", towerName)
                .getResultList();
        for (Mage mage : mages) {
            System.out.println("Mage: " + mage.getName() + ", Level: " + mage.getLevel());
        }
    }
}
