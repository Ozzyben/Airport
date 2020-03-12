package tests;

import checkIn.Bag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;



class BagTest {

    Bag b;

    @BeforeEach
    void setUp() {
        b = new Bag(10.0, 10.0);
    }

    @Test
    void sizeTest() {
        assertEquals(10, b.getSize());
        b.setSize(20.0);
        assertEquals(20, b.getSize());
    }

    @Test
    void weightTest() {
        assertEquals(10, b.getWeight());
        b.setWeight(20.0);
        assertEquals(20, b.getWeight());
    }
}