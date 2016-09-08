package com.commentremover.app;

import com.commentremover.exception.CommentRemoverException;
import com.commentremover.handling.CommentType;
import com.commentremover.pattern.FileExtension;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CommentProcessorTest {

    private static final String TEST_JAVA = "Test.java";
    private static final String TEST_JS = "test.js";
    private static final String TEST_HTML = "test.html";
    private static final String TEST_XML = "test.xml";
    private static final String TEST_JSP = "test.jsp";
    private static final String TEST_PROPERTIES = "test.properties";
    private static final String TEST_CSS = "test.css";

    private String compareFilePath;
    private String toProcessPath;
    private String workDirectoryPath;

    @Before
    public void setup() throws IOException {
        compareFilePath = getAbsolutePath("src", "test", "resources", "files", "compare");
        toProcessPath = getAbsolutePath("src", "test", "resources", "files", "to", "process");

        Path workDirectory = Paths.get("target", "tmp");
        FileUtils.deleteQuietly(workDirectory.toFile());
        FileUtils.forceMkdir(workDirectory.toFile());
        workDirectoryPath = workDirectory.toAbsolutePath().toString();
    }

    private String getAbsolutePath(String first, String... path) {
        return Paths.get(first, path).toAbsolutePath().toString();
    }

    @Test
    public void testRemovingJavaComments() throws CommentRemoverException {
        testRemoving(TEST_JAVA, createProcessorConfig().addExtensionToClear(FileExtension.JAVA).build());
    }

    @Test
    public void testRemovingJavaScriptComments() throws CommentRemoverException {
        testRemoving(TEST_JS, createProcessorConfig().addExtensionToClear(FileExtension.JS).build());
    }

    @Test
    public void testRemovingHtmlComments() throws CommentRemoverException {
        testRemoving(TEST_HTML, createProcessorConfig().addExtensionToClear(FileExtension.HTML).build());
    }

    @Test
    public void testRemovingXMLComments() throws CommentRemoverException {
        testRemoving(TEST_XML, createProcessorConfig().addExtensionToClear(FileExtension.XML).build());
    }

    @Test
    public void testRemovingJSPComments() throws CommentRemoverException {
        testRemoving(TEST_JSP, createProcessorConfig().addExtensionToClear(FileExtension.JSP).build());
    }

    @Test
    public void testRemovingPropertiesComments() throws CommentRemoverException {
        testRemoving(TEST_PROPERTIES, createProcessorConfig().addExtensionToClear(FileExtension.PROPERTIES).build());
    }

    @Test
    public void testRemovingCSSComments() throws CommentRemoverException {
        testRemoving(TEST_CSS, createProcessorConfig().addExtensionToClear(FileExtension.CSS).build());
    }

    private void testRemoving(String fileName, CommentRemoverConfiguration configuration) throws CommentRemoverException {
        Path processedFile = copyToWorkDir(fileName);
        Path compareFile = Paths.get(compareFilePath, fileName);

        new CommentProcessor(configuration).start();
        compareFiles(processedFile, compareFile);
    }

    private Path copyToWorkDir(String fileName) {
        Path fileToCopy = Paths.get(toProcessPath, fileName);
        Path targetFile = Paths.get(workDirectoryPath, fileName);

        try {
            FileUtils.copyFile(fileToCopy.toFile(), targetFile.toFile());
        } catch (IOException e) {
            throw new RuntimeException("Could not copy file " + fileName);
        }

        return targetFile;
    }

    private CommentRemoverConfiguration.Builder createProcessorConfig() {
        return CommentRemoverConfiguration.builder()
                .addCommentTypes(CommentType.SINGLE_LINE, CommentType.MULTI_LINE)
                .withStartPath(workDirectoryPath, false);
    }

    private void compareFiles(Path processedPath, Path comparePath) {
        try {

            List<String> processed = trim(Files.readAllLines(processedPath, Charset.forName("UTF-8")));
            List<String> compare = trim(Files.readAllLines(comparePath, Charset.forName("UTF-8")));

            for (String processedLine : processed) {
                if (StringUtils.isNotBlank(processedLine)) {
                    assertTrue(compare.contains(processedLine));
                }
            }
            assertEquals(compare, processed);
        } catch (IOException e) {
            throw new RuntimeException("Could not open file " + e.getMessage());
        }
    }

    private List<String> trim(List<String> lines) {
        List<String> trimmed = new ArrayList<>(lines.size());

        for (String line : lines) {
            String trimmedLine = StringUtils.trimToEmpty(line);
            if (StringUtils.isNotBlank(trimmedLine)) {
                trimmed.add(trimmedLine);
            }
        }

        return trimmed;
    }

}