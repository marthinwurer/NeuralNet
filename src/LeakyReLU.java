/**
 * Created by benjamin on 5/5/17.
 */
public class LeakyReLU implements Activation{

    @Override
    public float output(float input) {
        return input > 0? input : input * 0.01f;
    }

    @Override
    public float gradient(float input) {
        return input > 0? 1 : 0.01f;
    }
}
