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
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SmartCaseAPIClient {

    private HttpClient client;
    private Gson gson;

	private Map<String, EditorDef> editors = null;

	private static SmartCaseAPIClient instance = new SmartCaseAPIClient();

	private static final String PING_URI = "/info";
    private static final String UPLOAD_URI = "/save";
    private static final String DEPLOY_URI = "/deploy";
    private static final String EDITORS_URI = "/api/design/editordefinition";

	public static SmartCaseAPIClient getInstance() {
		return instance;
	}

    public SmartCaseAPIClient() {
        client = new HttpClient();
        client.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
        client.getHttpConnectionManager().getParams().setSoTimeout(5000);
        gson = new Gson();
    }

    public boolean ping() throws IOException {
	    String url = EnvironmentComponent.getInstance().getActiveEnvironment().getUrl();
        GetMethod pingMethod = new GetMethod(url + PING_URI);
        return client.executeMethod(pingMethod) == HttpStatus.SC_OK;
    }

    public String upload(byte[] contents) throws IOException {
	    String url = EnvironmentComponent.getInstance().getActiveEnvironment().getUrl();
        PostMethod uploadMethod = new PostMethod(url + UPLOAD_URI);
        uploadMethod.setRequestEntity(new StringRequestEntity(new String(contents, "UTF-8"),
            "application/json", "UTF-8"));
        client.executeMethod(uploadMethod);
        String response = uploadMethod.getResponseBody().toString();
        System.out.println(response);
        return "1"; // TODO use real ID returned from
    }

    public void deploy(String id) throws IOException {
	    String url = EnvironmentComponent.getInstance().getActiveEnvironment().getUrl();
        PostMethod deployMethod = new PostMethod(url + DEPLOY_URI);
        deployMethod.setRequestEntity(new StringRequestEntity("{id:" + id +
            "}",  "application/json", "UTF-8"));
        client.executeMethod(deployMethod);
    }

    public Map<String, EditorDef> getEditors() throws IOException {
		if (editors == null) editors = readEditors().stream()
			.collect(Collectors.toMap(EditorDef::getName, e -> e));
		return editors;
    }

	public void reset() {
		this.editors = null;
	}

    private List<EditorDef> readEditors() throws IOException {
	    String url = EnvironmentComponent.getInstance().getActiveEnvironment().getUrl();
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
