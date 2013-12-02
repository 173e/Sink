package sky.engine.cloud;
import java.util.Date;

import com.stackmob.sdk.model.StackMobModel;
  
public class Task extends StackMobModel {
  
    private String name;
    private Date dueDate;
  
    public Task(String name, Date dueDate) {
        super(Task.class);
        this.name = name;
        this.dueDate = dueDate;
    }
 
    public void setName(String name) { this.name = name; }
    public String getName() { return this.name; }
 
    public void setDueDate(Date dueDate) { this.dueDate = dueDate; }
    public Date getDueDate() { return this.dueDate; }
}