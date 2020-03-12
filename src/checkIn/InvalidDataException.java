package checkIn;

public class InvalidDataException extends Exception {

    public InvalidDataException(String m){
        super("An item of data was invalid.\n" + m);
    }
}
