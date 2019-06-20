package oracle.documaker.json;


import com.jayway.jsonpath.*;
import com.jayway.jsonpath.spi.cache.Cache;
import com.jayway.jsonpath.spi.cache.CacheProvider;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

import static com.jayway.jsonpath.Criteria.where;
import static com.jayway.jsonpath.Filter.filter;
import static com.jayway.jsonpath.JsonPath.parse;

/**
 * Hello world!
 */
public class App {
    private static Logger logger = Logger.getLogger(App.class.getName());
    public static void main(String[] args) throws IOException {
        Properties properties = new Properties();
        InputStream input = App.class.getClassLoader().getResourceAsStream("data.properties");
        properties.load(input);
        String json = properties.getProperty("inputJson") ;

        Configuration configuration = Configuration.defaultConfiguration();
        Object document = configuration.jsonProvider().parse(json);
        configuration.addOptions(Option.DEFAULT_PATH_LEAF_TO_NULL);
        /*JsonPathread**/
        String author0 = JsonPath.read(document, "$.store.book[0].author");
        logger.info(author0);
        /*with condition*/
        List<Map<String, Object>> expensiveBooks = JsonPath.read(document, "$.store.book[?(@.price > 10)]");
        logger.info("expensiveBooks= >" + expensiveBooks);

        /*Filter* */
        Filter cheapFictionFilter = filter(
                where("category").is("fiction").and("price").lte(10)
        );
        List<Map<String, Object>> books = JsonPath.read(document, "$.store.book[?]", cheapFictionFilter);
        logger.info("After Filtering  : " + books);

        //adding property
        parse(document).put("$.store.book[0]","test","demo");
        logger.info("After adding a property : "+ document);

        //create a new node
        DocumentContext context = parse(document).put("$","test","test-value");
        logger.info("After adding a node : "+document);

        //Predicate
        Predicate expensivePredicate = new Predicate() {
            @Override
            public boolean apply(PredicateContext ctx) {
              // System.out.println("Predicate :::: "+ctx.item());
               return ctx.item(Map.class).get("category").toString().equalsIgnoreCase("fiction");
            }
        };
        List<Map<String,Object>> expensive = JsonPath.parse(document).read("$.store.book[?]",expensivePredicate);
        logger.info("Using Predicate : "+expensive);
    }
}
