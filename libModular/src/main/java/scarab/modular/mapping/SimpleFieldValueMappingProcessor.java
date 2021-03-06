package scarab.modular.mapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import scarab.modular.DataLine;
import scarab.modular.DataValue;

public class SimpleFieldValueMappingProcessor implements MappingProcessor {

    private final Map<String, List<FieldValueMappingRule>> mappingRules;

    public SimpleFieldValueMappingProcessor(List<FieldValueMappingRule> mappingRules) {
        this.mappingRules = new HashMap<>();
        for (FieldValueMappingRule fieldValueMappingRule : mappingRules) {

            List<FieldValueMappingRule> mappingRuleList;
            if (this.mappingRules.containsKey(fieldValueMappingRule.getFieldName())) {
                mappingRuleList = this.mappingRules.get(fieldValueMappingRule.getFieldName());
            } else {
                mappingRuleList = new ArrayList<>();
            }
            mappingRuleList.add(fieldValueMappingRule);
            this.mappingRules.put(fieldValueMappingRule.getFieldName(), mappingRuleList);
        }
    }

    public DataLine process(DataLine dataLine) {
        for (DataValue dataValue : dataLine.getValues()) {
            List<FieldValueMappingRule> mappingRuleList = mappingRules.get(dataValue.getName());
            if (null != mappingRuleList && !mappingRuleList.isEmpty()) {
                for (FieldValueMappingRule fieldValueMappingRule : mappingRuleList) {
                    if (dataValue.getValue().equals(fieldValueMappingRule.getSourceValue())) {
                        dataValue.setValue(fieldValueMappingRule.getTargetValue());
                    }

                }
            }
        }

        return dataLine;
    }

}
