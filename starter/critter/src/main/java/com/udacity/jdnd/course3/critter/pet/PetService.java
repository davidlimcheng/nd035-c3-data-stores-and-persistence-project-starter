package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.CustomerRepository;
import org.hibernate.tool.schema.internal.exec.ScriptTargetOutputToFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PetService {
    @Autowired
    PetRepository petRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Transactional
    public Pet findById(long id) {
        return petRepository.findById(id).get();
    }

    @Transactional
    public Pet save(Pet newPet) {
        return petRepository.save(newPet);
    }

    @Transactional
    public List<Pet> findAllByOwnerId(long ownerId) {
        return petRepository.findAllByOwnerId(ownerId);
    }

    @Transactional
    public List<Pet> findAll() {
        return petRepository.findAll();
    }
}
