package com.commentremover.processors.conditions.impl;

import com.commentremover.processors.conditions.RemoveCondition;

public class NoSingleLineCommentCondition implements RemoveCondition {

    public static final String SINGLE_LINE_COMMENT = "//";

    @Override
    public boolean canBeRemoved(String token) {
        return !token.startsWith(SINGLE_LINE_COMMENT);
    }

}
