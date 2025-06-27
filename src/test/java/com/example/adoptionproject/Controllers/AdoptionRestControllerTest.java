package com.example.adoptionproject.Controllers;

import com.example.adoptionproject.controllers.AdoptionRestController;
import com.example.adoptionproject.entities.Adoption;
import com.example.adoptionproject.services.IAdoptionServices;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// AdoptionRestControllerTest.java
@WebMvcTest(AdoptionRestController.class)
class AdoptionRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IAdoptionServices adoptionServices;

    @Test
    void testGetAdoptionsByAdoptant() throws Exception {
        when(adoptionServices.getAdoptionsByAdoptant("Test"))
                .thenReturn(List.of(new Adoption()));

        mockMvc.perform(get("/byAdoptant/Test"))
                .andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(1)));

    }
}
