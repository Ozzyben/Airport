package checkIn;

public class CheckInException extends Exception {

    public CheckInException(String message){
        super("Passenger booking ref " + message + " is already checked in.");
    }
}
