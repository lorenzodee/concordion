package org.concordion.api;

import org.junit.Test;

import static org.junit.Assert.*;

public class ResourceTest {

    @Test
    public void canTellYouItsParent() throws Exception {
        assertEquals("/", parentPathOf("/abc"));
        assertEquals("/", parentPathOf("/abc/"));
        assertEquals("/abc/", parentPathOf("/abc/def"));
        assertEquals("/abc/", parentPathOf("/abc/def/"));
        assertEquals("/abc/def/", parentPathOf("/abc/def/ghi"));
    }

    @Test
    public void returnsNullForParentOfRoot() {
        assertNull(new Resource("/").getParent());
    }

    @Test
    public void canCalculateRelativePaths() throws Exception {
        assertEquals("x.html", relativePath("/spec/x.html", "/spec/x.html"));
        assertEquals("blah", relativePath("/spec/", "/spec/blah"));
        assertEquals("../x/", relativePath("/a/b/c/", "/a/b/x/"));
        assertEquals("../../../a/b/x/", relativePath("/x/b/c/", "/a/b/x/"));
        assertEquals("../../x/x/file.txt", relativePath("/a/b/c/file.txt", "/a/x/x/file.txt"));
        assertEquals("../file.txt", relativePath("/a/file.txt", "/file.txt"));
        assertEquals("../../../file.txt", relativePath("/a/b/c/file.txt", "/file.txt"));
        assertEquals("../../../image/concordion-logo.png", relativePath("/spec/concordion/breadcrumbs/Breadcrumbs.md", "/image/concordion-logo.png"));
    }

    @Test
    public void givenRelativePathFromOneResourceReturnsOtherResource() {
        assertEquals("/david.html", getResourceRelativeTo("/blah.html", "david.html"));
        assertEquals("/david.html", getResourceRelativeTo("/", "david.html"));
        assertEquals("/blah/david.html", getResourceRelativeTo("/blah/x", "david.html"));
        assertEquals("/blah/x/david.html", getResourceRelativeTo("/blah/x/y", "david.html"));
        assertEquals("/blah/x/z/david.html", getResourceRelativeTo("/blah/x/y", "z/david.html"));
        assertEquals("/blah/style.css", getResourceRelativeTo("/blah/docs/example.html", "../style.css"));
        assertEquals("/style.css", getResourceRelativeTo("/blah/docs/example.html", "../../style.css"));
        assertEquals("/blah/style.css", getResourceRelativeTo("/blah/docs/work/example.html", "../../style.css"));
        assertEquals("/blah/docs/style.css", getResourceRelativeTo("/blah/docs/work/example.html", "../style.css"));
        assertEquals("/style.css", getResourceRelativeTo("/blah/example.html", "../style.css"));
        assertEquals("/style.css", getResourceRelativeTo("/blah/", "../style.css"));
        assertEquals("/style.css", getResourceRelativeTo("/blah", "style.css"));
        assertEquals("/blah/docs/css/style.css", getResourceRelativeTo("/blah/docs/work/", "../css/style.css"));
    }

    @Test
    public void throwsExceptionIfRelativePathPointsAboveRoot() {
        try {
            getResourceRelativeTo("/blah/docs/example.html", "../../../style.css");
            fail();
        } catch (Exception e) {
            assertEquals("Path '../../../style.css' relative to '/blah/docs/example.html' " +
                    "evaluates above the root package.", e.getMessage());
        }
    }

    @Test
    public void leafPackageIsSnakeCase() {
        assertTrue(resource("/a/b_c/").hasSnakeCaseLeafPackage());
        assertTrue(resource("/a/b_c/BC").hasSnakeCaseLeafPackage());
        assertFalse(resource("/").hasSnakeCaseLeafPackage());
        assertFalse(resource("/a/").hasSnakeCaseLeafPackage());
        assertFalse(resource("/").hasSnakeCaseLeafPackage());
        assertFalse(resource("/a/bc/").hasSnakeCaseLeafPackage());
        assertFalse(resource("/a/bc/BC").hasSnakeCaseLeafPackage());
    }

    @Test
    public void snakeLeafPackageToCamelClass() {
        assertEquals(resource("/a/b_c/"), resource("/a/b_c/").withCamelCaseClassFromSnakeClassLeafPackage());
        assertEquals(resource("/a/b_c/BC"), resource("/a/b_c/BC").withCamelCaseClassFromSnakeClassLeafPackage());
        assertEquals(resource("/a/b_c/BC.html"), resource("/a/b_c/BC.html").withCamelCaseClassFromSnakeClassLeafPackage());
        assertEquals(resource("/a/b_c/BC"), resource("/a/b_c/Whatever").withCamelCaseClassFromSnakeClassLeafPackage());
        assertEquals(resource("/a/b_c/BC.suffix"), resource("/a/b_c/Whatever.suffix").withCamelCaseClassFromSnakeClassLeafPackage());
        assertEquals(resource("/a_b/AB"), resource("/a_b/Whatever").withCamelCaseClassFromSnakeClassLeafPackage());
        assertEquals(resource("/a/b_cd_ef/BCdEf.md"), resource("/a/b_cd_ef/x.md").withCamelCaseClassFromSnakeClassLeafPackage());
        assertEquals(resource("/"), resource("/").withCamelCaseClassFromSnakeClassLeafPackage());
        assertEquals(resource("/ab/"), resource("/ab/").withCamelCaseClassFromSnakeClassLeafPackage());
        assertEquals(resource("/Ab"), resource("/Ab").withCamelCaseClassFromSnakeClassLeafPackage());
        assertEquals(resource("/ab/CdE"), resource("/ab/CdE").withCamelCaseClassFromSnakeClassLeafPackage());
    }

    private String getResourceRelativeTo(String resourcePath, String relativePath) {
        return new Resource(resourcePath).getRelativeResource(relativePath).getPath();
    }

    private String relativePath(String from, String to) {
        return resource(from).getRelativePath(resource(to));
    }

    private String parentPathOf(String path) {
        return resource(path).getParent().getPath();
    }
    
    private Resource resource(String path) {
        return new Resource(path);
    }

}
