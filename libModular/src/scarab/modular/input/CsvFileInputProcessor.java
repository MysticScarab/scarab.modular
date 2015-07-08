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

	private boolean headerIncluded = false;
	private DataLine headerLine;

	public CsvFileInputProcessor(File inputFile, boolean headerIncluded) {
		super(inputFile);
		this.headerIncluded = headerIncluded;
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

		StringTokenizer tokenizer = new StringTokenizer(line, ",");
		Iterator<DataValue> headerValues;
		if (headerIncluded && null != headerLine) {
			headerValues = headerLine.getValues().iterator();
		} else {
			headerValues = getArificialHeader(tokenizer.countTokens());
		}
		while (tokenizer.hasMoreTokens()) {
			DataValue value = new DataValue();
			value.setName(headerValues.next().getValue());
			value.setValue(tokenizer.nextToken());
			value.setType(DataType.STRING);
			dataLine.add(value);
		}

		return dataLine;
	}

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
