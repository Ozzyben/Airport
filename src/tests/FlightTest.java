package tests;

import checkIn.Bag;
import checkIn.Flight;
import checkIn.InvalidDataException;
import checkIn.Passenger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FlightTest {

    Flight f;

    @BeforeEach
    void setUp() throws InvalidDataException {
        f = new Flight("ED1235", "Edinburgh", "Air France", 4, 250, 25);
        Passenger p1 = new Passenger("RU64532353HA", "Ruddy", "Hannah Catriona", "ED1235", true);
        Bag b1 = new Bag(1, 10);
        Bag b2 = new Bag(2, 20);
        Bag b3 = new Bag(3, 30);
        p1.addBag(b1);
        p1.addBag(b2);
        p1.addBag(b3);

        Passenger p2 = new Passenger("HI11740092PE", "Hill", "Pete", "ED1235", true);
        Bag b4 = new Bag(4, 40);
        Bag b5 = new Bag(5, 50);
        Bag b6 = new Bag(6, 60);
        p2.addBag(b4);
        p2.addBag(b5);
        p2.addBag(b6);

        f.addPassenger(p1);
        f.addPassenger(p2);
    }

    @Test
    void testSumming(){
        assertEquals(21, f.sumFlightSize());
        assertEquals(210, f.sumFlightWeight());
    }

    @Test
    void testCheckIn() throws InvalidDataException {
        Passenger p = new Passenger("HO53639615JA", "Horgan", "Jamie Caldwell", "ED1235",false);
        assertFalse(p.getCheckedIn());
        f.checkInToFlight(p,100);
        assertEquals(100, f.getExcessFees());
    }

    @Test
    void testValidCode(){
        String validCode = "AB1234";
        String invalidLength = "AB123";
        String invalidLetters = "123456";
        String pickle = "Pickle";

        assertFalse(f.invalidFlightCode(validCode));
        assertTrue(f.invalidFlightCode(invalidLength));
        assertTrue(f.invalidFlightCode(invalidLetters));
        assertTrue(f.invalidFlightCode(pickle));
    }
}