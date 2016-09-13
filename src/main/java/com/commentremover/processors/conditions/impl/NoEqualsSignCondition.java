package com.commentremover.processors.conditions.impl;

import com.commentremover.processors.conditions.RemoveCondition;

public class NoEqualsSignCondition implements RemoveCondition {

    public static final String EQUALS = "=";

    @Override
    public boolean canBeRemoved(String token) {
        return !token.startsWith(EQUALS);
    }
}
