package scarab.modular.mapping;

import org.junit.Test;
import scarab.modular.DataLine;
import scarab.modular.DataType;
import scarab.modular.DataValue;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.fail;

public class SimpleFieldValueMappingProcessorTest {

    @Test
    public void testProcess() {
        List<FieldValueMappingRule> mappingRules = new ArrayList<>();
        FieldValueMappingRule mappingRule = new FieldValueMappingRule("field1", "value1", "mapping1");
        //TODO: add rule content
        mappingRules.add(mappingRule);

        SimpleFieldValueMappingProcessor processor = new SimpleFieldValueMappingProcessor(mappingRules);

        DataLine originalData = new DataLine();
        DataValue value1 = new DataValue();
        value1.setName("name1");
        value1.setType(DataType.STRING);
        value1.setValue("value1");
        originalData.add(value1);

        DataLine mappedData = processor.process(originalData);
        //TODO: check result
    }

}
