package com.commentremover.processors.configurer.impl;

import com.commentremover.pattern.FileExtension;
import com.commentremover.processors.FileProcessor;

public class JSPProcessorConfigurer extends XMLProcessorConfigurer {

    private static final FileProcessor fileProcessor = FileExtension.JSP.getFileProcessor();

    @Override
    protected FileProcessor getFileProcessor() {
        return fileProcessor;
    }

}
