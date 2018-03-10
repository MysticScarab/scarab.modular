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

/**
 * 
 * @author Scarab
 *
 */
public class CsvFileInputProcessor extends AbstractFileInputProcessor {

	public static final String DELIMITER_COMMA = ",";
	public static final String DELIMITER_SEMICOLON = ";";
	public static final String DELIMITER_TABULATOR = "\t";

	private boolean headerIncluded = false;
	private String delimiter = DELIMITER_COMMA;
	private boolean quotedValues = true;
	private boolean ignoreEmptyLines = false;

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

	/**
	 * 
	 * @param inputFile
	 * @param headerIncluded
	 * @param delimiter
	 */
	public CsvFileInputProcessor(File inputFile, boolean headerIncluded, String delimiter) {
		this(inputFile, headerIncluded, DELIMITER_COMMA, true);
	}

	/**
	 * 
	 * @param inputFile
	 * @param headerIncluded
	 */
	public CsvFileInputProcessor(File inputFile, boolean headerIncluded) {
		this(inputFile, headerIncluded, DELIMITER_COMMA);
	}

	/**
	 * 
	 * @return
	 */
	public boolean isHeaderIncluded() {
		return headerIncluded;
	}

	/**
	 * 
	 * @param headerIncluded
	 */
	public void setHeaderIncluded(boolean headerIncluded) {
		this.headerIncluded = headerIncluded;
	}

	/**
	 * 
	 * @return
	 */
	public String getDelimiter() {
		return delimiter;
	}

	/**
	 * 
	 * @param delimiter
	 */
	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isQuotedValues() {
		return quotedValues;
	}

	/**
	 * 
	 * @param quotedValues
	 */
	public void setQuotedValues(boolean quotedValues) {
		this.quotedValues = quotedValues;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isIgnoreEmptyLines() {
		return ignoreEmptyLines;
	}

	/**
	 * 
	 * @param ignoreEmptyLines
	 */
	public void setIgnoreEmptyLines(boolean ignoreEmptyLines) {
		this.ignoreEmptyLines = ignoreEmptyLines;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * scarab.modular.input.AbstractFileInputProcessor#processFile(java.io.File)
	 */
	@Override
	protected List<DataLine> processFile(File inputFile) throws IOException, InputException {
		List<DataLine> dataLines = new ArrayList<>();

		BufferedReader reader = new BufferedReader(new FileReader(inputFile));
		String line;

		// process data
		while (null != (line = reader.readLine())) {
			if (!line.isEmpty()) {
				DataLine dataLine = processLine(line);
				// header included and not set?
				if (headerIncluded && null == headerLine) {
					headerLine = dataLine;
				} else { // add to payload
					dataLines.add(dataLine);
				}
			} else {
				// empty lines forbidden? -> Exception
				if (!ignoreEmptyLines) {
					reader.close();
					throw new InputException("Empty lines are not allowed.");
				}
			}
		}

		reader.close();

		return dataLines;

	}

	/**
	 * Process the given line. Separate the values by the configured delimiter.
	 * At this pint it is not possible to tell the {@link DataType} so every
	 * value is assign to the DataType.STRING.
	 * 
	 * @param line
	 *            String to process
	 * @return the processed line as {@link DataLine}
	 */
	private DataLine processLine(String line) {
		DataLine dataLine = new DataLine();

		StringTokenizer tokenizer = new StringTokenizer(line, delimiter);
		Iterator<DataValue> headerValues;
		if (headerIncluded && null != headerLine) {
			headerValues = headerLine.getValues().iterator();
		} else {
			headerValues = getArtificialHeader(tokenizer.countTokens());
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
	 * every {@link DataValue} object needs a name. The size of the generated
	 * header is configurable.
	 * 
	 * @param countTokens
	 *            how many header entries should be created.
	 * @return an iterator for the generated header
	 */
	private Iterator<DataValue> getArtificialHeader(int countTokens) {
		ArrayList<DataValue> header = new ArrayList<>();
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
