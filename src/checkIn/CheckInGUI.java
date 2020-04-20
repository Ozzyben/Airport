package checkIn;


import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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
		
		Airport airport;
		private Queue<Passenger> queue;
		
		public CheckInGUI(Airport airport) {
			this.airport = airport;
		}
		public void createAndShowGUI() {
			
			frame = new JFrame();
			frame.setPreferredSize(new Dimension(width, height));
			frame.setVisible(true);
			
			
			displayPassengers();
			displayFlights();
			displayDesks();
			
			
			frame.repaint();
		}
		
		public void displayPassengers() {
			
			//size is just the same width as the frame and 1/3 of the height. this will change for iteration 2
			int passengerListWidth = width;
			int PassengerListHeight = height/3;
			passengerList.setBounds(0, 0, passengerListWidth, PassengerListHeight);
			
			queue = airport.desk1.getQueue();
			for(int i = 0; i<queue.size(); i++) {
				
				Passenger hold = queue.remove();
				JTextArea thisPassenger = new JTextArea(hold.getFlightCode()+"  "+hold.getLastName()+"  "+hold.totalWeight()+"  "+ hold.totalSize());
				
				thisPassenger.setBounds(0, i/queue.size(), width, queue.size());
				thisPassenger.setVisible(true);
				passengerList.add(thisPassenger);
			}
			
			passengerList.setVisible(true);
			frame.add(passengerList);
			
		}
		
		public void displayDesks(){
			
			int desksWidth = width;
			int desksHeight = height/3;
			Desks.setBounds(0, height/3, desksWidth, desksHeight);
			
			Desks.setText(airport.desk1.currentPassenger.getLastName()+" is dropping off 1 bag of "+
			airport.desk1.currentPassenger.totalWeight()+". A baggage fee of "+"**FEE**"+" is due.");
			
			Desks.setVisible(true);
			frame.add(Desks);
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
		
		
		
		void updateQueue() {
			
			
			
		}
}
