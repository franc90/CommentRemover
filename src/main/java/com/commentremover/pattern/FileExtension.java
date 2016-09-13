package com.commentremover.pattern;

import com.commentremover.processors.FileProcessor;
import com.commentremover.processors.impl.CSSProcessor;
import com.commentremover.processors.impl.JSPProcessor;
import com.commentremover.processors.impl.JavaProcessor;
import com.commentremover.processors.impl.PropertyProcessor;
import com.commentremover.processors.impl.XMLProcessor;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public enum FileExtension {

    JAVA("java", new JavaProcessor()),
    JS("js", new JavaProcessor()),
    JSP("jsp", new JSPProcessor()),
    HTML("html", new XMLProcessor()),
    CSS("css", new CSSProcessor()),
    PROPERTIES("properties", new PropertyProcessor()),
    XML("xml", new XMLProcessor());

    private final String fileExtension;
    private final FileProcessor fileProcessor;

    private static final List<String> supportedExtensions = new LinkedList<>();

    static {
        for (FileExtension fileExtension : getAllExtensions()) {
            supportedExtensions.add(fileExtension.getFileExtension());
        }
    }

    FileExtension(String fileExtension, FileProcessor fileProcessor) {
        this.fileExtension = fileExtension;
        this.fileProcessor = fileProcessor;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public FileProcessor getFileProcessor() {
        return fileProcessor;
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
