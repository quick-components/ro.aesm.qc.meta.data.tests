package ro.aesm.qc.meta.data.tests.datapack;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.InputStream;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

import ro.aesm.qc.api.meta.component.IMetaComponentModel;
import ro.aesm.qc.meta.data.datapack.DpModel;
import ro.aesm.qc.meta.data.datapack.DpParser;

public class Test_DpParser {

	@Test
	public void test_1() throws Exception {
		String metaLocation = "ro/aesm/qc/meta/data/tests/datapack/dpparser_xrate_meta.xml";
		String dpName = "xrates";

		InputStream is = this.getClass().getClassLoader().getResourceAsStream(metaLocation);
		DpParser parser = new DpParser();
		Document doc = parser.getXmlDocument(is);
		Map<String, IMetaComponentModel> modelMap = parser.parseChildrenAsMap(doc.getDocumentElement());
		DpModel model = (DpModel) modelMap.get(dpName);
		assertTrue((model != null), "Datapack " + metaLocation + ":" + dpName + " resolved");
	}

}
