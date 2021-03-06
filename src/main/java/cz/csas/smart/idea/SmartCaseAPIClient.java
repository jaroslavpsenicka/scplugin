package cz.csas.smart.idea;

import com.google.common.net.MediaType;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.intellij.openapi.vfs.VirtualFile;
import cz.csas.smart.idea.model.EditorDef;
import cz.csas.smart.idea.model.Environment;
import cz.csas.smart.idea.model.UploadResponse;
import cz.csas.smart.idea.model.Violation;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.ByteArrayPartSource;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.http.HttpStatus;
import org.junit.Assert;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SmartCaseAPIClient {

    private static final String BASE_URI = "/api/design/casemodel";
    private static final String EDITORS_URI = "/api/design/editordefinition";
    private static SmartCaseAPIClient instance = new SmartCaseAPIClient();
    private HttpClient client;
    private Gson gson;
    private Map<String, EditorDef> editors = null;

    public SmartCaseAPIClient() {
        client = new HttpClient();
        client.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
        client.getHttpConnectionManager().getParams().setSoTimeout(5000);
        gson = new Gson();
    }

    public static SmartCaseAPIClient getInstance() {
        return instance;
    }

    public UploadResponse upload(VirtualFile file, Environment environment) throws IOException {
        String url = environment.getUrl();
        if (url.startsWith("file://")) {
            throw new IllegalStateException("cannot upload while off-line");
        }

        PostMethod uploadMethod = new PostMethod(url + BASE_URI);
        uploadMethod.setRequestHeader("X-Smart-Username", UserComponent.getInstance().getUser());
        Part filePart = new FilePart("file", new ByteArrayPartSource(file.getName(), file.contentsToByteArray()),
            MediaType.JSON_UTF_8.type(), "UTF-8");
        uploadMethod.setRequestEntity(new MultipartRequestEntity(new Part[]{filePart}, uploadMethod.getParams()));
        client.executeMethod(uploadMethod);
        return gson.fromJson(new String(uploadMethod.getResponseBody()), UploadResponse.class);
    }

    public void deploy(VirtualFile file, Environment environment) throws IOException {
        String url = environment.getUrl();
        if (url.startsWith("file://")) {
            throw new IllegalStateException("cannot deploy while off-line");
        }

        UploadResponse uploadResponse = upload(file, environment);
        PostMethod deployMethod = new PostMethod(url + BASE_URI + "/" + uploadResponse.getId() + "/deploy");
        deployMethod.setRequestHeader("X-Smart-Username", UserComponent.getInstance().getUser());
        client.executeMethod(deployMethod);
    }

    public void hotdeploy(VirtualFile file, Environment environment) throws IOException {
        String url = environment.getUrl();
        if (url.startsWith("file://")) {
            throw new IllegalStateException("cannot deploy while off-line");
        }

        PostMethod uploadMethod = new PostMethod(url + BASE_URI + "/hotdeploy");
        uploadMethod.setRequestHeader("X-Smart-Username", UserComponent.getInstance().getUser());
        Part filePart = new FilePart("file", new ByteArrayPartSource(file.getName(), file.contentsToByteArray()),
                "application/json", "UTF-8");
        uploadMethod.setRequestEntity(new MultipartRequestEntity(new Part[]{filePart}, uploadMethod.getParams()));
        client.executeMethod(uploadMethod);
        Assert.assertFalse("server error. Try later or check server.", HttpStatus.SC_INTERNAL_SERVER_ERROR == uploadMethod.getStatusCode());
        Assert.assertFalse(uploadMethod.getResponseBodyAsString(), HttpStatus.SC_BAD_REQUEST == uploadMethod.getStatusCode());
    }

    public List<Violation> validate(VirtualFile file, Environment environment) throws IOException {
        String url = environment.getUrl();
        if (url.startsWith("file://")) {
            throw new IllegalStateException("cannot validate while off-line");
        }

        PostMethod validateMethod = new PostMethod(url + BASE_URI + "/validate");
        validateMethod.setRequestHeader("X-Smart-Username", UserComponent.getInstance().getUser());
        Part filePart = new FilePart("file", new ByteArrayPartSource(file.getName(), file.contentsToByteArray()),
            "multipart/form-data", "UTF-8");
        validateMethod.setRequestEntity(new MultipartRequestEntity(new Part[]{filePart}, validateMethod.getParams()));
        client.executeMethod(validateMethod);

        if (validateMethod.getStatusCode() == HttpStatus.SC_OK) {
            Type listType = new TypeToken<ArrayList<Violation>>() {}.getType();
            final byte[] responseBody = validateMethod.getResponseBody();
            return gson.fromJson(new String(responseBody), listType);
        } else {
            return Collections.emptyList();
        }
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
            return gson.fromJson(get.getResponseBodyAsString(), new TypeToken<ArrayList<EditorDef>>() {
            }.getType());
        } else if (url.startsWith("file://")) {
            String base = url.substring(7); // file://
            InputStreamReader reader = new InputStreamReader(getClass().getResourceAsStream(base + EDITORS_URI));
            return gson.fromJson(reader, new TypeToken<ArrayList<EditorDef>>() {
            }.getType());
        }

        return Collections.emptyList();
    }
}
