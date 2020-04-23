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
		int width = 600;
		int height = 400;

		JFrame frame;
		Passenger currentPass;
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
			frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


			displayPassengers();
			displayFlights();
			displayDesks();

			//frame.invalidate();
			//frame.validate();
			frame.repaint();
			///frame.repaint();

			//attempt to slow the run-time so you can see the GUI

		}

		public synchronized void displayPassengers() {
			passengerList.removeAll();
			//size is just the same width as the frame and 1/3 of the height. this will change for iteration 2
			int passengerListWidth = width;
			int PassengerListHeight = height/3;
			passengerList.setBounds(0, 0, passengerListWidth, PassengerListHeight);

			Queue<Passenger> q = airport.queueing;


			Passenger[] itemsArray = new Passenger[q.size()+1];
	        itemsArray = q.toArray(itemsArray);

	       	for(int i=0; i<q.size(); i++) {
	       		/*
	       		Passenger hold = q.element();
	       		JTextArea thisPassenger = new JTextArea();
	       		thisPassenger.setText(hold.getFlightCode()+"  "+hold.getLastName()+"  "+hold.totalWeight()+"  "+ hold.totalSize());
	       		thisPassenger.setBounds(0, i*PassengerListHeight/5, passengerListWidth, PassengerListHeight/5);
	       		thisPassenger.setVisible(true);
	       		passengerList.add(thisPassenger);
	       		*/
	       			
	        		Passenger hold = itemsArray[i];
	        		if(hold!=null) {
	        			JTextArea thisPassenger = new JTextArea();
			       		thisPassenger.setText(hold.getFlightCode()+"  "+hold.getLastName()+"  "+hold.totalWeight()+"  "+ hold.totalSize());
			       		thisPassenger.setBounds(0, i*PassengerListHeight/5, passengerListWidth, PassengerListHeight/5);
			       		thisPassenger.setVisible(true);
			       		passengerList.add(thisPassenger);
	        		}
		       		



	       	}

	        passengerList.setVisible(true);
			frame.add(passengerList);

		}

		public void displayDesks(){
			List<CheckInDesk> desks_holder = airport.desks;
			Iterator<CheckInDesk> iterator = desks_holder.iterator();
			int totalDesks = airport.desks.size();
			while (iterator.hasNext()){
				createDeskDisplay(iterator.next(), totalDesks);
			}
			desksHolder.setVisible(true);
			frame.add(desksHolder);
		}


		public void displayFlights(){

			int flightsWidth = width;
			int flightsHeight = height/3;
			flights.setBounds(0, 2*height/3, flightsWidth, flightsHeight);
			if(airport.desks.get(0).currentPassenger!=null) {
				Flight flightDisplayed = airport.planes.get(airport.desks.get(0).currentPassenger.getFlightCode());
				flights.setText(flightDisplayed.flightCode + "  "+ flightDisplayed.destination);
			}
			
			

			flights.setVisible(true);
			frame.add(flights);
		}

		void createDeskDisplay(CheckInDesk desk, int totalDesks){
			int desksWidth = width/totalDesks;
			int desksHeight = height/3;
			Desks.setName("Desk " + desk.deskNumber);
			Desks.setBounds((desksWidth*desk.deskNumber) - desksWidth, height/3, desksWidth, desksHeight);

			Desks.setText(airport.desks.get(0).currentPassenger.getLastName()+" is dropping off 1 bag of "+
			airport.desks.get(0).currentPassenger.totalWeight()+". \nA baggage fee of "+desk.currentPassenger.bagCost()+" is due.");

			Desks.setVisible(true);
			desksHolder.add(Desks);
		}

		public void update() {


			displayPassengers();
			displayFlights();
			displayDesks();

			//frame.revalidate();
			frame.repaint();

		}
}
