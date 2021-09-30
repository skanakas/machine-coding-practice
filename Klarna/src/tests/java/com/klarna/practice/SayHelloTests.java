package com.klarna.practice;

import org.junit.Test;

import static org.junit.Assert.*;

import org.junit.Test;

public class SayHelloTests {
  
    // These example test cases are editable, feel free to add
    // your own tests to debug your code.
  
    @Test
    public void shouldSayHello() throws ValidationException {
        assertEquals("Hello, Qualified!", Challenge.sayHello("Qualified"));
    }
  
    @Test(expected=ValidationException.class)
    public void testNullValue() throws ValidationException {
        Challenge.sayHello(null);
    }
  
    @Test(expected=ValidationException.class)
    public void testEmptyInput() throws ValidationException {
        Challenge.sayHello("");
    }
    
    @Test(expected=ValidationException.class)
    public void testIntegerStringInput() throws ValidationException {
        Challenge.sayHello("1234353");
    }
}
