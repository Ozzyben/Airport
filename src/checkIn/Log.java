package checkIn;

public class Log {

    private static Log instance = new Log();
    private String log;

    private void Log(){
        log = "Airport Log\n\n";
    }

    public static Log getInstance(){
        return instance;
    }

    public void updateLog(String data){
        log += data + "\n";
        System.out.println(data);
    }

    public String getLog(){
        return log;
    }
}
