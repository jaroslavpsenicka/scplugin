package cz.csas.smart.idea;

import com.intellij.json.JsonLanguage;
import com.intellij.openapi.fileTypes.LanguageFileType;
import com.intellij.openapi.util.IconLoader;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class SmartFileType extends LanguageFileType {

    public static final SmartFileType INSTANCE = new SmartFileType();
    public static final String DEFAULT_EXTENSION = "smart";

    public SmartFileType() {
        super(JsonLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public String getName() {
        return "SmartCase DSL";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "SmartCase JSON file";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return DEFAULT_EXTENSION;
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return IconLoader.getIcon("/smart.png");
    }
}
