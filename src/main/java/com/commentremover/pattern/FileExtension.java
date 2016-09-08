package com.commentremover.pattern;

import com.commentremover.processors.FileProcessor;
import com.commentremover.processors.impl.CSSFileProcessor;
import com.commentremover.processors.impl.JSPFileProcessor;
import com.commentremover.processors.impl.JavaFileProcessor;
import com.commentremover.processors.impl.PropertyFileProcessor;
import com.commentremover.processors.impl.XMLFileProcessor;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public enum FileExtension {

    JAVA("java", new JavaFileProcessor()),
    JS("js", new JavaFileProcessor()),
    JSP("jsp", new JSPFileProcessor()),
    HTML("html", new XMLFileProcessor()),
    CSS("css", new CSSFileProcessor()),
    PROPERTIES("properties", new PropertyFileProcessor()),
    XML("xml", new XMLFileProcessor());

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
