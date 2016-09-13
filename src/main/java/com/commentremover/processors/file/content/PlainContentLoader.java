package com.commentremover.processors.file.content;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class PlainContentLoader implements ContentLoader {

    @Override
    public StringBuilder loadContent(File file) throws IOException {
        StringBuilder content = new StringBuilder((int) file.length());

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"))) {
            for (String temp = br.readLine(); temp != null; temp = br.readLine()) {
                content.append(temp).append("\n");
            }
        }

        return content;
    }

}
