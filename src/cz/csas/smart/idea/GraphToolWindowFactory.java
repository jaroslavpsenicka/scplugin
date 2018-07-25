package cz.csas.smart.idea;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.layout.HierarchicalLayout;
import org.graphstream.ui.swingViewer.DefaultView;
import org.graphstream.ui.swingViewer.basicRenderer.SwingBasicGraphRenderer;
import org.graphstream.ui.view.Viewer;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

import static gherkin.util.FixJava.readResource;
import static gherkin.util.FixJava.readStream;

public class GraphToolWindowFactory implements ToolWindowFactory {

    private ToolWindow toolWindow;
    private JComponent toolWindowContent;
    private Viewer graphViewer;
    private DefaultView defaultView;

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        this.toolWindow = toolWindow;

        Graph graph = new SingleGraph("Process Diagram");
        Node top = graph.addNode("A");
        top.addAttribute("ui.class", "important");
        top.addAttribute("ui.label", "A Node");
        graph.addNode("B" );
        graph.addNode("C" );
        graph.addEdge("AB", "A", "B");
        graph.addEdge("BC", "B", "C");
        graph.addEdge("CA", "C", "A");

        graph.setAttribute("ui.antialias");
        graph.setAttribute("ui.quality");
        String stylesheet = new String(readStream(getClass().getResourceAsStream("/graph.css")));
        graph.addAttribute("ui.stylesheet", stylesheet);

        this.graphViewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_GUI_THREAD);
        this.graphViewer.enableAutoLayout(new HierarchicalLayout());
        this.defaultView = new DefaultView(graphViewer, "graph", new SwingBasicGraphRenderer());
        this.defaultView = (DefaultView) graphViewer.addDefaultView(false);

        this.toolWindow.getComponent().add(defaultView);
    }


}
