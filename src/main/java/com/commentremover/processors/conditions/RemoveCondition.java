package com.commentremover.processors.conditions;

public interface RemoveCondition {

    boolean canBeRemoved(String token);

}
