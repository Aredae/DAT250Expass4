package no.hvl.dat110.rest.counters;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.google.gson.Gson;

public class TodoDAO {
		
		private static final String PERSISTENCE_UNIT_NAME = "todos";
	    private static EntityManagerFactory factory;
	    
		
		public TodoDAO() {
			factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		}
		
		public Todo getById(int id) {
			EntityManager em = factory.createEntityManager();
			Todo todo = new Todo();
			try {
		        todo = em.find(Todo.class, id);	
			}finally {
				em.close();
			}
			if(todo == null) {
				throw new EntityNotFoundException("Found no todo task with id " + id);
			}
	        return todo;
		}

		public List<Todo> getAll() {
			EntityManager em = factory.createEntityManager();
			List<Todo> todolist;
			try {
				Query q = em.createQuery("SELECT t FROM Todo t");
				todolist = q.getResultList();	
			}finally {
				em.close();
			}
			if(todolist ==null) {
				throw new EntityNotFoundException("No todo element exsist in the database");
			}
			return todolist;
		}

		public void add(Todo t) {
			EntityManager em = factory.createEntityManager();
			try {
				em.getTransaction().begin();
				em.persist(t);
				em.getTransaction().commit();	
			}finally {
				em.close();		
			}	
		}

		
		public void update(Todo t) {
			EntityManager em = factory.createEntityManager();
			try {
				em.getTransaction().begin();
				em.merge(t);
				em.getTransaction().commit();
			}
			finally {
				em.close();
			}
		}

		public void delete(Todo t) {
			EntityManager em = factory.createEntityManager();
			try {
				Todo toRemove = em.find(Todo.class, t.getId());
				em.getTransaction().begin();
				em.remove(toRemove);
				em.getTransaction().commit();
			}
			finally {
				em.close();
			}
		}
		
		public String toJson(List<Todo> l) {
			
			Gson gson = new Gson();
			
			String jsonInString = gson.toJson(l);
			return jsonInString;
			
		}
}
