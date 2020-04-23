package checkIn;

import java.util.List;
import java.util.Queue;
import java.util.Random;

public class QueueManager extends Thread {
    public List<Passenger> waitingRoom;
    public Queue<Passenger> queueing;
    private Log log;

    public QueueManager(List<Passenger> waitingRoom, Queue<Passenger> queueing) {
        this.waitingRoom = waitingRoom;
        this.queueing = queueing;
        log = Log.getInstance();
    }

    public void run() {
        while (!waitingRoom.isEmpty()) {
            try {
                Thread.sleep((long) (Math.random() * 100));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Passenger p = waitingRoom.get((int) Math.floor(Math.random() * waitingRoom.size()));
            addBagToPassenger(p);
            queueing.add(p);
            log.updateLog("Passenger " + p.getName() + " joined queue");
        }
    }

    private void addBagToPassenger(Passenger p) {
        Random random = new Random();
        int weight = 1 + random.nextInt(30 - 1);
        double volume = (1 + random.nextInt(5000 - 1)) / 1000;
        Bag b = new Bag(volume, weight);
        p.addBag(b);
    }


}
