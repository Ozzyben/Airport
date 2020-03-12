package checkIn;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Airport {

    private HashMap<String, Passenger> waitingRoom;
    private HashMap<String, Flight> planes;
    private CheckInGUI GUI;

    private int feePerExtraBag = 25;    //fee for each bag after first
    private int excessBagSize = 3;      //size bag can be before excess fees apply
    private int excessBagWeight = 20;   //weight a bag can be before excess fees apply
    private int excessFee = 50;         //the excess fee needed for over volume/weight

    public Airport() {
        waitingRoom = new HashMap<>();
        planes = new HashMap<>();
        GUI = new CheckInGUI();
    }

    /*Main, duh*/
    public static void main(String[] args) {
        Airport a = new Airport();      //object
        a.run();                        //run it
    }

    /*Will be called main
     * Contains code for main running loop/process
     * Non static content*/
    public void run() {
        //done
        Boolean running = true;
        readFlightData("FlightData");
        readPassengerData("PassengerData");

        GUI.createAndShowGUI();                                                 //Generate gui
        do {
            String userInput = GUI.getUserInput();                              //Request first contact
            if (userInput.toLowerCase().trim().equals("exit")) {                //exit option chosen
                System.out.println(writeReport());                              //gen reports
                running = false;                                                //end loop
            } else {                                                            //exit not chosen so booking reference and last name requested
                String[] userInfo = userInput.split(",");                //separate into ref and name
                String ref = userInfo[0].trim();
                String name = userInfo[1].trim();
                if (waitingRoom.containsKey(ref)) {                             //does given reference key apply to person in waiting room
                    if (waitingRoom.get(ref).getLastName().equals(name)) {      //does the last name given match the ref
                        double passengerFees = 0.0;                             //Fees for baggage
                        int bagNum = 0;                                         //Number of bags added
                        double tempFee = addBagToPassenger(ref, bagNum);        //Attempt to add first bag to passenger
                        while (tempFee >= 0.0) {                                //While user wants to add a bag
                            passengerFees += tempFee;                           //add new fee to total
                            bagNum++;
                            tempFee = addBagToPassenger(ref, bagNum);           //attempt to add another bag to passenger
                        }
                        planes.get(waitingRoom.get(ref).getFlightCode()).checkInToFlight(waitingRoom.get(ref), passengerFees); //find plane customer is on and check customer onto it
                        waitingRoom.remove(ref);                                //remove customer from waiting room
                    } else {
                        GUI.ErrorScreen(2);                          //last name and ref dont match show error screen
                    }
                } else {
                    GUI.ErrorScreen(1);                              //reference doesnt exist show error screen
                }
            }
        } while (running);                                                      //keep running
        System.exit(0);
    }

    /*Will read in flight data from file
     * And store within flight map*/
    private void readFlightData(String fileName) {
        //done
        BufferedReader br = null; //reader

        try {
            br = new BufferedReader(new FileReader(fileName));      //file reader
            String inputLine = br.readLine();                       //read the first line
            while (inputLine != null) {                             //while its got stuff
                String[] data = inputLine.split(",");        //split by commas
                for (int i = 0; i < data.length; i++) {
                    data[i] = data[i].trim();                       //trim off white space
                }
                if(data.length == 6) {
                    try {
                        Flight f = new Flight(data[0], data[1], data[2], Integer.parseInt(data[3]), Integer.parseInt(data[4]), Integer.parseInt(data[5])); //make new flight object using read data
                        planes.put(data[0], f);                             //put the flight in the map with flight code as ref
                    } catch (InvalidDataException e) {
                        System.out.println("Could not create flight.\n" + e.getMessage());
                        System.exit(0);
                    }
                } else {
                    System.out.println("Flight data line invalid. Dropping line");
                }
                inputLine = br.readLine();                          //read the next line
            }
        } catch (FileNotFoundException e) {
            System.out.println("FLight data file not found\n" + e);
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                //do nothing
            }
        }
    }


    /*Will read in passenger data from file
     * and store within passenger map waitingRoom*/
    private void readPassengerData(String fileName) {
        //done
        BufferedReader br = null; //reader

        try {
            br = new BufferedReader(new FileReader(fileName));      //file reader
            String inputLine = br.readLine();                       //read first line
            while (inputLine != null) {                             //while its got data on it
                String[] data = inputLine.split(",");        //split up by commas
                for (int i = 0; i < data.length; i++) {
                    data[i] = data[i].trim();                       //trim off white space
                }
                if(data.length == 5) {
                    Boolean checked = Boolean.parseBoolean(data[4]);    //make checked in into boolean
                    try {
                        Passenger p = new Passenger(data[0], data[1], data[2], data[3], checked); //create new passenger from line info
                        if (checked) {                                      //if they've already checked in
                            planes.get(data[3]).addPassenger(p);            //add directly to the flight
                        } else {                                            //if not
                            waitingRoom.put(data[0], p);                    //add to waiting room
                        }
                    } catch (InvalidDataException e) {
                        System.out.println("Could not create passenger.\n" + e.getMessage());
                        System.exit(0);
                    }
                } else {
                    System.out.println("Data line invalid. Dropping line");
                }
                inputLine = br.readLine();                          //read next line
            }
        } catch (FileNotFoundException e) {
            System.out.println("checkIn.Passenger data file not found\n" + e);
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                //do nothing
            }
        }
        //literally a copy paste from flight reader
    }

    /*Will call gui to get bag information
     * Will verify baggage size and weight
     * Will call gui to request excess fees if required
     * Will add bag to passenger and return with excess fees paid*/
    private double addBagToPassenger(String reference, int bagNumber) {
        //done
        List<Double> info = GUI.getPassengerBagInfo();                      //get user bag info
        if (info.get(0) < 0) {                                              //if -1 bag not wanting added
            return -1.0;                                                    //pass back
        } else {                                                            //bag wants added
            double bagVol = info.get(0);                                    //volume is first entry
            double bagWeight = info.get(1);                                 //weight is second
            double fee = 0.0;                                               //initalise fee counter
            //generate fees
            fee += (bagNumber * feePerExtraBag);                            //fee per bag > 1
            if (bagVol > excessBagSize) {                                   //is bag excess volume
                fee += excessFee;                                           //add fee
            }
            if (bagWeight > excessBagWeight) {                              // is bag excess weight
                fee += excessFee;                                           //add fee
            }
            //request fee from user and add bag
            if (fee > 0) {                                                  //if there is a fee
                if (GUI.requestFees(fee)) {                                 //call gui to request payment
                    Bag b = new Bag(bagVol, bagWeight);                     //create bag
                    waitingRoom.get(reference).addBag(b);                   //add bag to passenger in waiting room
                    return fee;                                             //return the totalled fee of bag
                } else {
                    GUI.ErrorScreen(4);                          //fee not paid show error screen (this one too??)
                    return addBagToPassenger(reference, bagNumber);         //see if customer wants to add different bag instead
                }
            } else {                                                        //no fee required, just add bag
                Bag b = new Bag(bagVol, bagWeight);                         //create bag
                waitingRoom.get(reference).addBag(b);                       //add bag to customer in waiting room
                return 0.0;                                                 //return no fee
            }
        }
    }

    /*Will call each flight to generate a report and store in list*/
    private List<String> collectReports() {
        //done
        Set<String> flightList = planes.keySet();   //generate iterable
        List<String> reports = new ArrayList<>();   //initialise list

        for (String key : flightList) {             //iterate over map
            reports.add(planes.get(key).report());  //request each flight to gen report and store in list
        }

        return reports;                             //return list
    }

    /*Takes in list of reports and formats them into longform final output string
     * write this to file as well as return*/
    private String writeReport() {
        //done
        String fullReport = "";                                                         //full report string
        List<String> reports = collectReports();                                        //grab all flight reports


        fullReport += "Report from completed session\n";
        fullReport += java.time.LocalDate.now() + "\n\n";
        for (String line : reports) {
            fullReport += line; //add each report
            fullReport += "\n";
        }
        fullReport += "Total passengers not checked in: " + waitingRoom.size();         //final line

        String fileName = "report" + System.currentTimeMillis() + ".txt";               //make new file name
        try {
            File write = new File(fileName);                                            //file
            if (!write.exists()) {                                                      //if its not already real
                write.createNewFile();                                                  //make it
                //System.out.println("File created successfully");
            } else {
                System.out.println("File already exits\nReports not saved");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedWriter fw = new BufferedWriter(new FileWriter(fileName));           //writer
            fw.write(fullReport);                                                       //write the whole report string
            fw.close();
            System.out.println("Report written to: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fullReport;                                                              //return the whole report string
    }
}
