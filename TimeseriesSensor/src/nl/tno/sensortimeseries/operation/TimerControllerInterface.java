package nl.tno.sensortimeseries.operation;

public interface TimerControllerInterface {

	public void registerSensorForRecurringTimerTask(String sensorid, long timerFreq, RecurringTimerTask�nterface recurringTimerTask�nterface);
	
	public void registerSensorForSingleTimerTask(String sensorid, long sleepTime, SingleTimerTask�nterface singleTimerTask�nterface);

}
