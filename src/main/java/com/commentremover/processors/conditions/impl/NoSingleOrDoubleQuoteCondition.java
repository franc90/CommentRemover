package com.commentremover.processors.conditions.impl;

import com.commentremover.processors.conditions.RemoveCondition;

public class NoSingleOrDoubleQuoteCondition implements RemoveCondition {

    public static final String DOUBLE_QUOTE = "\"";
    public static final String SINGLE_QUOTE = "\'";

    @Override
    public boolean canBeRemoved(String token) {
        return !(token.startsWith(DOUBLE_QUOTE) || token.startsWith(SINGLE_QUOTE));
    }

}
