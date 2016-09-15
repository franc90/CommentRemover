package com.commentremover.processors.impl;

import com.commentremover.pattern.FileExtension;

import java.util.Set;
import java.util.regex.Pattern;

public class JSPProcessor extends XMLProcessor {

    @Override
    protected Set<Pattern> getPatterns() {
        return FileExtension.JSP.getPatterns();
    }

}