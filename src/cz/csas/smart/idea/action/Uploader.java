package cz.csas.smart.idea.action;

import com.intellij.openapi.vfs.VirtualFile;
import cz.csas.smart.idea.SmartCaseAPIClient;
import cz.csas.smart.idea.model.Environment;
import cz.csas.smart.idea.model.UploadResponse;
import cz.csas.smart.idea.ui.UploadDialog;

import javax.swing.*;

public class Uploader {

    private Environment environment;

    public Uploader(Environment environment) {
        this.environment = environment;
    }

    public void upload(VirtualFile file, UploadDialog.DeployOptions options) {
        if (file != null) try {
            SmartCaseAPIClient client = SmartCaseAPIClient.getInstance();
            UploadResponse upload = client.upload(file, environment);
            if (options != UploadDialog.DeployOptions.NONE) {
                client.deploy(upload.getId(), environment, options == UploadDialog.DeployOptions.HOTDEPLOY);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error uploading file, " + ex.toString());
        }
    }

}
