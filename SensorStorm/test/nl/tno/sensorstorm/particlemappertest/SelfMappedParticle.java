package nl.tno.sensorstorm.particlemappertest;

import java.util.Map;

import nl.tno.sensorstorm.api.annotation.Mapper;
import nl.tno.sensorstorm.api.particles.Particle;

@Mapper(SomeCustomMapper.class)
public class SelfMappedParticle implements Particle {

	public long timestamp;
	public String id;
	public int intId;
	public Map<String, Double> map;
	public int shouldNotBeSaved;

	@Override
	public long getTimestamp() {
		return timestamp;
	}

	@Override
	public void setTimestamp(long sequenceNr) {
		this.timestamp = sequenceNr;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + intId;
		result = prime * result + ((map == null) ? 0 : map.hashCode());
		result = prime * result + shouldNotBeSaved;
		result = prime * result + (int) (timestamp ^ (timestamp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SelfMappedParticle other = (SelfMappedParticle) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (intId != other.intId)
			return false;
		if (map == null) {
			if (other.map != null)
				return false;
		} else if (!map.equals(other.map))
			return false;
		if (shouldNotBeSaved != other.shouldNotBeSaved)
			return false;
		if (timestamp != other.timestamp)
			return false;
		return true;
	}

}
