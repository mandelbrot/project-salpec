package transaction.dao;

import java.util.List;

import transaction.model.TransactionView;

public interface ITransactionViewDAO {
	public void addTransaction(TransactionView transaction);
	public void updateTransaction(TransactionView transaction);
	public void deleteTransaction(TransactionView transaction);
	public TransactionView getTransactionViewById(int idUser, int id);
	public List<TransactionView> getTransactionViews(int id_user);
}
