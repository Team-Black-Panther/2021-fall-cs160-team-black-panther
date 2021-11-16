package net.tbp.interval.ui.tracker;

import java.util.Date;

// class to manage the variable that relate to the tracker
public class Tracker {
    private int id;                 // id of task
    private String name;           // name of the task
    private Boolean status;         // true if task is completed, false if task have not complete
    private Date duedate;
    private Date completeddate;

    // construction of the reminder class
    public Tracker(int id, String name, Boolean status, Date duedate, Date completeddate) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.duedate = duedate;
        this.completeddate = completeddate;
    }

    // set id
    public void setReminderId(int id) {
        this.id = id;
    }

    // get id
    public int getReminderId() {
        return id;
    }

    // set name of reminder
    public void setName(String name){
        this.name = name;
    }

    // get name of reminder
    public String getName(){
        return name;
    }

    // set status of reminder
    public void setStatus(Boolean status){
        this.status = status;
    }

    // get status of reminder
    public boolean getStatus(){ return status;}

    // set priority of reminder
    public void setDuedate(Date duedate){
        this.duedate = duedate;
    }

    // get duedate of reminder
    public Date getDuedate(){ return duedate;}

    // set priority of reminder
    public void setCompleteddate(Date completeddate){
        this.completeddate = completeddate;
    }

    // get duedate of reminder
    public Date getCompleteddate(){ return completeddate;}
}
