package com.example.adoptionproject;

import com.example.adoptionproject.entities.Animal;
import com.example.adoptionproject.entities.Espece;
import com.example.adoptionproject.repositories.AnimalRepository;
import com.example.adoptionproject.services.AdoptionServicesImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AnimalServiceTest {

    @Mock
    private AnimalRepository animalRepository;

    @InjectMocks
    private AdoptionServicesImpl adoptionServices;

    @Test
    public void testAddAnimalSuccess() {
        // Arrange
        Animal animal = new Animal();
        animal.setNom("Rex");
        animal.setAge(3);
        animal.setSterilise(false);
        animal.setEspece(Espece.CHIEN);
        
        when(animalRepository.save(any(Animal.class))).thenReturn(animal);

        // Act
        Animal result = adoptionServices.addAnimal(animal);

        // Assert
        assertNotNull(result);
        assertEquals("Rex", result.getNom());
        assertEquals(Espece.CHIEN, result.getEspece());
        verify(animalRepository, times(1)).save(animal);
    }
}