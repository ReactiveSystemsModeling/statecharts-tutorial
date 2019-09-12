package traffic.light;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import traffic.light.ui.*;
import traffic.light.ui.Counter.Color;
import traffic.light.trafficlightctrl.ITrafficLightCtrlStatemachine;
import traffic.light.trafficlightctrl.TrafficLightCtrlStatemachine;

public class TrafficlightDemo extends JFrame {

	private static final long serialVersionUID = -8909693541678814631L;

	protected TrafficLightCtrlStatemachine statemachine;

	protected ITimer timer;

	private CrossWalkPanel crossing;

	private ButtonPanel buttonPanel;

	public static void main(String[] args) {
		TrafficlightDemo application = new TrafficlightDemo();
		application.addWindowListener(new WindowAdapter() {			
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		application.createContents();
		application.setupStatemachine();
		application.run();
	}

	protected void run() {
		statemachine.enter();
		//RuntimeService.getInstance().registerStatemachine(statemachine, 1);
	}

	protected void createContents() {
		setLayout(new BorderLayout());
		setTitle("Trafficlight");
		crossing = new CrossWalkPanel();
		add(BorderLayout.CENTER, crossing);
		buttonPanel = new ButtonPanel();
		add(BorderLayout.SOUTH, buttonPanel);
		setSize(250, 550);
		setVisible(true);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
	}

	protected void setupStatemachine() {
		statemachine = new TrafficLightCtrlStatemachine();
		timer = new MyTimerService(5.0);
		statemachine.setTimer(timer);
		
		statemachine.getSCITrafficLight().getListeners().add(new ITrafficLightCtrlStatemachine.SCITrafficLightListener() {			
			@Override
			public void onDisplayYellowRaised() {
				setLights(false, true, false);
			}
			
			@Override
			public void onDisplayRedRaised() {
				setLights(true, false, false);
			}
			
			@Override
			public void onDisplayNoneRaised() {
				setLights(false, false, false);
			}
			
			@Override
			public void onDisplayGreenRaised() {
				setLights(false, false, true);
			}
		});
		
		statemachine.getSCITimer().getListeners().add(new ITrafficLightCtrlStatemachine.SCITimerListener() {
			
			@Override
			public void onUpdateTimerValueRaised(long value) {
				crossing.getCounterVis().setCounterValue(value);
				repaint();
			}
			
			@Override
			public void onUpdateTimerColourRaised(String value) {
				crossing.getCounterVis().setColor(value == "Red" ? Color.RED : Color.GREEN);
			}
		});
		
		buttonPanel.getPoliceInterrupt()
				.addActionListener(e -> statemachine.getSCInterface().raisePolice_interrupt());
		
		buttonPanel.getSwitchOnOff()
				.addActionListener(e -> statemachine.getSCInterface().raiseToggle());
		
		statemachine.init();
	}
	
	private void setLights(boolean red, boolean yellow, boolean green) {
		crossing.getTrafficLightVis().setRed(red);
		crossing.getTrafficLightVis().setYellow(yellow);
		crossing.getTrafficLightVis().setGreen(green);
		repaint();
	}
}
