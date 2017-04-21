package Game;

import java.util.Timer;
import java.util.TimerTask;

public class CountdownTimer {
	
	//private Game game;
	// Period in seconds
	private int period;
	// Remaining time in seconds
	private int remainingTime;
	private Timer timer;
	
	public CountdownTimer(/*Game game,*/ int period, int maxTime) {
		//this.game = game;
		this.period = period;
		this.remainingTime = maxTime + 1;
		this.timer = new Timer();
	}
	
	private void decTime() {
		this.remainingTime -= period;
		if (this.remainingTime <= 0) {
			this.stopTimer();
		}
		//this.game.notifyTime(this.remainingTime);
	}
	
	public void stopTimer() {
		this.timer.cancel();
		this.timer.purge();
		this.remainingTime = 0;
	}
	
	public void runTimer() {
		final TimerTask task = new TimerTask() {
            @Override
            public void run() {
                decTime();
            }
        };
		timer.scheduleAtFixedRate(task, 0, this.period*1000);
	}
	
}
