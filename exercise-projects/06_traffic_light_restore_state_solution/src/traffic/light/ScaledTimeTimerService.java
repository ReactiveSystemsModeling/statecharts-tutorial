package traffic.light;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Default timer service implementation.
 *
 */
public class ScaledTimeTimerService implements ITimer {

	private final Timer timer = new Timer();
	
	private final List<TimeEventTask> timerTaskList = new ArrayList<TimeEventTask>();
	
	private final Lock lock = new ReentrantLock();
	
	private double realtime_scale = 1.0;
	private long start_time = -1;
	private long simulated_time = 0;
	
	public ScaledTimeTimerService(double realtime_scale) {
		this.realtime_scale = realtime_scale;
		this.simulated_time = 0;
	}
	public ScaledTimeTimerService() {}
	
	private void init() {
		start_time = System.currentTimeMillis();
	}
	
	/**
	 * Timer task that reflects a time event. It's internally used by
	 * {@link TimerService}.
	 *
	 */
	private class TimeEventTask extends TimerTask {
	
		private ITimerCallback callback;
	
		int eventID;
		
		long next_time;
	
		/**
		 * Constructor for a time event.
		 *
		 * @param callback
		 *            : Object that implements ITimerCallback, is called
		 *            when the timer expires.
		 *
		 * @param eventID
		 *            : Index position within the state machine's timeEvent
		 *            array.
		 */
		public TimeEventTask(ITimerCallback callback, int eventID, long next_time) {
			this.callback = callback;
			this.eventID = eventID;
			this.next_time = next_time;
		}
		public TimeEventTask(ITimerCallback callback, int eventID) {
			this(callback, eventID, 0);
		}
	
		public void run() {
			ScaledTimeTimerService.this.simulated_time = next_time;
			callback.timeElapsed(eventID);
		}
		
		@Override
		public boolean equals(Object obj) {
			if (obj instanceof TimeEventTask) {
				return ((TimeEventTask) obj).callback.equals(callback)
						&& ((TimeEventTask) obj).eventID == eventID;
			}
			return super.equals(obj);
		}
		
		@Override
		public int hashCode() {
			int prime = 37;
			int result = 1;
			
			int c = (int) this.eventID;
			result = prime * result + c;
			c = this.callback.hashCode();
			result = prime * result + c;
			return result;
		}
		
	}
	
	public void setTimer(final ITimerCallback callback, final int eventID,
			long delay, boolean isPeriodic) {
		
		if (start_time == -1) {
			// initialize if not already done
			init();
		} else if (timerTaskList.size() == 0) {
			// if initialized and no delays present, then this timer was added right after an external interrupt. In that case: set simulated time by scaling from the wall-clock time.
			simulated_time = (long) ((System.currentTimeMillis() - start_time) * realtime_scale);
		}
		
		// create a new TimerTask for given event and store it.
		TimeEventTask timerTask = new TimeEventTask(callback, eventID, simulated_time + delay);
		lock.lock();
		timerTaskList.add(timerTask);
	
		// start scheduling the timer
		if (isPeriodic) {
			timer.scheduleAtFixedRate(timerTask, (long) (delay / realtime_scale), (long) (delay / realtime_scale));
		} else {			
			long curr_wc_time = System.currentTimeMillis() - start_time;
			long diff = (long) ((delay / realtime_scale) - curr_wc_time + (simulated_time / realtime_scale));
			//System.out.println("WCT = " + curr_wc_time);
			//System.out.println("ST = " + simulated_time);
			//System.out.println("Scheduling at " + String.valueOf(curr_wc_time + Long.max(0, diff)));
			//System.out.println("Delay = " + Long.max(0, diff));
			timer.schedule(timerTask, Long.max(0, diff));
		}
		lock.unlock();

	}
	
	public void unsetTimer(ITimerCallback callback, int eventID) {
		lock.lock();
		int index = timerTaskList.indexOf(new TimeEventTask(callback, eventID));
		if (index != -1) {
			timerTaskList.get(index).cancel();
			timer.purge();
			timerTaskList.remove(index);
		}
		lock.unlock();

	}
	
	/**
	 * Cancel timer service. Use this to end possible timing threads and free
	 * memory resources.
	 */
	public void cancel() {
		lock.lock();
		timer.cancel();
		timer.purge();
		lock.unlock();
	}
}

