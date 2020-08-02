package ExpenseTracker;

/**
 * An (Wallet) exception that is thrown if the user attempts to pay off non-existant debt
 * @author Daniele Palazzo
 *
 */

public class No_debt_exception extends Wallet_exception {
	public No_debt_exception() {
		super("No debt to pay off.");
	}
}
