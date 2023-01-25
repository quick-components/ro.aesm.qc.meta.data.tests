package ro.aesm.qc.meta.data.tests.dataset;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

import ro.aesm.qc.api.meta.component.IMetaComponentModel;
import ro.aesm.qc.meta.data.dataset.DsModel;
import ro.aesm.qc.meta.data.dataset.DsParser;
import ro.aesm.qc.meta.data.dataset.processor.reader.DsCsvReader;
import ro.aesm.qc.meta.data.tests.AbstractTest;

public class Test_DsCsvReader extends AbstractTest {

	@Test
	public void test_csvCurrencies() throws Exception {

		String metaLocation = "ro/aesm/qc/meta/data/tests/dataset/csvreader_currencies_meta.xml";
		String dataLocation = "ro/aesm/qc/meta/data/tests/dataset/csvreader_currencies_data.csv";
		String dsName = "currencies";

		InputStream is = this.getClass().getClassLoader().getResourceAsStream(metaLocation);
		DsParser parser = new DsParser();
		Document doc = parser.getXmlDocument(is);
		Map<String, IMetaComponentModel> modelMap = parser.parseChildrenAsMap(doc.getDocumentElement());
		DsModel model = (DsModel) modelMap.get(dsName);
		assertTrue((model != null), "Dataset " + metaLocation + ":" + dsName + " resolved");

		try (InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(dataLocation)) {
			DsCsvReader dsCsvReader = new DsCsvReader();
			Map<String, List<Map<String, Object>>> result = new HashMap<>();
			dsCsvReader.read(model, inputStream, result, this.createExecutionContext());

			assertEquals(1, result.keySet().size(), "Has one list");
			assertEquals(true, result.containsKey(dsName), "Has list named `" + dsName + "`");

			List<Map<String, Object>> currenciesList = result.get(dsName);

			assertEquals(2, currenciesList.size(), "List `" + dsName + "` has 2 items");
			Map<String, Object> rec = currenciesList.get(0);

			assertEquals("Euro", rec.get("name"), "Name = Euro");
			assertEquals("EUR", rec.get("iso3"), "iso3 = EUR");
			assertEquals("978", rec.get("isonum"), "isonum = 978");
			assertEquals("2", rec.get("decimals"), "decimals = 2");
			assertEquals("user-name", rec.get("created_by"), "created_by=user-name");
		}

	}
}
