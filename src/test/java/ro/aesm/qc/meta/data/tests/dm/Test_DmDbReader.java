package ro.aesm.qc.meta.data.tests.dm;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

import ro.aesm.qc.api.meta.component.IMetaComponentModel;
import ro.aesm.qc.base.db.Db;
import ro.aesm.qc.base.db.Dbcp;
import ro.aesm.qc.meta.data.dm.DmModel;
import ro.aesm.qc.meta.data.dm.DmParser;
import ro.aesm.qc.meta.data.dm.processor.reader.DmDbReader;
import ro.aesm.qc.meta.data.dm.processor.sql.DsQueryBuilder;

public class Test_DmDbReader {

	@Test
	public void test_xrate1() throws Exception {

		String metaLocation = "ro/aesm/qc/meta/data/tests/dm/reader/dbreader_currencies_meta.xml";

		InputStream is = this.getClass().getClassLoader().getResourceAsStream(metaLocation);
		DmParser parser = new DmParser();
		Document doc = parser.getXmlDocument(is);
		Map<String, IMetaComponentModel> modelMap = parser.parseChildrenAsMap(doc.getDocumentElement());
		DmModel model = (DmModel) modelMap.get("currencies");
		assertTrue((model != null), "Data-map " + metaLocation + ":currencies resolved");

//		DsQueryBuilder qb = new DsQueryBuilder();
//		String sql = qb.buildSql(model.getListsMap().get("currencies"));
//		System.out.println(sql);
//
//		List<String> params = Db.translateSql(sql);
//		String translatedSql = params.get(0);
//		System.out.println("Translated sql");

		DmDbReader dsReader = new DmDbReader();
		Map<String, List<Map<String, Object>>> result = dsReader.read(model, null);

		//System.out.println(translatedSql);
	}

}
