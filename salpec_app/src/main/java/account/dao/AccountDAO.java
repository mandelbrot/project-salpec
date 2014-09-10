package account.dao;

import java.util.List;

import account.model.Account;
import account.model.AccountSelectView;
import account.model.AccountView;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class AccountDAO implements IAccountDAO {
	
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {        
		  
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

	public void addAccount(Account account) {
		getSessionFactory().getCurrentSession().save(account);
	}
	
	public void saveOrUpdateAccount(Account c) {
		getSessionFactory().getCurrentSession().saveOrUpdate(c);
	}

	public void deleteAccount(Account account) {
		getSessionFactory().getCurrentSession().delete(account);
	}

	public void updateAccount(Account account) {
		getSessionFactory().getCurrentSession().update(account);
	}

	public Account getAccountById(int id) {
		List list = getSessionFactory().getCurrentSession()
			.createQuery("select a.id, a.name, sum(t.value) as balance from Account a, Transaction t" +
			" where a.id = t.idAccount and id=?")
	        .setParameter(0, id).list();

		String sql_query = "select d.name,p.name,sum(p.price) as totalprice from Product p join p.dealer d group by p.name";
		
		return (Account)list.get(0);
	}

	public List<AccountView> getAccounts(int id_user) {
		
		//joinove ne znam složiti!!!
		
		System.out.println("----------AccountDAO: getting account balance: ");
		/*List list1 = getSessionFactory().getCurrentSession()
			.createQuery("select a.id, a.idUser, a.name, t.ie, t.value" + 
			" from Transaction t left join t.account as a" +
			" where t.idAccount=a.id and a.idUser=? ")
	        .setParameter(0, id_user).list();

		System.out.println("----------AccountDAO: getting account balance: list size " + list1.size());
		
        for (Object o: list1) {         
    	    Object values[] = (Object[]) o;
    	    String[] tmp = new String[values.length];
    	    for(int i = 0; i < values.length; ++i) {
    	         tmp[i] = String.valueOf(values[i]);
    	         System.out.println("----------AccountController: " + i + " value of object is: " + String.valueOf(values[i]));
    	    }
        }*/
		
		System.out.println("----------AccountDAO: getting account balance: ");
		List list = getSessionFactory().getCurrentSession()
			.createQuery("select a.id, a.idUser, a.name, " + 
			"sum(case when t.ie='1' then COALESCE(t.value, 0) else (COALESCE(t.value, 0)*(-1)) end) as balance " + 
			" from Transaction t, Account a" +
			" where t.idAccount=a.id and a.idUser=? group by a.id")
	        .setParameter(0, id_user).list();
		//prazni raèuni
		List listEmpty = getSessionFactory().getCurrentSession()
				.createQuery("select a.id, a.idUser, a.name, 0 as balance" + 
				" from Account a " +
				" where a.idUser=? and a.id not in " + 
				"(select distinct a.id from Transaction t, Account a " +
				"where t.idAccount=a.id and a.idUser=?)")
		        .setParameter(0, id_user).setParameter(1, id_user).list();

		System.out.println("----------AccountDAO: getting empty account balance: list size " + listEmpty.size());
		AccountView a;
        for (Object o: listEmpty) {         
    	    Object values[] = (Object[]) o;
    	    String[] tmp = new String[values.length];
    	    for(int i = 0; i < values.length; ++i) {
    	         tmp[i] = String.valueOf(values[i]);
    	    }
    	    a = new AccountView(Integer.parseInt(tmp[0]), Integer.parseInt(tmp[1]), 
    	    		(String) tmp[2], Double.parseDouble(tmp[3]));
    	    list.add(o);
        }
		return (List<AccountView>) list;
	}	
	
	public List<AccountSelectView> getAccountSelectView(int id_user) {
		List list = getSessionFactory().getCurrentSession()
				.createQuery("select a.id, a.name as name " +
					"from Account a where a.idUser=?")
		        .setParameter(0, id_user).list();
		return (List<AccountSelectView>) list;
	}    
}

