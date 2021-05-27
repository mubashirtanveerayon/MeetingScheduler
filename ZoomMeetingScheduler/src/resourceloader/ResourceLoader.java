package resourceloader;

import java.net.URL;

public final class ResourceLoader {

    public URL load(String path) {
        URL input = ResourceLoader.class.getResource(path);
        if (input == null) {
            input = ResourceLoader.class.getResource("/" + path);
        }
        return input;
    }
}
