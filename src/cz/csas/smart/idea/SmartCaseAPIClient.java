package cz.csas.smart.idea;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;

import java.io.IOException;

public class SmartCaseAPIClient {

    private HttpClient client;
    private GetMethod pingMethod;
    private PostMethod validateMethod;

    private static final String PING_URI = "/info";
    private static final String VALIDATE_URI = "/validate";

    public SmartCaseAPIClient(String serverUrl) {
        client = new HttpClient();
        String url = serverUrl.endsWith("/") ? serverUrl : serverUrl + "/";
        pingMethod = new GetMethod(url + PING_URI);
        validateMethod = new PostMethod(url + VALIDATE_URI);
    }

    public boolean ping() throws IOException {
        return client.executeMethod(pingMethod) == HttpStatus.SC_OK;
    }

    public boolean validate(String text) throws IOException {
        validateMethod.setRequestEntity(new StringRequestEntity(text,  "application/json", "UTF-8"));
        return client.executeMethod(validateMethod) == HttpStatus.SC_OK;
    }
}
