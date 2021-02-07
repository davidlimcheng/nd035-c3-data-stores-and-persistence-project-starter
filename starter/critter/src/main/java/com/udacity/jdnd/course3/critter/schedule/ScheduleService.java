package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ScheduleService {
    @Autowired
    ScheduleRepository scheduleRepository;

    @Transactional
    public Schedule save(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    @Transactional
    public List<Schedule> findAll() {
        return scheduleRepository.findAll();
    }

    @Transactional
    public List<Schedule> findAllByPet(Pet pet) {
        return scheduleRepository.findAllByPetsContaining(pet);
    }

    @Transactional
    public List<Schedule> findAllByEmployee(Employee employee) {
        return scheduleRepository.findAllByEmployeesContaining(employee);
    }

    @Transactional
    public List<Schedule> findAllByCustomer(Customer customer) {
        Set<Pet> pets = customer.getPets();
        Set<Schedule> schedules = new HashSet<>();

        pets.forEach(pet -> {
            List<Schedule> petSchedules = scheduleRepository.findAllByPetsContaining(pet);
            schedules.addAll(petSchedules);
        });

        return new ArrayList<>(schedules);
    }
}
