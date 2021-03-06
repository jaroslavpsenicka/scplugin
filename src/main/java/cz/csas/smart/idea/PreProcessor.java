package cz.csas.smart.idea;

import com.intellij.mock.MockVirtualFile;
import com.intellij.openapi.vfs.VirtualFile;
import org.apache.commons.io.IOUtils;

import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PreProcessor {

    public static final String IMPORT_REGEX = "\\s*//\\s*@import\\(\\s*(.*)\\s*\\)";
    private final Pattern pattern;

    public PreProcessor() {
        pattern = Pattern.compile(IMPORT_REGEX);
    }

    public VirtualFile process(VirtualFile file) throws Exception {
        String contents = new String(file.contentsToByteArray(), Charset.forName("UTF-8"));
        StringBuffer buffer = new StringBuffer(contents);
        Matcher matcher = pattern.matcher(contents);
        while (matcher.find()) {
            VirtualFile refFile = file.getParent().findFileByRelativePath(matcher.group(1));
            if (refFile != null && refFile.exists()) {
                buffer.replace(matcher.start(), matcher.end(), IOUtils.toString(refFile.getInputStream(), "UTF-8"));
            } else throw new IllegalStateException("file " + matcher.group(1) + " not found");

            matcher = pattern.matcher(buffer.toString());
        }

        return new MockVirtualFile(file.getName(), buffer.toString());
    }

}
