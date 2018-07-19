package cz.csas.smart.idea;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import cz.csas.smart.idea.model.EditorDef;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SmartCaseAPIClient {

    private final String url;
    private HttpClient client;

    private Gson gson;

    private static final String PING_URI = "/info";
    private static final String UPLOAD_URI = "/save";
    private static final String DEPLOY_URI = "/deploy";
    private static final String EDITORS_URI = "/api/design/editordefinition";

    public SmartCaseAPIClient(String serverUrl) {
        client = new HttpClient();
        client.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
        client.getHttpConnectionManager().getParams().setSoTimeout(5000);
        url = serverUrl;
        gson = new Gson();
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
        if (url.startsWith("http://")) {
            GetMethod get = new GetMethod(url + EDITORS_URI);
            get.setRequestHeader("X-Smart-Username", UserComponent.getInstance().getUser());
            client.executeMethod(get);
            return gson.fromJson(get.getResponseBodyAsString(), new TypeToken<ArrayList<EditorDef>>(){}.getType());
        } else if (url.startsWith("file://")) {
            String base = url.substring(7); // file://
            InputStreamReader reader = new InputStreamReader(getClass().getResourceAsStream(base + EDITORS_URI));
            return gson.fromJson(reader, new TypeToken<ArrayList<EditorDef>>(){}.getType());
        }

        return Collections.emptyList();
    }

}
