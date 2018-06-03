package org.concordion.internal.parser.flexmark;

import com.vladsch.flexmark.html.CustomNodeRenderer;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.*;
import com.vladsch.flexmark.util.html.Attributes;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.sequence.BasedSequence;

import java.util.HashSet;
import java.util.Set;

public class ConcordionNodeRenderer implements NodeRenderer {

    public ConcordionNodeRenderer(DataHolder options) {
    }

    @Override
    public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
        HashSet<NodeRenderingHandler<?>> set = new HashSet<NodeRenderingHandler<?>>();
        set.add(new NodeRenderingHandler<ConcordionCommandNode>(ConcordionCommandNode.class, new CustomNodeRenderer<ConcordionCommandNode>() {
            @Override
            public void render(ConcordionCommandNode node, NodeRendererContext context, HtmlWriter html) {
                renderCommand(node, context, html, node.getCommand(), node.getExpression());
            }
        }));

        return set;
    }

    private void renderCommand(ConcordionCommandNode node, NodeRendererContext context, HtmlWriter html, String command, BasedSequence variable) {
        final Attributes nodeAttributes = new Attributes();
        nodeAttributes.addValue(command, variable);
        html.setAttributes(nodeAttributes).withAttr().tag("span");
        html.text(node.getChars());
        context.delegateRender();
        html.closeTag("span");
    }

    public static class Factory implements NodeRendererFactory {
        @Override
        public NodeRenderer create(final DataHolder options) {
            return new ConcordionNodeRenderer(options);
        }
    }
}
