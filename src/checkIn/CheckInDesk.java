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

    private Log log;

    public CheckInDesk (String id, Queue<Passenger> queue, HashMap<String, Flight> planes, CheckInGUI gUI2){
        this.id = id;
        this.setQueue(queue);
        this.planes = planes;
        this.GUI = gUI2;
        deskNumber = deskCount++;
        log = Log.getInstance();
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
        while(true) {
            while (!(queue.isEmpty())) {
                System.out.println(id + ": Remaining in queue " + queue.size());
                checkInPassenger();
                //GUI.update();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(id + " closed.");
            try {
                Thread.sleep(1000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void checkInPassenger(){
        currentPassenger = queue.remove();
        log.updateLog(id + ": Currently checking in " + currentPassenger.getName());
        String flightCode = currentPassenger.getFlightCode();
        planes.get(flightCode).addPassenger(currentPassenger);
        double fee = currentPassenger.bagCost();
        planes.get(flightCode).addFee(fee);
        log.updateLog(id + ": Finished checking in " + currentPassenger.getName() + ", required £" + fee + " luggage fee");
    }

	public Queue<Passenger> getQueue() {
		return queue;
	}
	public void setQueue(Queue<Passenger> queue) {
		this.queue = queue;
	}
	

}
