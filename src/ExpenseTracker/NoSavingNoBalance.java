package ExpenseTracker;

/**
 * A Wallet state in which both the balance property and the savings property are 0
 * @author Daniele Palazzo
 *
 */

public class NoSavingNoBalance implements State {
	
	private Wallet wallet;
	
	/**
	 * Constructs the state with the Wallet it belongs to
	 * @param wallet The Wallet that the state belongs to
	 */
	public NoSavingNoBalance(Wallet wallet) {
		this.wallet = wallet;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void add_money(double amount) throws Invalid_amount_exception {
		if (amount <= 0) { throw new Invalid_amount_exception(); }
		wallet.balance += amount;
		
		//state transition if needed
		if (amount <= 80) {
			wallet.set_state(wallet.low_balance_state());
		}
		else wallet.set_state(wallet.has_balance_state());

		System.out.println("You have increased your balance! Woohoo!\n");

	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void spend_money(double amount) throws Invalid_amount_exception {
		if (amount <= 0) { throw new Invalid_amount_exception(); }
		wallet.debt += amount;
		System.out.println("No balance and no savings left. Your debt has been increased.\n");

	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void transfer_to_savings(double amount) throws Insufficient_funds_exception {
		throw new Insufficient_funds_exception();

	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void pay_off_debt_with_balance(double amount) throws Insufficient_funds_exception {
		throw new Insufficient_funds_exception();

	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void pay_off_debt_with_savings(double amount) throws Insufficient_funds_exception {
		throw new Insufficient_funds_exception();

	}

}
