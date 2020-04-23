package checkIn;

import java.util.Queue;

public class QueueManager extends Thread{
    public Queue<Passenger> waitingRoom;
    public Queue<Passenger> queueing;
    private Log log;

    public QueueManager(Queue<Passenger> waitingRoom, Queue<Passenger> queueing){
        this.waitingRoom = waitingRoom;
        this.queueing = queueing;
        log = Log.getInstance();
    }

    public void run(){
        while(!waitingRoom.isEmpty()){
            try {
                Thread.sleep((long) (Math.random()*100));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Passenger p = waitingRoom.remove();
            queueing.add(p);
            log.updateLog("Passenger " + p.getName() + " joined queue");
        }
    }


}
