package checkIn;

public class Timer extends Thread {

    private int seconds = 10;

    public Timer() {
    }

    public void run() {
        for (int i = 0; i < seconds; i++) {
            System.out.println((seconds - i) + " seconds left");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
