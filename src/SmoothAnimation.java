public class SmoothAnimation {
    static String[] frames = {
"""
 /$$$$$$$$/$$$$$$  /$$$$$$$  /$$$$$$$$  /$$$$$$         /$$$$$$  /$$   /$$  /$$$$$$  /$$$$$$$$/$$$$$$$$/$$$$$$$   /$$$$$$ 
 | $$_____/$$__  $$| $$__  $$|_____ $$  /$$__  $$       /$$__  $$| $$  | $$ /$$__  $$|__  $$__/__  $$__/ $$__  $$ /$$__  $$
 | $$    | $$  \\ $$| $$  \\ $$     /$$/ | $$  \\ $$      | $$  \\ $$| $$  | $$| $$  \\ $$   | $$     | $$  | $$  \\ $$| $$  \\ $$
 | $$$$$ | $$  | $$| $$$$$$$/    /$$/  | $$$$$$$$      | $$  | $$| $$  | $$| $$$$$$$$   | $$     | $$  | $$$$$$$/| $$  | $$
 | $$__/ | $$  | $$| $$__  $$   /$$/   | $$__  $$      | $$  | $$| $$  | $$| $$__  $$   | $$     | $$  | $$__  $$| $$  | $$
 | $$    | $$  | $$| $$  \\ $$  /$$/    | $$  | $$      | $$/$$ $$| $$  | $$| $$  | $$   | $$     | $$  | $$  \\ $$| $$  | $$
 | $$    |  $$$$$$/| $$  | $$ /$$$$$$$$| $$  | $$      |  $$$$$$/|  $$$$$$/| $$  | $$   | $$     | $$  | $$  | $$|  $$$$$$/
|__/     \\______/ |__/  |__/|________/|__/  |__/       \\____ $$$ \\______/ |__/  |__/   |__/     |__/  |__/  |__/ \\______/ 
""",
    };


    public static void main(String[] args) {
        final int width = 40; // Screen width

        long lastTime = System.nanoTime(); // Track the last frame time
        final long frameDuration = 1_000_000_000 / 10; // Target 60 FPS
        int frame = 0;

        while (true) {
            // Calculate elapsed time
            long currentTime = System.nanoTime();
            long elapsedTime = currentTime - lastTime;

            if (elapsedTime >= frameDuration) {
                // Clear the screen
                System.out.print("\033[H\033[2J");
                System.out.flush();

                Ansi.printCentered(frames[frame % frames.length], 137, 7);

                // Update the last frame time
                lastTime = currentTime;
                frame++;
            } else {
                // Sleep for a short time to save CPU cycles
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
