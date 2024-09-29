package com.igorjoz.lab4;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Tower {
    @Id
    private String name;
    private int height;
    @OneToMany(mappedBy = "tower")
    private List<Mage> mages;

    // getters and setters
}
