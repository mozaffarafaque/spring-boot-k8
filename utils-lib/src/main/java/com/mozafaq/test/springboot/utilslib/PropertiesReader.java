package com.mozafaq.test.springboot.utilslib;

import java.util.*;
import java.io.*;

public class PropertiesReader {

    public Properties getProperties(String location) {
        try {

            FileReader reader = new FileReader(location);
            Properties p = new Properties();
            p.load(reader);
            return p;

        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
