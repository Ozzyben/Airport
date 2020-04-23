package checkIn;


import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;

import javax.swing.*;

public class CheckInGUI {

	// alterable width and height of the window. all components are scaled to width
		// and height
		int width = 400;
		int height = 400;
		
		JFrame frame;
		
		JTextArea passengerList = new JTextArea();
		JTextArea Desks = new JTextArea();
		JTextArea flights = new JTextArea();
		JPanel desksHolder = new JPanel(); 
		
		Airport airport;
		private Queue<Passenger> queue;
		
		public CheckInGUI(Airport airport) {
			this.airport = airport;
		}
		public void createAndShowGUI() {
			
			frame = new JFrame();
			frame.setSize(width, height);
			//frame.setPreferredSize(new Dimension(width, height));
			frame.setVisible(true);
			
			
			displayPassengers();
			displayFlights();
			displayDesks();
			
			
			frame.repaint();
			
			//attempt to slow the run-time so you can see the GUI
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public void displayPassengers() {
			
			//size is just the same width as the frame and 1/3 of the height. this will change for iteration 2
			int passengerListWidth = width;
			int PassengerListHeight = height/3;
			passengerList.setBounds(0, 0, passengerListWidth, PassengerListHeight);
			
			Iterator<Passenger> it = airport.waitingRoom.iterator();    
	        	int i = 0;
	        	while(it.hasNext()) {
	        		Passenger hold = it.next();
	        		JTextArea thisPassenger = new JTextArea(hold.getFlightCode()+"  "+hold.getLastName()+"  "+hold.totalWeight()+"  "+ hold.totalSize());
	        		thisPassenger.setBounds(0, i/airport.waitingRoom.size(), width, airport.waitingRoom.size());
				thisPassenger.setVisible(true);
				passengerList.add(thisPassenger);
				i++;
	       		}
			
			
			passengerList.setVisible(true);
			frame.add(passengerList);
			
		}
		
		public void displayDesks(){
			
			Iterator<CheckInDesk> iterator = airport.desks.iterator();
			int totalDesks = airport.desks.size();
			while (iterator.hasNext()){
				createDeskDisplay(iterator.next(), totalDesks);
			}
			
			frame.add(desksHolder);
		}


		public void displayFlights(){
			
			int flightsWidth = width;
			int flightsHeight = height/3;
			flights.setBounds(0, 2*height/3, flightsWidth, flightsHeight);
			
			Flight flightDisplayed = airport.planes.get(airport.desk1.currentPassenger.getFlightCode());
			flights.setText(flightDisplayed.flightCode + "  "+ flightDisplayed.destination);
			
			flights.setVisible(true);
			frame.add(flights);
		}
		
		void createDeskDisplay(CheckInDesk desk, int totalDesks){
			int desksWidth = width/totalDesks;
			int desksHeight = height/3;
			Desks.setName("Desk " + desk.deskNumber);
			Desks.setBounds((desksWidth*totalDesks) - desksWidth, height/3, desksWidth, desksHeight);
			
			Desks.setText(desk.currentPassenger.getLastName()+" is dropping off 1 bag of "+
			desk.currentPassenger.totalWeight()+". A baggage fee of "+"**FEE**"+" is due.");
			
			Desks.setVisible(true);
			desksHolder.add(Desks);
		}
		
		void updateQueue() {
			
			
			
		}
}
