package com.commentremover.pattern;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public enum FileExtension {

    JAVA("java"), JS("js"), JSP("jsp"), HTML("html"), CSS("css"), PROPERTIES("properties"), XML("xml");

    private final String fileExtension;

    private static final List<String> supportedExtensions = new LinkedList<>();

    static {
        for (FileExtension fileExtension : getAllExtensions()) {
            supportedExtensions.add(fileExtension.getFileExtension());
        }
    }

    FileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public static boolean isExtensionSupported(String fileExtension) {
        return supportedExtensions.contains(fileExtension);
    }

    public static List<FileExtension> getAllExtensions() {
        return Arrays.asList(values());
    }

    public static FileExtension getForExtensionName(String fileExtensionName) {
        for (FileExtension fileExtension : values()) {
            if (fileExtension.getFileExtension().equals(fileExtensionName)) {
                return fileExtension;
            }
        }
        return null;
    }
}
