package nl.tno.sensortimeseries.operation;

import java.util.List;

import nl.tno.sensortimeseries.model.Measurement;

public interface RecurringTimerTaskĖnterface {
	
	public List<Measurement> doTimerRecurringTask(long timestamp);

}
