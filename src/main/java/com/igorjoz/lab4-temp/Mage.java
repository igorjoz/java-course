package com.igorjoz.lab4;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Mage {
    @Id
    private String name;
    private int level;
    @ManyToOne
    private Tower tower;

    // getters and setters
}
