package br.com.padtec.quarkus.task.boundary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletionStage;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Timeout;

import br.com.padtec.quarkus.task.control.TaskDAO;
import br.com.padtec.quarkus.task.entity.Task;
import io.vertx.axle.core.eventbus.EventBus;
import io.vertx.axle.core.eventbus.Message;

@Path("/tasks")
@Produces(MediaType.APPLICATION_JSON)
@SuppressWarnings("deprecation")
public class TaskResource {

	@Inject
	private TaskDAO taskDAO;
	
	@Inject
	EventBus eventBus;
	
	@GET
	@Fallback(fallbackMethod = "getDefaultTaskList")
	@Timeout(1)
	public List<Task> listAll() {
		return this.taskDAO.getAll();
	}

	@GET
	@Path("/{id}")	
	public CompletionStage<Task> find(@PathParam("id") int id) {
		return eventBus.<Task>send("find", id).thenApply(Message::body);
	}
	
	@POST
	public void create(Task task) {		
		this.taskDAO.create(task);
	}
	
	@PUT
	@Path("/{id}")	
	public void update(@PathParam("id") int id, Task task) {
		this.taskDAO.update(id, task);
	}
	
	@DELETE
	@Path("/{id}")
	public void delete(@PathParam("id") int id) {
		this.taskDAO.delete(id);
	}
	
	public List<Task> getDefaultTaskList() {
		Task taskDefault1 = new Task(997, "Default 01", false);
		Task taskDefault2 = new Task(998, "Default 02", false);
		Task taskDefault3 = new Task(999, "Default 03", true);		
		return new ArrayList<>(Arrays.asList(taskDefault1, taskDefault2, taskDefault3));
	}
}