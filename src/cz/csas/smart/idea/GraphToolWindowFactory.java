package cz.csas.smart.idea;

import com.intellij.openapi.compiler.CompilationStatusListener;
import com.intellij.openapi.compiler.CompileContext;
import com.intellij.openapi.compiler.CompilerManager;
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
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.Viewer;
import org.jetbrains.annotations.NotNull;

import static gherkin.util.FixJava.readStream;

public class GraphToolWindowFactory implements ToolWindowFactory {

    private ToolWindow toolWindow;

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
    }

    private void reloadGraph(PsiFile file) {
        toolWindow.getComponent().removeAll();
        if (file != null) {
            Graph graph = new SingleGraph("Process Diagram");
            graph.setAttribute("ui.antialias");
            graph.setAttribute("ui.quality");
            String stylesheet = new String(readStream(getClass().getResourceAsStream("/graph.css")));
            graph.addAttribute("ui.stylesheet", stylesheet);

            PsiUtils.getTasks(file.getFirstChild(), null).forEach(task -> graph.addNode(task.getText()));
            Viewer graphViewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_GUI_THREAD);
            graphViewer.enableAutoLayout();
            toolWindow.getComponent().add(graphViewer.addDefaultView(false));
        }
    }

}
