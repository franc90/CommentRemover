package com.commentremover.processors.conditions.impl;

import com.commentremover.processors.conditions.RemoveCondition;

public class NoMultiLineCommentCondition implements RemoveCondition {

    public static final String MULTI_LINE_COMMENT = "/*";

    @Override
    public boolean canBeRemoved(String token) {
        return !token.startsWith(MULTI_LINE_COMMENT);
    }
}
