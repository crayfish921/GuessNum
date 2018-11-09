import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Random;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static Random random = new Random();

    public static void main(String[] args) {
        int tries = 5;
        int guessRange = 50;
        int randomizedNumber = random.nextInt(guessRange) + 1;
        int hintRegion = guessRange / 2;

        boolean hintUsed = false;
        boolean useRegularHint = true;

        String hint = randomizedNumber > hintRegion ? "Number is higher than " + hintRegion
                : "Number is lower than " + hintRegion;

        while (tries > 0) {
            System.out.println("Please enter your guess in a numbers range of 1 to " + guessRange);
            int guess;

            try {
                guess = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Input a number please");
                scanner.nextInt();
                continue;
            }

            if (guess == randomizedNumber) {
                System.out.println("Your guess was correct, congratulations you won!");
                return;
            } else {
                tries--;
                System.out.println("You were incorrect --> " + tries + " tries remaining");

                if (tries == 1 && hintUsed) {
                    hintUsed = false;
                    useRegularHint = false;
                }

                if (!hintUsed) {
                    System.out.println(tries != 1 ? "Enter Y or y in order to receive a hint"
                            : "Enter Y or y in order to receive a super hint");

                    String pressedKey = scanner.next();

                    if (pressedKey.equals("Y") || pressedKey.equals("y")) {
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
