package tests;

import checkIn.Bag;
import checkIn.InvalidDataException;
import checkIn.Passenger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PassengerTest {

    Passenger p;

    @BeforeEach
    void setUp() throws InvalidDataException {
        p = new Passenger("RU64532353HA", "Ruddy", "Hannah Catriona", "PR6523", false);
    }

    @Test
    void checkInTest(){
        assertFalse(p.getCheckedIn());
        try{p.CheckIn();}catch(Exception e){}//do nothing it will pass
        assertThrows(Exception.class, () -> p.CheckIn());
    }

    @Test
    void bagTest(){
        Bag b1 = new Bag(1, 10);
        Bag b2 = new Bag(2, 20);
        Bag b3 = new Bag(3, 30);
        p.addBag(b1);
        p.addBag(b2);
        p.addBag(b3);

        assertEquals(60, p.totalWeight());
        assertEquals(6, p.totalSize());
    }

    @Test
    void testValidRef(){
        String validRef = "AB12345678CD";
        String invalidLength = "EF1234GH";
        String invalidLetters = "123456789011";
        String asparaginase = "asparaginase";

        assertFalse(p.invalidBookingReference(validRef));
        assertTrue(p.invalidBookingReference(invalidLength));
        assertTrue(p.invalidBookingReference(invalidLetters));
        assertTrue(p.invalidBookingReference(asparaginase));
    }
}