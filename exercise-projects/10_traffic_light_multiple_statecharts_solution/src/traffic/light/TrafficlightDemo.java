package traffic.light;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import traffic.light.ITimer;
import traffic.light.crossing.CrossingStatemachine;
import traffic.light.trafficlightctrl.ITrafficLightCtrlStatemachine;
import traffic.light.trafficlightctrl.TrafficLightCtrlStatemachine;
import traffic.light.ui.Counter.Color;
import traffic.light.ui.ButtonPanel;
import traffic.light.ui.CrossWalkPanel;
import traffic.light.ui.TrafficLight;
import traffic.light.ui.TrafficLightFrame;

public class TrafficlightDemo extends TrafficLightFrame {

	private static final long serialVersionUID = -8909693541678814631L;

	protected CrossingStatemachine crossingCtrl;
	protected TrafficLightCtrlStatemachine trafficLightA;
	protected TrafficLightCtrlStatemachine trafficLightB;

	protected ITimer timer;
	
	public static void main(String[] args) {
		TrafficlightDemo application = new TrafficlightDemo();
		application.init();
		
		application.setupStatemachine();
		
		application.run();
	}


	protected void run() {
		crossingCtrl.enter();
	}


	protected void setupStatemachine() {
		
		crossingCtrl  = new CrossingStatemachine();
		trafficLightA = new TrafficLightCtrlStatemachine();
		trafficLightB = new TrafficLightCtrlStatemachine();
		
		timer = new ScaledTimeTimerService(5.0);
		trafficLightA.setTimer(timer);
		trafficLightB.setTimer(timer);
		crossingCtrl.setTimer(timer);
		
		crossingCtrl.init();
		trafficLightA.init();
		trafficLightB.init();
		
		crossingCtrl.setTrafficLightA(trafficLightA);
		crossingCtrl.setTrafficLightB(trafficLightB);
		
		bindTrafficLightCtrlToUi(trafficLightA, trafficLightPanelA);
		bindTrafficLightCtrlToUi(trafficLightB, trafficLightPanelB);
		
		buttonPanel.getPoliceInterrupt()
				.addActionListener(e -> crossingCtrl.getSCInterface().raiseToggleInterrupt());
		
		buttonPanel.getSwitchOnOff()
		.addActionListener(e -> crossingCtrl.getSCInterface().raiseToggleOnOff());
		
	}


	protected void bindTrafficLightCtrlToUi(TrafficLightCtrlStatemachine trafficLightCtrlStatemachine, final CrossWalkPanel panel) {
		trafficLightCtrlStatemachine.getSCITrafficLight().getListeners().add(new ITrafficLightCtrlStatemachine.SCITrafficLightListener() {			
			@Override
			public void onDisplayYellowRaised() {
				setLights(panel.getTrafficLightVis(),false, true, false);
			}
			
			@Override
			public void onDisplayRedRaised() {
				setLights(panel.getTrafficLightVis(),true, false, false);
			}
			
			@Override
			public void onDisplayNoneRaised() {
				setLights(panel.getTrafficLightVis(),false, false, false);
			}
			
			@Override
			public void onDisplayGreenRaised() {
				setLights(panel.getTrafficLightVis(),false, false, true);
			}
		});
		
		trafficLightCtrlStatemachine.getSCITimer().getListeners().add(new ITrafficLightCtrlStatemachine.SCITimerListener() {
			
			@Override
			public void onUpdateTimerValueRaised(long value) {
				panel.getCounterVis().setCounterValue(value);
				repaint();
			}
			
			@Override
			public void onUpdateTimerColourRaised(String value) {
				panel.getCounterVis().setColor(value == "Red" ? Color.RED : Color.GREEN);
			}
		});
	}
	
	private void setLights(TrafficLight tl, boolean red, boolean yellow, boolean green) {
		tl.setRed(red);
		tl.setYellow(yellow);
		tl.setGreen(green);
		repaint();
	}
	
	protected CrossWalkPanel trafficLightPanelA;
	protected CrossWalkPanel trafficLightPanelB;
	
	protected void createContents() {
		setLayout(new BorderLayout());
		setTitle("Trafficlight");
		trafficLightPanelA = new CrossWalkPanel();
		add(BorderLayout.LINE_START, trafficLightPanelA);
		trafficLightPanelB = new CrossWalkPanel();
		add(BorderLayout.LINE_END, trafficLightPanelB);
		buttonPanel = new ButtonPanel();
		add(BorderLayout.PAGE_END, buttonPanel);
		setSize(450, 560);
		setVisible(true);
	}

}
