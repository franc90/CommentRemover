package com.commentremover.processors.impl;

import com.commentremover.pattern.FileExtension;
import com.commentremover.processors.AbstractFileProcessor;

import java.util.Set;
import java.util.regex.Pattern;

public class CSSProcessor extends AbstractFileProcessor {

    @Override
    protected Set<Pattern> getPatterns() {
        return FileExtension.CSS.getPatterns();
    }

}