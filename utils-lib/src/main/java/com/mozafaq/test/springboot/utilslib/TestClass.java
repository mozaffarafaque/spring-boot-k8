package com.mozafaq.test.springboot.utilslib;

import java.util.UUID;

public class TestClass {


    public String randomString() {
        return UUID.randomUUID().toString();
    }
}
