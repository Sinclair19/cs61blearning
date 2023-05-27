package gh2;
import edu.princeton.cs.algs4.StdAudio;
import edu.princeton.cs.algs4.StdDraw;
import deque.*;

/**
 * A client that uses the synthesizer package to replicate a plucked guitar string sound
 */
public class GuitarHero {
    public static final String KEYBOARD = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
    public static final double BASE = 440.0;

    public static void main(String[] args) {
        /* create two guitar strings */
        ArrayDeque<GuitarString> guitarStringDeque = new ArrayDeque<>();
        for (int i = 0; i < KEYBOARD.length(); i += 1) {
            guitarStringDeque.addLast(new GuitarString(BASE * Math.pow(2, (i - 24) / 12)));
        }

        while (true) {

            /* check if the user has typed a key; if so, process it */
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                int index = KEYBOARD.indexOf(key);
                if (index != -1) {
                    GuitarString playing = guitarStringDeque.get(index);
                    playing.pluck();
                }
            }

            /* compute the superposition of samples */
            double sample = 0.0;
            for (GuitarString item : guitarStringDeque) {
                sample += item.sample();
            }


            /* play the sample on standard audio */
            StdAudio.play(sample);

            /* advance the simulation of each guitar string by one step */
            for (GuitarString item : guitarStringDeque) {
                item.tic();
            }
        }
    }
}

