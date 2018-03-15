package dashbase;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import org.apache.lucene.util.BytesRef;
import org.junit.Test;
import rapid.ingester.RapidIngestedData;
import rapid.parser.regex.RegexDocumentParser;
import rapid.parser.regex.RegexParserPattern;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static org.junit.Assert.assertTrue;

public class regexToolsTest {
      private static final File PARSER_CONFIG_FILE = new File("src/test/resources", "regex_parser.json");
    @Test
    public void test() throws IOException {

        JsonFactory jsonFactory = new JsonFactory();
        JsonParser p = jsonFactory.createParser(PARSER_CONFIG_FILE);
        ObjectMapper mapper = new ObjectMapper();
        Map parserConfig = mapper.readValue(p, Map.class);
        String line = "10.65.124.42 - - [08/Mar/2018:15:19:34 -0800] \"GET /biz_details/maternity_data HTTP/1.1\" 200 - \"https://www.yelp.com/biz/sheetmetal-fabricators-vernal\" \"Mozilla/5.0 (Macintosh; Intel Mac OS X 10.12; rv:57.0) Gecko/20100101 Firefox/57.0\" 41 www.yelp.com 80 40428 \"-\" -";
        RegexDocumentParser parser = new RegexDocumentParser();
        parser.configure(parserConfig);
        RapidIngestedData data = new RapidIngestedData();
        boolean success = parser.parse(new BytesRef(line), data);
        assertTrue("did not parse: " + line, success);

    }
}
