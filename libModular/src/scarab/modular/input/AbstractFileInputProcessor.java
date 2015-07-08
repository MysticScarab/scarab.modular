package scarab.modular.input;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import scarab.modular.DataLine;
import scarab.modular.ModularException;
import scarab.modular.mapping.MappingProcessor;

public abstract class AbstractFileInputProcessor implements InputProcessor {

	private File inputFile;

	public AbstractFileInputProcessor(File inputFile) {
		this.inputFile = inputFile;
	}

	@Override
	public List<DataLine> process() throws ModularException {
		return process(null);
	}

	@Override
	public List<DataLine> process(MappingProcessor mappingProcessor) throws ModularException {
		List<DataLine> result = new ArrayList<DataLine>();
		List<DataLine> inputLines;
		try {
			inputLines = processFile(inputFile);
		} catch (IOException e) {
			throw new InputException(e.getMessage());
		}

		if (null != mappingProcessor) {
			for (DataLine dataLine : inputLines) {
				result.add(mappingProcessor.process(dataLine));
			}
		} else {
			result = inputLines;
		}

		return result;
	}

	protected abstract List<DataLine> processFile(File inputFile) throws IOException;

}
