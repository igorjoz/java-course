package com.igorjoz.lab4;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Tower {
    @Id
    private String name;
    private int height;

    @OneToMany(mappedBy = "tower", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Mage> mages = new ArrayList<>();

    public Tower() {}

    public Tower(String name, int height) {
        this.name = name;
        this.height = height;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public List<Mage> getMages() {
        return mages;
    }

    public void addMage(Mage mage) {
        mages.add(mage);
        mage.setTower(this);
    }

    public void removeMage(Mage mage) {
        mages.remove(mage);
        mage.setTower(null);
    }
}
