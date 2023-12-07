package utils;

public class ReusableMethods {

    public static void waitFor(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            System.out.println("Error occurred while waiting for " + seconds + " seconds.");
        }
    }

}