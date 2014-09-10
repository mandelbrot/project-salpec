package currency.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import transaction.model.TransactionView;

import currency.model.Currency;
import currency.model.CurrencySelectView;
import currency.dao.ICurrencyDAO;

@Transactional(readOnly = true)
public class CurrencyService implements ICurrencyService {

	// CurrencyDAO is injected...
	ICurrencyDAO currencyDAO;
	
	@Transactional(readOnly = false)
	public void addCurrency(Currency currency) {
		getCurrencyDAO().addCurrency(currency);
	}
	
	@Transactional(readOnly = false)
	public void deleteCurrency(Currency currency) {
		getCurrencyDAO().deleteCurrency(currency);
	}
	
	@Transactional(readOnly = false)
	public void updateCurrency(Currency currency) {
		getCurrencyDAO().updateCurrency(currency);
	}

	public Currency getCurrencyById(int id) {
		return getCurrencyDAO().getCurrencyById(id);
	}

	public List<Currency> getCurrencys(int id) {	
		return getCurrencyDAO().getCurrencys(id);
	}

	public List<CurrencySelectView> getCurrencySelectView() {	
		return getCurrencyDAO().getCurrencySelectView();
	}
	
/*	public List<CurrencySelectView> getCurrencySelectView(int idUser) {	
		return getCurrencyDAO().getCurrencySelectView(idUser);
	}*/

	public ICurrencyDAO getCurrencyDAO() {
		return currencyDAO;
	}

	public void setCurrencyDAO(ICurrencyDAO currencyDAO) {
		this.currencyDAO = currencyDAO;
	}
}
