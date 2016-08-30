package com.bartovapps.bucketdrop.beans;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by BartovMoti on 08/30/16.
 */
public class Drop extends RealmObject{
    private String goal;
    @PrimaryKey
    private long added;
    private long when;
    boolean completed;

    public Drop() {
    }

    public Drop(String goal, long added, long when, boolean completed) {
        this.goal = goal;
        this.added = added;
        this.completed = completed;
        this.when = when;
    }


    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public long getAdded() {
        return added;
    }

    public void setAdded(long added) {
        this.added = added;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public long getWhen() {
        return when;
    }

    public void setWhen(long when) {
        this.when = when;
    }

}
