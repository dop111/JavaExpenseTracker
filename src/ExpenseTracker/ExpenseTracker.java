package ExpenseTracker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
/**
 * Class containing the main method
 * @author Daniele Palazzo
 *
 */
public class ExpenseTracker {
	
	private static void initialize_from_save_file(File path) throws IOException {
		
		//wallet and account initialization info
		String user_name="";
		double balance=0;
		double savings=0;
		double debt=0;
		String history = "";
				
		BufferedReader file = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
		
		String line;
		String[] words_in_line;
		
		line = file.readLine();
		while(line != null) {
			//check each word on line
			words_in_line = line.split("\\s+");
			for (int i =0; i < words_in_line.length; i++) {
				if (words_in_line[i].equals("balance:")) {
					balance = Double.parseDouble(words_in_line[i+1]);
					break;
				}
				if (words_in_line[i].equals("savings:")) {
					savings = Double.parseDouble(words_in_line[i+1]);
					break;
				}
				if (words_in_line[i].equals("debt:")) {
					debt = Double.parseDouble(words_in_line[i+1]);
					break;
				}
				if (words_in_line[i].equals("Username:")) {
					user_name = words_in_line[i+1];
					break;
				}
				if (words_in_line[i].equals("Command")) {
					//read in previous history
					line = file.readLine();
					while(line != null) {
						history +=line + '\n';
						line = file.readLine();
					}
					
					break;
				}
			}
			line = file.readLine();
		}
		file.close();
		my_account = new Account(user_name);
		my_account.prepend_history(history);
		my_wallet = new Wallet(Math.abs(balance),Math.abs(savings),Math.abs(debt));// abs() gets rid of negative values
	}

	private static void initialize_from_input() throws IOException {
		//wallet and account initialization info
		String user_name="";
		double balance=0;
		double savings=0;
		double debt=0;
		
		System.out.println("Please enter your account name:");
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		
		user_name = input.readLine();
		
		System.out.println("Please enter your current balance:");
		while(true) {
			try {
			balance = Double.parseDouble(input.readLine());
			break;
			} catch (NumberFormatException | IOException  e) {
				System.out.println("Invalid input; please try again.\n");
			}
		}
		System.out.println("Please enter how much savings you have:");
		while(true) {
			try {
			savings = Double.parseDouble(input.readLine());
			break;
			} catch (NumberFormatException | IOException  e) {
				System.out.println("Invalid input; please try again.\n");
			}
		}
		System.out.println("Please enter how much debt you have:");
		while(true) {
			try {
			debt = Double.parseDouble(input.readLine());
			break;
			} catch (NumberFormatException | IOException  e) {
				System.out.println("Invalid input; please try again.\n");
			}
		}
		my_account = new Account(user_name);
		my_wallet = new Wallet(Math.abs(balance),Math.abs(savings),Math.abs(debt));// abs() gets rid of negative values
	}

	private static void handle_command(String command) {
		String[] command_parts = command.split("\\s+"); //parse command
		
		if (command_parts[0].equals("help")) {
			System.out.println("add_money - Adds money to your balance\n"
				+ "spend_money - Subtracts money from your account.\n"
				+ "transfer_to_savings - transfers the specified amount to your savings\n"
				+ "pay_off_debt_with_balance - pays off the specified amount of debt using your balance\n"
				+ "pay_off_debt_with_savings - pays off the specified amount of debt using your savings\n"
				+ "undo - undo last operation\n"
				+ "history - print your past activity\n"
				+ "info - print wallet info\n"
				+ "save - save all information to a file (in the same folder as executable)\n");
			return;
		}
		if (command_parts[0].equals("undo")) { 
			my_account.undo_last();
			return;
		}
		if (command_parts[0].equals("history")) {
			System.out.println("-----------------------------------");
			
			try {
				my_account.print_history(new OutputStreamWriter(System.out));
			} catch (IOException e) {
				System.out.println("Error: " + e.getMessage());
			}
			
			System.out.println("-----------------------------------\n");
			return;
		}
		if (command_parts[0].equals("info")) {
			System.out.println("-------------------------");
			
			try {
				my_wallet.print_info(new OutputStreamWriter(System.out));
			} catch (IOException e) {
				System.out.println("Error: " + e.getMessage());
			}
			
			System.out.println("-------------------------\n");
			return;
		}
		if (command_parts[0].equals("save")) {
			try {
				OutputStreamWriter file = new OutputStreamWriter(new FileOutputStream(new File("saved_expenses_data.txt")));
				my_wallet.print_info(file);
				file.write("Username: " + my_account.get_user_name() + '\n');
				my_account.print_history(file);
			} catch (IOException e) {
				System.out.println("Error: " + e.getMessage());
			}
			return;
		}
		double amount=0;
		try {
			amount = Double.parseDouble(command_parts[1]);
			} catch (NumberFormatException  e) {
				System.out.println("Invalid input\n");
				return;
			}
		
		if (command_parts[0].equals("add_money")) my_account.execute_command(new Add_money(amount, my_wallet));
		if (command_parts[0].equals("spend_money"))  my_account.execute_command(new Spend_money(amount,my_wallet));
		if (command_parts[0].equals("transfer_to_savings"))  my_account.execute_command(new Transfer_to_savings(amount,my_wallet));
		if (command_parts[0].equals("pay_off_debt_with_balance"))  my_account.execute_command(new Pay_off_debt_with_balance(amount,my_wallet));
		if (command_parts[0].equals("pay_off_debt_with_savings")) my_account.execute_command(new Pay_off_debt_with_savings(amount, my_wallet));
		
	}
	
	private static Wallet my_wallet;
	private static Account my_account;
	
	public static void main(String[] args) {
		
		System.out.println("Would you like to load previous save? (Type \"yes\" or \"no\".)");
		
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		String answer="";
		
		//read answer
		try {
			answer = input.readLine();
		} catch (IOException e2) {
			System.out.println("Error: " + e2.getMessage() + '\n');
			return; //terminate application
		}
		
		if (answer.equals("yes")) {
			try {
				initialize_from_save_file(new File("saved_expenses_data.txt"));
			
			} catch (FileNotFoundException e) {
				System.out.println("File not found.");
				//try initialize from input
				try {
					initialize_from_input();
				} catch (IOException e1) {
					System.out.println("Error: " + e1.getMessage() + '\n');
					return; //terminate application
				}
				
			} catch (IOException e) {
				System.out.println("Error: " + e.getMessage() + '\n');
				return; //terminate
			}
		} else {
			try {
				initialize_from_input();
			} catch (IOException e1) {
				System.out.println("Error: " + e1.getMessage() + '\n');
				return; //terminate application
			}
		}
	
		//main program loop
		while (true) {
			System.out.println("Please enter your command followed by the amount (type \"help\" for the commands):");
			
			String command="";
			try {
				command = input.readLine();
			} catch (IOException e1) {
				System.out.println("Invalid input\n");
				continue;
			}
			handle_command(command); //parse and execute command
		}
		
	}
	
}

