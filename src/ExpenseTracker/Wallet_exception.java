package ExpenseTracker;

/**
 * Exception that can be thrown by a Wallet (this is an abstract class)
 * @author Daniele Palazzo
 *
 */
public abstract class Wallet_exception extends Exception {
	public Wallet_exception(String message) {
		super(message);
	}
}
