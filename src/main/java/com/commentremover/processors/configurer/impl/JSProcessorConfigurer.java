package com.commentremover.processors.configurer.impl;

import com.commentremover.pattern.FileExtension;
import com.commentremover.processors.FileProcessor;

public class JSProcessorConfigurer extends JavaProcessorConfigurer {

    private static final FileProcessor fileProcessor = FileExtension.JS.getFileProcessor();

    @Override
    protected FileProcessor getFileProcessor() {
        return fileProcessor;
    }
}
