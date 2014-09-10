package com.otv.user.dao;

import java.util.List;

import com.otv.model.User;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class UserDAO implements IUserDAO {
	
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {        
		  
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

	//@Override
	public void addUser(User user) {

	    //config.setListener("save-update", new UseringSaveOrUpdateEventListener());
	    //SaveOrUpdateEventListener[] stack = { new UseringSaveOrUpdateEventListener()};
	    //EventListenerGroupImpl e = new EventListenerGroupImpl();
	    //config.EventListenerGroupImpl().setSaveOrUpdateEventListeners(stack);    

			//getSessionFactory().getCurrentSession().saveOrUpdate(new User("R1","rrrrrrrr",1));
			//getSessionFactory().getCurrentSession().saveOrUpdate(new User("R2","rrrrrrrr",1));
	      
		getSessionFactory().getCurrentSession().save(user);
	}

	public boolean login(User user) {
			
		return true;
		
	    //provjeriti user-pass
/*
		SessionFactory sessionFactory;
		ServiceRegistry serviceRegistry;
		Session session = null;

		Configuration cfg = new Configuration().addResource(
				"roseindia/employee.hbm.xml").configure();
				serviceRegistry = new ServiceRegistryBuilder().applySettings(
				cfg.getProperties()).buildServiceRegistry();
			sessionFactory = cfg.buildSessionFactory(serviceRegistry);

		session = sessionFactory.openSession();
		//SELECT clause with from
		Query selectClause = session.createQuery("select pass from user u.name = " + user.getName() + " and u.pass = " + user.getPass());
		System.out.println("Displaying id from table employee");
		boolean check = false;
		for(Iterator<Integer> it= selectClause.iterate(); it.hasNext();)
		{
			Integer intr = it.next();
			System.out.println("Employee Id : "+intr);
			check = true;
		}

		session.close();

		return check;*/
	}
	
	
	public User login(String user, String pass) {
		Session session = getSessionFactory().getCurrentSession();

		User current = new User();
		
		//SELECT clause with from
		//Query selectClause = session.createQuery("from user as u where u.name = '" + user + "' and u.pass = '" + pass + "'");
		System.out.println("Check for user credentials...");
		List<User> userList = (List<User>) session.createQuery("from User as u where u.name = '" + user + "' and u.pass = '" + pass + "'").list();
		for(User u : (List<User>) userList)
		{
			current = u;
			System.out.println("User found: " + u.getName());
		}

		//session.close();

		return current.getId() == 0 ? null : current;
	}

	//@Override
	public void deleteUser(User user) {
		getSessionFactory().getCurrentSession().delete(user);
	}

	//@Override
	public void updateUser(User user) {
		getSessionFactory().getCurrentSession().update(user);
	}

	//@Override
	public void updateLang(User user) {
		//getSessionFactory().getCurrentSession()
		//.createQuery("update User u set u.language='" + user.getLanguage() + "' where so.id= '" + user.getId() + "'");
		getSessionFactory().getCurrentSession()
		.createQuery("from User");
		
	}
	
	//@Override
	public User getUserById(int id) {
		List list = getSessionFactory().getCurrentSession()
											.createQuery("from User where id=?")
									        .setParameter(0, id).list();

		String sql_query = "select d.name,p.name,sum(p.price) as totalprice from Product p join p.dealer d group by p.name";
		
		return (User)list.get(0);
	}

	//@Override
	public List<User> getUsers() {
		List list = getSessionFactory().getCurrentSession().createQuery("from User").list();
		return list;
	}
}
/*
class UseringSaveOrUpdateEventListener extends DefaultSaveOrUpdateEventListener
{
   public Serializable onSaveOrUpdate(SaveOrUpdateEvent event)
         throws HibernateException {
      if( event.getObject() instanceof User ) {
         User user = (User)event.getObject();
         System.out.println("Preparing to save or update user ");

      }
      
      return super.onSaveOrUpdate(event);
   }
}*/


