package ro.aesm.qc.meta.data.tests.dm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

import ro.aesm.qc.api.base.IExecutionContext;
import ro.aesm.qc.api.meta.component.IMetaComponentModel;
import ro.aesm.qc.base.ExecutionContext;
import ro.aesm.qc.meta.data.dm.DmModel;
import ro.aesm.qc.meta.data.dm.DmParser;
import ro.aesm.qc.meta.data.dm.processor.reader.DsCsvReader;
import ro.aesm.qc.meta.data.dm.processor.reader.DsXmlReader;

public class Test_DmFileReader {

	private IExecutionContext createExecutionContext() {
		return new ExecutionContext("12345", "user-name");
	}

	@Test
	public void test_xrate1() throws Exception {

		String metaLocation = "ro/aesm/qc/meta/data/tests/dm/reader/xmlreader_xrate1_meta.xml";
		String dataLocation = "ro/aesm/qc/meta/data/tests/dm/reader/xmlreader_xrate1_data.xml";

		InputStream is = this.getClass().getClassLoader().getResourceAsStream(metaLocation);
		DmParser parser = new DmParser();
		Document doc = parser.getXmlDocument(is);
		Map<String, IMetaComponentModel> modelMap = parser.parseChildrenAsMap(doc.getDocumentElement());
		DmModel model = (DmModel) modelMap.get("xrates");
		assertTrue((model != null), "Data-map " + metaLocation + ":xrates resolved");

		try (InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(dataLocation)) {
			DsXmlReader dmReader = new DsXmlReader();

			Map<String, List<Map<String, Object>>> result = dmReader.read(model, inputStream,
					this.createExecutionContext());

			assertEquals(1, result.keySet().size(), "Has one list");
			assertEquals(true, result.containsKey("rates"), "Has list named `rates`");

			List<Map<String, Object>> list = result.get("rates");
			assertEquals(3, list.size(), "List `rates` has 3 items");

			Map<String, Object> rec;
			rec = list.get(0);
			assertEquals("EUR", rec.get("currency"), "Record 1, currency=EUR");
			assertEquals("2022-10-07", rec.get("date"), "Record 1, date=2022-10-07");
			assertEquals("RON", rec.get("ref_currency"), "Record 1, ref_currency=RON");
			assertEquals("Romania leu", rec.get("ref_currency_name"), "Record 1, ref_currency=Romania leu");
			assertEquals(false, rec.containsKey("factor"), "Record 1, no multiplier");
			assertEquals("4.9", rec.get("rate"), "Record 1, rate=4.9");
			assertEquals("user-name", rec.get("created_by"), "Record 1, created_by=user-name");
			assertEquals(true, rec.get("created_at") instanceof LocalDateTime, "Record 1, created_at is date");

			rec = list.get(2);
			assertEquals("HUF", rec.get("currency"), "Record 3, currency=HUF");
			assertEquals("2022-10-07", rec.get("date"), "Record 3, date=2022-10-07");
			assertEquals("RON", rec.get("ref_currency"), "Record 3, ref_currency=RON");
			assertEquals("Romania leu", rec.get("ref_currency_name"), "Record 3, ref_currency=Romania leu");
			assertEquals("100", rec.get("factor"), "Record 3, multiplier=100");
			assertEquals("1.17", rec.get("rate"), "Record 3, rate=1.1660");
		}
	}

	@Test
	public void test_xrate2() throws Exception {

		String metaLocation = "ro/aesm/qc/meta/data/tests/dm/reader/xmlreader_xrate2_meta.xml";
		String dataLocation = "ro/aesm/qc/meta/data/tests/dm/reader/xmlreader_xrate2_data.xml";

		InputStream is = this.getClass().getClassLoader().getResourceAsStream(metaLocation);
		DmParser parser = new DmParser();
		Document doc = parser.getXmlDocument(is);
		Map<String, IMetaComponentModel> modelMap = parser.parseChildrenAsMap(doc.getDocumentElement());
		DmModel model = (DmModel) modelMap.get("xrates");
		assertTrue((model != null), "Data-map " + metaLocation + ":xrates resolved");

		try (InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(dataLocation)) {
			DsXmlReader xmlParser = new DsXmlReader();
			Map<String, List<Map<String, Object>>> result = xmlParser.read(model, inputStream,
					this.createExecutionContext());

			assertEquals(2, result.keySet().size(), "Has two lists");
			assertEquals(true, result.containsKey("currencies"), "Has list named `currencies`");
			assertEquals(true, result.containsKey("rates"), "Has list named `rates`");

			List<Map<String, Object>> currenciesList = result.get("currencies");
			List<Map<String, Object>> ratesList = result.get("rates");

			assertEquals(2, currenciesList.size(), "List `currencies` has 2 items");
			assertEquals(4, ratesList.size(), "List `rates` has 4 items");

			assertEquals("Romania leu updated in customizer", currenciesList.get(0).get("name"),
					"RON name updated in customizer");
		}
	}

	@Test
	public void test_csvCurrencies() throws Exception {

		String metaLocation = "ro/aesm/qc/meta/data/tests/dm/reader/csvreader_currencies_meta.xml";
		String dataLocation = "ro/aesm/qc/meta/data/tests/dm/reader/csvreader_currencies_data.csv";

		InputStream is = this.getClass().getClassLoader().getResourceAsStream(metaLocation);
		DmParser parser = new DmParser();
		Document doc = parser.getXmlDocument(is);
		Map<String, IMetaComponentModel> modelMap = parser.parseChildrenAsMap(doc.getDocumentElement());
		DmModel model = (DmModel) modelMap.get("currencies");
		assertTrue((model != null), "Data-map " + metaLocation + ":currencies resolved");

		try (InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(dataLocation)) {
			DsCsvReader dsCsvReader = new DsCsvReader();
			Map<String, List<Map<String, Object>>> result = dsCsvReader.read(model, inputStream,
					this.createExecutionContext());

			assertEquals(1, result.keySet().size(), "Has one list");
			assertEquals(true, result.containsKey("currencies"), "Has list named `currencies`");

			List<Map<String, Object>> currenciesList = result.get("currencies");

			assertEquals(2, currenciesList.size(), "List `currencies` has 2 items");
			Map<String, Object> rec = currenciesList.get(0);

			assertEquals("Euro", rec.get("name"), "Name = Euro");
			assertEquals("EUR", rec.get("iso3"), "iso3 = EUR");
			assertEquals("978", rec.get("isonum"), "isonum = 978");
			assertEquals("2", rec.get("decimals"), "decimals = 2");
			assertEquals("user-name", rec.get("created_by"), "created_by=user-name");
		}

	}

}