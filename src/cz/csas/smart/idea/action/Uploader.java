package cz.csas.smart.idea.action;

import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.ui.MessageType;
import com.intellij.openapi.ui.popup.Balloon;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.StatusBar;
import com.intellij.openapi.wm.WindowManager;
import com.intellij.ui.awt.RelativePoint;
import cz.csas.smart.idea.EnvironmentComponent;
import cz.csas.smart.idea.SmartCaseAPIClient;
import cz.csas.smart.idea.model.Environment;
import cz.csas.smart.idea.model.UploadResponse;
import cz.csas.smart.idea.ui.UploadDialog;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

public class Uploader implements HyperlinkListener {

    private VirtualFile file;
    private StatusBar statusBar;

    public enum DeployOptions {
        NONE, DEPLOY, HOTDEPLOY;
    }

    public Uploader(DataContext ctx) {
        this.file = ctx.getData(DataKeys.VIRTUAL_FILE);
        this.statusBar = WindowManager.getInstance().getStatusBar(DataKeys.PROJECT.getData(ctx));
    }

    public void upload(EnvironmentComponent env) {
        DeployOptions options = env.isAutoDeploy() ? DeployOptions.DEPLOY : DeployOptions.NONE;
        this.upload(env.getActiveEnvironment(), options);
    }

    public void upload(Environment env, DeployOptions options) {
        if (file != null) try {
            SmartCaseAPIClient client = SmartCaseAPIClient.getInstance();
            UploadResponse upload = client.upload(file, env);
            String message = "Upload of " + file.getName() + " to " + env.getName() + " complete.";
            if (options != DeployOptions.NONE) {
                client.deploy(upload.getId(), env, options == DeployOptions.HOTDEPLOY);
                message = "Deployment of " + file.getName() + " to " + env.getName() + " complete. " +
                    "<a href='1'>Click here to start the case.</a>";
            }

            JBPopupFactory.getInstance()
                .createHtmlTextBalloonBuilder(message, MessageType.INFO, this)
                .setFadeoutTime(3500)
                .createBalloon()
                .show(RelativePoint.getCenterOf(statusBar.getComponent()), Balloon.Position.atRight);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error uploading file, " + ex.toString());
        }
    }

    @Override
    public void hyperlinkUpdate(HyperlinkEvent event) {
        System.out.println(event.getURL());
    }
}
