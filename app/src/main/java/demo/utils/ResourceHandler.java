package demo.utils;

import java.io.InputStream;

public interface ResourceHandler {
    public InputStream getFileResourcesAsStream(String filename);
}
