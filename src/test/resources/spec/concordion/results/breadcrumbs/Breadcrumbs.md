# Breadcrumbs

To make it easier to navigate around the results, and to remove the headache of having to maintain upward links manually, breadcrumbs are automatically added to documents. The package structure provides the breadcrumb structure.

### Example

Given the following source files:

<div>
        <table concordion:execute="setUpResource(#resourceName, #content)">
            <tr>
                <th concordion:set="#resourceName">Resource Name</th>
                <th concordion:set="#content">Content</th>
            </tr>
            <tr>
                <td><code>/spec/admin/Admin.html</code></td>
                <td><pre>&lt;html /&gt;</pre></td>
            </tr>
            <tr>
                <td><code>/spec/admin/user/User.html</code></td>
                <td><pre>&lt;html&gt;
&lt;title&gt;Users&lt;/title&gt;                
&lt;/html&gt;</pre></td>
            </tr>
            <tr>
                <td><code>/spec/admin/user/DeletingUsers.html</code></td>
                <td><pre>&lt;html /&gt;</pre></td>
            </tr>
        </table>
</div>        
        
We expect these breadcrumbs to be generated:

<div>
        <table concordion:execute="#breadcrumbs = getBreadcrumbsFor(#resourceName)">
            <tr>
                <th concordion:set="#resourceName">Resource Name</th>
                <th concordion:assertEquals="#breadcrumbs.text">Breadcrumb Text</th>
                <th concordion:assertEquals="#breadcrumbs.html">Breadcrumb HTML</th>
            </tr>
            <tr>
                <td><code>/spec/admin/Admin.html</code></td>
                <td></td>
                <td><pre></pre></td>
            </tr>
            <tr>
                <td><code>/spec/admin/user/User.html</code></td>
                <td nowrap="nowrap">Admin &gt;</td>
                <td><pre>
&lt;span class="breadcrumbs"&gt; _
&lt;a href="../Admin.html"&gt;Admin&lt;/a&gt; &amp;gt; _
&lt;/span&gt;
</pre></td>
            </tr>
            <tr>
                <td><code>/spec/admin/user/DeletingUsers.html</code></td>
                <td nowrap="nowrap">Admin &gt; Users &gt;</td>
                <td><pre>
&lt;span class="breadcrumbs"&gt; _
&lt;a href="../Admin.html"&gt;Admin&lt;/a&gt; &amp;gt; _
 &lt;a href="User.html"&gt;Users&lt;/a&gt; &amp;gt; _
&lt;/span&gt;
</pre></td>
            </tr>
        </table>
</div>
        
## Further Details

*   [How are breadcrumbs determined?](DeterminingBreadcrumbs.html)
*   [Where does the text of the breadcrumb come from?](Wording.html)