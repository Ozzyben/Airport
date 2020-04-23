package checkIn;

import java.io.*;
import java.util.*;
import java.util.concurrent.LinkedBlockingDeque;

public class Airport {

    public volatile Queue<Passenger> waitingRoom;
    public volatile HashMap<String, Flight> planes;
    public volatile CheckInGUI GUI;

    public volatile Log log;

    private int feePerExtraBag = 25;    //fee for each bag after first
    private int excessBagSize = 3;      //size bag can be before excess fees apply
    private int excessBagWeight = 20;   //weight a bag can be before excess fees apply
    private int excessFee = 50;         //the excess fee needed for over volume/weight

    public List<CheckInDesk> desks;
    
    public Airport() {
        waitingRoom = new LinkedList<>();
        planes = new HashMap<>();
        GUI = new CheckInGUI(this);
        desks = new ArrayList<>();
        log = Log.getInstance();
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
        //generateTestData("FlightDataRand", "PassengerDataRand");
        readFlightData("FlightDataRand");
        readPassengerData("PassengerDataRand");
        addBagToPassenger();
        System.out.println("Queue is " + waitingRoom.size() + " people long");

        for(int i = 0; i < 2; i++){
            String name = "Desk" + (i+1);
            newDesk(name);
        }
        //GUI.createAndShowGUI();
        try {
            desks.get(0).join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(!waitingRoom.isEmpty()){
            try {
                desks.get(0).join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else if(waitingRoom.isEmpty()) {
            log.updateLog("All desks closed, queue empty.\nGenerating report.");
            writeReport();
            writeLogToFile();
            System.exit(0);
        }

    }

    private void newDesk(String deskName){
        CheckInDesk desk = new CheckInDesk(deskName, waitingRoom, planes, GUI);
        log.updateLog("New check in desk " + deskName + " opened.");
        desk.start();
        desks.add(desk);
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
                        //System.out.println("Could not create flight.\n" + e.getMessage());
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
                if (data.length == 5) {
                    Boolean checked = Boolean.parseBoolean(data[4]);    //make checked in into boolean
                    try {
                        Passenger p = new Passenger(data[0], data[1], data[2], data[3], checked); //create new passenger from line info
                        if (checked) {                                      //if they've already checked in
                            planes.get(data[3]).addPassenger(p);            //add directly to the flight
                        } else {                                            //if not
                            waitingRoom.add(p);                    //add to waiting room
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
    private void writeReport() {
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
    }

    private void writeLogToFile() {
        String fileName = "log" + System.currentTimeMillis() + ".txt";               //make new file name
        try {
            File write = new File(fileName);                                            //file
            if (!write.exists()) {                                                      //if its not already real
                write.createNewFile();                                                  //make it
                //System.out.println("File created successfully");
            } else {
                System.out.println("File already exits\nLog not saved");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedWriter fw = new BufferedWriter(new FileWriter(fileName));           //writer
            fw.write(log.getLog());                                                       //write the whole report string
            fw.close();
            System.out.println("Log written to: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void generateTestData(String flightDataName, String passengerDataName){
        String[] firstName = {"Jerry", "Herbert", "Tatiana", "Herman", "Eilidh", "Elizabeth", "Bruno", "Theodore", "Louise", "Chloe", "Jamie", "Ryan", "Hermione", "Michael"};
        String[] lastName = {"McKay", "Hogg", "Tonyoli", "Gorse", "Feasby", "Cassidy", "Edwards", "Marshall", "Hughes", "Reilly", "Swartz"};

        String[] destinations = {"Glasgow", "Edinburgh", "London", "Strasbourg", "Paris", "New York", "Berlin", "Frankfurt", "Krakow", "Amsterdam", "Oslo", "Stockholm", "Prague"};
        String[] airlines = {"British Airways", "Air France", "Easyjet", "Turkish Airlines", "KLM", "Ryanair"};

        String flightData = "";
        String passengerData = "";

        for(int i = 0; i < 20; i++){
            String destination = destinations[(int) Math.round(Math.random() * (destinations.length-1))];
            String airline = airlines[(int) Math.round(Math.random() * (airlines.length-1))];
            String flightCode = destination.substring(0,2) + (Math.round(Math.random() * 8999)+1000);
            int passengerNum = (int) ((Math.round(Math.random() * 15)+5)*10);
            flightData += flightCode + ", " + destination + ", " + airline + ", " + passengerNum + ", " + ((Math.round(Math.random()*20))*50) + ", " + ((Math.round(Math.random()*20))*50) + "\n";
            for(int j = 0; j < 20/*(passengerNum - ((int)Math.round(Math.random()*50)))*/; j++){
                String FName = firstName[(int) Math.round(Math.random() * (firstName.length-1))];
                String LName = lastName[(int) Math.round(Math.random()* (lastName.length-1))];
                String passengerCode = LName.substring(0,2) + (Math.round(Math.random()*89999999)+10000000) + FName.substring(0,2);
                passengerData += passengerCode + ", " + LName + ", " + FName + ", " + flightCode + ", FALSE\n";
            }
        }
        try {
            File write = new File(flightDataName);                                            //file
            if (!write.exists()) {                                                      //if its not already real
                write.createNewFile();                                                  //make it
                //System.out.println("File created successfully");
            } else {
                System.out.println("File already exits\nTest data not saved");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedWriter fw = new BufferedWriter(new FileWriter(flightDataName));           //writer
            fw.write(flightData);                                                       //write the whole report string
            fw.close();
            System.out.println("Test data written to: " + flightDataName);
        } catch (IOException e) {
            e.printStackTrace();
        }	

        try {
            File write = new File(passengerDataName);                                            //file
            if (!write.exists()) {                                                      //if its not already real
                write.createNewFile();                                                  //make it
                //System.out.println("File created successfully");
            } else {
                System.out.println("File already exits\nTest data not saved");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedWriter fw = new BufferedWriter(new FileWriter(passengerDataName));           //writer
            fw.write(passengerData);                                                       //write the whole report string
            fw.close();
            System.out.println("Test data written to: " + passengerDataName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //Will add a bag to all Passenger in the waiting room with random values
    private void addBagToPassenger()
    {
    	for(Passenger p : waitingRoom) 
    	{
    		Random random = new Random();
        	int bag_x= 15 + random.nextInt(150-15); 
        	int bag_y= 10 + random.nextInt(70-10);
        	int bag_z= 10 + random.nextInt(50-10); 
        	int weight = 1 + random.nextInt(100-1);
    		double volume = (bag_x*bag_y*bag_z)/1000.0;
        	Bag b = new Bag(volume, weight);
        	p.addBag(b);
    	}
    }
}
