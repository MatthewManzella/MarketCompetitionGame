package Game;

import java.util.*;

public class gameFile {

	public static void main(String[] args) {
		/*
		 * The user is instructed to choose mode 1 (HHI) or 2 (CR4) to play the game in.
		 * The user's response is then validated to be 1 or 2 through the modeSelector
		 * method. After this, the list of initial market share percentages are
		 * generated with the marketGenerator method and then the companies list is
		 * created so that there is one company per initial share. The market is then
		 * displayed in the form "company #X : y%" via the displayMarket method.
		 * Finally, based on the user's initially selected mode, the hhiMode or cr4Mode
		 * method is called using a conditional statement.
		 * 
		 * This is all controlled using a while loop in which the user is asked to enter
		 * a 1 to continue playing after a round ends.
		 * 
		 * 
		 * Calls: modeSelector, marketGenerator, displayMarket, hhiMode, cr4Mode, playGame, breakUp, merge
		   Called by: None
		 */
		Scanner sc = new Scanner(System.in);
		// Pre-setting playAgain to enter the loop the first time.
		String playAgain = "1";
		// While loop allows user to play another round if they enter a 1 when asked to
		// play again.
		while (playAgain.equals("1")) {
			// Introduction messages...
			System.out.println("\n\n\nWelcome to the Economic Competition Game!");
			System.out.println(
					"\nHere you will have the opportunity to manipulate markets and their competition levels.\n");
			System.out.println("You will have 5 moves to turn the market given to you into one with the specified "
					+ "level of competition based on\nmarket share percentages.");

			// Prompts the user to enter 1 or 2 to select a game mode. This response is
			// recorded by the modeSelector function
			// where the user's response is verified to be 1 or 2 (user re-prompted within
			// function if they didn't enter 1 or 2).
			System.out.println("\n1.) HHI (Herfindahl-Hirschman Index)\n2.) CR4 (4 Firm Concentration Ratio)");
			System.out.print(
					"\nSelect mode 1 or 2 to determine which criteria will be used to determine level of competition: ");
			String choice = sc.nextLine();
			String mode = modeSelector(choice, sc, false);

			// The percentage per company market shares are randomly generated in the
			// marketGenerator
			// function and stored in the shares list.
			ArrayList<Integer> shares = marketGenerator();
			// See below.
			ArrayList<String> companies = new ArrayList<>();
			// The companies list is created based on the number of shares generated in the
			// marketGenerator function(one company per share in the shares list).
			for (int i = 1; i < shares.size() + 1; i++) {
				companies.add("Company #" + i);
			}

			// Every company is displayed alongside their share in the market (each line:
			// company #X : y%)
			displayMarket(companies, shares);

			// Selects the mode based on the user's input from previously in the program.
			if (mode.equals("1")) {
				hhiMode(companies, shares, sc);
			} else {
				cr4Mode(companies, shares, sc);
			}
			// Message displayed after game to ask the player to play again.
			System.out.print("\nEnter 1 to play again. Enter any other value to quit: ");
			playAgain = sc.nextLine();
		}
		sc.close();
	}

	private static String modeSelector(String choice, Scanner sc, boolean inGame) {
		/*
		 * User's response is verified to be 1 or 2 (or 3 if in-game). The user is
		 * re-prompted if they didn't enter 1 or 2 (or 3 if in game).
		 * 
		 * Parameters: The user's choice, the scanner, and a boolean value stating if
		 * they are in the game or out of the game. - Note: This method is used for two
		 * different purposes. 1.) To select HHI or CR4 mode before the game, 2.) to
		 * select merge, break, or quit (hence the 3rd option) during the game.
		 * 
		 * Return: user's verified choice: either 1 or 2 (or 3 if in game).
		 * Calls: None
		   Called by: main, playGame
		 */
		while (!((choice.equals("1") || choice.equals("2")) || (inGame == true && choice.equals("3")))) {
			// triggers a NumberFormatException if choice != int
			if (inGame == true) {
				// See playGame note next to modeSelector. "0" designates that an attempt to merge was made with
				// only one company. Appropriate message is displayed for this case.
				if (choice.equals("0")) {
					System.out.print("\nEnter 2 to break up your company or 3 to end before your five moves are done: ");
				} else {
					System.out.print("\nERROR. Enter 1 or 2: ");
				}
			} else {
				System.out.print("\nERROR. Enter 1 or 2: ");
			}
			choice = sc.nextLine();
		}
		return choice;
	}

	private static ArrayList<Integer> marketGenerator() {
		// Randomly generates numbers 0-100 (0s disregarded) that will add up to 100 in
		// the end. These number represent the percent shares that companies will hold
		// in the market as displayed later on.

		// Return: shares, an arrayList of all percent shares generated in this function
		// in descending order.
		// Calls: None
		// Called by: main
		//
		
		ArrayList<Integer> shares = new ArrayList<>();
		int sum = 0;
		int randint = 0;
		int max = 0;
		int loop_num = 0;
		// A random integer out of 1, 2, and 3 is stored in rand_selector. This number
		// determines the seeding of how spread
		// apart the initial market will be (3 = companies with big shares. 1 = lots of
		// companies with small shares).
		int rand_selector = (int) Math.floor(Math.random() * 3) + 1;
		// max = highest number that can be generated up until loop_num is greater than
		// or equal to the sum of previous numbers generated.
		switch (rand_selector) {
		case (1):
			loop_num = 75;
			max = 10;
			break;
		case (2):
			loop_num = 60;
			max = 20;
			break;
		// max for case 3 = 100-sum (no limit). This is stated within the loop so that
		// it
		// is consistently updated.
		case (3):
			loop_num = 100;
			break;
		}
		// while the sum of all random numbers generated within the loop < loop_num...
		while (sum < loop_num) {
			// max = the maximum number that can be randomly generated in the randint
			// expression below
			if (rand_selector == 3) {
				max = 100 - sum;
			}
			// Generates a number between 0-max (inclusive)
			randint = (int) Math.floor(Math.random() * (max + 1));
			// if the randint isn't 0, it is added to the cumulative sum and added to the
			// shares arrayList
			if (randint != 0) {
				sum += randint;
				shares.add(randint);
			}
		}
		// Once the while loop is exited, the amount needed to boost the sum to 100 is
		// added as a share in
		// shares (only if option 1 or 2).
		if (rand_selector != 3) {
			shares.add(100 - sum);
		}
		// The arrayList is sorted and subsequently reversed so that it is returned in
		// descending order for better
		// display later on.
		Collections.sort(shares);
		Collections.reverse(shares);
		return shares;
	}

	private static void displayMarket(ArrayList<String> companies, ArrayList<Integer> shares) {
		// Parameters: companies arrayList and shares arrayList, both to be displayed.

		// Every company is displayed alongside their share in the market (each line:
		// company # x : y%)
		// Calls: None
		// Called by: main, playGame
		//
		
		System.out.println("\n");
		for (int i = 0; i < companies.size(); i++) {
			System.out.println(companies.get(i) + "  :  " + shares.get(i) + "%");
		}
	}

	private static void hhiMode(ArrayList<String> companies, ArrayList<Integer> shares, Scanner sc) {
		/*
		 * Sets up the game before sending necessary information to playGame function.
		 * First, a list of competition levels and a list of corresponding HHI indexes
		 * for those competition levels are created. Second, the initial HHI of the
		 * market is calculated. Both the competition level and range that the initial
		 * HHI fits into are deleted from their respective lists. Then a corresponding
		 * competition level/range is randomly chosen from the list, subsequently
		 * becoming the target for the game.
		 * 
		 * Parameters: List of companies, list of shares, scanner.
		 * 
		 * Calls: hhi_calc, displayMarket, playGame, rangeFormatter, companyVerifier
		   Called by: main
		 */
		Random random = new Random();
		// List of possible competition levels.
		ArrayList<String> comp_levels = new ArrayList<>(
				Arrays.asList("low competition", "moderate competition", "high competition"));
		// List of HHI ranges corresponding with their respective competition levels in
		// the list above.
		ArrayList<String> ranges = new ArrayList<>(Arrays.asList("2501-10000", "1500-2500", "0-1499"));
		int initial_hhi = hhi_calc(shares);
		System.out.println("\nCurrent HHI: " + initial_hhi);
		// Determines the index of the level of competition of the current HHI in the
		// two lists above.
		int idx;
		if (initial_hhi > 2500) {
			idx = 0;
		} else if (1500 <= initial_hhi && initial_hhi <= 2500) {
			idx = 1;
		} else {
			idx = 2;
		}
		System.out.println("\nThis market currently has " + comp_levels.get(idx) + " based on the HHI.");
		// Removes the current state from both lists so that the target chosen isn't the
		// same as the current one.
		comp_levels.remove(idx);
		ranges.remove(idx);
		// Random competition level chosen alongside corresponding HHI range.
		int randint = random.nextInt(2);
		String targetRange = ranges.get(randint);
		String targetComp = comp_levels.get(randint);
		System.out.println("\nYou now have 5 moves to turn this market into one with " + targetComp + " (" + targetRange
				+ " HHI).");
		System.out.println(
				"\nYou must choose one of the following two options every move: \n\t1.) Merge 2 companies \n\t2.) Break "
						+ "1 single company up into 2 separate companies");
		// Game begins
		playGame(sc, companies, shares, "hhi", targetRange, targetComp);
	}

	private static void cr4Mode(ArrayList<String> companies, ArrayList<Integer> shares, Scanner sc) {
		/*
		 * Sets up the game before sending necessary information to playGame function.
		 * First, a list of competition levels and a list of corresponding CR4 amounts
		 * for those competition levels are created. Second, the initial CR4 of the
		 * market is calculated. Both the competition level and range that the initial
		 * CR4 fits into are deleted from their respective lists. Then a corresponding
		 * competition level/range is randomly chosen from the list, subsequently
		 * becoming the target for the game.
		 * 
		 * Parameters: List of companies, list of shares, scanner.
		 * 
		 * Calls: cr4_calc, displayMarket, playGame, rangeFormatter, companyVerifier
		   Called by: main
		 */

		// See hhiMode for additional comments. Format/procedures are identical.
		Random random = new Random();
		ArrayList<String> comp_levels = new ArrayList<>(
				Arrays.asList("low competition", "moderate competition", "high competition"));
		ArrayList<String> ranges = new ArrayList<>(Arrays.asList("81%-100%", "60%-80%", "0%-59%"));
		int initial_cr4 = cr4_calc(shares);
		System.out.println("\nCurrent CR4: " + initial_cr4 + "%");
		int idx;
		if (initial_cr4 > 80) {
			idx = 0;
		} else if (60 <= initial_cr4 && initial_cr4 <= 80) {
			idx = 1;
		} else {
			idx = 2;
		}
		System.out.println("\nThis market currently has " + comp_levels.get(idx) + " based on the CR4.");
		comp_levels.remove(idx);
		ranges.remove(idx);
		int randint = random.nextInt(2);
		String targetRange = ranges.get(randint);
		String targetComp = comp_levels.get(randint);
		System.out.println("\nYou now have 5 moves to turn this market into one with " + targetComp + " (" + targetRange
				+ " CR4).");
		System.out.println(
				"\nYou must choose one of the following two options every move: \n\t1.) Merge 2 companies \n\t2.) Break "
						+ "1 single company up into 2 separate companies");
		playGame(sc, companies, shares, "cr4", targetRange, targetComp);
	}

	private static void merge(ArrayList<String> companies, ArrayList<Integer> shares, Scanner sc) {
		/*
		 * Merges two companies (combines the companies to format Company #A/B, adds the
		 * shares together) as requested by the user in game.
		 * 
		 * Parameters: Companies list, shares list, scanner.
		 * 
		 * Calls: companyVerifier
		   Called by: playGame
		 */
		System.out.print("\nEnter the first company that is part of the merger: Company #");
		String identifier1 = sc.nextLine();
		// The number of the first company entered is checked to be a valid company #
		// using the companyVerifier method.
		identifier1 = companyVerifier(identifier1, companies, sc, null);
		String company1 = "Company #" + identifier1;
		System.out.print("\nEnter the second company that is part of the merger: Company #");
		String identifier2 = sc.nextLine();
		// The number of the second company entered is checked to be a valid company #
		// using the companyVerifier method.
		identifier2 = companyVerifier(identifier2, companies, sc, identifier1);
		String company2 = "Company #" + identifier2;
		// transferredShare represents the lower of the two market share percentages (to
		// be added to the higher one later on).
		int transferredShare = shares.get(companies.indexOf(company2));
		// Sets the market share of company1 to be the sum of its initial percentage and
		// transferredShare.
		shares.set(companies.indexOf(company1), shares.get(companies.indexOf(company1)) + transferredShare);
		// Sets the name of company1 to the format Company #A/B.
		companies.set(companies.indexOf(company1), company1 + "/" + identifier2);
		// Removes the remnants of company2 from the market.
		shares.remove(companies.indexOf(company2));
		companies.remove(companies.indexOf(company2));
	}

	private static String companyVerifier(String identifier, ArrayList<String> companies, Scanner sc,
			String prevIdentifier) {
		/*
		 * Verifies that the company entered by the user in the merge or break up method
		 * exists in the companies arrayList. User is re-prompted until a correct
		 * company # is entered. In the case of a merger, the code also ensures that the
		 * same company isn't entered twice.
		 * 
		 * Parameters: String of the user's input for the company #, list of companies
		 * in the market, scanner, and the string of the user's input for the first
		 * company # if the merge function is being utilized (to ensure there are no
		 * repeats).
		 * 
		 * Returns: A proper company #
		 * 
		 * Calls: None
		   Called by: merge, breakUp
		 */
		while (!companies.contains("Company #" + identifier) || (identifier.equals(prevIdentifier))) {
			// Covers for if the company is not in the companies ArrayList.
			if (!companies.contains("Company #" + identifier)) {
				System.out.print("\nERROR. Enter a company that is currently listed in the market above: Company #");
				// Covers for if the second company to be merged is the same as the first.
			} else if (identifier.equals(prevIdentifier)) {
				System.out
						.print("\nERROR. Enter a company that is different from the first one you entered: Company #");

			}
			identifier = sc.nextLine();
		}
		return identifier;
	}

	private static void breakUp(ArrayList<String> companies, ArrayList<Integer> shares, Scanner sc) {
		/*
		 * Breaks up a single company into two separate companies based on the user's
		 * input.
		 * 
		 * Parameters: List of companies in the market, list of shares in the market,
		 * scanner.
		 * 
		 * Calls: companyVerifier, shareVerifier
	       Called by: playGame
		 */
		int initial_share = 1;
		String identifier = "0";
		// Prompts the user to enter the number of a company to break up. Re-prompts the
		// user until a company with a greater market share than 1% is entered.
		while (initial_share == 1) {
			System.out.print("\nEnter the company that you would like to break up into 2: Company #");
			// companyVerifier verifies that the entered company is in the companies list.
			identifier = companyVerifier(sc.nextLine(), companies, sc, null);
			initial_share = shares.get(companies.indexOf("Company #" + identifier));
			if (initial_share == 1) {
				System.out.println("\nYou cannot break up a company with only 1% market share in this game.");
			}
		}
		String initialCompany = "Company #" + identifier;
		// Creates 2 new companies from the initial one.
		String newCompany1 = initialCompany + ".a";
		String newCompany2 = initialCompany + ".b";
		// Stores the inital companies market share.
		int maxShare = shares.get(companies.indexOf(initialCompany));
		System.out.println("\nYour new companies will be called " + newCompany1 + " and " + newCompany2);
		System.out.print("\nEnter the % total market share to be given to " + newCompany1 + " (must be less than "
				+ maxShare + "%). Enter only the number, not the \"%\": ");
		// Verifies that the user's share entered for company _.a is less than maxShare
		// and a positive integer.
		int aShare = shareVerifier(sc.nextLine(), sc, maxShare);
		// Assigns company _.a the share entered and company_.a the remaining %.
		System.out.println("\n" + newCompany1 + " will have " + aShare + "% of total market share.");
		System.out.println(newCompany2 + " now has " + (maxShare - aShare) + "% of total market share.");
		// Stores the index of the initial company so that companies a and b + their
		// shares can be inserted in the right places.
		int breakLocation = companies.indexOf(initialCompany);
		// Inserts companies a and b into companies list and their shares into shares
		// list.
		companies.set(breakLocation, newCompany1);
		shares.set(breakLocation, aShare);
		companies.add(breakLocation + 1, newCompany2);
		shares.add(breakLocation + 1, maxShare - aShare);
	}

	private static int shareVerifier(String aShare, Scanner sc, int maxShare) {
		/*
		 * Verifies that the share entered by the user in breakUp is proper (a positive
		 * integer between 0 and maxShare. User is re-prompted until criteria are met.
		 * 
		 * Parameters: User's initial input for the Company #_.a's share, scanner, and
		 * the ceiling for how high of a share you can enter.
		 * 
		 * Returns: The verified, proper share for Company #_.a (int)
		 * 
		 * Calls: None
		   Called by: breakUp
		 */
		int final_aShare = 0;
		int round_tracker = 0;
		while (!(final_aShare > 0 && final_aShare < maxShare)) {
			// Ensures that the user is not re-prompted if already prompted in playGame
			if (round_tracker != 0)
				aShare = sc.nextLine();
			round_tracker++;
			try {
				final_aShare = Integer.parseInt(aShare);
				// Returns if the share is between 0 and maxShare.
				if (!(final_aShare > 0 && final_aShare < maxShare)) {
					System.out.print("\nERROR. Please enter an integer less than " + maxShare + ": ");
				}
				// Accounts for if the user's input is not a whole number.
			} catch (NumberFormatException e) {
				System.out.print("\nERROR. Please enter an integer that is less than " + maxShare + ": ");
				final_aShare = 0;
			}
		}
		return final_aShare;
	}


	private static void playGame(Scanner sc, ArrayList<String> companies, ArrayList<Integer> shares, String mode,
			String finalRange, String targetComp) {
		/*
		 * Carries out the game play from start to finish.
		 * 
		 * Parameters: Scanner, companies list, shares list, mode chosen (hhi vs cr4),
		 * index range to target for, associated level of competition to target for.
		 * 
		 * Calls: displayMarket, hhi_calc, cr4_calc, modeSelector, merge, breakUp, rangeFormatter
		   Called by: main
		 */
		String choice;
		int finalScore;
		// 5 turns...
		for (int i = 0; i < 5; i++) {
			// Entire market is displayed (every company next to their share).
			displayMarket(companies, shares);
			// Prints current HHI or CR4.
			if (mode.equals("hhi"))
				System.out.println("\nCurrent HHI: " + hhi_calc(shares));
			else
				System.out.println("\nCurrent CR4: " + cr4_calc(shares) + "%");
			System.out.println("\n" + "MOVE #" + (i + 1) + ":");
			System.out.print("\nEnter 1 to merge or 2 to break up (enter 3 to end before your five moves are done): ");
			choice = sc.nextLine();
			// Records + verifies the user's choice to merge or break up (or end the game).
			String final_choice = modeSelector(choice, sc, true);
			// User is repeatedly asked to enter another option if they choose to merge with
			// only one company present.
			if (final_choice.equals("1") && companies.size() == 1) {
				while (final_choice.equals("1")) {
					System.out.println("\nERROR. More than 1 company must be in the market in order to merge.");
					final_choice = modeSelector("0", sc, true);
				}
			}
			// Calls appropriate procedure for each possible choice from the user.
			if (final_choice.equals("3")) {
				break;
			} else if (final_choice.equals("2")) {
				breakUp(companies, shares, sc);
			} else {
				merge(companies, shares, sc);
			}
		}
		// End of loop. Market displayed one more time.
		displayMarket(companies, shares);
		// Final score displayed.
		if (mode.equals("hhi")) {
			finalScore = hhi_calc(shares);
			System.out.println("\nYour Final HHI: " + finalScore);
			System.out.println("\nYour Target HHI Range: (" + finalRange + ")");
		} else {
			finalScore = cr4_calc(shares);
			System.out.println("\nYour Final CR4: " + finalScore + "%");
			System.out.println("\nYour Target CR4 Range: (" + finalRange + ")");
		}
		// Creates an array with the max and min of the index range that was being
		// targeted via rangeFormatter.
		int[] finalBounds = rangeFormatter(mode, finalRange);
		int min = finalBounds[0];
		int max = finalBounds[1];
		// Displays appropriate message after comparing the user's final score with the
		// target index range.
		if (min <= finalScore && max >= finalScore) {
			System.out.println("\nGreat job, you properly adjusted the market to have " + targetComp + "!");
		} else {
			System.out.println("\nAlmost! You didn't get the market to have " + targetComp + " this time.");
		}
	}

	private static int[] rangeFormatter(String mode, String range) {
		/*
		 * Takes the target range string and converts both the min and max from the
		 * range into ints that can be compared with actual values.
		 * 
		 * Parameters: Mode of the game (cr4 or hhi), range of target index.
		 * 
		 * Returns: int array of the min and max of the index range.
		 * 
		 * Calls: None
	       Called by: playGame
		 */
		String[] initialString;
		// Creates array to store max and min.
		int[] finalString = new int[2];
		// Pre-formats range string by stripping the % sign if a cr4 range.
		if (mode.equals("cr4")) {
			range = range.replace("%", "");
		}
		// Splits the range into its max and min int values stored in an array.
		initialString = range.split("-");
		finalString[0] = Integer.parseInt(initialString[0]);
		finalString[1] = Integer.parseInt(initialString[1]);
		return finalString;
	}

	private static int hhi_calc(ArrayList<Integer> shares) {
		/*
		 * Calculates HHI index score.
		 * 
		 * Parameters: Shares list.
		 * 
		 * Returns: Calculated HHI score.
		 * 
		 * Calls: None
		   Called by: hhiMode, playGame
		 */
		int hhi = 0;
		for (int i = 0; i < shares.size(); i++) {
			hhi += Math.pow(shares.get(i), 2);
		}
		return hhi;
	}

	private static int cr4_calc(ArrayList<Integer> shares) {
		/*
		 * Calculates CR4 index score.
		 * 
		 * Parameters: Shares list.
		 * 
		 * Returns: Calculated CR4 score.
		 * 
		 * Calls: None
		   Called by: cr4Mode, playGame
		 */
		int loop_limit = 0;
		int cr4 = 0;
		// Sets the loop to end after 4 rounds unless the market contains less than 4
		// companies (then loop repetitions == number of companies).
		if (shares.size() >= 4) {
			loop_limit = 4;
		} else {
			loop_limit = shares.size();
		}
		for (int i = 0; i < loop_limit; i++) {
			cr4 += shares.get(i);
		}
		return cr4;
	}
}
