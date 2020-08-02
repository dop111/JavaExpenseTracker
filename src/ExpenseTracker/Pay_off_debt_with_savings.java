package ExpenseTracker;

/**
 * A command that reduces Wallet debt using the Wallet savings
 * @author Daniele Palazzo
 *
 */

public class Pay_off_debt_with_savings implements ICommand {

	private double amount;
	private Wallet wallet;
	private State original_wallet_state; //for undo
	
	/**
	 * 
	 * @param amount The amount to reduce the debt by
	 * @param wallet The wallet that will be affected
	 */
	
	public Pay_off_debt_with_savings(double amount, Wallet wallet) {
		this.amount = amount;
		this.wallet = wallet;
		this.original_wallet_state = wallet.get_state();
	}
	
	@Override
	public void doit() throws No_debt_exception, Invalid_amount_exception, Insufficient_funds_exception {
		wallet.pay_off_debt_with_savings(amount);
	}

	@Override
	public void undoit() {
		wallet.savings += amount;
		wallet.debt += amount;
		wallet.set_state(original_wallet_state);
	}

	@Override
	public String description() {
		return "Pay off debt with savings";
	}

	@Override
	public double get_amount() {
		return amount;
	}

}
