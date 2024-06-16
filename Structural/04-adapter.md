Design Pattern: Adapter Design Pattern

Diagram:
```
    +----------------+
    |    Target      |
    +----------------+
    | + request()    |
    +----------------+
            ^
            |
            |
    +--------------------+
    |      Adapter       |
    +--------------------+
    | - adaptee          |
    | + request()        |
    +--------------------+
            |
            |
            v
    +---------------------+
    |       Adaptee       |
    +---------------------+
    | + specificRequest() |
    +---------------------+
```

Explanation:
The Adapter Design Pattern is a structural pattern that allows objects with incompatible interfaces to collaborate. It acts as a bridge between two incompatible interfaces, converting the interface of one class into another interface that clients expect. This pattern involves a single class (Adapter) that is responsible for communication between two independent or incompatible interfaces. The Adapter translates requests from the client into requests that the Adaptee can understand and vice versa.

Key Points:
- Allows objects with incompatible interfaces to work together
- Acts as a bridge between two incompatible interfaces
- Converts the interface of one class into another interface that clients expect
- Involves a single class (Adapter) for communication between interfaces
- Promotes flexibility and reusability of existing code

Real-Life Applications:
- Using a third-party library that has a different interface than the one expected by the client code
- Integrating legacy code with a new system that has a different interface
- Connecting a Java application with a database using JDBC (Java Database Connectivity) adapter

Remember:
"The Adapter Pattern is like a travel adapter that allows you to plug your device into a foreign power outlet, making them compatible."

Sample Code:
```java
// Target interface
interface MediaPlayer {
    void play(String audioType, String fileName);
}

// Adaptee class
class AdvancedMediaPlayer {
    public void playVlc(String fileName) {
        System.out.println("Playing vlc file. Name: " + fileName);
    }

    public void playMp4(String fileName) {
        System.out.println("Playing mp4 file. Name: " + fileName);
    }
}

// Adapter class
class MediaAdapter implements MediaPlayer {
    private AdvancedMediaPlayer advancedMusicPlayer;

    public MediaAdapter(String audioType) {
        if (audioType.equalsIgnoreCase("vlc")) {
            advancedMusicPlayer = new AdvancedMediaPlayer();
        } else if (audioType.equalsIgnoreCase("mp4")) {
            advancedMusicPlayer = new AdvancedMediaPlayer();
        }
    }

    public void play(String audioType, String fileName) {
        if (audioType.equalsIgnoreCase("vlc")) {
            advancedMusicPlayer.playVlc(fileName);
        } else if (audioType.equalsIgnoreCase("mp4")) {
            advancedMusicPlayer.playMp4(fileName);
        }
    }
}

// Client code
class AudioPlayer implements MediaPlayer {
    private MediaAdapter mediaAdapter;

    public void play(String audioType, String fileName) {
        if (audioType.equalsIgnoreCase("mp3")) {
            System.out.println("Playing mp3 file. Name: " + fileName);
        } else if (audioType.equalsIgnoreCase("vlc") || audioType.equalsIgnoreCase("mp4")) {
            mediaAdapter = new MediaAdapter(audioType);
            mediaAdapter.play(audioType, fileName);
        } else {
            System.out.println("Invalid media. " + audioType + " format not supported");
        }
    }
}

// Usage
class Main {
    public static void main(String[] args) {
        AudioPlayer audioPlayer = new AudioPlayer();

        audioPlayer.play("mp3", "song.mp3");
        audioPlayer.play("vlc", "movie.vlc");
        audioPlayer.play("mp4", "video.mp4");
        audioPlayer.play("avi", "recording.avi");
    }
}
```

In this example, the Adapter Pattern is used to make the `AdvancedMediaPlayer` (Adaptee) compatible with the `MediaPlayer` (Target) interface. The `MediaAdapter` class acts as the Adapter, implementing the `MediaPlayer` interface and translating the requests from the client (`AudioPlayer`) to the appropriate methods of the `AdvancedMediaPlayer`. The client code can then work with the `MediaPlayer` interface seamlessly, without knowing about the underlying `AdvancedMediaPlayer` classes.