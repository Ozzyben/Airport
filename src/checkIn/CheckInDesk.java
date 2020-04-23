package checkIn;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Queue;

public class CheckInDesk extends Thread {

    public String id;
	private Queue<Passenger> queue;
    public Passenger currentPassenger;
    private HashMap<String, Flight> planes;
    private CheckInGUI GUI;
    private static int deskCount = 0;
    public int deskNumber = 0;

    public CheckInDesk (String id, Queue<Passenger> queue, HashMap<String, Flight> planes, CheckInGUI GUI){
        this.id = id;
        this.setQueue(queue);
        this.planes = planes;
        this.GUI = GUI;
        deskNumber = deskCount++;
    }
/*    
    private void addPassanger(Passenger passenger){
    	if (queue.contains(passenger)){
    		System.out.println("Passenger is already in the queue for this desk!");
    	}else{
    		queue.add(passenger);
    	}
    }
*/
/* 
    private String createQueueList(){
    	String passsengerList = "";
    	if (!queue.isEmpty()){
    		Iterator<Passenger> iterator = queue.iterator();
    		while (iterator.hasNext()){
    			Passenger passenger = iterator.next();
    			passsengerList += passenger.getLastName() + "\n";
    		}
    	}
    	return passsengerList;
    }
*/
    public void run() {

        while(!(queue.isEmpty())) {
            System.out.println(id + " " + queue.size());
            currentPassenger = queue.remove();
            System.out.println(currentPassenger.getLastName());
            String flightCode = currentPassenger.getFlightCode();
            planes.get(flightCode).addPassenger(currentPassenger);
            planes.get(flightCode).addFee(currentPassenger.bagCost());
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

	public Queue<Passenger> getQueue() {
		return queue;
	}
	public void setQueue(Queue<Passenger> queue) {
		this.queue = queue;
	}
	

}
