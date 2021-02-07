package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetService;
import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.CustomerService;
import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    @Autowired
    ScheduleService scheduleService;

    @Autowired
    PetService petService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    CustomerService customerService;

    // Create Schedule
    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule newSchedule = convertScheduleDTOToSchedule(scheduleDTO);
        Schedule savedSchedule = scheduleService.save(newSchedule);

        return convertScheduleToScheduleDTO(savedSchedule);
    }

    // Find All Schedules
    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> schedules = scheduleService.findAll();
        return convertScheduleListToScheduleDTO(schedules);
    }

    // Get Schedule By Pet ID
    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        Pet pet = petService.findById(petId);
        List<Schedule> schedules = scheduleService.findAllByPet(pet);

        return convertScheduleListToScheduleDTO(schedules);
    }

    // Get Schedule By Employee ID
    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        Employee employee = employeeService.findById(employeeId);
        List<Schedule> schedules = scheduleService.findAllByEmployee(employee);

        return convertScheduleListToScheduleDTO(schedules);
    }

    // Get Schedule by Customer ID
    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        Customer customer = customerService.findById(customerId);
        List<Schedule> schedules = scheduleService.findAllByCustomer(customer);

        return convertScheduleListToScheduleDTO(schedules);
    }

    private Schedule convertScheduleDTOToSchedule(ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, schedule);

        Set<Pet> schedulePets = scheduleDTO.getPetIds().stream().map(id -> petService.findById(id)).collect(Collectors.toSet());
        Set<Employee> scheduleEmployees = scheduleDTO.getEmployeeIds().stream().map(id -> employeeService.findById(id)).collect(Collectors.toSet());

        schedule.setPets(schedulePets);
        schedule.setEmployees(scheduleEmployees);

        return schedule;
    }

    private ScheduleDTO convertScheduleToScheduleDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, scheduleDTO);

        List<Long> petIds = schedule.getPets().stream().map(Pet::getId).collect(Collectors.toList());
        List<Long> employeeIds = schedule.getEmployees().stream().map(Employee::getId).collect(Collectors.toList());

        scheduleDTO.setPetIds(petIds);
        scheduleDTO.setEmployeeIds(employeeIds);

        return scheduleDTO;
    }

    private List<ScheduleDTO> convertScheduleListToScheduleDTO(List<Schedule> schedules) {
        return schedules.stream().map(this::convertScheduleToScheduleDTO).collect(Collectors.toList());
    }
}
