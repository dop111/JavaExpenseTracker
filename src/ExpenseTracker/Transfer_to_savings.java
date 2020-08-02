package ExpenseTracker;

/**
 * A command that transfers an amount from a Wallet's balance to its savings
 * @author Daniele Palazzo
 *
 */

public class Transfer_to_savings implements ICommand {
	
	private double amount;
	private Wallet wallet;
	private State original_wallet_state; //for undo
	
	/**
	 * 
	 * @param amount The amount to transfer from the balance to the savings 
	 * @param wallet The Wallet that will be affected
	 */
	public Transfer_to_savings(double amount, Wallet wallet) {
		this.amount = amount;
		this.wallet = wallet;
		this.original_wallet_state = wallet.get_state();
	}
	/**
	 * Perform the action: Transfer from the balance to the savings within the wallet
	 */
	@Override
	public void doit() throws Insufficient_funds_exception, Invalid_amount_exception {
		wallet.transfer_to_savings(amount);
	}
	/**
	 * Undo the action: Transfer it back
	 */
	@Override
	public void undoit() {
		wallet.balance += amount;
		wallet.savings -= amount;
		wallet.set_state(original_wallet_state);
	}

	@Override
	public String description() {
		return "Transfer to savings";
	}

	@Override
	public double get_amount() {
		return amount;
	}
}
