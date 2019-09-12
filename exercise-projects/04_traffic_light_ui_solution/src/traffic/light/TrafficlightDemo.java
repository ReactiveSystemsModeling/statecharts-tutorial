package traffic.light;

import traffic.light.trafficlightctrl.ITrafficLightCtrlStatemachine;
import traffic.light.trafficlightctrl.TrafficLightCtrlStatemachine;
import traffic.light.ui.TrafficLightFrame;

public class TrafficlightDemo extends TrafficLightFrame {

	private static final long serialVersionUID = -8909693541678814631L;

	protected TrafficLightCtrlStatemachine statemachine;

	protected ITimer timer;
	
	public static void main(String[] args) {
		TrafficlightDemo application = new TrafficlightDemo();
		application.init();
		
		application.setupStatemachine();
		
		application.run();
	}

	protected void run() {
		statemachine.enter();
	}


	protected void setupStatemachine() {
		statemachine = new TrafficLightCtrlStatemachine();
		timer = new ScaledTimeTimerService(5.0);
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
			public void onDisplayGreenRaised() {
				setLights(false, false, true);
			}
		});
		
		
		buttonPanel.getPoliceInterrupt()
				.addActionListener(e -> statemachine.getSCInterface().raisePolice_interrupt());
				
		statemachine.init();
	}
	
	private void setLights(boolean red, boolean yellow, boolean green) {
		crossing.getTrafficLightVis().setRed(red);
		crossing.getTrafficLightVis().setYellow(yellow);
		crossing.getTrafficLightVis().setGreen(green);
		repaint();
	}
}
