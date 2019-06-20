package oracle.documaker.json;


import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;


public class sample {
    public static void main(String[] args) {
    String json = "{ \"Policy\": { \"PolicyNumber\": \"HO399288\", \"IssueDate\": \"2018-05-16\", \"Mode\": \"MONTHLY\", \"BasePremium\" : 357.20, \"Currency\" : \"USD\", \"Endorsements\": [ { \"Type\": \"AUTO\", \"EndorsementDate\" : \"2018-12-05\", \"Premium\" : 24.38, \"Currency\" : \"USD\" }, { \"Type\": \"BOAT\", \"EndorsementDate\" : \"2019-02-10\", \"Premium\" : 6.88, \"Currency\" : \"USD\" }, { \"Type\": \"CAMPER\", \"EndorsementDate\" : \"2019-02-10\", \"Premium\" : 1.04, \"Currency\" : \"USD\" } ] } }";
        Configuration conf = Configuration.defaultConfiguration();
        conf.addOptions(Option.ALWAYS_RETURN_LIST);

        Object doc = conf.jsonProvider().parse(json);
        JSONObject obj = new JSONObject();

        JSONArray contentArray = JsonPath.read(doc,"$.Policy.Endorsements[*]");
        JSONObject contentObj = new JSONObject();
        contentObj.put("Contents",contentArray);

        JSONArray layoutarray = new JSONArray();
        layoutarray.add(contentObj);

        JSONArray docArray = new JSONArray();
        JSONObject docObj = new JSONObject();
        docObj.put("PolicyNumber",JsonPath.read(doc,"$.Policy.PolicyNumber"));
        docObj.put("IssueDate",JsonPath.read(doc,"$.Policy.IssueDate"));
        docObj.put("Mode",JsonPath.read(doc,"$.Policy.Mode"));
        docObj.put("BasePremium",JsonPath.read(doc,"$.Policy.BasePremium"));
        docObj.put("Currency",JsonPath.read(doc,"$.Policy.Currency"));
        docObj.put("Layouts",layoutarray);
        docArray.add(docObj);

        obj.put("Documents",docArray);
        System.out.println(obj);
        //System.out.println("Helo :::"+JsonPath.using(Configuration.defaultConfiguration().addOptions(Option.ALWAYS_RETURN_LIST,Option.SUPPRESS_EXCEPTIONS)).parse(json).read("$.*"));
    }
}
