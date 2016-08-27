# Breadcrumb Wording

The words used in the breadcrumb are taken from the contents of the `<title>` if one is present.
If it is not present, or is empty (does not contain any alphanumeric characters) then the first `<h1>` is used.
If the `<h1>` is missing or empty, the name of the resource is used and converted from camel-case into properly spaced words.

The reason for preferring the `<title>` is that it is usually written in a more concise way - more
suitable for breadcrumbs - than the first level heading.

### [Examples](- "")

<div>
<table concordion:execute="#text = getBreadcrumbWordingFor(#resourceName, #content)">
    <tr>
        <th concordion:set="#resourceName">Resource Name</th>
        <th concordion:set="#content">Content</th>
        <th concordion:assertEquals="#text">Expected Breadcrumb Text</th>
    </tr>        
    <tr>
        <td>MyResource1.html</td>
        <td><pre>
&lt;html&gt;
    &lt;head&gt;
        &lt;title&gt;Concise Name&lt;/title&gt;
    &lt;/head&gt;
    &lt;body&gt;
        &lt;h1&gt;A Slightly Longer Name&lt;/h1&gt;
    &lt;/body&gt;
&lt;/html&gt;
</pre></td>
        <td>Concise Name</td>
    </tr>
    <tr>
        <td>MyResource2.html</td>
        <td><pre>
&lt;html&gt;
    &lt;body&gt;
        &lt;h1&gt;A Slightly Longer Name&lt;/h1&gt;
    &lt;/body&gt;
&lt;/html&gt;
</pre></td>
        <td>A Slightly Longer Name</td>
    </tr>
    <tr>
        <td>MyResource3.html</td>
        <td><pre>
&lt;html&gt;
    &lt;body&gt;
        &lt;h1&gt;A Slightly Longer Name&lt;/h1&gt;
        &lt;h1&gt;Another Long Name&lt;/h1&gt;
    &lt;/body&gt;
&lt;/html&gt;
</pre></td>
        <td>A Slightly Longer Name</td>
    </tr>
    <tr>
        <td>MyResource4.html</td>
        <td><pre>
&lt;html&gt;
    &lt;body&gt;
        &lt;h1&gt;  &lt;/h1&gt;
        &lt;h1&gt;Another Long Name&lt;/h1&gt;
    &lt;/body&gt;
&lt;/html&gt;
</pre></td>
        <td>Another Long Name</td>
    </tr>
    <tr>
        <td>MyResource.html</td>
        <td><pre>
&lt;html&gt;
    &lt;body&gt;
        &lt;h1&gt;  &lt;/h1&gt;
    &lt;/body&gt;
&lt;/html&gt;
</pre></td>
        <td>My Resource</td>
    </tr>
    <tr>
        <td>MyCamelCaseResource.html</td>
        <td><pre>
&lt;html /&gt;
</pre></td>
        <td>My Camel Case Resource</td>
    </tr>
</table>
</div>