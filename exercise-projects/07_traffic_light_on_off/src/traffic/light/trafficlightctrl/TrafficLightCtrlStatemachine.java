/** Generated by YAKINDU Statechart Tools code generator. */
package traffic.light.trafficlightctrl;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import traffic.light.ITimer;

public class TrafficLightCtrlStatemachine implements ITrafficLightCtrlStatemachine {
	protected class SCInterfaceImpl implements SCInterface {
	
		private boolean police_interrupt;
		
		
		public synchronized void raisePolice_interrupt() {
			police_interrupt = true;
			runCycle();
		}
		
		private boolean toggle;
		
		
		public synchronized void raiseToggle() {
			toggle = true;
			runCycle();
		}
		
		private long redPeriod;
		
		public synchronized long getRedPeriod() {
			return redPeriod;
		}
		
		public synchronized void setRedPeriod(long value) {
			this.redPeriod = value;
		}
		
		private long greenPeriod;
		
		public synchronized long getGreenPeriod() {
			return greenPeriod;
		}
		
		public synchronized void setGreenPeriod(long value) {
			this.greenPeriod = value;
		}
		
		private long yellowPeriod;
		
		public synchronized long getYellowPeriod() {
			return yellowPeriod;
		}
		
		public synchronized void setYellowPeriod(long value) {
			this.yellowPeriod = value;
		}
		
		protected void clearEvents() {
			police_interrupt = false;
			toggle = false;
		}
	}
	
	
	protected class SCITrafficLightImpl implements SCITrafficLight {
	
		private List<SCITrafficLightListener> listeners = new LinkedList<SCITrafficLightListener>();
		
		public List<SCITrafficLightListener> getListeners() {
			return listeners;
		}
		private boolean displayRed;
		
		
		public synchronized boolean isRaisedDisplayRed() {
			return displayRed;
		}
		
		protected void raiseDisplayRed() {
			displayRed = true;
			for (SCITrafficLightListener listener : listeners) {
				listener.onDisplayRedRaised();
			}
		}
		
		private boolean displayGreen;
		
		
		public synchronized boolean isRaisedDisplayGreen() {
			return displayGreen;
		}
		
		protected void raiseDisplayGreen() {
			displayGreen = true;
			for (SCITrafficLightListener listener : listeners) {
				listener.onDisplayGreenRaised();
			}
		}
		
		private boolean displayYellow;
		
		
		public synchronized boolean isRaisedDisplayYellow() {
			return displayYellow;
		}
		
		protected void raiseDisplayYellow() {
			displayYellow = true;
			for (SCITrafficLightListener listener : listeners) {
				listener.onDisplayYellowRaised();
			}
		}
		
		private boolean displayNone;
		
		
		public synchronized boolean isRaisedDisplayNone() {
			return displayNone;
		}
		
		protected void raiseDisplayNone() {
			displayNone = true;
			for (SCITrafficLightListener listener : listeners) {
				listener.onDisplayNoneRaised();
			}
		}
		
		protected void clearEvents() {
		}
		protected void clearOutEvents() {
		
		displayRed = false;
		displayGreen = false;
		displayYellow = false;
		displayNone = false;
		}
		
	}
	
	
	protected SCInterfaceImpl sCInterface;
	
	protected SCITrafficLightImpl sCITrafficLight;
	
	private boolean initialized = false;
	
	public enum State {
		main_normal,
		main_normal_normal_Red,
		main_normal_normal_Yellow,
		main_normal_normal_Green,
		main_interrupted,
		main_interrupted_blinking_Black,
		main_interrupted_blinking_Yellow,
		$NullState$
	};
	
	private State[] historyVector = new State[1];
	private final State[] stateVector = new State[1];
	
	private int nextStateIndex;
	
	private ITimer timer;
	
	private final boolean[] timeEvents = new boolean[5];
	
	public TrafficLightCtrlStatemachine() {
		sCInterface = new SCInterfaceImpl();
		sCITrafficLight = new SCITrafficLightImpl();
	}
	
	public synchronized void init() {
		this.initialized = true;
		if (timer == null) {
			throw new IllegalStateException("timer not set.");
		}
		for (int i = 0; i < 1; i++) {
			stateVector[i] = State.$NullState$;
		}
		for (int i = 0; i < 1; i++) {
			historyVector[i] = State.$NullState$;
		}
		clearEvents();
		clearOutEvents();
		sCInterface.setRedPeriod(60);
		
		sCInterface.setGreenPeriod(55);
		
		sCInterface.setYellowPeriod(5);
	}
	
	public synchronized void enter() {
		if (!initialized) {
			throw new IllegalStateException(
				"The state machine needs to be initialized first by calling the init() function."
			);
		}
		if (timer == null) {
			throw new IllegalStateException("timer not set.");
		}
		enterSequence_main_default();
	}
	
	public synchronized void runCycle() {
		if (!initialized)
			throw new IllegalStateException(
					"The state machine needs to be initialized first by calling the init() function.");
		clearOutEvents();
		for (nextStateIndex = 0; nextStateIndex < stateVector.length; nextStateIndex++) {
			switch (stateVector[nextStateIndex]) {
			case main_normal_normal_Red:
				main_normal_normal_Red_react(true);
				break;
			case main_normal_normal_Yellow:
				main_normal_normal_Yellow_react(true);
				break;
			case main_normal_normal_Green:
				main_normal_normal_Green_react(true);
				break;
			case main_interrupted_blinking_Black:
				main_interrupted_blinking_Black_react(true);
				break;
			case main_interrupted_blinking_Yellow:
				main_interrupted_blinking_Yellow_react(true);
				break;
			default:
				// $NullState$
			}
		}
		clearEvents();
	}
	public synchronized void exit() {
		exitSequence_main();
	}
	
	/**
	 * @see IStatemachine#isActive()
	 */
	public synchronized boolean isActive() {
		return stateVector[0] != State.$NullState$;
	}
	
	/** 
	* Always returns 'false' since this state machine can never become final.
	*
	* @see IStatemachine#isFinal()
	*/
	public synchronized boolean isFinal() {
		return false;
	}
	/**
	* This method resets the incoming events (time events included).
	*/
	protected void clearEvents() {
		sCInterface.clearEvents();
		sCITrafficLight.clearEvents();
		for (int i=0; i<timeEvents.length; i++) {
			timeEvents[i] = false;
		}
	}
	
	/**
	* This method resets the outgoing events.
	*/
	protected void clearOutEvents() {
		sCITrafficLight.clearOutEvents();
	}
	
	/**
	* Returns true if the given state is currently active otherwise false.
	*/
	public synchronized boolean isStateActive(State state) {
	
		switch (state) {
		case main_normal:
			return stateVector[0].ordinal() >= State.
					main_normal.ordinal()&& stateVector[0].ordinal() <= State.main_normal_normal_Green.ordinal();
		case main_normal_normal_Red:
			return stateVector[0] == State.main_normal_normal_Red;
		case main_normal_normal_Yellow:
			return stateVector[0] == State.main_normal_normal_Yellow;
		case main_normal_normal_Green:
			return stateVector[0] == State.main_normal_normal_Green;
		case main_interrupted:
			return stateVector[0].ordinal() >= State.
					main_interrupted.ordinal()&& stateVector[0].ordinal() <= State.main_interrupted_blinking_Yellow.ordinal();
		case main_interrupted_blinking_Black:
			return stateVector[0] == State.main_interrupted_blinking_Black;
		case main_interrupted_blinking_Yellow:
			return stateVector[0] == State.main_interrupted_blinking_Yellow;
		default:
			return false;
		}
	}
	
	/**
	* Set the {@link ITimer} for the state machine. It must be set
	* externally on a timed state machine before a run cycle can be correctly
	* executed.
	* 
	* @param timer
	*/
	public synchronized void setTimer(ITimer timer) {
		this.timer = timer;
	}
	
	/**
	* Returns the currently used timer.
	* 
	* @return {@link ITimer}
	*/
	public ITimer getTimer() {
		return timer;
	}
	
	public synchronized void timeElapsed(int eventID) {
		timeEvents[eventID] = true;
		runCycle();
	}
	
	public SCInterface getSCInterface() {
		return sCInterface;
	}
	
	public SCITrafficLight getSCITrafficLight() {
		return sCITrafficLight;
	}
	
	public synchronized void raisePolice_interrupt() {
		sCInterface.raisePolice_interrupt();
	}
	
	public synchronized void raiseToggle() {
		sCInterface.raiseToggle();
	}
	
	public synchronized long getRedPeriod() {
		return sCInterface.getRedPeriod();
	}
	
	public synchronized void setRedPeriod(long value) {
		sCInterface.setRedPeriod(value);
	}
	
	public synchronized long getGreenPeriod() {
		return sCInterface.getGreenPeriod();
	}
	
	public synchronized void setGreenPeriod(long value) {
		sCInterface.setGreenPeriod(value);
	}
	
	public synchronized long getYellowPeriod() {
		return sCInterface.getYellowPeriod();
	}
	
	public synchronized void setYellowPeriod(long value) {
		sCInterface.setYellowPeriod(value);
	}
	
	/* Entry action for state 'Red'. */
	private void entryAction_main_normal_normal_Red() {
		timer.setTimer(this, 0, (sCInterface.getRedPeriod() * 1000), false);
		
		sCITrafficLight.raiseDisplayRed();
	}
	
	/* Entry action for state 'Yellow'. */
	private void entryAction_main_normal_normal_Yellow() {
		timer.setTimer(this, 1, (sCInterface.getYellowPeriod() * 1000), false);
		
		sCITrafficLight.raiseDisplayYellow();
	}
	
	/* Entry action for state 'Green'. */
	private void entryAction_main_normal_normal_Green() {
		timer.setTimer(this, 2, (sCInterface.getGreenPeriod() * 1000), false);
		
		sCITrafficLight.raiseDisplayGreen();
	}
	
	/* Entry action for state 'Black'. */
	private void entryAction_main_interrupted_blinking_Black() {
		timer.setTimer(this, 3, 500, false);
		
		sCITrafficLight.raiseDisplayNone();
	}
	
	/* Entry action for state 'Yellow'. */
	private void entryAction_main_interrupted_blinking_Yellow() {
		timer.setTimer(this, 4, 500, false);
		
		sCITrafficLight.raiseDisplayYellow();
	}
	
	/* Exit action for state 'Red'. */
	private void exitAction_main_normal_normal_Red() {
		timer.unsetTimer(this, 0);
	}
	
	/* Exit action for state 'Yellow'. */
	private void exitAction_main_normal_normal_Yellow() {
		timer.unsetTimer(this, 1);
	}
	
	/* Exit action for state 'Green'. */
	private void exitAction_main_normal_normal_Green() {
		timer.unsetTimer(this, 2);
	}
	
	/* Exit action for state 'Black'. */
	private void exitAction_main_interrupted_blinking_Black() {
		timer.unsetTimer(this, 3);
	}
	
	/* Exit action for state 'Yellow'. */
	private void exitAction_main_interrupted_blinking_Yellow() {
		timer.unsetTimer(this, 4);
	}
	
	/* 'default' enter sequence for state normal */
	private void enterSequence_main_normal_default() {
		enterSequence_main_normal_normal_default();
	}
	
	/* 'default' enter sequence for state Red */
	private void enterSequence_main_normal_normal_Red_default() {
		entryAction_main_normal_normal_Red();
		nextStateIndex = 0;
		stateVector[0] = State.main_normal_normal_Red;
		
		historyVector[0] = stateVector[0];
	}
	
	/* 'default' enter sequence for state Yellow */
	private void enterSequence_main_normal_normal_Yellow_default() {
		entryAction_main_normal_normal_Yellow();
		nextStateIndex = 0;
		stateVector[0] = State.main_normal_normal_Yellow;
		
		historyVector[0] = stateVector[0];
	}
	
	/* 'default' enter sequence for state Green */
	private void enterSequence_main_normal_normal_Green_default() {
		entryAction_main_normal_normal_Green();
		nextStateIndex = 0;
		stateVector[0] = State.main_normal_normal_Green;
		
		historyVector[0] = stateVector[0];
	}
	
	/* 'default' enter sequence for state interrupted */
	private void enterSequence_main_interrupted_default() {
		enterSequence_main_interrupted_blinking_default();
	}
	
	/* 'default' enter sequence for state Black */
	private void enterSequence_main_interrupted_blinking_Black_default() {
		entryAction_main_interrupted_blinking_Black();
		nextStateIndex = 0;
		stateVector[0] = State.main_interrupted_blinking_Black;
	}
	
	/* 'default' enter sequence for state Yellow */
	private void enterSequence_main_interrupted_blinking_Yellow_default() {
		entryAction_main_interrupted_blinking_Yellow();
		nextStateIndex = 0;
		stateVector[0] = State.main_interrupted_blinking_Yellow;
	}
	
	/* 'default' enter sequence for region main */
	private void enterSequence_main_default() {
		react_main__entry_Default();
	}
	
	/* 'default' enter sequence for region normal */
	private void enterSequence_main_normal_normal_default() {
		react_main_normal_normal__entry_Default();
	}
	
	/* deep enterSequence with history in child normal */
	private void deepEnterSequence_main_normal_normal() {
		switch (historyVector[0]) {
		case main_normal_normal_Red:
			enterSequence_main_normal_normal_Red_default();
			break;
		case main_normal_normal_Yellow:
			enterSequence_main_normal_normal_Yellow_default();
			break;
		case main_normal_normal_Green:
			enterSequence_main_normal_normal_Green_default();
			break;
		default:
			break;
		}
	}
	
	/* 'default' enter sequence for region blinking */
	private void enterSequence_main_interrupted_blinking_default() {
		react_main_interrupted_blinking__entry_Default();
	}
	
	/* Default exit sequence for state normal */
	private void exitSequence_main_normal() {
		exitSequence_main_normal_normal();
	}
	
	/* Default exit sequence for state Red */
	private void exitSequence_main_normal_normal_Red() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
		
		exitAction_main_normal_normal_Red();
	}
	
	/* Default exit sequence for state Yellow */
	private void exitSequence_main_normal_normal_Yellow() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
		
		exitAction_main_normal_normal_Yellow();
	}
	
	/* Default exit sequence for state Green */
	private void exitSequence_main_normal_normal_Green() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
		
		exitAction_main_normal_normal_Green();
	}
	
	/* Default exit sequence for state interrupted */
	private void exitSequence_main_interrupted() {
		exitSequence_main_interrupted_blinking();
	}
	
	/* Default exit sequence for state Black */
	private void exitSequence_main_interrupted_blinking_Black() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
		
		exitAction_main_interrupted_blinking_Black();
	}
	
	/* Default exit sequence for state Yellow */
	private void exitSequence_main_interrupted_blinking_Yellow() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
		
		exitAction_main_interrupted_blinking_Yellow();
	}
	
	/* Default exit sequence for region main */
	private void exitSequence_main() {
		switch (stateVector[0]) {
		case main_normal_normal_Red:
			exitSequence_main_normal_normal_Red();
			break;
		case main_normal_normal_Yellow:
			exitSequence_main_normal_normal_Yellow();
			break;
		case main_normal_normal_Green:
			exitSequence_main_normal_normal_Green();
			break;
		case main_interrupted_blinking_Black:
			exitSequence_main_interrupted_blinking_Black();
			break;
		case main_interrupted_blinking_Yellow:
			exitSequence_main_interrupted_blinking_Yellow();
			break;
		default:
			break;
		}
	}
	
	/* Default exit sequence for region normal */
	private void exitSequence_main_normal_normal() {
		switch (stateVector[0]) {
		case main_normal_normal_Red:
			exitSequence_main_normal_normal_Red();
			break;
		case main_normal_normal_Yellow:
			exitSequence_main_normal_normal_Yellow();
			break;
		case main_normal_normal_Green:
			exitSequence_main_normal_normal_Green();
			break;
		default:
			break;
		}
	}
	
	/* Default exit sequence for region blinking */
	private void exitSequence_main_interrupted_blinking() {
		switch (stateVector[0]) {
		case main_interrupted_blinking_Black:
			exitSequence_main_interrupted_blinking_Black();
			break;
		case main_interrupted_blinking_Yellow:
			exitSequence_main_interrupted_blinking_Yellow();
			break;
		default:
			break;
		}
	}
	
	/* Default react sequence for initial entry  */
	private void react_main__entry_Default() {
		enterSequence_main_normal_default();
	}
	
	/* Default react sequence for initial entry  */
	private void react_main_normal_normal__entry_Default() {
		enterSequence_main_normal_normal_Red_default();
	}
	
	/* Default react sequence for deep history entry hist */
	private void react_main_normal_normal_hist() {
		/* Enter the region with deep history */
		if (historyVector[0] != State.$NullState$) {
			deepEnterSequence_main_normal_normal();
		} else {
			enterSequence_main_normal_normal_Red_default();
		}
	}
	
	/* Default react sequence for initial entry  */
	private void react_main_interrupted_blinking__entry_Default() {
		enterSequence_main_interrupted_blinking_Yellow_default();
	}
	
	private boolean react() {
		return false;
	}
	
	private boolean main_normal_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (sCInterface.police_interrupt) {
				exitSequence_main_normal();
				enterSequence_main_interrupted_default();
				react();
			} else {
				did_transition = false;
			}
		}
		if (did_transition==false) {
			did_transition = react();
		}
		return did_transition;
	}
	
	private boolean main_normal_normal_Red_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (timeEvents[0]) {
				exitSequence_main_normal_normal_Red();
				enterSequence_main_normal_normal_Green_default();
				main_normal_react(false);
			} else {
				did_transition = false;
			}
		}
		if (did_transition==false) {
			did_transition = main_normal_react(try_transition);
		}
		return did_transition;
	}
	
	private boolean main_normal_normal_Yellow_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (timeEvents[1]) {
				exitSequence_main_normal_normal_Yellow();
				enterSequence_main_normal_normal_Red_default();
				main_normal_react(false);
			} else {
				did_transition = false;
			}
		}
		if (did_transition==false) {
			did_transition = main_normal_react(try_transition);
		}
		return did_transition;
	}
	
	private boolean main_normal_normal_Green_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (timeEvents[2]) {
				exitSequence_main_normal_normal_Green();
				enterSequence_main_normal_normal_Yellow_default();
				main_normal_react(false);
			} else {
				did_transition = false;
			}
		}
		if (did_transition==false) {
			did_transition = main_normal_react(try_transition);
		}
		return did_transition;
	}
	
	private boolean main_interrupted_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (sCInterface.police_interrupt) {
				exitSequence_main_interrupted();
				react_main_normal_normal_hist();
				react();
			} else {
				did_transition = false;
			}
		}
		if (did_transition==false) {
			did_transition = react();
		}
		return did_transition;
	}
	
	private boolean main_interrupted_blinking_Black_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (timeEvents[3]) {
				exitSequence_main_interrupted_blinking_Black();
				enterSequence_main_interrupted_blinking_Yellow_default();
				main_interrupted_react(false);
			} else {
				did_transition = false;
			}
		}
		if (did_transition==false) {
			did_transition = main_interrupted_react(try_transition);
		}
		return did_transition;
	}
	
	private boolean main_interrupted_blinking_Yellow_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (timeEvents[4]) {
				exitSequence_main_interrupted_blinking_Yellow();
				enterSequence_main_interrupted_blinking_Black_default();
				main_interrupted_react(false);
			} else {
				did_transition = false;
			}
		}
		if (did_transition==false) {
			did_transition = main_interrupted_react(try_transition);
		}
		return did_transition;
	}
	
}
