package sink.cloud;

import java.util.Date;

public class Task  {
	  
    private String name;
    private Date dueDate;
  
    public Task(String name, Date dueDate) {
        this.name = name;
        this.dueDate = dueDate;
    }
 
    public void setName(String name) { this.name = name; }
    public String getName() { return this.name; }
 
    public void setDueDate(Date dueDate) { this.dueDate = dueDate; }
    public Date getDueDate() { return this.dueDate; }
}