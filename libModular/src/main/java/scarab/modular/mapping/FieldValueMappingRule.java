package scarab.modular.mapping;

public class FieldValueMappingRule {

	private String fieldName;
	private String sourceValue;
	private String targetValue;

	public FieldValueMappingRule(String fieldName, String sourceValue, String targetValue) {
		this.fieldName = fieldName;
		this.sourceValue = sourceValue;
		this.targetValue = targetValue;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getSourceValue() {
		return sourceValue;
	}

	public void setSourceValue(String sourceValue) {
		this.sourceValue = sourceValue;
	}

	public String getTargetValue() {
		return targetValue;
	}

	public void setTargetValue(String targetValue) {
		this.targetValue = targetValue;
	}

}
