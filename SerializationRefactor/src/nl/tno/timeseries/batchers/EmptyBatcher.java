package nl.tno.timeseries.batchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import nl.tno.timeseries.interfaces.Batcher;
import nl.tno.timeseries.interfaces.DataParticle;

public class EmptyBatcher implements Batcher {

	@Override
	public void init(String channelID, long startSequenceNr, @SuppressWarnings("rawtypes")Map stormConfig) {
		
	}

	@Override
	public List<List<DataParticle>> batch(DataParticle inputParticle) {
		ArrayList<List<DataParticle>> result = new ArrayList<List<DataParticle>>();
		ArrayList<DataParticle> batchedParticles = new ArrayList<DataParticle>();
		batchedParticles.add(inputParticle);
		result.add(batchedParticles);
		return result;
	}

}