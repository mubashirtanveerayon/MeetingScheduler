package resourceloader;

import java.net.URL;

public final class ResourceLoader {
    
    public static void main(String[] args) {
        System.out.println(load("res/red.png"));
    }

    public static URL load(String path) {
        URL input = ResourceLoader.class.getResource(path);
        if (input == null) {
            input = ResourceLoader.class.getResource("/" + path);
        }
        return input;
    }
}
