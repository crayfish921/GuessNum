import java.util.*;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static Random random = new Random();
    private static List<GameResult> gameResults = new ArrayList<>();

    public static void main(String[] args) {
        do {
            System.out.println("Please enter your name");

            String userName = scanner.next();

            System.out.println("Please type the numbers range you would like to make guesses in");

            int guessRange = scanner.nextInt();
            int tries = (guessRange / 10) + 1;
            Long startTime = System.currentTimeMillis();

            startGame(userName, guessRange, tries, startTime);

            System.out.println("Enter y/Y if you want to play another game");
        } while (userAgreed());

        gameResults.forEach(gameResult -> System.out.printf("%s %.2f sec %d tries remaining\n", gameResult.name, gameResult.duration / 1000.0,  gameResult.triesCount));
    }

    private static boolean userAgreed() {
        String pressedKey = scanner.next();

        return pressedKey.equals("Y") || pressedKey.equals("y");
    }

    private static int readUserNum(int guessRange) {
        int guess = 0;
        boolean validNumberEntered = false;

        while (!validNumberEntered) {
            try {
                guess = scanner.nextInt();
                if ((guess <= guessRange) && (guess > 0)) {
                    validNumberEntered = true;
                } else {
                    System.out.println("Please input the number in your specified range");
                }
            } catch (InputMismatchException e) {
                System.out.println("Input a number please");
                scanner.next();
            }
        }

        return guess;
    }

    private static void startGame(String userName, int guessRange, int tries, Long gameStartTime) {
        boolean hintUsed = false;
        boolean useRegularHint = true;
        int randomizedNumber = random.nextInt(guessRange) + 1;

        int hintRegion = guessRange / 2;

        String hint = randomizedNumber > hintRegion ? "Number is higher than " + hintRegion
                : "Number is lower than " + hintRegion;

        while (tries > 0) {
            System.out.println("Please enter your guess in a numbers range of 1 to " + guessRange);
            int guess = readUserNum(guessRange);

            if (guess == randomizedNumber) {
                System.out.println("Your guess was correct, congratulations you won!");

                Long duration = System.currentTimeMillis() - gameStartTime;

                GameResult result = new GameResult();
                result.name = userName;
                result.triesCount = tries;
                result.duration = duration;
                gameResults.add(result);

                return;
            } else {
                tries--;
                System.out.println("You were incorrect --> " + tries + " tries remaining");

                if (tries == 1 && hintUsed) {
                    hintUsed = false;
                    useRegularHint = false;
                }

                if (!hintUsed) {
                    System.out.println(tries != 1 ? "Enter Y/y in order to receive a hint"
                            : "Enter Y/y in order to receive a super hint");

                    if (userAgreed()) {
                        if (tries == 1 && !useRegularHint) {
                            System.out.println(randomizedNumber + " is your super hint :)");
                        } else {
                            System.out.println(hint);
                        }
                        hintUsed = true;
                    }
                }
            }
        }
    }
}
