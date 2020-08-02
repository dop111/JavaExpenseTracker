package ExpenseTracker;
/**
 * Commands that can be executed by the Account class
 * @author Daniele Palazzo
 *
 */
public interface ICommand {
	/**
	 * Execute the command
	 * @throws Invalid_amount_exception A wallet exception that is thrown when calling a Wallet method
	 * with an amount that is not valid (e.g. negative value)
	 * @throws Insufficient_funds_exception A wallet exception that is thrown when the Wallet does not have enough funds to
	 * perform a requested action
	 * @throws No_debt_exception A wallet exception that is thrown when attempting to pay off non existent debt in the Wallet
	 */
	public void doit() throws Invalid_amount_exception, Insufficient_funds_exception, No_debt_exception;
	/**
	 * Do the opposite of what the doit method does. Undo the command. 
	 */
	public void undoit();
	/**
	 * The description of the command
	 * @return Return a string describing what the command does. (Useful for printing the command history.)
	 */
	public String description();
	/**
	 * @return Return the amount stored in the command. (Useful for printing the command history.)
	 */
	public double get_amount();
}
