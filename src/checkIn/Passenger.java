package checkIn;

import java.util.ArrayList;
import java.util.List;

public class Passenger {
    private String bookingReference;
    private String lastName;
    private String firstNames;
    private String flightCode;
    private Boolean checkedIn;
    private Bag bag;
    //private int fee;

    public Passenger(String bookingReference, String lastName, String firstNames, String flightCode, Boolean checkedIn) throws InvalidDataException {
        if(invalidBookingReference(bookingReference)){
            throw new InvalidDataException("The booking reference: " + bookingReference + " is invalid.");
        } else {
            this.bookingReference = bookingReference;
        }
        this.lastName = lastName;
        this.firstNames = firstNames;
        this.flightCode = flightCode;
        this.checkedIn = checkedIn;
    }

    public Boolean invalidBookingReference(String ref){
        if(ref.length() == 12){
            return !Character.isLetter(ref.charAt(0)) ||
                    !Character.isLetter(ref.charAt(1)) ||
                    !Character.isDigit(ref.charAt(2)) ||
                    !Character.isDigit(ref.charAt(3)) ||
                    !Character.isDigit(ref.charAt(4)) ||
                    !Character.isDigit(ref.charAt(5)) ||
                    !Character.isDigit(ref.charAt(6)) ||
                    !Character.isDigit(ref.charAt(7)) ||
                    !Character.isDigit(ref.charAt(8)) ||
                    !Character.isDigit(ref.charAt(9)) ||
                    !Character.isLetter(ref.charAt(10)) ||
                    !Character.isLetter(ref.charAt(11));
        }
        return true;
    }


    /*Changes check in status to true
    * Throws error if already checked in
    * Use custom exception*/
    public void CheckIn() throws CheckInException{
        if(checkedIn==false) {
        	checkedIn=true;
        } else {
            throw new CheckInException(this.bookingReference);
        }
    }


    /*Creates new bag object
    * Adds new bag to list in passenger*/
    public void addBag(Bag b){
        bag = b;
    }

    /*Will sum the total weight of all the baggage this passenger has*/
    public double totalWeight(){
        if(bag != null) {
            return bag.getWeight();
        } else{
            return 0;
        }
    }
    /*Will sum up the total size of all the baggage this passenger has*/
    public double totalSize(){
        if(bag != null){
            return bag.getSize();
        } else {
            return 0;
        }

    }
    
    /*//public int fee()
    {
    	int feePerExtraBag = 25;    //fee for each bag after first
        int excessBagSize = 121;      //size bag can be before excess fees apply
        int excessBagWeight = 32;   //weight a bag can be before excess fees apply
        int excessFee = 50;         //the excess fee needed for over volume/weight
    	int fee = 0;                                               //initalise fee counter
        //generate fees
                  
        if (bag.getSize()> excessBagSize) {                                   //is bag excess volume
            fee += excessFee;                                           //add fee
        }
        if (bag.getWeight() > excessBagWeight) {                              // is bag excess weight
            fee += excessFee;                                           //add fee
        }
        return fee;
    }*/


    //Auto generated getter and setters
    public String getLastName() {
        return lastName;
    }

    public String getFlightCode() {
        return flightCode;
    }

    public Boolean getCheckedIn() {
        return checkedIn;
    }
}
