package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.CustomerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {
    @Autowired
    PetService petService;

    @Autowired
    CustomerService customerService;

    // Save Pet
    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet newPet = convertPetDTOToPet(petDTO);
        Pet savedPet = petService.save(newPet);

        return convertPetToPetDTO(savedPet);
    }

    // Get Pet by ID
    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        Pet pet = petService.findById(petId);
        return convertPetToPetDTO(pet);
    }

    @GetMapping
    public List<PetDTO> getPets(){
        List<Pet> pets = petService.findAll();
        return convertListPetToPetDTO(pets);
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        List<Pet> pets = petService.findAllByOwnerId(ownerId);
        return convertListPetToPetDTO(pets);
    }

    private PetDTO convertPetToPetDTO(Pet pet) {
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO);

        petDTO.setOwnerId(pet.getOwner().getId());
        return petDTO;
    }

    private Pet convertPetDTOToPet(PetDTO petDTO) {
        Customer owner = customerService.findById(petDTO.getOwnerId());

        Pet newPet = new Pet();
        BeanUtils.copyProperties(petDTO, newPet);
        newPet.setOwner(owner);

        Set<Pet> ownerPets = owner.getPets();

        if (ownerPets != null) {
            ownerPets.add(newPet);
            owner.setPets(ownerPets);
        } else {
            Set<Pet> newOwnerPets = new HashSet<Pet>();
            newOwnerPets.add(newPet);
            owner.setPets(newOwnerPets);
        }

        return newPet;
    }

    private List<PetDTO> convertListPetToPetDTO(List<Pet> pets) {
        return pets.stream().map(this::convertPetToPetDTO).collect(Collectors.toList());
    }
}
