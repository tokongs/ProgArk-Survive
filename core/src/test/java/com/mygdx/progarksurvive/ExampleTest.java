package com.mygdx.progarksurvive;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExampleTest {

    @Test
    void testExample() {
        String test = "Hei :)";
        assertEquals(test, "Hei :)", "test should be equal to Hei :)");
    }
}