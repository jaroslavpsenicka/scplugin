package cz.csas.smart.idea;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class SmartCaseAPIClient {

    private final String url;
    private HttpClient client;

    private static final String PING_URI = "/info";
    private static final String UPLOAD_URI = "/save";
    private static final String DEPLOY_URI = "/deploy";

    public SmartCaseAPIClient(String serverUrl) {
        client = new HttpClient();
        url = serverUrl.endsWith("/") ? serverUrl : serverUrl + "/";
    }

    public boolean ping() throws IOException {
        GetMethod pingMethod = new GetMethod(url + PING_URI);
        return client.executeMethod(pingMethod) == HttpStatus.SC_OK;
    }

    public String upload(byte[] contents) throws IOException {
        PostMethod uploadMethod = new PostMethod(url + UPLOAD_URI);
        uploadMethod.setRequestEntity(new StringRequestEntity(new String(contents, "UTF-8"),
            "application/json", "UTF-8"));
        client.executeMethod(uploadMethod);
        String response = uploadMethod.getResponseBody().toString();
        System.out.println(response);
        return "1"; // TODO use real ID returned from
    }

    public void deploy(String id) throws IOException {
        PostMethod deployMethod = new PostMethod(url + DEPLOY_URI);
        deployMethod.setRequestEntity(new StringRequestEntity("{id:" + id +
            "}",  "application/json", "UTF-8"));
        client.executeMethod(deployMethod);
    }
}
