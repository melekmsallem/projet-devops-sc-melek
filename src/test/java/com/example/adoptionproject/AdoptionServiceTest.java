package com.example.adoptionproject;

import com.example.adoptionproject.entities.Adoptant;
import com.example.adoptionproject.entities.Adoption;
import com.example.adoptionproject.entities.Animal;
import com.example.adoptionproject.entities.Espece;
import com.example.adoptionproject.repositories.AdoptantRepository;
import com.example.adoptionproject.repositories.AdoptionRepository;
import com.example.adoptionproject.repositories.AnimalRepository;
import com.example.adoptionproject.services.AdoptionServicesImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AdoptionServiceTest {

    @Mock
    private AdoptionRepository adoptionRepository;

    @Mock
    private AdoptantRepository adoptantRepository;

    @Mock
    private AnimalRepository animalRepository;

    @InjectMocks
    private AdoptionServicesImpl adoptionServices;

    @Test
    public void testAddAdoptionSuccess() {
        // Arrange
        Adoptant adoptant = new Adoptant();
        adoptant.setIdAdoptant(1);
        adoptant.setNom("Test");
        adoptant.setAdresse("Adresse");
        adoptant.setTelephone(123456);

        Animal animal = new Animal();
        animal.setIdAnimal(1);
        animal.setNom("Rex");
        animal.setAge(3);
        animal.setSterilise(false);
        animal.setEspece(Espece.CHIEN);

        Adoption adoption = new Adoption();
        adoption.setFrais(100.0f);

        when(adoptantRepository.findById(1)).thenReturn(Optional.of(adoptant));
        when(animalRepository.findById(1)).thenReturn(Optional.of(animal));
        when(adoptionRepository.save(Mockito.<Adoption>any())).thenReturn(adoption);

        // Act
        Adoption result = adoptionServices.addAdoption(adoption, 1, 1);

        // Assert
        assertNotNull(result);
        assertEquals(100.0f, result.getFrais());
        assertEquals(adoptant, result.getAdoptant());
        assertEquals(animal, result.getAnimal());
    }

    @Test
    public void testAddAdoptionWithInvalidIds() {
        when(adoptantRepository.findById(anyInt())).thenReturn(Optional.empty());

        Adoption adoption = new Adoption();
        Adoption result = adoptionServices.addAdoption(adoption, 999, 999);

        assertNull(result);
    }
}