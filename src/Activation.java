/**
 * Created by benjamin on 5/5/17.
 */
public interface Activation {
    /**
     * given input as the input, what is the output?
     * @param input
     * @return
     */
    float output(float input);

    /**
     * what is the slope at the given input?
     * @param input
     * @return
     */
    float gradient(float input);
}
