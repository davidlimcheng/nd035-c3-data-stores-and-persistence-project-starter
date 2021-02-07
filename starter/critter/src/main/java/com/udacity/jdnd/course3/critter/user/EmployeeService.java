package com.udacity.jdnd.course3.critter.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;

    @Transactional
    public Employee save(Employee newEmployee) {
        return employeeRepository.save(newEmployee);
    }

    @Transactional
    public void setEmployeeAvailability(Set<DayOfWeek> daysAvailable, long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).get();
        employee.setDaysAvailable(daysAvailable);
    }

    @Transactional
    public Employee findById(long employeeId) {
        return employeeRepository.findById(employeeId).get();
    }

    @Transactional
    public Set<Employee> findAllByAvailabilityAndSkill(LocalDate date, HashSet<EmployeeSkill> skills) {
        DayOfWeek dayofWeek = date.getDayOfWeek();

        // our derived jpa query method will return employees with a partial match of the skills
        // so we need to add additional logic to ensure that we only return employees whose skills fully match the request
        Set<Employee> possibleEmployees = employeeRepository.findAllByDaysAvailableContainingAndSkillsIn(dayofWeek, skills);
        Set<Employee> matchedEmployees = new HashSet<Employee>();

        possibleEmployees.forEach(employee -> {
            if (employee.getSkills().containsAll(skills)) {
                matchedEmployees.add(employee);
            }
        });

        return matchedEmployees;
    }
}
