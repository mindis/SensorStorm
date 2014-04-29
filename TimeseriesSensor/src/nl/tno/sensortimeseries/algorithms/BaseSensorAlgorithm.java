package nl.tno.sensortimeseries.algorithms;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import nl.tno.sensortimeseries.model.Measurement;
import nl.tno.sensortimeseries.operation.RecurringTimerTask�nterface;
import nl.tno.sensortimeseries.operation.SingleTimerTask�nterface;
import nl.tno.sensortimeseries.operation.TimerControllerInterface;

abstract public class BaseSensorAlgorithm implements Serializable, SingleTimerTask�nterface, RecurringTimerTask�nterface {
	private static final long serialVersionUID = 3175889757656827653L;
	private TimerControllerInterface timerController;
	protected String incommingSensorid;
	protected long initTimestamp;

	public void initAlgorithm(String incommingSensorid, 
							  long initTimestamp, 
							  @SuppressWarnings("rawtypes") Map conf, 
							  TimerControllerInterface timerController) {
		this.incommingSensorid = incommingSensorid;
		this.initTimestamp = initTimestamp;
		this.timerController = timerController;
	}
	
	abstract public List<Measurement> handleMeasurement(Measurement measurement);

	
	protected void registerSensorForRecurringTimerTask(String sensorid, long timerFreq, RecurringTimerTask�nterface recurringTimerTask�nterface) {
		timerController.registerSensorForRecurringTimerTask(sensorid, timerFreq, recurringTimerTask�nterface);
	}
	
	protected void registerSensorForSingleTimerTask(String sensorid, long sleepTime, SingleTimerTask�nterface singleTimerTask�nterface) {
		timerController.registerSensorForSingleTimerTask(sensorid, sleepTime, singleTimerTask�nterface);
	}


}
