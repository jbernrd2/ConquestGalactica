import java.io.File;

import edu.illinois.cs.cs125.lib.zen.Zen;

/**
 * Example program written using Zen Graphics.
 *
 * @see <a href="https://github.com/cs125-illinois/Zen">Zen on GitHub</a>
 * @see <a href="https://cs125-illinois.github.io/Zen/">Zen Documentation</a>
 * @see <a href="https://cs125.cs.illinois.edu/lab/1/#zen">Lab 1 Writeup</a>
 */
public class SpriteMoveFlipBuffer {
    /**
     * This example shows how to use buffer swapping.
     *
     * Zen maintains two image buffers. The foreground buffer is currently displayed; the background
     * buffer is not. A complete image can be assembled unseen in the background buffer. The buffers
     * are then swapped and the image is then visible to the user.
     *
     * This is a very common technique in graphics stacks. First, it allows us to restart rendering
     * from a blank canvas without worrying about what might have been drawn previously.
     *
     * In addition, assembling an entire window can require a lot of computation and compositing of
     * multiple layers. For example, on Android the smartphone platform is responsible for drawing
     * the top and bottom of the display, while the application draws the middle. These two parts
     * have to be combined before the image is displayed to the user. Buffer swapping ensures that
     * users never see the screen in an incomplete state. All assembly takes place on the unseen
     * buffer.
     *
     * @see https://en.wikipedia.org/wiki/Multiple_buffering
     * @param unused unused input parameters
     */
    public static void main(final String[] unused) {

        /*
         * The X coordinate of the image that we are going to draw.
         */
        int x = 100;

        /*
         * Get the path to our sprite image.
         */
        ClassLoader classLoader = SpriteMoveFlipBuffer.class.getClassLoader();
        File spriteFile = new File(classLoader.getResource("sprite1.gif").getFile());

        /*
         * This loop will run until the user exits the program.
         */
        while (Zen.isRunning()) {

            /*
             * Draw the image into the background buffer at coordinates (x, 200).
             */
            Zen.drawImage(spriteFile.getAbsolutePath(), x, 200);

            /*
             * Swap the background and foreground buffer, so the shifted image we created above is
             * now visible.
             */
            Zen.flipBuffer();

            /*
             * Sleep for 50 milliseconds.
             */
            Zen.sleep(50);

            /*
             * Shift the image right by 5 coordinate units.
             */
            x = x + 5;
        }
    }
}
