package com.commentremover.processors.impl;

import com.commentremover.handling.RegexSelector;
import com.commentremover.pattern.FileExtension;

import java.util.regex.Pattern;

public class JSPProcessor extends XMLProcessor {

    @Override
    protected Pattern getPattern() {
        return RegexSelector.getRegexByFileType(FileExtension.JSP);
    }

}