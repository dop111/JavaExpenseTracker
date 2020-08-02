package ExpenseTracker;

/**
 * An (Wallet) exception that is thrown if an invalid amount is entered 
 * @author Daniele Palazzo
 *
 */

public class Invalid_amount_exception extends Wallet_exception {
	public Invalid_amount_exception() {
		super("Invalid amount.");
	}
}
