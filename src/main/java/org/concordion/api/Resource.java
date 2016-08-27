package org.concordion.api;

import org.concordion.internal.util.Check;

public class Resource {

    private final String path;
    private final String[] parts;
    private final String name;
    private final boolean isPackage;

    public Resource(String path) {
        path = path.replaceAll("\\\\", "/");
        Check.isTrue(path.startsWith("/"), "Internal error: Resource path should start with a slash");
        this.path = path;
        isPackage = endsWithSlash(path);
        parts = path.split("/");
        if (parts.length == 0) {
            name = "";
        } else {
            name = parts[parts.length - 1];
        }
    }

    public String getPath() {
        return path;
    }

    private boolean isPackage() {
        return isPackage;
    }

    private boolean endsWithSlash(String s) {
        return s.endsWith("/");
    }

    private Resource getPackage() {
        if (isPackage()) {
            return this;
        }
        return getParent();
    }

    public Resource getParent() {
        if (getPath().equals("/")) {
            return null;
        }
        StringBuilder parentPath = new StringBuilder();
        parentPath.append("/");
        for (int i = 1; i < parts.length - 1; i++) {
            parentPath .append(parts[i]).append("/");
        }
        return new Resource(parentPath.toString());
    }

    public String getRelativePath(Resource resource) {

        if (resource.getPath().equals(path)) {
            return name;
        }

        // Find common stem and ignore it
        // Use ../ to move up the path from here to common stem
        // Append the rest of the path from resource
        String[] therePieces = resource.getPathPieces();
        String[] herePieces = getPathPieces();

        int sharedPiecesCount = 0;
        for (int i = 0; i < herePieces.length; i++) {
            if (therePieces.length <= i) {
                break;
            }
            if (therePieces[i].equals(herePieces[i])) {
                sharedPiecesCount++;
            } else {
                break;
            }
        }

        StringBuilder r = new StringBuilder();
        r.append("");

        for (int i = sharedPiecesCount; i < herePieces.length; i++) {
            r.append("../");
        }

        for (int i = sharedPiecesCount; i < therePieces.length; i++) {
            r.append(therePieces[i]).append("/");
        }

        if (resource.isPackage()) {
            return r.toString();
        }
        return r.append(resource.getName()).toString();
    }

    private String[] getPathPieces() {
        String packagePath = getPackage().getPath();
        if ("/".equals(packagePath)) {
            return new String[]{""};
        }
        return packagePath.split("/");
    }

    public String getName() {
        return name;
    }

    public Resource getRelativeResource(String relativePath) {
        Check.isFalse(relativePath.startsWith("/"), "Relative path should not start with a slash");

        String subPath = removeAnyLeadingDotSlashFrom(relativePath);

        Resource p = getPackage();
        while (subPath.startsWith("../")) {
            p = p.getParent();
            if (p == null) {
                throw new RuntimeException("Path '" + relativePath + "' relative to '" + getPath() + "' "
                        + "evaluates above the root package.");
            }
            subPath = subPath.replaceFirst("../", "");
        }

        Check.isFalse(subPath.contains("../"),
                "The ../ operator is currently only supported at the start of expressions");

        return new Resource(p.getPath() + subPath);
    }

    private String removeAnyLeadingDotSlashFrom(String subPath) {
        return subPath.replaceFirst("^\\./", "");
    }

    @Override
    public String toString() {
        return "[Resource: " + path + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((path == null) ? 0 : path.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Resource other = (Resource) obj;
        if (path == null) {
            if (other.path != null) {
                return false;
            }
        } else if (!path.equals(other.path)) {
            return false;
        }
        return true;
    }

    /**
     * Returns true if the "leaf" component of the package name (ie the last component) is snake case (has an "_")
     */
    public boolean hasSnakeCaseLeafPackage() {
        if (parts.length == 0) {
            return false;
        }
        if (isPackage()) {
            return parts[parts.length - 1].contains("_");
        } else {
            return parts[parts.length - 2].contains("_");
        }
    }

    /**
     * Returns a resource with the class name generated by camel-casing the snake-case leaf package name
     */
    public Resource withCamelCaseClassFromSnakeClassLeafPackage() {
        if (isPackage() || !hasSnakeCaseLeafPackage()) {
            return this;
        }
        StringBuilder newPath = new StringBuilder(path.length());
        int componentCount = parts.length;
        String classSuffix = "";
        if (!isPackage) {
            componentCount -= 1;
            String className = parts[parts.length - 1];
            int i = className.indexOf(".");
            if (i >= 0) {
                classSuffix = className.substring(i);
            }

        }
        for (int i = 0; i < componentCount; i++) {
            newPath.append(parts[i]).append("/");
        }
        String leafPackageName = parts[componentCount - 1];
        newPath.append(snakeToCamelCase(leafPackageName, classSuffix));
        return new Resource(newPath.toString());
    }

    private String snakeToCamelCase(String input, String suffix) {
        String[] words = input.split("_");
        StringBuilder name = new StringBuilder(input.length());
        for (String word : words) {
            name.append(Character.toUpperCase(word.charAt(0)) + word.substring(1));
        }
        return name.toString() + suffix;
    }
}
