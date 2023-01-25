package ro.aesm.qc.meta.data.tests.dataset;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

import ro.aesm.qc.api.meta.component.IMetaComponentModel;
import ro.aesm.qc.meta.data.dataset.DsModel;
import ro.aesm.qc.meta.data.dataset.DsParser;
import ro.aesm.qc.meta.data.dataset.processor.reader.DsDbReader;

public class Test_DsDbReader {

	@Test
	public void test_xrate1() throws Exception {

		String metaLocation = "ro/aesm/qc/meta/data/tests/dataset/dbreader_currencies_meta.xml";
		String dsName = "currencies";

		InputStream is = this.getClass().getClassLoader().getResourceAsStream(metaLocation);
		DsParser parser = new DsParser();
		Document doc = parser.getXmlDocument(is);
		Map<String, IMetaComponentModel> modelMap = parser.parseChildrenAsMap(doc.getDocumentElement());
		DsModel dsModel = (DsModel) modelMap.get(dsName);
		assertTrue((dsModel != null), "Data-set " + metaLocation + ":" + dsName + " resolved");

		DsDbReader dsReader = new DsDbReader();
		List<Map<String, Object>> result = dsReader.read(dsModel, null);

		System.out.println("Finished");
	}

}
