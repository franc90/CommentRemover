package com.commentremover.processors.file.content;

import com.commentremover.processors.conditions.RemoveCondition;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Set;

public class ContentLoaderWithSingleLineSanitize implements ContentLoader {

    private final String singleLineComment;
    private final String singleLineCommentEscapeToken;
    private final Set<RemoveCondition> conditions;

    public ContentLoaderWithSingleLineSanitize(String singleLineComment, String singleLineCommentEscapeToken, Set<RemoveCondition> conditions) {
        this.singleLineComment = singleLineComment;
        this.singleLineCommentEscapeToken = singleLineCommentEscapeToken;
        this.conditions = conditions;
    }

    @Override
    public StringBuilder loadContent(File file) throws IOException {
        StringBuilder content = new StringBuilder((int) file.length());

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"))) {
            String temp;
            while ((temp = br.readLine()) != null) {
                String trimmedTemp = temp.trim();

                if (trimmedTemp.startsWith(singleLineComment) && shouldProcess(trimmedTemp)) {
                    content.append(singleLineCommentEscapeToken).append("\n");
                } else {
                    content.append(temp).append("\n");
                }
            }
        }

        return content;
    }

    private boolean shouldProcess(String trimmedTemp) {
        boolean canBeRemoved = true;
        for (RemoveCondition removeCondition : conditions) {
            canBeRemoved &= removeCondition.canBeRemoved(trimmedTemp);
        }
        return canBeRemoved;
    }
}
