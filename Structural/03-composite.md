Design Pattern: Composite Design Pattern

Diagram:
```
        +-----------------------+
        |        Component      |
        +-----------------------+
        | + operation()         |
        | + add(Component)      |
        | + remove(Component)   |
        | + getChild(int)       |
        +-----------------------+
                    ^
                    |
           +--------+--------+
           |                 |
+--------------------+  +----------------------+
|        Leaf        |  |     Composite        |
+--------------------+  +----------------------+
| + operation()      |  | - children           |
+--------------------+  | + operation()        |
                        | + add(Component)     |
                        | + remove(Component)  |
                        | + getChild(int)      |
                        +----------------------+
```

Explanation:
The Composite Design Pattern is a structural pattern that allows you to compose objects into a tree-like structure to represent part-whole hierarchies. It lets clients treat individual objects and compositions of objects uniformly. The pattern consists of three main components: Component, Leaf, and Composite. The Component defines the common interface for both the Leaf and Composite objects, while the Leaf represents the individual objects and the Composite represents the collection of objects. This pattern is useful when you want to represent hierarchical structures and treat individual objects and compositions consistently.

Key Points:
- Allows composing objects into a tree-like structure
- Represents part-whole hierarchies
- Treats individual objects and compositions uniformly
- Consists of Component, Leaf, and Composite components
- Useful for representing hierarchical structures

Real-Life Applications:
- Representing a file system hierarchy with directories and files
- Building a user interface with nested components like panels, buttons, and containers
- Modeling an organization structure with departments and employees

Remember:
"The Composite Pattern is like a Russian nesting doll, where each doll can contain other dolls or be a single doll itself."

Sample Code:
```java
// Component interface
interface FileSystemComponent {
    void display();
}

// Leaf class
class File implements FileSystemComponent {
    private String name;

    public File(String name) {
        this.name = name;
    }

    public void display() {
        System.out.println("File: " + name);
    }
}

// Composite class
class Directory implements FileSystemComponent {
    private String name;
    private List<FileSystemComponent> components = new ArrayList<>();

    public Directory(String name) {
        this.name = name;
    }

    public void add(FileSystemComponent component) {
        components.add(component);
    }

    public void remove(FileSystemComponent component) {
        components.remove(component);
    }

    public FileSystemComponent getChild(int index) {
        return components.get(index);
    }

    public void display() {
        System.out.println("Directory: " + name);
        for (FileSystemComponent component : components) {
            component.display();
        }
    }
}

// Client code
class FileSystem {
    public static void main(String[] args) {
        Directory root = new Directory("root");
        Directory documents = new Directory("documents");
        Directory music = new Directory("music");

        File file1 = new File("file1.txt");
        File file2 = new File("file2.txt");
        File song1 = new File("song1.mp3");

        root.add(documents);
        root.add(music);
        documents.add(file1);
        documents.add(file2);
        music.add(song1);

        root.display();
    }
}
```

In this example, the Composite Pattern is used to represent a file system hierarchy. The `FileSystemComponent` interface defines the common operations for both files and directories. The `File` class represents individual files, while the `Directory` class represents a collection of files and directories. The client code demonstrates how to create a file system hierarchy by adding files and directories to the root directory and displaying the entire structure. The Composite Pattern allows treating files and directories uniformly, making it easy to work with hierarchical structures.