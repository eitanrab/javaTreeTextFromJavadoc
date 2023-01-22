import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class TreeGenerator {

    public static void main(String[] args) {
        // root folder of the project
        String root = "path/to/project";
        // get all java files in the project
        List<File> javaFiles = getAllJavaFiles(root);
        // create an empty tree
        TreeNode rootNode = new TreeNode("root", "");
        // create a JavaParser instance
        JavaParser javaParser = new JavaParser();
        // for each java file
        for (File file : javaFiles) {
            try {
                // parse the file
                CompilationUnit cu = javaParser.parse(file).getResult().orElse(null);
                // extract package name
                String packageName = cu.getPackageDeclaration().map(PackageDeclaration::getNameAsString).orElse("default");
                // get the package node
                TreeNode packageNode = rootNode.getChild(packageName);
                if (packageNode == null) {
                    packageNode = new TreeNode(packageName, "");
                    rootNode.addChild(packageNode);
                }
                // for each type (class, interface, enum)
                for (TypeDeclaration type : cu.getTypes()) {
                    // extract class name
                    String className = type.getNameAsString();
                    // extract javadoc
                    String javadoc = "";
                    if (type.getComment().isPresent()) {
                        javadoc = type.getComment().get().getContent();
                    }
                    // create a class node
                    TreeNode classNode = new TreeNode(className, javadoc);
                    packageNode.addChild(classNode);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        // print the tree
        System.out.println(rootNode.toString());
    }

    public static List<File> getAllJavaFiles(String root) {
        List<File> javaFiles = new ArrayList<>();
        try {
            Files.walk(Paths.get(root)).forEach(filePath -> {
                if (Files.isRegularFile(filePath) && filePath.toString().endsWith(".java")) {
                    javaFiles.add(filePath.toFile());
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return javaFiles;
    }
}

class TreeNode {
    String name;
    String javadoc;
    List<TreeNode> children;

    public TreeNode(String name, String javadoc) {
        this.name = name;
        this.javadoc = javadoc;
        this.children = new ArrayList<>();
    }

    public void addChild(TreeNode child) {
        children.add(child);
    }

    public TreeNode getChild(String name) {
        for (TreeNode child : children) {
            if (child.name.equals(name)) {
                return child;
            }
        }
        return null;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        printTree(this, 0, sb);
        return sb.toString();
    }

    private void printTree(TreeNode node, int level, StringBuilder sb) {
        StringBuilder s = new StringBuilder("    ");
        for (int i = 0; i < level; i++) {
            s.append("    ");
            sb.append("    ");  // add 4 spaces for each level of indentation
        }
        sb.append(node.name.trim());
        if (!node.javadoc.isEmpty()) {
            sb.append(node.javadoc.replaceAll("\r\n|\n\r", "\n" + s.toString()));
        }
        sb.append("\n");
        for (TreeNode child : node.children) {
            printTree(child, level + 1, sb);
        }
    }

}
