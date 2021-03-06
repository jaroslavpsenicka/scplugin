package cz.csas.smart.idea;

import com.intellij.openapi.compiler.CompilationStatusListener;
import com.intellij.openapi.compiler.CompileContext;
import com.intellij.openapi.compiler.CompilerTopics;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.FileEditorManagerAdapter;
import com.intellij.openapi.fileEditor.FileEditorManagerEvent;
import com.intellij.openapi.fileEditor.FileEditorManagerListener;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.util.messages.MessageBus;
import org.apache.commons.io.IOUtils;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.ui.layout.HierarchicalLayout;
import org.graphstream.ui.view.Viewer;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;

public class GraphToolWindowFactory implements ToolWindowFactory {

    static {
        System.setProperty("gs.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
    }

    private ToolWindow toolWindow;
    private String stylesheet;

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {

        MessageBus messageBus = project.getMessageBus();
        messageBus.connect().subscribe(FileEditorManagerListener.FILE_EDITOR_MANAGER, new FileEditorManagerAdapter() {
            public void selectionChanged(@NotNull FileEditorManagerEvent event) {
                VirtualFile file = event.getNewFile();
                reloadGraph(file != null ? PsiManager.getInstance(project).findFile(file) : null);
            }
        });

        messageBus.connect().subscribe(CompilerTopics.COMPILATION_STATUS, new CompilationStatusListener() {
            public void compilationFinished(boolean aborted, int errors, int warnings, CompileContext compileContext) {
                VirtualFile[] files = FileEditorManager.getInstance(project).getSelectedFiles();
                if (files.length > 0) reloadGraph(PsiManager.getInstance(project).findFile(files[0]));
            }
        });

        this.toolWindow = toolWindow;
        try {
            stylesheet = new String(IOUtils.toString(getClass().getResourceAsStream("/graph.css")));
        } catch (IOException ex) {
            System.out.println("Error reading graph stylesheet");
        }
    }

    private void reloadGraph(PsiFile file)  {
        toolWindow.getComponent().removeAll();
        if (file != null) {
            Graph graph = new DefaultGraph(file.getName());
            graph.setStrict(false);
            graph.addAttribute("ui.stylesheet", stylesheet);
            graph.addNode("START").addAttributes(new HashMap<String, Object>() {{
                put("ui.label", "Start");
                put("ui.class", "start");
                put("layout.weight", 0.0f);
            }});
            graph.addNode("END").addAttributes(new HashMap<String, Object>() {{
                put("ui.label", "End");
                put("ui.class", "end");
                put("layout.weight", 1.0f);
            }});

            PsiUtils.getTasks(file.getFirstChild(), null)
                    .forEach(t -> graph.addNode(t.getName()).addAttributes(new HashMap<String, Object>() {{
                                                                               put("ui.label", t.getName());
                                                                               put("layout.weight", 0.5f);
                                                                           }}
                    ));
            PsiUtils.getTransitions(file.getFirstChild())
                    .forEach(t -> graph.addEdge(t.getName(), t.getSource(), t.getTarget(), true)
                            .addAttribute("ui.label", t.getName()));

            Viewer viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_GUI_THREAD);
            viewer.enableAutoLayout();
            HierarchicalLayout layout = new HierarchicalLayout();
            layout.setRoots("START");
            layout.addSink(layout);

            toolWindow.getComponent().add(viewer.addDefaultView(false));
        }
    }

}
