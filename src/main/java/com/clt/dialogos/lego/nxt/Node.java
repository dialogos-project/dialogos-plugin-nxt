package com.clt.dialogos.lego.nxt;

import com.clt.diamant.ExecutionLogger;
import java.awt.Color;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.event.MouseInputListener;

import com.clt.diamant.IdMap;
import com.clt.diamant.InputCenter;
import com.clt.diamant.WozInterface;
import com.clt.diamant.graph.ui.GraphUI;
import com.clt.diamant.graph.ui.UIElement;
import com.clt.xml.XMLWriter;

/**
 * @author Daniel Bobbert
 *
 */
public abstract class Node extends com.clt.diamant.graph.Node {

    private static final boolean LEGO_NODE_UI = false;

    public Node() {
        super();
    }

    public static Color getDefaultColor() {
        return new Color(64, 64, 64);
    }

    @Override
    public JComponent createEditorComponent(Map<String, Object> properties) {
        return this.createEditorComponentImpl(properties);
    }

    protected abstract JComponent createEditorComponentImpl(Map<String, Object> properties);

    @Override
    public com.clt.diamant.graph.Node execute(WozInterface comm, InputCenter input, ExecutionLogger logger) {
        int edge = this.executeNXT(comm);
        return this.getEdge(edge).getTarget();
    }

    protected abstract int executeNXT(WozInterface comm);

    @Override
    public UIElement createUI(GraphUI graphUI, MouseInputListener viewScroller) {
        if (Node.LEGO_NODE_UI) {
            return new LegoNodeUI(graphUI, this, viewScroller);
        } else {
            return super.createUI(graphUI, viewScroller);
        }
    }

    @Override
    public void writeVoiceXML(XMLWriter w, IdMap uid_map) {
        // no VoiceXML support
    }

    public static String getNodeTypeName(Class<?> c) {
        String name = c.getName();
        // cut off package name
        name = name.substring(name.lastIndexOf('.') + 1);
        if (name.endsWith("Node")) {
            name = name.substring(0, name.length() - 4);
        }
        return Resources.getString(name);
    }

}
