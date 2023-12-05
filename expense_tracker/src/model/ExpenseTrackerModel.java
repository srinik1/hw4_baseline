package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
 * Represents a model for tracking expenses in an expense tracker application.
 * This class maintains a list of transactions and a list of indices that match a certain filter.
 * It follows the Observer design pattern, acting as the Observable in this pattern,
 * notifying registered listeners about the state changes in the model.
 *
 * <p>The class allows for adding transactions, retrieving transactions, removing transactions,
 * setting and getting matched filter indices. It also supports registering, checking, and
 * notifying listeners regarding changes in the model.</p>
 */
public class ExpenseTrackerModel {

  //encapsulation - data integrity
  private List<Transaction> transactions;
  private List<Integer> matchedFilterIndices;
  private final List<ExpenseTrackerModelListener> expenseTrackerModelListeners = new ArrayList<>();

  // This is applying the Observer design pattern.                          
  // Specifically, this is the Observable class. 
    
  public ExpenseTrackerModel() {
    transactions = new ArrayList<Transaction>();
    matchedFilterIndices = new ArrayList<Integer>();
  }
  /**
   * Adds a transaction to the expense tracker.
   *
   * @param t The transaction to be added.
   * @throws IllegalArgumentException if the transaction is null.
   */
  public void addTransaction(Transaction t) {
    // Perform input validation to guarantee that all transactions added are non-null.
    if (t == null) {
      throw new IllegalArgumentException("The new transaction must be non-null.");
    }
    transactions.add(t);
    // The previous filter is no longer valid.
    matchedFilterIndices.clear();
    stateChanged();
  }
  /**
   * Removes a transaction from the expense tracker.
   *
   * @param t The transaction to be removed.
   */
  public void removeTransaction(Transaction t) {
    transactions.remove(t);
    // The previous filter is no longer valid.
    matchedFilterIndices.clear();
    stateChanged();
  }
  /**
   * Retrieves an unmodifiable list of all transactions.
   *
   * @return An unmodifiable list of transactions.
   */
  public List<Transaction> getTransactions() {
    //encapsulation - data integrity
    return Collections.unmodifiableList(new ArrayList<>(transactions));
  }
  /**
   * Sets the matched filter indices.
   *
   * @param newMatchedFilterIndices A list of indices to set.
   * @throws IllegalArgumentException if the list is null or indices are out of range.
   */
  public void setMatchedFilterIndices(List<Integer> newMatchedFilterIndices) {
      // Perform input validation
      if (newMatchedFilterIndices == null) {
	  throw new IllegalArgumentException("The matched filter indices list must be non-null.");
      }
      for (Integer matchedFilterIndex : newMatchedFilterIndices) {
	  if ((matchedFilterIndex < 0) || (matchedFilterIndex > this.transactions.size() - 1)) {
	      throw new IllegalArgumentException("Each matched filter index must be between 0 (inclusive) and the number of transactions (exclusive).");
	  }
      }
      // For encapsulation, copy in the input list 
      this.matchedFilterIndices.clear();
      this.matchedFilterIndices.addAll(newMatchedFilterIndices);
      stateChanged();
  }
  /**
   * Retrieves a copy of the matched filter indices.
   *
   * @return A list containing the matched filter indices.
   */
  public List<Integer> getMatchedFilterIndices() {
      // For encapsulation, copy out the output list
      List<Integer> copyOfMatchedFilterIndices = new ArrayList<Integer>();
      copyOfMatchedFilterIndices.addAll(this.matchedFilterIndices);
      return copyOfMatchedFilterIndices;
  }

  /**
   * Registers the given ExpenseTrackerModelListener for
   * state change events.
   *
   * @param listener The ExpenseTrackerModelListener to be registered
   * @return If the listener is non-null and not already registered,
   *         returns true. If not, returns false.
   */   
  public boolean register(ExpenseTrackerModelListener listener) {
      // For the Observable class, this is one of the methods.
      //
      // TODO
      if(listener == null){
          return false;
      }
      if(!expenseTrackerModelListeners.contains(listener)){
          expenseTrackerModelListeners.add(listener);
          return true;
      }
      return false;
  }
  /**
   * Retrieves the number of listeners currently registered with this model.
   *
   * @return The number of registered ExpenseTrackerModelListeners.
   */
  public int numberOfListeners() {
      // For testing, this is one of the methods.
      //
      //TODO
      return expenseTrackerModelListeners.size();
  }
  /**
   * Checks if a specific listener is registered with this model.
   *
   * @param listener The ExpenseTrackerModelListener to check for registration.
   * @return true if the listener is registered, false otherwise.
   */
  public boolean containsListener(ExpenseTrackerModelListener listener) {
      // For testing, this is one of the methods.
      //
      //TODO
      return expenseTrackerModelListeners.contains(listener);
  }
  /**
   * Notifies all registered listeners about a state change in the model.
   * This method is protected and is intended to be called internally
   * when the state of the model changes.
   */
  protected void stateChanged() {
      // For the Observable class, this is one of the methods.
      //
      //TODO
      List<ExpenseTrackerModelListener> listenersCopy = new ArrayList<>(expenseTrackerModelListeners);
      for (ExpenseTrackerModelListener listener : listenersCopy) {
          listener.update(this);
      }
  }
}
