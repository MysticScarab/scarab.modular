package scarab.modular.input;

import java.util.List;

import scarab.modular.DataLine;
import scarab.modular.ModularException;
import scarab.modular.mapping.MappingProcessor;

public interface InputProcessor {

	List<DataLine> process() throws ModularException;
	List<DataLine> process(MappingProcessor mappingProcessor) throws ModularException;

}
