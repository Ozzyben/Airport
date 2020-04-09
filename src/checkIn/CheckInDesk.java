package checkIn;

import java.util.HashMap;
import java.util.Queue;

public class CheckInDesk extends Thread {

    private Queue<Passenger> queue;
    private Passenger passenger;
    private HashMap<String, Flight> planes;
    private CheckInGUI GUI;

    public CheckInDesk (Queue<Passenger> queue, HashMap<String, Flight> planes, CheckInGUI GUI){
        this.queue = queue;
        this.planes = planes;
        this.GUI = GUI;
    }

    public void run() {
        while(!queue.isEmpty()) {
            passenger = queue.remove();
            System.out.println(passenger.getFlightCode());
            String flightCode = passenger.getFlightCode();
            planes.get(flightCode).addPassenger(passenger);
        }
    }

}
