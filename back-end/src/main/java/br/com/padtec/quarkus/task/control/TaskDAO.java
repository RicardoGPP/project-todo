package br.com.padtec.quarkus.task.control;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

import br.com.padtec.quarkus.task.entity.Task;
import io.quarkus.vertx.ConsumeEvent;
import redis.clients.jedis.Jedis;

@ApplicationScoped
public class TaskDAO {

	private static final String TASK_REFERENCES = "tasks";
	private static final String TASK_PREFIX = "task";
	private static final String ID_CONTROL = "id";
	
	private Jedis jedis;
	
	public TaskDAO() {
		this.jedis = new Jedis();
	}
	
	private int generateNewId() {
		return this.jedis.incr(ID_CONTROL).intValue();
	}
	
	private String generateTaskReference(int id) {
		return TASK_PREFIX + "#" + id;
	}
	
	public List<Task> getAll() {
		for (int i = 0; i < Integer.MAX_VALUE; i++);
		List<Task> tasks = new ArrayList<Task>();
		List<String> taskReferences = this.jedis.lrange(TASK_REFERENCES, 0, -1);
		for (String taskReference : taskReferences) {
			Map<String, String> taskMap = this.jedis.hgetAll(taskReference);
			if (taskMap.keySet().size() == 2) {
				Task task = new Task();
				task.setId(Integer.parseInt(taskReference.split("#")[1]));
				task.setDescription(taskMap.get("description"));
				task.setDone(taskMap.get("done").equals("true"));
				tasks.add(task);
			}
		}
		return tasks;	
	}
	
	@ConsumeEvent(value = "find", blocking = true)
	public Task find(int id) {
		Map<String, String> taskMap = this.jedis.hgetAll(this.generateTaskReference(id));
		if (taskMap.keySet().size() == 2) {
			Task task = new Task();
			task.setId(id);
			task.setDescription(taskMap.get("description"));
			task.setDone(taskMap.get("done").equals("true"));
			return task;
		}		
		return null;
	}
	
	public void create(Task task) {		
		int id = this.generateNewId();
		String taskReference = this.generateTaskReference(id);			
		this.jedis.lpush(TASK_REFERENCES, taskReference);		
		this.jedis.hset(taskReference, "description", task.getDescription());
		this.jedis.hset(taskReference, "done", task.isDone() ? "true" : "false");
	}
	
	public void update(int id, Task task) {
		String taskReference = this.generateTaskReference(id);
		Map<String, String> taskMap = this.jedis.hgetAll(taskReference);
		if (taskMap.keySet().size() == 2) {
			this.jedis.hset(taskReference, "description", task.getDescription());
			this.jedis.hset(taskReference, "done", task.isDone() ? "true" : "false");
		}
	}
	
	public void delete(int id) {
		String taskReference = this.generateTaskReference(id);
		this.jedis.lrem(TASK_REFERENCES, 0, taskReference);
		this.jedis.hdel(taskReference, "description");
		this.jedis.hdel(taskReference, "done");
	}
}