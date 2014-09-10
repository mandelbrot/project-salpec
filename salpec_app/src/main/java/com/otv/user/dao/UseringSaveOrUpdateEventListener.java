/*package com.otv.user.dao;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.event.internal.DefaultSaveOrUpdateEventListener;
import org.hibernate.event.spi.SaveOrUpdateEvent;

import com.otv.model.User;

public class UseringSaveOrUpdateEventListener extends DefaultSaveOrUpdateEventListener
{
   public void onSaveOrUpdate(SaveOrUpdateEvent event)
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
             System.out.println("SaveOrUpdateEventListener.onSaveOrUpdate(SaveOrUpdateEvent)");
             super.onSaveOrUpdate(event);
         }
      }
      else
      {
    	  super.onSaveOrUpdate(event);
          System.out.println("ssssss");
      }
   }
}

*/