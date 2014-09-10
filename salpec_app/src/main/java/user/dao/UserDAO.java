package user.dao;

import java.util.List;


import org.hibernate.Session;
import org.hibernate.SessionFactory;

import user.model.User;

public class UserDAO implements IUserDAO {
	
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {        
		  
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

	public void addUser(User user) {
		getSessionFactory().getCurrentSession().save(user);
	}
/*
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
		System.out.println("----------UserDAO.login: Displaying id from table employee");
		boolean check = false;
		for(Iterator<Integer> it= selectClause.iterate(); it.hasNext();)
		{
			Integer intr = it.next();
			System.out.println("----------UserDAO.login: Employee Id : "+intr);
			check = true;
		}

		session.close();

		return check;
	}
*/	
	
	public User login(String user, String pass) {
		Session session = getSessionFactory().getCurrentSession();

		User current = new User();
		
		System.out.println("----------UserDAO.login: Check for user credentials...");
		List<User> userList = (List<User>) session.createQuery("from User as u where u.name = '" + user + "' and u.pass = '" + pass + "'").list();
		for(User u : (List<User>) userList)
		{
			current = u;
			System.out.println("----------UserDAO.login: User found: " + u.getName());
		}

		return current.getId() == 0 ? null : current;
	}

	public void deleteUser(User user) {
		getSessionFactory().getCurrentSession().delete(user);
	}

	public void updateUser(User user) {
		getSessionFactory().getCurrentSession().update(user);
	}

	public void updateLang(User user) {
		//getSessionFactory().getCurrentSession()
		//.createQuery("update User u set u.language='" + user.getLanguage() + "' where so.id= '" + user.getId() + "'");
		getSessionFactory().getCurrentSession()
		.createQuery("from User");	
	}
	
	public User existsUser(User user) {
		List list = getSessionFactory().getCurrentSession()
											.createQuery("select User from User where name=?")
									        .setParameter(0, user.getName()).list();
		
		return (User)list.get(0);
	}
	
	public User getUserById(int id) {
		List list = getSessionFactory().getCurrentSession()
											.createQuery("from User where id=?")
									        .setParameter(0, id).list();

		String sql_query = "select d.name,p.name,sum(p.price) as totalprice from Product p join p.dealer d group by p.name";
		
		return (User)list.get(0);
	}
	
	public User getUserByName(String name) {
		List list = getSessionFactory().getCurrentSession()
											.createQuery("from User where name=?")
									        .setParameter(0, name).list();
		if(list.size() > 0 )
			return (User)list.get(0);
		else
			return null;
	}
	
	public List<User> getUsers() {
		List list = getSessionFactory().getCurrentSession().createQuery("from User").list();
		return list;
	}
	
	public boolean hasAccounts(int id){
		if(id < 1) return false;
		
		List list = getSessionFactory().getCurrentSession()
											.createQuery("select name from Account where idUser=?")
									        .setParameter(0, id).list();
		if(list.size() > 0 )
			return true;
		else
			return false;
	}
}

