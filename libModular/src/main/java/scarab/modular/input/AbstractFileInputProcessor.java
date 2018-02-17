package scarab.modular.input;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import scarab.modular.DataLine;
import scarab.modular.ModularException;
import scarab.modular.mapping.MappingProcessor;

/**
 * 
 * @author Scarab
 *
 */
public abstract class AbstractFileInputProcessor implements InputProcessor {

	private File inputFile;

	/**
	 * 
	 * @param inputFile
	 */
	public AbstractFileInputProcessor(File inputFile) {
		this.inputFile = inputFile;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see scarab.modular.input.InputProcessor#process()
	 */
	public List<DataLine> process() throws ModularException {
		return process(null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see scarab.modular.input.InputProcessor#process(scarab.modular.mapping.
	 * MappingProcessor)
	 */
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

	/**
	 * 
	 * @param inputFile
	 * @return
	 * @throws IOException
	 * @throws InputException
	 */
	protected abstract List<DataLine> processFile(File inputFile) throws IOException, InputException;

}
