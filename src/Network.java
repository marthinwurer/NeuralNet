import java.util.ArrayList;
import java.util.Arrays;

/**
 * A network consists of multiple layers
 */
public class Network {

	ArrayList<Layer> layers = new ArrayList<>();

	int inwidth, outwidth;
	float[] outputs;
	float[] inputs;
	float[] error;

	public Network(int inwidth, int outwidth){
		this.inwidth = inwidth;
		this.outwidth = outwidth;
		this.inputs = new float[inwidth];
		this.outputs = new float[outwidth];
		this.error = new float[outwidth];
		this.error[0] = outwidth * 2;
	}

	public Network addlayer(Layer toadd){
		// check whether the layer will work with the previous one.

		if( layers.size() > 0) {
			if (layers.get(layers.size() - 1).outsize != toadd.insize) {
				throw new IllegalArgumentException();
			}
		}
		else{
			if (toadd.insize != inwidth){
				throw new IllegalArgumentException();
			}
		}

		layers.add(toadd);

		return this;
	}

	public float[] evaluate(float[] inputs){

		// store the inputs
		this.inputs = Arrays.copyOf(inputs,inputs.length);
		float[] next = inputs;

		for(Layer l : layers){
			next = l.evaluate(next);
		}

		this.outputs = Arrays.copyOf(next,next.length);
		return outputs;
	}

	public void train(float[] inputs, float[] expected, float weight){
		evaluate(inputs);

		float[] back = new float[error.length];
		for( int ii = 0; ii < expected.length; ++ii){
			error[ii] = expected[ii] - outputs[ii];
			back[ii] = error[ii] * weight;
		}

		for ( int ii = layers.size() - 1; ii > 0; --ii){
			back = layers.get(ii).backpropagate(back);
		}



	}


	public float mean_square_error(){
		float mse = 0;
		for( int ii = 0; ii < error.length; ++ii){
			mse += error[ii] * error[ii];
		}

		return mse / error.length;

	}


	public static void main(String args[]){
		float[][] in = {{0.0f, 0.0f}, {0.0f, 1.0f},{1.0f, 0.0f},{1.0f, 1.0f}};
		float[][] out = {{1.0f},{0.0f},{0.0f},{1.0f}};

		Network net = new Network(2, 1);
		net.addlayer(new Layer(2, 4, 0.1f, new LeakyReLU()));
		net.addlayer(new Layer(4, 4, 0.1f, new LeakyReLU()));
		net.addlayer(new Layer(4, 1, 0.1f, new LeakyReLU()));

//		for (int ii = 0; ii < 20000; ii++){
		int count = 0;
		while(net.mean_square_error() > 0.001){
			for (int jj = 0; jj < in.length; jj++) {
                net.train(in[jj], out[jj], 0.1f);
			}
            System.out.println(net.mean_square_error());
			count++;
		}
		System.out.println();
		System.out.println(count);
		for (int jj = 0; jj < in.length; jj++) {
			System.out.println(Arrays.toString(net.evaluate(in[jj])));
		}
	}


}
