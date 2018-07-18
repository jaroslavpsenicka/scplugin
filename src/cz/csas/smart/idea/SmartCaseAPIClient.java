package cz.csas.smart.idea;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import cz.csas.smart.idea.model.EditorDef;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;

import java.io.IOException;
import java.util.List;

public class SmartCaseAPIClient {

    private final String url;
    private HttpClient client;

    private ObjectMapper objectMapper = new ObjectMapper();

    private static final String PING_URI = "/info";
    private static final String UPLOAD_URI = "/save";
    private static final String DEPLOY_URI = "/deploy";
    private static final String EDITORS_URI = "/api/design/editordefinition";

    public SmartCaseAPIClient(String serverUrl) {
        client = new HttpClient();
        client.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
        client.getHttpConnectionManager().getParams().setSoTimeout(5000);
        url = serverUrl.endsWith("/") ? serverUrl : serverUrl + "/";
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES , false);
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

    public List<EditorDef> readEditors() throws IOException {
        GetMethod get = new GetMethod(url + EDITORS_URI);
        get.setRequestHeader("X-Smart-Username", UserComponent.getInstance().getUser());
        client.executeMethod(get);
        CollectionType type = objectMapper.getTypeFactory().constructCollectionType(List.class, EditorDef.class);
        return objectMapper.readValue(get.getResponseBody(), type);
    }



}
