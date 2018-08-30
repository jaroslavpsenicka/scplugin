package cz.csas.smart.idea;

import com.intellij.openapi.fileTypes.FileTypeConsumer;
import com.intellij.openapi.fileTypes.FileTypeFactory;
import org.jetbrains.annotations.NotNull;

public class SmartFileTypeFactory extends FileTypeFactory {

    public void createFileTypes(@NotNull FileTypeConsumer consumer) {
        consumer.consume(SmartFileType.INSTANCE, SmartFileType.DEFAULT_EXTENSION);
    }

}
