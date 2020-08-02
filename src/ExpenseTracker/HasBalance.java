package ExpenseTracker;

/**
 * A Wallet state in which the balance property is higher than 80
 * @author Daniele Palazzo
 *
 */

public class HasBalance implements State {
	private Wallet wallet;
	
	/**
	 * Constructs the state with the Wallet it belongs to
	 * @param wallet The Wallet that the state belongs to
	 */
	public HasBalance(Wallet wallet) {
		this.wallet = wallet;
	}
	
	@Override
	public void add_money(double amount) throws Invalid_amount_exception {
		if (amount <= 0) { throw new Invalid_amount_exception(); }
		wallet.balance += amount;
		
	}

	@Override
	public void spend_money(double amount) throws Invalid_amount_exception {
		
		if (amount <= 0) { throw new Invalid_amount_exception(); }
		wallet.balance -= amount;

		//state transition if needed
		if (wallet.balance <= 80 && wallet.balance > 0) {
			wallet.set_state( wallet.low_balance_state());
			System.out.println("Your balance is now low.\n");
		}
		else if (wallet.balance <= 0 && wallet.savings >= Math.abs(wallet.balance)) {
			wallet.savings -= Math.abs(wallet.balance);
			wallet.balance = 0;
			wallet.set_state((wallet.savings > 0) ? wallet.has_saving_no_balance_state() : wallet.no_saving_no_balance_state());

			if (wallet.get_state() == wallet.has_saving_no_balance_state()) System.out.println("No balance left.\n");
			else System.out.println("No balance and no savings left. Spending money will result in debt.\n");
		}
		else if (wallet.balance <= 0 && wallet.savings < Math.abs(wallet.balance)) {
			wallet.balance += wallet.savings;
			wallet.savings = 0;
			wallet.debt += Math.abs(wallet.balance);
			wallet.balance = 0;
			wallet.set_state( wallet.no_saving_no_balance_state());
			System.out.println("Your debt has been increased. No balance and no savings left.\n");
		}

	}

	@Override
	public void transfer_to_savings(double amount) throws Insufficient_funds_exception,Invalid_amount_exception {
		if (amount <= 0) { throw new Invalid_amount_exception(); }

		if (amount <= wallet.balance) { wallet.balance -= amount; wallet.savings += amount; }
		else throw new Insufficient_funds_exception();

		//state transition if needed
		if (wallet.balance == 0) {
			wallet.set_state( wallet.has_saving_no_balance_state());
			System.out.println("No balance left.\n");
			return;
		}
		if (wallet.balance <= 80) {
			wallet.set_state(wallet.low_balance_state());
			System.out.println("Your balance is now low.\n");
		}
	}

	@Override
	public void pay_off_debt_with_balance(double amount) throws No_debt_exception, Invalid_amount_exception, Insufficient_funds_exception {
		if (wallet.debt == 0) { throw new No_debt_exception(); }
		if (amount <= 0) { throw new Invalid_amount_exception(); }

		if (amount <= wallet.balance && amount <=wallet.debt) { wallet.balance -= amount; wallet.debt -= amount; }
		else if (amount > wallet.balance) throw new Insufficient_funds_exception();
		else if (amount > wallet.debt) throw new Invalid_amount_exception();

		//state transition if needed
		if (wallet.balance <= 80) {
			wallet.set_state( wallet.low_balance_state());
			System.out.println("Your balance is now low.\n");
		}
		if (wallet.balance == 0 && wallet.savings > 0) {
			wallet.set_state( wallet.has_saving_no_balance_state());
			System.out.println("No balance left.\n");
		}

		if (wallet.balance == 0 && wallet.savings == 0) {
			wallet.set_state( wallet.no_saving_no_balance_state());
			System.out.println("No balance and no savings left. Spending money will result in debt.\n");
		}

	}

	@Override
	public void pay_off_debt_with_savings(double amount) throws No_debt_exception, Invalid_amount_exception, Insufficient_funds_exception {
		if (wallet.debt == 0) { throw new No_debt_exception(); }
		if (amount <= 0) { throw new Invalid_amount_exception(); }

		if (amount <= wallet.savings && amount <= wallet.debt) { wallet.savings -= amount; wallet.debt -= amount; }
		else if (amount > wallet.savings) throw new Insufficient_funds_exception();
		else if (amount > wallet.debt) throw new Invalid_amount_exception();

	}

}
