package ExpenseTracker;

/**
 * An (Wallet) exception that is thrown if there isn't enough funds to perform a Wallet operation
 * @author Daniele Palazzo
 *
 */

public class Insufficient_funds_exception extends Wallet_exception{
	public Insufficient_funds_exception() {
		super("Insufficient funds.");
	}
}
