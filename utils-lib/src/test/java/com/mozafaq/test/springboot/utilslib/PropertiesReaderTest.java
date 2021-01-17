package com.mozafaq.test.springboot.utilslib;

import org.testng.annotations.Test;

import java.util.Properties;

import static org.testng.Assert.*;

public class PropertiesReaderTest {

    String fileLocation = System.getenv("HOME") + "/codebase/spring-boot-k8/etc/secret.properties";

    @Test
    public void testGetProperties() {
        PropertiesReader propertiesReader = new PropertiesReader();
        Properties properties =
                propertiesReader.getProperties(fileLocation);
        System.out.println(properties);
        assertEquals(properties.get("access.key"), "efgh");
        assertEquals(properties.get("secret.key"), "abcd");
    }

}