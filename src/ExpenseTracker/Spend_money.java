package ExpenseTracker;

/**
 * A command that deducts money from a Wallet
 * @author Daniele Palazzo
 *
 */

public class Spend_money implements ICommand {

	private double amount;
	private Wallet wallet;
	private State original_wallet_state; //for undo
	private double balance, savings, debt; //for undo
	
	/**
	 * 
	 * @param amount The amount to take away from the Wallet
	 * @param wallet The Wallet that will be affected
	 */
	public Spend_money(double amount, Wallet wallet) {
		this.amount = amount;
		this.wallet = wallet;
		this.original_wallet_state = wallet.get_state();
		
		this.balance = wallet.balance;
		this.savings = wallet.savings;
		this.debt = wallet.debt;
	}
	/**
	 * Perform the action: Spend money from the Wallet
	 */
	@Override
	public void doit() throws Invalid_amount_exception {
		wallet.spend_money(amount);
	}
	/**
	 * Undo the action: Set the Wallet back to how it was before spending money from it
	 */
	@Override
	public void undoit() {
		wallet.balance = this.balance;
		wallet.savings = this.savings;
		wallet.debt = this.debt;
		wallet.set_state(original_wallet_state);

	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String description() {
		return "Spend money";
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public double get_amount() {
		return amount;
	}

}
