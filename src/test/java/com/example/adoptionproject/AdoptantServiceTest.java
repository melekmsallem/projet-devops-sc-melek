package com.example.adoptionproject;

import com.example.adoptionproject.entities.Adoptant;
import com.example.adoptionproject.repositories.AdoptantRepository;
import com.example.adoptionproject.services.AdoptionServicesImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdoptantServiceTest {  // Nom cohérent avec le fichier

    @Mock
    private AdoptantRepository adoptantRepository;

    @InjectMocks
    private AdoptionServicesImpl adoptionServices;

    @Test
    public void testAddAdoptantSuccess() {
        // 1. Préparation des données de test
        Adoptant adoptant = new Adoptant();
        adoptant.setIdAdoptant(1);
        adoptant.setNom("Jean Dupont");
        adoptant.setAdresse("123 Rue de Paris");
        adoptant.setTelephone(123456789);

        // 2. Configuration du comportement du mock
        when(adoptantRepository.save(any(Adoptant.class))).thenReturn(adoptant);

        // 3. Exécution de la méthode à tester
        Adoptant result = adoptionServices.addAdoptant(adoptant);

        // 4. Vérifications des résultats
        assertNotNull(result, "L'adoptant retourné ne devrait pas être null");
        assertEquals("Jean Dupont", result.getNom());
        assertEquals(123456789, result.getTelephone());

        // 5. Vérification que la méthode du repository a bien été appelée
        verify(adoptantRepository, times(1)).save(adoptant);
    }

    @Test
    public void testAddAdoptantWithNullValues() {
        // Test avec des valeurs nulles
        Adoptant adoptant = new Adoptant(); // Tous les champs sont null

        when(adoptantRepository.save(any(Adoptant.class))).thenReturn(adoptant);

        Adoptant result = adoptionServices.addAdoptant(adoptant);

        assertNotNull(result);
        assertNull(result.getNom()); // Vérifie que le nom est bien null
        verify(adoptantRepository, times(1)).save(adoptant);
    }
}