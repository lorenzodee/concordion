# Determining Breadcrumbs
	
Only one page in each package acts as the "index page" for that package.
The index page is used for breadcrumb links.

The index page is located by:

1. if the package name is [snake_case](https://en.wikipedia.org/wiki/Snake_case), trying both the snake_case and 
[camelCase](https://en.wikipedia.org/wiki/Camel_case) variants of the index page name, and
2. capitalising the package name, and
3. appending "._type_" for each possible specification type (`html`, `xhtml`, `md` or `markdown` by default, 
but extensions can configure additional specification types via the `ConcordionExtender` interface).

For example:

* the index page for the package `foo` will be one of `Foo.html`, `Foo.xhtml`, `Foo.md` or `Foo.markdown`
* the index page for the package `foo_bar` will be one of:
   `Foo_bar.html`, `Foo_bar.xhtml`, `Foo_bar.md`, `Foo_bar.markdown`,
   `FooBar.html`, `FooBar.xhtml`, `FooBar.md` or `FooBar.markdown`

If no index page is found for a package then no breadcrumb for that package is displayed.

For non-index pages within a package, the index page of the package is included as a breadcrumb
link in addition to parent package breadcrumb links.

### [Examples](- "")

Given the following source files:

<div>
        <table concordion:execute="setUpResource(#resourceName, #html)">
            <tr>
                <th concordion:set="#resourceName">Filename</th>
                <th concordion:set="#html">HTML</th>
            </tr>
            <tr>
                <td><code>/spec/admin/Admin.html</code></td>
                <td>&lt;html/&gt;</td>
            </tr>
            <tr>
                <td><code>/spec/admin/Auth.html</code></td>
                <td>&lt;html/&gt;</td>
            </tr>
            <tr>
                <td><code>/spec/admin/group/Group.md</code></td>
                <td>&lt;html/&gt;</td>
            </tr>
            <tr>
                <td><code>/spec/admin/group/Permissions.markdown</code></td>
                <td>&lt;html/&gt;</td>
            </tr>
            <tr>
                <td><code>/spec/admin/user/user.html</code></td>
                <td>&lt;html/&gt;</td>
            </tr>
            <tr>
                <td><code>/spec/admin/user/AnotherPage.html</code></td>
                <td>&lt;html/&gt;</td>
            </tr>
            <tr>
                <td><code>/spec/admin/user/deletingUsers/DeletingUsers.html</code></td>
                <td>&lt;html/&gt;</td>
            </tr>
            <tr>
                <td><code>/spec/admin/user/deletingUsers/SecurityIssues.html</code></td>
                <td>&lt;html/&gt;</td>
            </tr>
            <tr>
                <td><code>/spec/admin/user/adding_some_users/AddingSomeUsers.html</code></td>
                <td>&lt;html&gt;&lt;head&gt;&lt;title&gt;Adding Some Users&lt;/title&gt;&lt;/head&gt;&lt;/html&gt;</td>
            </tr>
            <tr>
                <td><code>/spec/admin/user/adding_some_users/SecurityIssues.html</code></td>
                <td>&lt;html/&gt;</td>
            </tr>
            <tr>
                <td><code>/spec/admin/user/changing_the_users/Changing_the_users.html</code></td>
                <td>&lt;html&gt;&lt;head&gt;&lt;title&gt;Changing Users&lt;/title&gt;&lt;/head&gt;&lt;/html&gt;</td>
            </tr>
            <tr>
                <td><code>/spec/admin/user/changing_the_users/Security_issues.html</code></td>
                <td>&lt;html/&gt;</td>
            </tr>
        </table>
</div>        

We expect these breadcrumbs to be generated:
        
<div>        
        <table concordion:execute="#text = getBreadcrumbTextFor(#resourceName)">
            <tr>
                <th concordion:set="#resourceName">Resource Name</th>
                <th concordion:assertEquals="#text">Breadcrumb Text</th>
                <th>Remarks</th>
            </tr>
            <tr>
                <td><code>/spec/admin/Admin.html</code></td>
                <td></td>
                <td></td>
            </tr>
            <tr>
                <td><code>/spec/admin/Auth.html</code></td>
                <td>Admin &gt;</td>
                <td></td>
            </tr>
            <tr>
                <td><code>/spec/admin/group/Group.md</code></td>
                <td>Admin &gt;</td>
                <td><em>This is an index page, so only the parent package breadcrumb links are shown.</em></td>
            </tr>
            <tr>
                <td><code>/spec/admin/group/Permissions.markdown</code></td>
                <td>Admin &gt; Group &gt;</td>
                <td><em>This is not an index page, so the index page of the package is included in addition to the 
                parent package breadcrumb links.</em></td>
            </tr>
            <tr>
                <td><code>/spec/admin/user/user.html</code></td>
                <td>Admin &gt;</td>
                <td><em>Not capitalised, so is not an index page despite the same wording.</em></td>
            </tr>
            <tr>
                <td><code>/spec/admin/user/AnotherPage.html</code></td>
                <td>Admin &gt;</td>
                <td></td>
            </tr>
            <tr>
                <td><code>/spec/admin/user/deletingUsers/DeletingUsers.html</code></td>
                <td>Admin &gt;</td>
                <td></td>
            </tr>
            <tr>
                <td><code>/spec/admin/user/deletingUsers/SecurityIssues.html</code></td>
                <td>Admin &gt; Deleting Users &gt;</td>
                <td></td>
            </tr>
            <tr>
                <td><code>/spec/admin/user/adding_some_users/AddingSomeUsers.html</code></td>
                <td>Admin &gt;</td>
                <td><em>A snake_case package name is matched against the equivalent CamelCase resource.</em></td>
            </tr>
            <tr>
                <td><code>/spec/admin/user/adding_some_users/SecurityIssues.html</code></td>
                <td>Admin &gt; Adding Some Users &gt;</td>
                <td></td>
            </tr>
            <tr>
                <td><code>/spec/admin/user/changing_the_users/Changing_the_users.html</code></td>
                <td>Admin &gt;</td>
                <td><em>A snake_case package name is also matched against the Snake_case resource.</em></td>
            </tr>
            <tr>
                <td><code>/spec/admin/user/changing_the_users/Security_issues.html</code></td>
                <td>Admin &gt; Changing Users &gt;</td>
                <td></td>
            </tr>
        </table>
</div>