package ro.aesm.qc.meta.data.tests.dm;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

import ro.aesm.qc.api.meta.component.IMetaComponentModel;
import ro.aesm.qc.base.db.Db;
import ro.aesm.qc.meta.data.dm.DmModel;
import ro.aesm.qc.meta.data.dm.DmParser;
import ro.aesm.qc.meta.data.dm.processor.sql.DsQueryBuilder;

public class Test_DmDbReader {

	@Test
	public void test_xrate1() throws Exception {

		String metaLocation = "ro/aesm/qc/meta/data/tests/dm/reader/dbreader_meta.xml";

		InputStream is = this.getClass().getClassLoader().getResourceAsStream(metaLocation);
		DmParser parser = new DmParser();
		Document doc = parser.getXmlDocument(is);
		Map<String, IMetaComponentModel> modelMap = parser.parseChildrenAsMap(doc.getDocumentElement());
		DmModel model = (DmModel) modelMap.get("xrates");
		assertTrue((model != null), "Data-map " + metaLocation + ":xrates resolved");

		DsQueryBuilder qb = new DsQueryBuilder();
		String sql = qb.buildSql(model.getListsMap().get("rates"));
		System.out.println(sql);
		
		List<String> params =  Db.translateSql(sql);
		String translatedSql  = params.get(0);
		System.out.println("Translated sql");
		System.out.println(translatedSql);
	}

}
