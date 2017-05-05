/**
 * Created by benjamin on 5/5/17.
 */
public class ReLU implements Activation {
    @Override
    public float output(float input) {
        return input > 0? input : 0;
    }

    @Override
    public float gradient(float input) {
        return input > 0? 1 : 0;
    }
}
