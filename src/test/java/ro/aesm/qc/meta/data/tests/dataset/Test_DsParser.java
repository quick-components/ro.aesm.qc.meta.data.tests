package ro.aesm.qc.meta.data.tests.dataset;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.InputStream;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

import ro.aesm.qc.api.meta.component.IMetaComponentModel;
import ro.aesm.qc.meta.data.dataset.DsModel;
import ro.aesm.qc.meta.data.dataset.DsParser;

public class Test_DsParser {

	@Test
	public void test_1() throws Exception {

		String metaLocation = "ro/aesm/qc/meta/data/tests/dataset/dsparser_currencies_meta.xml";
		String dsName = "currencies";

		InputStream is = this.getClass().getClassLoader().getResourceAsStream(metaLocation);
		DsParser parser = new DsParser();
		Document doc = parser.getXmlDocument(is);
		Map<String, IMetaComponentModel> modelMap = parser.parseChildrenAsMap(doc.getDocumentElement());
		DsModel model = (DsModel) modelMap.get(dsName);
		assertTrue((model != null), "Dataset " + metaLocation + ":" + dsName + " resolved");

	}

}
