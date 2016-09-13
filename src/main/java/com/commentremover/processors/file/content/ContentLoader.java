package com.commentremover.processors.file.content;

import java.io.File;
import java.io.IOException;

public interface ContentLoader {

    StringBuilder loadContent(File file) throws IOException;

}
