package net.tbp.interval.ui.reminder;

// class to manage the variable that relate to the reminder
public class Reminder {
    private String title;           // title of the task
    private int id;                 // id of task
    private String description;     // description of task
    private Boolean status;         // true if task is completed, false if task have not co,plete

    // construction of the reminder class
    public Reminder(int id, String title, String description, Boolean status ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
    }

    // set id
    public void setReminderId(int id) {
        this.id = id;
    }

    // get id
    public int getReminderId() {
        return id;
    }

    // set title of reminder
    public void setTitle(String title){
        this.title = title;
    }

    // get title of reminder
    public String getTitle(){
        return title;
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
}


