package scarab.modular;

import java.util.ArrayList;
import java.util.List;

public class DataLine {

	private List<DataValue> values;

	public List<DataValue> getValues() {
		return values;
	}

	public void setValues(List<DataValue> values) {
		this.values = values;
	}

	public void add(DataValue value) {
		if (null == values) {
			values = new ArrayList<DataValue>();
		}
		values.add(value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		if (null != values && !values.isEmpty()) {
			for (DataValue dataValue : values) {
				buffer.append(dataValue);
				buffer.append(" - ");
			}
		}

		return buffer.toString();
	}

}
