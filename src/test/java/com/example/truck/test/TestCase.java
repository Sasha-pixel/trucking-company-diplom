package com.example.truck.test;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import com.example.truck.infrastructure.configuration.ApplicationConfiguration;
import org.junit.jupiter.api.extension.ExtendWith;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@SpringBootTest
@ContextConfiguration(classes = {ApplicationConfiguration.class})
@ActiveProfiles("test")
public abstract class TestCase {

}
