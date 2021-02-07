package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.pet.Pet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    @Transactional
    public Customer save(Customer newCustomer) {
        return customerRepository.save(newCustomer);
    }

    @Transactional
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Transactional
    public Customer findById(long id) {
        return customerRepository.findById(id).get();
    }

    @Transactional
    public Customer findByPet(Pet pet) {
        return customerRepository.findByPetsContaining(pet);
    }
}
