package net.tbp.interval.ui.reminder;

public class Reminder {
    private String title;
    private int id;
    private String description;
    private Boolean status;

    public Reminder(int id, String title, String description, Boolean status ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
    }

    public void setReminderId(int id) {
        this.id = id;
    }

    public int getReminderId() {
        return id;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getTitle(){
        return title;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }

    public void setStatus(Boolean status){
        this.status = status;
    }

    public boolean getStatus(){ return status;}
}


