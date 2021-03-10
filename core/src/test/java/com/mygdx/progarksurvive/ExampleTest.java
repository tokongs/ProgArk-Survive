package com.mygdx.progarksurvive;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExampleTest {

    @Mock
    private List mockedList;

    @Test
    void testExample() {
        String test = "Hei :)";
        assertEquals(test, "Hei :)", "test should be equal to Hei :)");
    }

    @Test
    void testMocking(){
        when(mockedList.get(0)).thenReturn("Hei :)");
        assertEquals(mockedList.get(0), "Hei :)", "Mocking failed :(");
    }
}