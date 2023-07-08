# MarketCompetitionGame
The Market Competition Game is intended to help simulate how merging and breaking up individual companies will affect a market's level of competition.

I was inspired to build this game after taking a Microeconomics class during my freshman year of college. I found the concept of market structures fascinating and the fact that there are numerical ways to track level of competition (Herfindahl–Hirschman index and 4-Firm Concentration Ratio) was very interesting to me. I decided to build this game to combine my passion for programming with my interest in economics.

The game begins by displaying a handful of welcome messages and subsequently asking the user to choose a unit of measurement for their market's level of competition. The options are 1.) Herfindahl–Hirschman index (HHI) or 2.) 4-Firm Concentration Ratio (CR4). The user is re-prompted via input validation if they do not enter number 1 or 2 to pick an option. After an option is picked, a market is randomly generated such as the one below and the current competition score (HHI or CR4) is displayed:

                                                          Company #1  :  60%
                                                          Company #2  :  28%
                                                          Company #3  :  5%
                                                          Company #4  :  2%
                                                          Company #5  :  2%
                                                          Company #6  :  2%
                                                          Company #7  :  1%

                                                          Current HHI: 4422

After this, the user is given 5 moves to turn the market from one level of competition to a randomly chosen one based on the HHI or CR4 (all revealed in target ranges). Their 3 options for each of the 5 moves are to 1.) merge two companies into one, 2.) break up one individual company into two separate companies (appropriate new share per company can be chosen by user), or 3.) end the round before the 5 moves are done. The user is re-prompted via input validation if they do not enter number 1, 2, or 3 to pick an option. This whole choice process is repeated 5 times, each time allowing a potential merger or break up that will alter the market's level of competition as judged by the HHI or CR4. The updated market and competition score (see above) is displayed every round. 

When the 5 moves are done or the user selects option 3, the round is ended. A message is displayed congratulating the user if they got the market to have the desired level of competition or thanking them for playing if they didn't. The user is then prompted to enter a 1 to play again or another value to quit.

Additional comments can be viewed in gameFile.java.
