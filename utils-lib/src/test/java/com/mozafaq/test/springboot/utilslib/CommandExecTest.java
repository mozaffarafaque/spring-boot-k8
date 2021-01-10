package com.mozafaq.test.springboot.utilslib;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class CommandExecTest {

    @Test
    public void testExecuteCommand() {

       CommandExec exec = new CommandExec();
       exec.executeCommand(new String[]{"ping", "google.com", "-c", "5" }, 10_000);
    }
}