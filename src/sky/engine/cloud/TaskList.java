package sky.engine.cloud;
import java.util.ArrayList;
import java.util.List;
 
import com.stackmob.sdk.model.StackMobModel;
 
public class TaskList extends StackMobModel {
 
    private String name;
    private List<Task> tasks;
    private Task topTask;
    private TaskList parentList;
 
    public TaskList(String name) {
        super(TaskList.class);
        tasks = new ArrayList<Task>();
        this.name = name;
    }
 
    public String getName() {
        return name;
    }
 
    public List<Task> getTasks() {
        return tasks;
    } 
}