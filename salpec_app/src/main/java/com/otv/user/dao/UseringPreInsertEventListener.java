/*package com.otv.user.dao;

import org.hibernate.HibernateException;
import org.hibernate.event.internal.DefaultSaveEventListener;
import org.hibernate.event.spi.SaveOrUpdateEvent;
import org.hibernate.event.spi.PreInsertEvent;

import com.otv.model.User;


public class UseringPreInsertEventListener extends DefaultPreInsertEventListener   
{
   public void onSave(SaveOrUpdateEvent event)
         throws HibernateException {
      if(event.getObject() instanceof User) {
         User user = (User)event.getObject();
         System.out.println("Preparing to save or update user ");
         if( user.getName().equalsIgnoreCase("RRRRR")) {
             System.out.println("Conventional user not recorded.");
             
             // Here we prevent the invocation
             // of saveOrUpdate on the Session from having 
             // any effect on the database!
             //return null;
             System.out.println("RRRRR");
          }
         else
         {
             System.out.println("SaveEventListener.onSaveOrUpdate(SaveOrUpdateEvent)");
             super.onPreInsert(event);
         }
      }
      else
      {
    	  super.onPreInsert(event);
      }
   }
}
*/