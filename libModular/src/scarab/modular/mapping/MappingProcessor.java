package scarab.modular.mapping;

import scarab.modular.DataLine;
import scarab.modular.ModularException;

public interface MappingProcessor {

	public DataLine process(DataLine dataLine) throws ModularException;

}
