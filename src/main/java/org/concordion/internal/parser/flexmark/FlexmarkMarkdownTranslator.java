package org.concordion.internal.parser.flexmark;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.profile.pegdown.Extensions;
import com.vladsch.flexmark.profile.pegdown.PegdownOptionsAdapter;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataSet;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import com.vladsch.flexmark.util.misc.Extension;

import java.util.Collection;
import java.util.Map;

public class FlexmarkMarkdownTranslator {
    private final Parser parser;
    private final HtmlRenderer htmlRenderer;

    public FlexmarkMarkdownTranslator(int pegdownExtensions, DataSet flexmarkOptions, Map<String, String> namespaces, String targetConcordionNamespace) {
        // Only interrupts an HTML block on a blank line if all tags in the HTML block are closed.
        // Closer to Pegdown HTML block parsing behaviour.
        boolean strictHtml = false;

        MutableDataHolder options = new MutableDataSet(PegdownOptionsAdapter.flexmarkOptions(strictHtml,
                Extensions.TABLES | Extensions.STRIKETHROUGH | pegdownExtensions, new Extension[0]));

        Collection<Extension> extensions = Parser.EXTENSIONS.get(options);
        extensions.add(FlexmarkConcordionExtension.create());
        options.set(Parser.EXTENSIONS, extensions);
        if (flexmarkOptions != null) {
            options.setAll(flexmarkOptions);
        }
        options.set(ConcordionMarkdownOptions.CONCORDION_ADDITIONAL_NAMESPACES, namespaces);
        options.set(ConcordionMarkdownOptions.CONCORDION_TARGET_NAMESPACE, targetConcordionNamespace);

        parser = Parser.builder(options).build();
        htmlRenderer = HtmlRenderer.builder(options).build();
    }

    public String markdownToHtml(String markdown) {
        Node document = parser.parse(markdown);
        String html = htmlRenderer.render(document);
        return html.trim();
    }
}
