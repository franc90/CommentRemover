package com.commentremover.pattern;

import com.commentremover.processors.FileProcessor;
import com.commentremover.processors.impl.CSSProcessor;
import com.commentremover.processors.impl.JSPProcessor;
import com.commentremover.processors.impl.JavaProcessor;
import com.commentremover.processors.impl.PropertyProcessor;
import com.commentremover.processors.impl.XMLProcessor;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public enum FileExtension {

    JAVA("java", new JavaProcessor(), RegexPatterns.MULTI_AND_SINGLE_LINE),
    JS("js", new JavaProcessor(), RegexPatterns.MULTI_AND_SINGLE_LINE),
    JSP("jsp", new JSPProcessor(), RegexPatterns.JSP),
    HTML("html", new XMLProcessor(), RegexPatterns.HTML_XML),
    CSS("css", new CSSProcessor(), RegexPatterns.CSS),
    PROPERTIES("properties", new PropertyProcessor(), RegexPatterns.PROPERTIES),
    XML("xml", new XMLProcessor(), RegexPatterns.HTML_XML);

    private final String fileExtension;
    private final FileProcessor fileProcessor;
    private final Set<Pattern> patterns;

    private static final List<String> supportedExtensions = new LinkedList<>();

    static {
        for (FileExtension fileExtension : getAllExtensions()) {
            supportedExtensions.add(fileExtension.getFileExtension());
        }
    }

    FileExtension(String fileExtension, FileProcessor fileProcessor, Pattern pattern, Pattern... optionalPatterns) {
        this.fileExtension = fileExtension;
        this.fileProcessor = fileProcessor;
        this.patterns = new HashSet<>();
        this.patterns.add(pattern);
        for (Pattern optionalPattern : optionalPatterns) {
            this.patterns.add(optionalPattern);
        }
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public FileProcessor getFileProcessor() {
        return fileProcessor;
    }

    public Set<Pattern> getPatterns() {
        return patterns;
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
