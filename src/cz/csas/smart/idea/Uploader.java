package cz.csas.smart.idea;

import com.intellij.openapi.ui.MessageType;
import com.intellij.openapi.ui.popup.Balloon;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.StatusBar;
import com.intellij.ui.awt.RelativePoint;
import cz.csas.smart.idea.model.Environment;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

public class Uploader implements HyperlinkListener {

    private VirtualFile file;
    private StatusBar statusBar;

    public enum DeployOptions {
        UPLOAD, DEPLOY, HOTDEPLOY;
    }

    public Uploader(VirtualFile file, StatusBar statusBar) {
        this.file = file;
        this.statusBar = statusBar;
    }

    public void upload(Environment env, DeployOptions options) {
        String message = null;
        if (file != null) try {
            SmartCaseAPIClient client = SmartCaseAPIClient.getInstance();
            if (options == DeployOptions.UPLOAD) {
                client.upload(file, env);
                message = "Upload of " + file.getName() + " to " + env.getName() + " complete.";
            } else if (options == DeployOptions.DEPLOY) {
                client.deploy(file, env);
                message = "Deployment of " + file.getName() + " to " + env.getName() + " complete. " +
                    "<a href='1'>Click here to start the case.</a>";
            } else if (options == DeployOptions.HOTDEPLOY) {
                client.hotdeploy(file, env);
                message = "Hotdeploy of " + file.getName() + " to " + env.getName() + " complete. " +
                    "<a href='1'>Click here to start the case.</a>";
            }

            JBPopupFactory.getInstance()
                .createHtmlTextBalloonBuilder(message, MessageType.INFO, this)
                .setFadeoutTime(3500)
                .createBalloon()
                .show(RelativePoint.getNorthEastOf(statusBar.getComponent()), Balloon.Position.atRight);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error uploading file, " + ex.toString());
        }
    }

    @Override
    public void hyperlinkUpdate(HyperlinkEvent event) {
        System.out.println(event.getURL());
    }
}
