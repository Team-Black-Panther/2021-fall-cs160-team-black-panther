package net.tbp.interval.ui.reminder;

import java.util.Date;

// class to manage the variable that relate to the reminder
public class Reminder {
    private String name;           // name of the task
    private int id;                 // id of task
    private String description;     // description of task
    private Boolean status;         // true if task is completed, false if task have not co,plete
    private int priority;
    private Date duedate;

    // construction of the reminder class
    public Reminder(int id, String name, String description, Boolean status, int priority, Date duedate ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.duedate = duedate;
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

    // set description
    public void setDescription(String description){
        this.description = description;
    }

    // get description
    public String getDescription(){
        return description;
    }

    // set status of reminder
    public void setStatus(Boolean status){
        this.status = status;
    }

    // get status of reminder
    public boolean getStatus(){ return status;}

    // set priority of reminder
    public void setPriority(int priority){
        this.priority = priority;
    }

    // get duedate of reminder
    public int getPriority(){ return priority;}

    // set priority of reminder
    public void setDuedate(Date duedate){
        this.duedate = duedate;
    }

    // get duedate of reminder
    public Date getDuedate(){ return duedate;}
}


