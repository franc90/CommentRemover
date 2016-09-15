package com.commentremover.processors.impl;

import com.commentremover.pattern.FileExtension;
import com.commentremover.processors.AbstractFileProcessor;

import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;

public class PropertyProcessor extends AbstractFileProcessor {

    public static final String SINGLE_LINE_COMMENT = "#";
    public static final String SINGLE_LINE_COMMENT_ESCAPE_TOKEN = "#" + UUID.randomUUID().toString();

    @Override
    protected Set<Pattern> getPatterns() {
        return FileExtension.PROPERTIES.getPatterns();
    }

}