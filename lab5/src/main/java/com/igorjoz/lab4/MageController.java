package com.igorjoz.lab4;

import java.util.Optional;

public class MageController {
    private MageRepository repository;

    public MageController(MageRepository repository) {
        this.repository = repository;
    }

    public String find(String name) {
        return repository.find(name)
                .map(mage -> "Found: " + mage.getName() + " Level: " + mage.getLevel())
                .orElse("not found");
    }

    public String delete(String name) {
        Optional<Mage> mageOptional = repository.find(name);
        if (!mageOptional.isPresent()) {
            return "not found";
        }
        repository.delete(name);
        return "done";
    }

//    public String save(String name, int level) {
//        Optional<Mage> existingMage = repository.find(name);
//        if (existingMage.isPresent()) {
//            return "bad request";
//        }
//        Mage newMage = new Mage(name, level);
//        repository.save(newMage);
//        return "done";
//    }

    public String save(String name, int level) {
        if (level < 0) {
            return "bad request";
        }
        Optional<Mage> existingMage = repository.find(name);
        if (existingMage.isPresent()) {
            return "done";
        } else {
            Mage newMage = new Mage(name, level);
            repository.save(newMage);
            return "done";
        }
    }
}
