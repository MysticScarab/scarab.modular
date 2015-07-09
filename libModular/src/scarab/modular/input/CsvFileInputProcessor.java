package scarab.modular.input;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import scarab.modular.DataLine;
import scarab.modular.DataType;
import scarab.modular.DataValue;

public class CsvFileInputProcessor extends AbstractFileInputProcessor {

	public static final String DELIMITER_COMMA = ",";
	public static final String DELIMITER_SEMICOLON = ";";
	public static final String DELIMITER_TABULATOR = "\t";

	private boolean headerIncluded = false;
	private String delimiter = DELIMITER_COMMA;
	private boolean quotedValues = true;

	private DataLine headerLine;

	/**
	 * Full constructor with all possible parameters.
	 * 
	 * @param inputFile
	 *            input file to process
	 * @param headerIncluded
	 *            is there a header line included?
	 * @param delimiter
	 *            the delimiter used in the input file
	 * @param quotedValues
	 *            are double quotes used for values? then they are removed
	 */
	public CsvFileInputProcessor(File inputFile, boolean headerIncluded, String delimiter, boolean quotedValues) {
		super(inputFile);
		this.headerIncluded = headerIncluded;
		this.delimiter = delimiter;
		this.quotedValues = quotedValues;
	}

	public CsvFileInputProcessor(File inputFile, boolean headerIncluded, String delimiter) {
		this(inputFile, headerIncluded, DELIMITER_COMMA, true);
	}

	public CsvFileInputProcessor(File inputFile, boolean headerIncluded) {
		this(inputFile, headerIncluded, DELIMITER_COMMA);
	}

	@Override
	protected List<DataLine> processFile(File inputFile) throws IOException {
		List<DataLine> dataLines = new ArrayList<DataLine>();

		BufferedReader reader = new BufferedReader(new FileReader(inputFile));
		String line;
		if (headerIncluded) {
			line = reader.readLine();
			headerLine = processLine(line);
		}

		// content verarbeiten
		while (null != (line = reader.readLine())) {
			DataLine dataLine = processLine(line);
			dataLines.add(dataLine);
		}

		reader.close();

		return dataLines;

	}

	private DataLine processLine(String line) {
		DataLine dataLine = new DataLine();

		StringTokenizer tokenizer = new StringTokenizer(line, delimiter);
		Iterator<DataValue> headerValues;
		if (headerIncluded && null != headerLine) {
			headerValues = headerLine.getValues().iterator();
		} else {
			headerValues = getArificialHeader(tokenizer.countTokens());
		}
		while (tokenizer.hasMoreTokens()) {
			DataValue value = new DataValue();
			value.setName(headerValues.next().getValue());
			String token = tokenizer.nextToken();
			if (quotedValues && token.startsWith("\"") && token.endsWith("\"") && token.length() >= 2) {
				token = token.substring(1, token.length() - 1);
			}
			value.setValue(token);
			value.setType(DataType.STRING);
			dataLine.add(value);
		}

		return dataLine;
	}

	/**
	 * If no header is included in the input file a header is generated, because
	 * every {@link DataValue} object needs an name. The size of the generated
	 * header is configurable.
	 * 
	 * @param countTokens
	 *            how many header entries should be created.
	 * @return an iterator for the generated header
	 */
	private Iterator<DataValue> getArificialHeader(int countTokens) {
		ArrayList<DataValue> header = new ArrayList<DataValue>();
		for (int i = 0; i < countTokens; i++) {
			DataValue value = new DataValue();
			value.setName("HEADER-" + i);
			value.setValue("HEADER-" + i);
			value.setType(DataType.STRING);
			header.add(value);
		}

		return header.iterator();
	}

}
