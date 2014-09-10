package transaction.dao;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import transaction.model.TransactionView;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.Transformers;

import account.model.AccountSelectView;
import consumption.model.ConsumptionSelectView;
import currency.model.CurrencySelectView;

public class TransactionViewDAO implements ITransactionViewDAO {
	
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {        
		  
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

	public void addTransaction(TransactionView transaction) {
		getSessionFactory().getCurrentSession().save(transaction);
	}

	public void deleteTransaction(TransactionView transaction) {
		getSessionFactory().getCurrentSession().delete(transaction);
	}

	public void updateTransaction(TransactionView transaction) {
		getSessionFactory().getCurrentSession().update(transaction);
	}

	public TransactionView getTransactionViewById(int idUser, int id) {
		List list = getSessionFactory().getCurrentSession()
				.createQuery("select t.id, t.idAccount, t.idCurrency, t.idConsumption, t.value, t.ie, t.e, " +
					"t.active, t.description, a.name as accountName, c.name as consumptionName, " +
					"cu.code, t.date, t.dateTransaction from Transaction t, " +
					"Account a, Consumption c, Currency cu where t.idAccount = a.id and t.idConsumption = c.id and " +
					" t.idCurrency = cu.id and a.idUser=? and t.active ='Y' and t.id=?")
		        .setParameter(0, idUser).setParameter(1, id).list();

		TransactionView tTransaction = new TransactionView(0, 0, 0, 0, 
			0, "0", "0", 
			"Y", "", "", 
			"", "HRK", 
			new Date(), new Date(), 
			new AccountSelectView(0, ""), 
			new CurrencySelectView(0, ""), 
			new ConsumptionSelectView(0, ""));
		
		if(list.size() < 1)
			return tTransaction;
		else
		{			
			for (Object o: list) 
			{         
	    	    Object values[] = (Object[]) o;
	    	    String[] tmp = new String[values.length];
	    	    //System.out.println("----------TransactionViewController.init: Size of the object is: " + values.length);  
	    	    for(int i = 0; i < values.length; ++i) {
	    	         tmp[i] = String.valueOf(values[i]);
	    	         //System.out.println("----------TransactionViewController.init: " + i + " value of object is: " + String.valueOf(values[i]));
	    	    }
	    		Date d1 = new Date();
	    		Date d2 = new Date();	
	    		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    		try 
	    		{
	    			d1 = formatter.parse(tmp[12]);
	    			d2 = formatter.parse(tmp[13]);
	    		} 
	    		catch (ParseException e) 
	    		{
	    			e.printStackTrace();
	    			d1 = new Date();
		    		d2 = new Date();
	    		}
	        	tTransaction = new TransactionView(Integer.parseInt(tmp[0]), 
	        			Integer.parseInt(tmp[1]), Integer.parseInt(tmp[2]), Integer.parseInt(tmp[3]), 
	        			Double.parseDouble(tmp[4]), (String) tmp[5], (String) tmp[6], 
	        			(String) tmp[7], (String) tmp[8], (String) tmp[9], 
	        			(String) tmp[10], (String) tmp[11], d1, d2, 
	        			new AccountSelectView(Integer.parseInt(tmp[1]), (String) tmp[9]),
						new CurrencySelectView(Integer.parseInt(tmp[2]), (String) tmp[11]),
						new ConsumptionSelectView(Integer.parseInt(tmp[3]), (String) tmp[10]));
			}
			return tTransaction;
		}
	}

	@SuppressWarnings("unchecked")
	public List<TransactionView> getTransactionViews(int id_user) {
		List<TransactionView> list = (List<TransactionView>) getSessionFactory().getCurrentSession()
				.createQuery("select t.id, t.idAccount, t.idCurrency, t.idConsumption, t.value, t.ie, t.e, " +
					"t.active, t.description, a.name as accountName, c.name as consumptionName, " +
					"cu.code, t.date, t.dateTransaction from Transaction t, " +
					"Account a, Consumption c, Currency cu where t.idAccount = a.id and t.idConsumption = c.id and " +
					" t.idCurrency = cu.id and a.idUser=? and t.active ='Y' order by t.dateTransaction desc")
                .setParameter(0, id_user).list();

/*		//ili ovak samo kaj treba napisati originalni SQL i da polja u selectu budu polja
		//u klasi (tu nisu jer TransactionView ima i AccountSelectView, CurrencySelectView i ConsumptionSelectView pa
		// se ne mapiraju kak spada)
		List<TransactionView> list1 = (List<TransactionView>) getSessionFactory().getCurrentSession()
				.createSQLQuery("select t.id, t.id_Account, t.id_Currency, t.id_Consumption, t.value, t.ie, t.e, " +
					"t.active, t.description, a.name as accountName, c.name as consumptionName, " +
					"cu.code, t.date, t.date_Transaction from Transaction t, " +
					"Account a, Consumption c, Currency cu where t.id_Account = a.id and t.id_Consumption = c.id and " +
					"t.id_Currency = cu.id and a.id_User=? and t.active ='Y' order by t.date_Transaction desc").addEntity(TransactionView.class)
                .setParameter(0, id_user).list();*/
		
/*		List<TransactionView> entries = castList(TransactionView.class, getSessionFactory().getCurrentSession()
				.createQuery("select t.id, t.idAccount, t.idCurrency, t.idConsumption, t.value, t.ie, t.e, " +
						"t.active, t.description, a.name as accountName, c.name as consumptionName, " +
						"cu.code, t.date, t.dateTransaction from Transaction t, " +
						"Account a, Consumption c, Currency cu where t.idAccount = a.id and t.idConsumption = c.id and " +
						" t.idCurrency = cu.id and a.idUser=? and t.active ='Y' order by t.dateTransaction desc")
			        .setParameter(0, id_user).list());*/
		return list;
	}	
	
	public static <T> List<T> castList(Class<? extends T> clazz, Collection<?> c) {
	    List<T> r = new ArrayList<T>(c.size());
	    for(Object o: c)
	      r.add(clazz.cast(o));
	    return r;
	}
}

