package ExpenseTracker;

/**
 * A Wallet state in which the Wallet balance is 0 but the Wallet savings is greater than 0
 * @author Daniele Palazzo
 *
 */

public class HasSavingNoBalance implements State {
	
	private Wallet wallet;
	
	/**
	 * Constructs the state with the Wallet it belongs to
	 * @param wallet The Wallet that the state belongs to
	 */
	public HasSavingNoBalance(Wallet wallet) {
		this.wallet = wallet;
	}
	
	@Override
	public void add_money(double amount) throws Invalid_amount_exception {
		if (amount <= 0) { throw new Invalid_amount_exception(); }
		wallet.balance += amount;
		
		//state transition if needed
		if (amount > 80) wallet.set_state (wallet.has_balance_state());

	}

	@Override
	public void spend_money(double amount) throws Invalid_amount_exception {
		if (amount <= 0) { throw new Invalid_amount_exception(); }
		wallet.savings -= amount;

		//state transition if needed
		if (wallet.savings == 0) {
			wallet.set_state(wallet.no_saving_no_balance_state());
			System.out.println("No balance and no savings left. Spending money will result in debt.\n");
		}
		else if (wallet.savings < 0) {
			wallet.debt += Math.abs(wallet.savings);
			wallet.savings = 0;
			wallet.set_state( wallet.no_saving_no_balance_state());
			System.out.println("No balance and no savings left. Your debt has been increased.\n");
		}

	}

	@Override
	public void transfer_to_savings(double amount) throws Insufficient_funds_exception {
		throw new Insufficient_funds_exception();

	}

	@Override
	public void pay_off_debt_with_balance(double amount) throws Insufficient_funds_exception {
		throw new Insufficient_funds_exception();
	}

	@Override
	public void pay_off_debt_with_savings(double amount) throws No_debt_exception, Invalid_amount_exception, Insufficient_funds_exception {
		if (wallet.debt == 0) { throw new No_debt_exception(); }
		if (amount <= 0) { throw new Invalid_amount_exception(); }

		if (amount <= wallet.savings && amount <= wallet.debt) { wallet.savings -= amount; wallet.debt -= amount; }
		else if (amount > wallet.savings) throw new Insufficient_funds_exception();
		else if (amount > wallet.debt) throw new Invalid_amount_exception();

		//state transition if needed
		if (wallet.savings == 0) {
			wallet.set_state( wallet.no_saving_no_balance_state());
			System.out.println( "No balance and no savings left. Spending money will result in debt.\n");
		}

	}

}
