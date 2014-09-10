/*package account.model;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import account.dao.AccountDAO;

public class AccountConverter implements Converter {

    private static AccountDAO fooDAO = new AccountDAO();

    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        // Convert the unique String representation of Foo to the actual Foo object.
        return fooDAO.find(value);
    }

    public int getAsInt(FacesContext context, UIComponent component, Object value) {
        // Convert the Foo object to its unique String representation.
        return ((AccountSelectView) value).getId();
    }
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        // Convert the Foo object to its unique String representation.
        return ((AccountSelectView) value).getName();
    }
}*/