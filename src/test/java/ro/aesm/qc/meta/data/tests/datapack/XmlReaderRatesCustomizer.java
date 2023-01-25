package ro.aesm.qc.meta.data.tests.datapack;

import java.util.List;
import java.util.Map;

import ro.aesm.qc.meta.data.dataset.processor.reader.AbstractDsCustomizer;

public class XmlReaderRatesCustomizer extends AbstractDsCustomizer {

	@Override
	public void onNewRecord(String listName, List<Map<String, Object>> records, Map<String, Object> record,
			Map<String, Object> parentRecord) {
		if (listName.equals("currencies")) {
			if (record.get("code").equals("RON")) {
				String name = (String) record.get("name");
				record.put("name", name + " updated in customizer");
			}
		}
	}
}
