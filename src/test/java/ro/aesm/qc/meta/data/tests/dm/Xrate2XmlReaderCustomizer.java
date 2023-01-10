package ro.aesm.qc.meta.data.tests.dm;

import java.util.Map;

import ro.aesm.qc.meta.data.dm.processor.reader.DefaultDsReaderCustomizer;

public class Xrate2XmlReaderCustomizer extends DefaultDsReaderCustomizer {

	@Override
	public void onNewRecord(String listName, Map<String, Object> rec, Map<String, Object> parentRec) {
		if (listName.equals("currencies")) {
			if (rec.get("code").equals("RON")) {
				String name = (String) rec.get("name");
				rec.put("name", name + " updated in customizer");
			}
		}
	}
}
