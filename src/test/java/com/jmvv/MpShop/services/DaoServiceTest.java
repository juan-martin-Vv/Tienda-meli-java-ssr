package com.jmvv.MpShop.services;

import static org.junit.Assert.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

@ExtendWith(MockitoExtension.class)
// @RunWith(SpringRunner.class)
// @SpringBootTest
public class DaoServiceTest {


    @InjectMocks
    @Autowired
    // @Mock
    DaoService daoService;
   
    @BeforeEach
    void setup(){
        daoService.PostConstruct();
    }

    @Test
    void testGetAllPhones() {
        System.out.println("test");
        System.out.println(daoService);
        System.out.println("in :"+daoService.getHelo("pepe"));
        System.out.println(daoService.getAllPhones());
        assertFalse(daoService.getAllPhones().isEmpty());
    }

}
