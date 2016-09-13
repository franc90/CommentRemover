package com.commentremover.app;

import com.commentremover.handling.UserInputHandler;
import com.commentremover.pattern.FileExtension;
import com.commentremover.processors.configurer.FileProcessorConfigurer;
import com.commentremover.processors.configurer.impl.CssProcessorConfigurer;
import com.commentremover.processors.configurer.impl.HTMLProcessorConfigurer;
import com.commentremover.processors.configurer.impl.JSPProcessorConfigurer;
import com.commentremover.processors.configurer.impl.JSProcessorConfigurer;
import com.commentremover.processors.configurer.impl.JavaProcessorConfigurer;
import com.commentremover.processors.configurer.impl.PropertyProcessorConfigurer;
import com.commentremover.processors.configurer.impl.XMLProcessorConfigurer;

import java.util.HashSet;
import java.util.Set;

public class CommentRemoverInitializer {

    private final UserInputHandler userInputHandler = new UserInputHandler();

    private final static Set<FileProcessorConfigurer> configurers = new HashSet<>();

    static {
        configurers.add(new CssProcessorConfigurer());
        configurers.add(new JavaProcessorConfigurer());
        configurers.add(new JSProcessorConfigurer());
        configurers.add(new JSPProcessorConfigurer());
        configurers.add(new HTMLProcessorConfigurer());
        configurers.add(new PropertyProcessorConfigurer());
        configurers.add(new XMLProcessorConfigurer());
    }

    public CustomFileVisitor initialize(CommentRemoverConfiguration configuration) {
        userInputHandler.checkAllStates(configuration);

        for (FileProcessorConfigurer configurer : configurers) {
            configurer.configure(configuration);
        }

        return new CustomFileVisitor(new VisitorConfig(configuration));
    }

}
