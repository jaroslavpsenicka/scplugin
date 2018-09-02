package cz.csas.smart.idea;

import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.vfs.VirtualFile;
import cz.csas.smart.idea.model.Environment;
import cz.csas.smart.idea.model.Violation;

import javax.swing.*;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
public class Validator {

    private final VirtualFile file;

    public Validator(VirtualFile file) {
        this.file = file;
    }

    public Validator(DataContext ctx) {
        this(ctx.getData(DataKeys.VIRTUAL_FILE));
    }

    public VirtualFile getFile() {
        return file;
    }

    public List<Violation> validate(Environment env) {
        JOptionPane.setDefaultLocale(Locale.forLanguageTag("cs-CZ"));
        try {
            SmartCaseAPIClient client = SmartCaseAPIClient.getInstance();
            return client.validate(file, env);
        } catch (UnknownHostException ex) {
            JOptionPane.showMessageDialog(null, "Nepodařilo se připojit k " + env.getUrl() + ". Jste připojeni k síti a běží server SmartCae?");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error validating file, " + ex.toString());
        }

        return Collections.emptyList();
    }
}
