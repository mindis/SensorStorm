package nl.tno.timeseries.batchers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import nl.tno.timeseries.interfaces.Batcher;
import nl.tno.timeseries.interfaces.DataParticle;

public class NumberOfParticlesBatcher implements Batcher, Serializable {

	private static final long serialVersionUID = 2852865728648428422L;
	private int nrOfParticlesToBatch;
	private List<DataParticle> buffer; 

	@Override
	public void init(String channelID, long startSequenceNr, @SuppressWarnings("rawtypes")Map stormConfig) {
		//TODO haal dit uit de stormConfig
		nrOfParticlesToBatch = 10;
		
		buffer = new ArrayList<DataParticle>();
	}

	@Override
	public List<List<DataParticle>> batch(DataParticle inputParticle) {
		ArrayList<List<DataParticle>> result = new ArrayList<List<DataParticle>>();
		
		buffer.add(inputParticle);
		while (buffer.size() >= nrOfParticlesToBatch) {
			ArrayList<DataParticle> batchedParticles = new ArrayList<DataParticle>(buffer.subList(0, nrOfParticlesToBatch));
			buffer.removeAll(batchedParticles);
			result.add(batchedParticles);
		}
		
		return result;
	}

}