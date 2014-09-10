package transaction.service;

import java.util.List;

import transaction.model.Transaction;
import transaction.model.TransactionView;

public interface ITransactionService {
	public void addTransaction(Transaction transaction);
	public void updateTransaction(Transaction transaction);
	public void saveOrUpdateTransaction(Transaction transaction);
	public void deleteTransaction(Transaction transaction);
	public Transaction getTransactionById(int id);
	public TransactionView getTransactionViewById(int idUser, int id);
	public List<Transaction> getTransactions(int id_user);
	public List<TransactionView> getTransactionViews(int id_user);
}
