package ro.aesm.qc.meta.data.tests.datapack;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

import ro.aesm.qc.api.base.IExecutionContext;
import ro.aesm.qc.api.meta.component.IMetaComponentModel;
import ro.aesm.qc.base.ExecutionContext;
import ro.aesm.qc.meta.data.datapack.DpModel;
import ro.aesm.qc.meta.data.datapack.DpParser;
import ro.aesm.qc.meta.data.datapack.processor.reader.DpXmlReader;

public class Test_DpXmlReader {

	@Test
	public void test_rates() throws Exception {

		String metaLocation = "ro/aesm/qc/meta/data/tests/datapack/xmlreader_rates_meta.xml";
		String dataLocation = "ro/aesm/qc/meta/data/tests/datapack/xmlreader_rates_data.xml";
		String dpName = "xrates";

		String dsCurrenciesName = "currencies";
		String dsRatesName = "rates";

		InputStream is = this.getClass().getClassLoader().getResourceAsStream(metaLocation);
		DpParser parser = new DpParser();
		Document doc = parser.getXmlDocument(is);
		Map<String, IMetaComponentModel> modelMap = parser.parseChildrenAsMap(doc.getDocumentElement());
		DpModel model = (DpModel) modelMap.get(dpName);
		assertTrue((model != null), "Datapack " + metaLocation + ":" + dpName + " resolved");

		try (InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(dataLocation)) {
			DpXmlReader reader = new DpXmlReader();
			Map<String, List<Map<String, Object>>> result = new HashMap<>();

			reader.read(model, inputStream, result, this.createExecutionContext());

			assertEquals(2, result.keySet().size(), "Has 2 datasets");
			assertEquals(true, result.containsKey(dsCurrenciesName), "Has dataset named `" + dsCurrenciesName + "`");

			List<Map<String, Object>> currenciesList = result.get(dsCurrenciesName);
			assertEquals(2, currenciesList.size(), "List `" + dsCurrenciesName + "` has 2 items");

			Map<String, Object> rec = currenciesList.get(0);
			assertEquals("Romania leu updated in customizer", rec.get("name"), "Name = Romania leu");
			assertEquals("RON", rec.get("code"), "code = RON");

			rec = currenciesList.get(1);
			assertEquals("Euro", rec.get("name"), "Name = Euro");
			assertEquals("EUR", rec.get("code"), "code = EUR");

			List<Map<String, Object>> ratesList = result.get(dsRatesName);
			assertEquals(4, ratesList.size(), "List `" + dsRatesName + "` has 4 items");

			rec = ratesList.get(0);
			assertEquals(currenciesList.get(0).get("code"), rec.get("ref_currency"),
					"ref_currency equals with parent `code` ");
			assertEquals(currenciesList.get(0).get("rownum"), rec.get("ref_currency_rownum"),
					"ref_currency_rownum with parent `rownum` ");

			assertEquals("2022-10-06", rec.get("dateFromAttr1"), "dateFromAttr1 ");
			assertEquals("2022-10-07", rec.get("dateFromAttr2"), "dateFromAttr2 ");
			assertEquals("2022-10-08", rec.get("dateFromField1"), "dateFromField1 ");
			assertEquals("2022-10-09", rec.get("dateFromField2"), "dateFromField2 ");

			rec = ratesList.get(3);
			assertEquals(currenciesList.get(1).get("code"), rec.get("ref_currency"),
					"ref_currency equals with parent `code` ");
			assertEquals(currenciesList.get(1).get("rownum"), rec.get("ref_currency_rownum"),
					"ref_currency_rownum with parent `rownum` ");
		}

	}

	private IExecutionContext createExecutionContext() {
		return new ExecutionContext("12345", "user-name");
	}
}
