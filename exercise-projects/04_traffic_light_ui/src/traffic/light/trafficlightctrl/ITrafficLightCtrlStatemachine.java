/** Generated by YAKINDU Statechart Tools code generator. */
package traffic.light.trafficlightctrl;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import traffic.light.IStatemachine;
import traffic.light.ITimerCallback;

public interface ITrafficLightCtrlStatemachine extends ITimerCallback,IStatemachine {
	public interface SCInterface {
	
		public long getRedPeriod();
		
		public void setRedPeriod(long value);
		
		public long getGreenPeriod();
		
		public void setGreenPeriod(long value);
		
		public long getYellowPeriod();
		
		public void setYellowPeriod(long value);
		
	}
	
	public SCInterface getSCInterface();
	
	public interface SCITrafficLight {
	
		public boolean isRaisedDisplayRed();
		
		public boolean isRaisedDisplayGreen();
		
		public boolean isRaisedDisplayYellow();
		
	public List<SCITrafficLightListener> getListeners();
	}
	
	public interface SCITrafficLightListener {
	
		public void onDisplayRedRaised();
		public void onDisplayGreenRaised();
		public void onDisplayYellowRaised();
		}
	
	public SCITrafficLight getSCITrafficLight();
	
}