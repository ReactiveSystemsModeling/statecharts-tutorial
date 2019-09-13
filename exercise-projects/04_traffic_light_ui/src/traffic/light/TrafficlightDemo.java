package traffic.light;

import traffic.light.trafficlightctrl.ITrafficLightCtrlStatemachine;
import traffic.light.trafficlightctrl.TrafficLightCtrlStatemachine;
import traffic.light.ui.TrafficLightFrame;

public class TrafficlightDemo extends TrafficLightFrame {

	private static final long serialVersionUID = -8909693541678814631L;

	/** The application requires a single state machine.*/
	protected TrafficLightCtrlStatemachine statemachine;
	protected ITimer timer;
	
	/** main method that will be called to start the application. */
	public static void main(String[] args) {
		
		TrafficlightDemo application = new TrafficlightDemo();
		application.init();
		application.setupStatemachine();
	}

	
	/**
	 * This method includes everything, that is required to setup and start the generated traffic light state machine and to attach it to the UI.
	 */
	protected void setupStatemachine() {
		
		// First we create a state machine instance. 
		statemachine = new TrafficLightCtrlStatemachine();
		
		// The state machines makes use of time events. To process time events the state machine requires a timer service.
		// Here an instance of a tiner sevice that supports time scaling (here factor 5) is used and created. 
		timer = new ScaledTimeTimerService(5.0);
		// And the timer service is set on the state machine. 
		statemachine.setTimer(timer);
		
		// Here the the state machine must be connected to the UI. Event listeners for out events can be registered.
		
		// TODO: the implementation here is incomplete. Add the missin method calls and methods.
		statemachine.getSCITrafficLight().getListeners().add(new ITrafficLightCtrlStatemachine.SCITrafficLightListener() {	
			
			@Override
			public void onDisplayYellowRaised() {
				// use the setLights() method which is implemented below to switch the lights on and off
			}
			
		});
		
		// As soon everything is hooked up the state machine can be initialized
		statemachine.init();
		
		// The state machine is activated by entering it. Only if it is active it will process events.  
		statemachine.enter();
	}
	
	
	/**
	 * Use this method to switch the colors in the UI.
	 * 
	 * @param red
	 * @param yellow
	 * @param green
	 */
	private void setLights(boolean red, boolean yellow, boolean green) {
		crossing.getTrafficLightVis().setRed(red);
		crossing.getTrafficLightVis().setYellow(yellow);
		crossing.getTrafficLightVis().setGreen(green);
		repaint();
	}
}
