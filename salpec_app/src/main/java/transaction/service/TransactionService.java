package transaction.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import transaction.dao.ITransactionDAO;
import transaction.dao.ITransactionViewDAO;
import transaction.model.Transaction;
import transaction.model.TransactionView;

@Transactional(readOnly = true)
public class TransactionService implements ITransactionService {

	// TransactionDAO is injected...
	ITransactionDAO transactionDAO;
	ITransactionViewDAO transactionViewDAO;
	
	@Transactional(readOnly = false)
	public void addTransaction(Transaction transaction) {
		getTransactionDAO().addTransaction(transaction);
	}
	
	@Transactional(readOnly = false)
	public void deleteTransaction(Transaction transaction) {
		getTransactionDAO().deleteTransaction(transaction);
	}
	
	@Transactional(readOnly = false)
	public void updateTransaction(Transaction transaction) {
		getTransactionDAO().updateTransaction(transaction);
	}

	@Transactional(readOnly = false)
	public void saveOrUpdateTransaction(Transaction transaction) {
		getTransactionDAO().saveOrUpdateTransaction(transaction);
	}
	
	public Transaction getTransactionById(int id) {
		return getTransactionDAO().getTransactionById(id);
	}
	
	public TransactionView getTransactionViewById(int idUser, int id) {
		return getTransactionViewDAO().getTransactionViewById(idUser, id);
	}

	public List<Transaction> getTransactions(int id) {	
		return getTransactionDAO().getTransactions(id);
	}

	public List<TransactionView> getTransactionViews(int id) {	
		return getTransactionViewDAO().getTransactionViews(id);
	}

	public ITransactionDAO getTransactionDAO() {
		return transactionDAO;
	}

	public ITransactionViewDAO getTransactionViewDAO() {
		return transactionViewDAO;
	}

	public void setTransactionDAO(ITransactionDAO transactionDAO) {
		this.transactionDAO = transactionDAO;
	}

	public void setTransactionViewDAO(ITransactionViewDAO transactionViewDAO) {
		this.transactionViewDAO = transactionViewDAO;
	}
}
