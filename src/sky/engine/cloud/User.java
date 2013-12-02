package sky.engine.cloud;

import java.util.List;
import com.stackmob.sdk.model.StackMobUser;
  
public class User extends StackMobUser {
  
    private List<TaskList> taskLists;
    private String email;
  
    public User(String username, String password) {
        super(User.class, username, password);
    }
  
  
    public List<TaskList> getTaskLists() {
        return taskLists;
    }
  
    public void setTasks(List<TaskList> taskLists) {
        this.taskLists = taskLists;
    }
}