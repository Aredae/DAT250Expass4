package no.hvl.dat110.rest.counters;

import static spark.Spark.after;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.put;
import static spark.Spark.post;
import static spark.Spark.delete;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.google.gson.Gson;

/**
 * Hello world!
 *
 */
public class App1 {
	
	//static Counters counters = null;
	static TodoDAO t = null;
	
	public static void main(String[] args) {
		
		
        t = new TodoDAO();
        //votes.forEach(v -> System.out.println("Vote with id: " + v.getId()));
		
		if (args.length > 0) {
			port(Integer.parseInt(args[0]));
		} else {
			port(8080);
		}

		//todo = new Todo();
		
		after((req, res) -> {
  		  res.type("application/json");
  		});
		
		post("/todo", (req,res) -> {
			Gson gson = new Gson();
			
			Todo todo = gson.fromJson(req.body(), Todo.class);
			t.add(todo);
			return todo.toJson();
		});
		
		get("/hello", (req, res) -> "Hello World!");
		
        get("/todo", (req, res) -> {
        	List<Todo> l = t.getAll();
        	
        	Gson gson = new Gson();
        	
        	return gson.toJson(l);
        	
        });
        
        delete("/todo", (req,res)->{
        	Gson gson = new Gson();
        	
        	Todo todo = gson.fromJson(req.body(), Todo.class);
        	t.delete(todo);
        	return todo.toJson();
        });
               
        put("/todo", (req,res) -> {
        
        	Gson gson = new Gson();
        	
        	Todo todo = gson.fromJson(req.body(), Todo.class);
        	t.update(todo);
            return todo.toJson();
        	
        });
    }
    
}
