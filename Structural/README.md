
# Structural Design Pattern
| Pattern | Note | Code |
|:-------------|:--------------:|-------------:|
| Decorator Design Pattern | The Decorator Pattern is like dressing up an object with additional accessories or behaviors, without changing its core identity. | [Decorator Design Pattern](01-decorator.md) |
| Proxy Design Pattern | The Proxy Pattern is like a 'stand-in' for the real object, controlling access and adding additional behaviors. | [Proxy Design Pattern](02-proxy.md) |
| Composite Design Pattern | The Composite Pattern is like a Russian nesting doll, where each doll can contain other dolls or be a single doll itself. | [Composite Design Pattern](03-composite.md) |
| Adapter Design Pattern | The Adapter Pattern is like a travel adapter that allows you to plug your device into a foreign power outlet, making them compatible. | [Adapter Design Pattern](04-adapter.md) |
| Bridge Design Pattern | The Bridge Pattern is like a bridge that connects two lands (abstraction and implementation) while allowing them to exist and change independently. | [Bridge Design Pattern](05-bridge.md) |
| Facade Design Pattern | The Facade Pattern is like a concierge at a hotel who handles all the complex tasks for you, providing a simple and unified interface. | [Facade Design Pattern](06-facade.md) |
| FlyWeight Design Pattern | The Flyweight Pattern is like a shared library where books with the same content are not duplicated, but instead, multiple library cards point to the same book. | [FlyWeight Design Pattern](07-flyweight.md) |

Certainly! As an expert software engineer, I'd be happy to help you build an intuition for when to use these structural design patterns. Each pattern addresses specific structural challenges in software design. Let's break them down:

1. Proxy Pattern

Use when:
- You need to control access to an object.
- You want to add functionality when accessing an object without changing its code.
- You need to lazy-load objects or implement caching.

Example scenario:
You're building a system that loads large images, but you want to defer loading until the image is actually needed.

```java
public interface Image {
    void display();
}

public class RealImage implements Image {
    private String filename;

    public RealImage(String filename) {
        this.filename = filename;
        loadFromDisk();
    }

    private void loadFromDisk() {
        System.out.println("Loading " + filename);
    }

    public void display() {
        System.out.println("Displaying " + filename);
    }
}

public class ProxyImage implements Image {
    private RealImage realImage;
    private String filename;

    public ProxyImage(String filename) {
        this.filename = filename;
    }

    public void display() {
        if (realImage == null) {
            realImage = new RealImage(filename);
        }
        realImage.display();
    }
}
```

2. Composite Pattern

Use when:
- You need to represent part-whole hierarchies of objects.
- You want clients to be able to treat individual objects and compositions of objects uniformly.

Example scenario:
You're designing a file system where both files and directories are treated as file system items.

```java
public abstract class FileSystemItem {
    protected String name;

    public FileSystemItem(String name) {
        this.name = name;
    }

    public abstract void print();
}

public class File extends FileSystemItem {
    public File(String name) {
        super(name);
    }

    public void print() {
        System.out.println("File: " + name);
    }
}

public class Directory extends FileSystemItem {
    private List<FileSystemItem> contents = new ArrayList<>();

    public Directory(String name) {
        super(name);
    }

    public void add(FileSystemItem item) {
        contents.add(item);
    }

    public void print() {
        System.out.println("Directory: " + name);
        for (FileSystemItem item : contents) {
            item.print();
        }
    }
}
```

3. Adapter Pattern

Use when:
- You need to make incompatible interfaces work together.
- You want to reuse existing classes that don't have an interface that a client requires.

Example scenario:
You have a new audio player that works with advanced audio formats, but you want it to also play old formats.

```java
public interface AdvancedMediaPlayer {
    void playFile(String filename);
}

public class Mp4Player implements AdvancedMediaPlayer {
    public void playFile(String filename) {
        System.out.println("Playing MP4 file: " + filename);
    }
}

public interface MediaPlayer {
    void play(String audioType, String filename);
}

public class MediaAdapter implements MediaPlayer {
    AdvancedMediaPlayer advancedMusicPlayer;

    public MediaAdapter(String audioType) {
        if (audioType.equalsIgnoreCase("mp4")) {
            advancedMusicPlayer = new Mp4Player();
        }
    }

    public void play(String audioType, String filename) {
        if (audioType.equalsIgnoreCase("mp4")) {
            advancedMusicPlayer.playFile(filename);
        }
    }
}
```

4. Decorator Pattern

Use when:
- You need to add responsibilities to objects dynamically without affecting other objects.
- You want to add functionalities to objects without subclassing.

Example scenario:
You're building a coffee ordering system where you can add various condiments to a base coffee.

```java
public interface Coffee {
    double getCost();
    String getDescription();
}

public class SimpleCoffee implements Coffee {
    public double getCost() {
        return 1;
    }

    public String getDescription() {
        return "Simple coffee";
    }
}

public abstract class CoffeeDecorator implements Coffee {
    protected Coffee decoratedCoffee;

    public CoffeeDecorator(Coffee c) {
        this.decoratedCoffee = c;
    }

    public double getCost() {
        return decoratedCoffee.getCost();
    }

    public String getDescription() {
        return decoratedCoffee.getDescription();
    }
}

public class Milk extends CoffeeDecorator {
    public Milk(Coffee c) {
        super(c);
    }

    public double getCost() {
        return super.getCost() + 0.5;
    }

    public String getDescription() {
        return super.getDescription() + ", milk";
    }
}
```

5. Bridge Pattern

Use when:
- You want to separate an abstraction from its implementation so that both can vary independently.
- You have a set of closely related classes that only differ in some details.

Example scenario:
You're developing a shape drawing application that needs to work with multiple rendering engines.

```java
public interface DrawingAPI {
    void drawCircle(double x, double y, double radius);
}

public class SVGDrawingAPI implements DrawingAPI {
    public void drawCircle(double x, double y, double radius) {
        System.out.printf("SVG circle at (%f,%f) radius %f\n", x, y, radius);
    }
}

public abstract class Shape {
    protected DrawingAPI drawingAPI;

    protected Shape(DrawingAPI drawingAPI) {
        this.drawingAPI = drawingAPI;
    }

    public abstract void draw();
}

public class Circle extends Shape {
    private double x, y, radius;

    public Circle(double x, double y, double radius, DrawingAPI drawingAPI) {
        super(drawingAPI);
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    public void draw() {
        drawingAPI.drawCircle(x, y, radius);
    }
}
```

6. Facade Pattern

Use when:
- You want to provide a simple interface to a complex subsystem.
- You need to decouple a subsystem from its clients and other subsystems.

Example scenario:
You're building a home theater system with multiple components (TV, sound system, lights, etc.).

```java
public class HomeTheaterFacade {
    private TV tv;
    private SoundSystem sound;
    private Lights lights;

    public HomeTheaterFacade(TV tv, SoundSystem sound, Lights lights) {
        this.tv = tv;
        this.sound = sound;
        this.lights = lights;
    }

    public void watchMovie(String movie) {
        lights.dim();
        tv.turnOn();
        sound.turnOn();
        sound.setVolume(10);
        tv.playMovie(movie);
    }

    public void endMovie() {
        tv.turnOff();
        sound.turnOff();
        lights.brighten();
    }
}
```

7. Flyweight Pattern

Use when:
- You need to support a large number of fine-grained objects efficiently.
- The majority of each object's state can be made extrinsic (shared).

Example scenario:
You're developing a text editor that needs to render a large amount of characters efficiently.

```java
public class Character {
    private char symbol;
    private String color;
    private String font;

    public Character(char symbol, String color, String font) {
        this.symbol = symbol;
        this.color = color;
        this.font = font;
    }

    public void print() {
        System.out.printf("Character %c with color %s and font %s\n", symbol, color, font);
    }
}

public class CharacterFactory {
    private Map<Character, Character> characters = new HashMap<>();

    public Character getCharacter(char symbol, String color, String font) {
        Character character = characters.get(symbol);
        if (character == null) {
            character = new Character(symbol, color, font);
            characters.put(symbol, character);
        }
        return character;
    }
}
```

Building Intuition:
- Use Proxy for controlled access to objects.
- Use Composite for tree-like object structures.
- Use Adapter to make incompatible interfaces work together.
- Use Decorator to add responsibilities to objects dynamically.
- Use Bridge to separate abstraction from implementation.
- Use Facade to simplify complex subsystem interfaces.
- Use Flyweight to efficiently handle large numbers of similar objects.