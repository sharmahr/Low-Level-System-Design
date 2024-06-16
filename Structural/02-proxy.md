Design Pattern: Proxy Design Pattern

Diagram:
```
            +----------------+
            |    Subject     |
            +----------------+
            | + request()    |
            +----------------+
                ^
                |
            +------+-------+
            |              |
    +---------------+  +------------------+
    |   RealSubject |  |      Proxy       |
    +---------------+  +------------------+
    | + request()   |  | - realSubject    |
    +---------------+  | + request()      |
                       | + preRequest()   |
                       | + postRequest()  |
                       +------------------+
```

Explanation:
The Proxy Design Pattern is a structural pattern that provides a surrogate or placeholder for another object to control access to it. It allows you to create a proxy object that acts as an intermediary between the client and the real object. The proxy object has the same interface as the real object and can intercept or modify the requests before forwarding them to the real object. This pattern is useful for adding an extra level of indirection, such as access control, lazy initialization, logging, or remote communication.

Key Points:
- Provides a surrogate or placeholder for another object
- Allows controlling access to the real object
- Proxy object has the same interface as the real object
- Can intercept or modify requests before forwarding them to the real object
- Useful for adding an extra level of indirection, such as access control or lazy initialization

Real-Life Applications:
- Implementing a virtual proxy for loading large images or documents on demand
- Creating a protection proxy for restricting access to sensitive objects based on user permissions
- Using a remote proxy for communicating with remote objects across a network

Remember:
"The Proxy Pattern is like a 'stand-in' for the real object, controlling access and adding additional behaviors."

Sample Code:
```java
// Subject interface
interface Image {
    void display();
}

// RealSubject class
class RealImage implements Image {
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

// Proxy class
class ImageProxy implements Image {
    private RealImage realImage;
    private String filename;

    public ImageProxy(String filename) {
        this.filename = filename;
    }

    public void display() {
        if (realImage == null) {
            realImage = new RealImage(filename);
        }
        preDisplay();
        realImage.display();
        postDisplay();
    }

    private void preDisplay() {
        System.out.println("Preparing to display image");
    }

    private void postDisplay() {
        System.out.println("Image displayed successfully");
    }
}

// Client code
class ImageGallery {
    public static void main(String[] args) {
        Image image1 = new ImageProxy("image1.jpg");
        Image image2 = new ImageProxy("image2.jpg");

        image1.display(); // Loading and displaying image1
        System.out.println();
        image1.display(); // Displaying image1 without loading
        System.out.println();
        image2.display(); // Loading and displaying image2
    }
}
```

In this example, the Proxy Pattern is used to create a virtual proxy for loading and displaying images. The `Image` interface defines the common interface for both the real image and the proxy image. The `RealImage` class represents the actual image object that is loaded from disk and displayed. The `ImageProxy` class acts as a proxy for the real image, delaying the loading of the image until it is actually needed. The proxy also adds additional behaviors, such as preparing and post-processing steps. The client code interacts with the image objects through the `Image` interface, unaware of whether it is dealing with a real image or a proxy.