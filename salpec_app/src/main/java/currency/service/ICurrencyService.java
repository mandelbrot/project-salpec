package currency.service;

import java.util.List;

import transaction.model.TransactionView;

import currency.model.Currency;
import currency.model.CurrencySelectView;

public interface ICurrencyService {
	public void addCurrency(Currency currency);
	public void updateCurrency(Currency currency);
	public void deleteCurrency(Currency currency);
	public Currency getCurrencyById(int id);
	public List<Currency> getCurrencys(int id_user);
	public List<CurrencySelectView> getCurrencySelectView();
	//public List<CurrencySelectView> getCurrencySelectView(int id_user);
}
