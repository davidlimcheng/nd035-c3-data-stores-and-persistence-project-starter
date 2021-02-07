package com.udacity.jdnd.course3.critter.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.HashSet;
import java.util.Set;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    public Set<Employee> findAllByDaysAvailableContainingAndSkillsIn(DayOfWeek day, HashSet<EmployeeSkill> skills);
}
