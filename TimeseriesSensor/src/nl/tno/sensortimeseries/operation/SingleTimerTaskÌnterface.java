package nl.tno.sensortimeseries.operation;

import java.util.List;

import nl.tno.sensortimeseries.model.Measurement;

public interface SingleTimerTask�nterface {
	
	public List<Measurement> doTimerSingleTask(long timestamp);

}
