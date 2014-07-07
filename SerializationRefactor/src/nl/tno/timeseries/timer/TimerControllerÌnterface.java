package nl.tno.timeseries.timer;



public interface TimerController�nterface {

	public void registerOperationForRecurringTimerTask(String channelId, long timerFreq, TimerTaskInterface timerTask);
	
	public void registerOperationForSingleTimerTask(String channelId, long wakeupTime, TimerTaskInterface timerTask);


}
