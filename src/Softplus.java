/**
 * Created by benjamin on 5/7/17.
 */
public class Softplus implements Activation {
	@Override
	public float output(float input) {
		return (float)Math.log(1.0 + Math.exp(input));
	}

	@Override
	public float gradient(float input) {
		return (float)(1.0/( 1 +  Math.exp(-input)));
	}
}
