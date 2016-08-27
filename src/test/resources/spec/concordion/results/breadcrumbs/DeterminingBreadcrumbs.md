# Determining Breadcrumbs
	
Only one page in each package acts as the "index page" for that package.
The index page is used for breadcrumb links.

The index page is located by capitalising the package name and appending ".html".
If no index page is found for a package then no breadcrumb for that package is displayed.

For non-index pages within a package, the index page of the package is included as a breadcrumb
link in addition to parent package breadcrumb links.

### [Example](- "")

Given the following source files:

<div>
        <table concordion:execute="setUpResource(#resourceName)">
            <tr>
                <th concordion:set="#resourceName">Filename</th>
                <th>Remarks</th>
            </tr>
            <tr>
                <td><code>/spec/admin/Admin.html</code></td>
                <td></td>
            </tr>
            <tr>
                <td><code>/spec/admin/user/user.html</code></td>
                <td><em>Not capitalised, so is not an index page 
                despite the same wording.</em></td>
            </tr>
            <tr>
                <td><code>/spec/admin/user/AnotherPage.html</code></td>
                <td></td>
            </tr>
            <tr>
                <td><code>/spec/admin/user/deletingUsers/DeletingUsers.html</code></td>
                <td></td>
            </tr>
            <tr>
                <td><code>/spec/admin/user/deletingUsers/SecurityIssues.html</code></td>
                <td></td>
            </tr>
        </table>
</div>        

We expect these breadcrumbs to be generated:
        
<div>        
        <table concordion:execute="#text = getBreadcrumbTextFor(#resourceName)">
            <tr>
                <th concordion:set="#resourceName">Resource Name</th>
                <th concordion:assertEquals="#text">Breadcrumb Text</th>
            </tr>
            <tr>
                <td><code>/spec/admin/Admin.html</code></td>
                <td></td>
            </tr>
            <tr>
                <td><code>/spec/admin/user/user.html</code></td>
                <td>Admin &gt;</td>
            </tr>
            <tr>
                <td><code>/spec/admin/user/AnotherPage.html</code></td>
                <td>Admin &gt;</td>
            </tr>
            <tr>
                <td><code>/spec/admin/user/deletingUsers/DeletingUsers.html</code></td>
                <td>Admin &gt;</td>
            </tr>
            <tr>
                <td><code>/spec/admin/user/deletingUsers/SecurityIssues.html</code></td>
                <td>Admin &gt; Deleting Users &gt;</td>
            </tr>
        </table>
</div>