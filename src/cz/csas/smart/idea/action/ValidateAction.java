package cz.csas.smart.idea.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import cz.csas.smart.idea.EnvironmentComponent;
import cz.csas.smart.idea.UserComponent;
import cz.csas.smart.idea.ValidationComponent;
import cz.csas.smart.idea.Validator;
import cz.csas.smart.idea.model.Environment;
import cz.csas.smart.idea.model.Violation;
import cz.csas.smart.idea.ui.ValidateDialog;
import org.apache.commons.lang.StringUtils;

import java.util.List;

public class ValidateAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent event) {
        Validator validator = new Validator();
        String user = UserComponent.getInstance().getUser();
        Environment env = EnvironmentComponent.getInstance().getActiveEnvironment();
        if (StringUtils.isEmpty(user)) {
            ValidateDialog dialog = new ValidateDialog(event.getProject());
            if (dialog.showAndGet()) {
                env = dialog.getSelectedEnvironment();
                UserComponent.getInstance().setUser(dialog.getUsername());
            }
        }

        ValidationComponent.getInstance().setCurrentViolations(validator.validate(env));
    }

}
