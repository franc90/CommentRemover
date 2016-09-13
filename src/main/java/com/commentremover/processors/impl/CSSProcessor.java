package com.commentremover.processors.impl;

import com.commentremover.handling.RegexSelector;
import com.commentremover.pattern.FileExtension;
import com.commentremover.processors.AbstractFileProcessor;

import java.util.regex.Pattern;

public class CSSProcessor extends AbstractFileProcessor {

    @Override
    protected Pattern getPattern() {
        return RegexSelector.getRegexByFileType(FileExtension.CSS);
    }

}