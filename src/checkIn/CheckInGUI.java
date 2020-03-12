package checkIn;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import java.util.List;

import javax.swing.*;

public class CheckInGUI {

	// we are using enum to control which 'scene' is displayed on the GUI.
	public enum Scene {
		CHECKIN, USERINFO, BAGCHECK, BAGS, ERROR1, ERROR2, RETURN, EXIT, FEES
	}

	static Scene scene = Scene.CHECKIN;

	// alterable width and height of the window. all components are scaled to width
	// and height
	int width = 400;
	int height = 400;

	double fees;
	boolean feePaid;

	// declaring all swing objects
	JFrame frame = new JFrame("GUI");
	JButton checkIn = new JButton("Check In");
	JButton exit = new JButton("Exit");
	JButton noBags = new JButton("No Bag");
	JButton enterBagInfo = new JButton("Enter Bag Info");
	JButton enter = new JButton("Enter");
	JButton retry = new JButton("Retry");
	JButton confirm = new JButton("Confirm");
	JTextArea weightkgTag = new JTextArea("Bag weight (kg)");
	JTextArea error = new JTextArea();
	JTextArea bagxTag = new JTextArea("Bag width (cm)");
	JTextArea bagyTag = new JTextArea("Bag height (cm)");
	JTextArea bagzTag = new JTextArea("Bag depth (cm)");
	JTextArea surnameTag = new JTextArea("Passenger Surname:");
	JTextArea referenceTag = new JTextArea("Flight Reference:");
	JTextArea feeInfo = new JTextArea("The baggage fee for this flight is: " + fees);
	JButton acceptFee = new JButton("Accept Fee");
	JButton declineFee = new JButton("Decline Fee (remove baggage)");
	JTextField bagx = new JTextField();
	JTextField bagy = new JTextField();
	JTextField bagz = new JTextField();
	JTextField weightkg = new JTextField();
	static JTextField surname = new JTextField();
	static JTextField reference = new JTextField();
	static String surnameText;
	static String referenceText;
	static double bag_x;
	static double bag_y;
	static double bag_z;
	static double bag_kg;

	public CheckInGUI() {
	}

	/*
	 * Will generate the standard gui layout Home screen with no required input from
	 * user Just a logo or something
	 */
	public void createAndShowGUI() {

		frame.setSize(width, height);
		frame.setLayout(null);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // makes program stop on exit

		setupListener();

		// set the bounds for all objects on screen. this determines where they are laid
		// on the JFrame.
		checkIn.setBounds(width / 4, height / 4, width / 2, height / 5);
		exit.setBounds(width / 4, 2 * height / 4, width / 2, height / 5);
		noBags.setBounds(width / 4, height / 4, width / 2, height / 5);
		enterBagInfo.setBounds(width / 4, 2 * height / 4, width / 2, height / 5);
		surname.setBounds(width / 4, height / 5, width / 2, height / 5 - 20);
		reference.setBounds(width / 4, 2 * height / 5, width / 2, height / 5 - 20);
		enter.setBounds(width / 4, 3 * height / 5, width / 2, height / 5 - 20);
		error.setBounds(width / 8, height / 4, 3 * width / 4, height / 8);
		retry.setBounds(width / 4, 2 * height / 4, width / 2, height / 5);
		surnameTag.setBounds(width / 4, height / 5 - 20, width / 2, height / 5 - 20);
		referenceTag.setBounds(width / 4, 2 * height / 5 - 20, width / 2, height / 5 - 20);
		bagxTag.setBounds(width / 4, 2 * height / 10 - 17, width / 4, height / 15);
		bagyTag.setBounds(width / 4, 3 * height / 10 - 17, width / 4, height / 15);
		bagzTag.setBounds(width / 4, 4 * height / 10 - 17, width / 4, height / 15);
		bagx.setBounds(width / 4, 2 * height / 10, width / 4, height / 15);
		bagy.setBounds(width / 4, 3 * height / 10, width / 4, height / 15);
		bagz.setBounds(width / 4, 4 * height / 10, width / 4, height / 15);
		weightkg.setBounds(2 * width / 4 + 20, 2 * height / 10, width / 4, height / 15);
		weightkgTag.setBounds(2 * width / 4 + 20, 2 * height / 10 - 17, width / 4, height / 15);
		confirm.setBounds(width / 2 + 20, height / 2, width / 4, height / 8);
		feeInfo.setBounds(0, height / 4, width, height / 5);
		acceptFee.setBounds(width / 4, height / 2, width / 4 - 20, height / 5);
		declineFee.setBounds(width / 2, height / 2, width / 4 - 20, height / 5);
	}

	/*
	 * Will give the user the option to check in passenger or Exit If check in
	 * passenger then request reference code and last name Pass these back (comma
	 * separated) If exit pass back "EXIT" to gen reports
	 */
	public String getUserInput() {

		paintScene();

		while (!(scene == Scene.EXIT) && !(scene == Scene.RETURN)) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if (scene == Scene.RETURN) {
			String info = referenceText + " , " + surnameText;
			return info;
		}
		if (scene == Scene.EXIT) {
			return "exit";
		}
		return "";

	}

	// this method is in charge of painting whichever objects are required on
	// screen. it uses the 'scene' enum
	private void paintScene() {

		switch (scene) {
		case ERROR1:
			error.setText("Flight Reference doesnt exist.");
			frame.getContentPane().add(error);
			frame.getContentPane().add(retry);
			break;
		case ERROR2:
			error.setText("Surname and Flight reference dont match");
			frame.getContentPane().add(error);
			frame.getContentPane().add(retry);
			break;
		case CHECKIN:
			frame.getContentPane().add(checkIn);
			frame.getContentPane().add(exit);
			break;
		case USERINFO:
			frame.getContentPane().add(surname);
			frame.getContentPane().add(reference);
			frame.getContentPane().add(enter);
			frame.add(surnameTag);
			frame.add(referenceTag);
			break;
		case BAGCHECK:
			frame.getContentPane().add(noBags);
			frame.getContentPane().add(enterBagInfo);
			break;
		case BAGS:
			frame.getContentPane().add(bagx);
			frame.getContentPane().add(bagy);
			frame.getContentPane().add(bagz);
			frame.getContentPane().add(bagxTag);
			frame.getContentPane().add(bagyTag);
			frame.getContentPane().add(bagzTag);
			frame.add(weightkg);
			frame.add(weightkgTag);
			frame.add(confirm);
			break;
		case FEES:
			frame.getContentPane().add(feeInfo);
			frame.getContentPane().add(acceptFee);
			frame.getContentPane().add(declineFee);
		}

		// this makes sure the frame which has been set is now painted onto the frame
		frame.repaint();
	}

	// this method sets up the listener for the buttons and handles whatever each
	// button should do.
	private void setupListener() {
		ActionListener Listener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == checkIn) {
					frame.getContentPane().removeAll();
					scene = Scene.USERINFO;
				}
				if (e.getSource() == exit) {
					frame.getContentPane().removeAll();
					scene = Scene.EXIT;
				}
				if (e.getSource() == enter) {
					frame.getContentPane().removeAll();
					// get user entered surname and reference
					surnameText = surname.getText();
					referenceText = reference.getText();
					scene = Scene.RETURN;
				}
				if (e.getSource() == retry) {
					surname.setText("");
					reference.setText("");
					frame.getContentPane().removeAll();
					scene = Scene.USERINFO;
				}
				if (e.getSource() == noBags) {
					frame.getContentPane().removeAll();
					surname.setText("");
					reference.setText("");
					bag_x = -1.0;
					scene = Scene.CHECKIN;
				}
				if (e.getSource() == enterBagInfo) {
					// if you don't reset the user entered variables they will stay as the values
					// for the previous bag entered
					bagx.setText("");
					bagy.setText("");
					bagz.setText("");
					weightkg.setText("");
					frame.getContentPane().removeAll();
					scene = Scene.BAGS;
				}
				if (e.getSource() == confirm) {
					// get user entered bag dimensions
					bag_x = Double.parseDouble(bagx.getText());
					bag_y = Double.parseDouble(bagy.getText());
					bag_z = Double.parseDouble(bagz.getText());
					bag_kg = Double.parseDouble(weightkg.getText());
					frame.getContentPane().removeAll();
					scene = Scene.FEES;
				}
				if (e.getSource() == acceptFee) {
					frame.getContentPane().removeAll();
					feePaid = true;
					scene = Scene.BAGCHECK;
				}
				if (e.getSource() == declineFee) {
					frame.getContentPane().removeAll();
					feePaid = false;
					scene = Scene.BAGCHECK;
				}

				paintScene();

			}
		};

		// adding the action listener to all of the required swing objects
		checkIn.addActionListener(Listener);
		exit.addActionListener(Listener);
		enter.addActionListener(Listener);
		surname.addActionListener(Listener);
		reference.addActionListener(Listener);
		retry.addActionListener(Listener);
		noBags.addActionListener(Listener);
		enterBagInfo.addActionListener(Listener);
		confirm.addActionListener(Listener);
		acceptFee.addActionListener(Listener);
		declineFee.addActionListener(Listener);

	}

	/*
	 * Will ask user if they want to add a bag if yes then ask user for bag
	 * dimensions and weight convert x,y,z dimensions to m^3 volume pass back
	 * volume, weight if no then pass back -1
	 */
	public List<Double> getPassengerBagInfo() {

		scene = Scene.BAGCHECK;
		paintScene();

		while (scene == Scene.BAGCHECK) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		paintScene();
		while (scene == Scene.BAGS) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		// if bag_x is -1.0 then no more bags will be entered
		if (bag_x == -1.0) {
			List<Double> hold = new ArrayList<Double>();
			hold.add(-1.0);
			return hold;
		}
		// calculate bag volume from dimensions given
		double bagx_ = bag_x / 100;
		double bagy_ = bag_y / 100;
		double bagz_ = bag_z / 100;
		double volume = bagx_ * bagy_ * bagz_;

		List<Double> hold = new ArrayList<Double>();
		hold.add(volume);
		hold.add(bag_kg);
		return hold;
	}

	/*
	 * Will request the user pay a fee for excess baggage return true to indicate
	 * some sort of payment procedure has been passed return false to indicate
	 * customer refusal to pay and bag should not be added
	 */
	public Boolean requestFees(double fee) {

		fees = fee;

		feeInfo.setText("The baggage fee for this flight is: " + fees);

		paintScene();

		while (scene == Scene.FEES) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return feePaid;
	}

	/*
	 * Show an error screen based on error code 0: Generic error 1: Booking code
	 * could not be found 2: Booking code and last name did not match
	 *
	 * Possibly more to come Will return "OK" or something to indicate user wants to
	 * proceed
	 */
	public void ErrorScreen(int errorCode) {
		switch (errorCode) {
		case 1:
			scene = Scene.ERROR1;
			break;
		case 2:
			scene = Scene.ERROR2;
			break;
		}
	}
}
