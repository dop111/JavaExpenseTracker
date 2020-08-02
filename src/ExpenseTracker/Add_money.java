package ExpenseTracker;

/**
 * A command that adds money to a Wallet
 * @author Daniele Palazzo
 *
 */

public class Add_money implements ICommand {

	private double amount;
	private Wallet wallet;
	private State original_wallet_state; //for undo
	
	/**
	 * @param amount The amount to be added to the Wallet
	 * @param wallet The wallet that the amount will be added to
	 */
	public Add_money(double amount, Wallet wallet) {
		this.amount = amount;
		this.wallet = wallet;
		this.original_wallet_state = wallet.get_state();
	}
	
	/**
	 * Perform the action: Add money to the Wallet
	 */
	@Override
	public void doit() throws Invalid_amount_exception {
		wallet.add_money(amount);
	}
	
	/**
	 * Undo the action: Set the Wallet back to how it was before adding money to it
	 */
	@Override
	public void undoit() {
		wallet.balance -= amount;
		wallet.set_state(original_wallet_state);
	}

	@Override
	public String description() {
		return "Add money";
	}
	
	@Override
	public double get_amount() {
		return amount;
	}

}
