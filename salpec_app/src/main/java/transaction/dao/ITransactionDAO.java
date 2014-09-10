package transaction.dao;

import java.util.List;

import transaction.model.Transaction;

public interface ITransactionDAO {
	public void addTransaction(Transaction transaction);
	public void updateTransaction(Transaction transaction);
	public void saveOrUpdateTransaction(Transaction transaction);
	public void deleteTransaction(Transaction transaction);
	public Transaction getTransactionById(int id);
	public List<Transaction> getTransactions(int id_user);
}
