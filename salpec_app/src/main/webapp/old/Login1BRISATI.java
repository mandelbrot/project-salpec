package user.managed.bean;

import java.io.IOException;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

//import org.apache.log4j.Level;
//import org.apache.log4j.Logger;

public class Login1BRISATI {
	String errorMessageLogin;    
	
    public void checkAuthentication() throws IOException {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();

        if (externalContext.getUserPrincipal() != null) {
            externalContext.redirect(externalContext.getRequestContextPath() + "/index.xhtml");
        }
    }
	public void login1(){
/*
		boolean result = businessBean.checkCredentiais(username, password);

		if(result){
			try {
				errorMessageLogin="";
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("username", username);
				FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
	
			} 
			catch (IOException ex) 
			{
				Logger.getLogger(UserCredentiaisView.class.getName()).log(Level.ERROR, null, ex);
			}
		}
		else
		{
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("username", "");
			errorMessageLogin = "Credentials not supported.";
		}	*/
	}
}
