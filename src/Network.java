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

	public Network(int[] layer_sizes, Activation func){
		this.inwidth = layer_sizes[0];
		this.outwidth = layer_sizes[layer_sizes.length - 1];
		this.inputs = new float[inwidth];
		this.outputs = new float[outwidth];
		this.error = new float[outwidth];
		this.error[0] = outwidth * 2;

		// build the layers
		for(int ii = 1; ii < layer_sizes.length; ++ii){
			// get the size of the previous layer.
			int prev = layer_sizes[ii-1];
			this.addlayer(new Layer(prev, layer_sizes[ii], 0.1f, func));
		}
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

	public void train(float[] inputs, float[] expected, float rate){
		evaluate(inputs);

		float[] back = new float[error.length];
		for( int ii = 0; ii < expected.length; ++ii){
			error[ii] = expected[ii] - outputs[ii];
			back[ii] = error[ii] * rate;
			if( Math.abs(error[ii]) > 5.0){
				System.out.println(Arrays.toString(outputs) + ";" + Arrays.toString(error));
//				error[ii] = Math.signum(error[ii]) * 5.0f;
			}
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

		int[] layers = {2, 20, 20, 20, 20, 1};

		Network net = new Network(layers, new LeakyReLU());

		int count = 0;
		float total_error = 10000;
		do{
			total_error = 0;
			for (int jj = 0; jj < in.length; jj++) {
                net.train(in[jj], out[jj], 0.0001f); // if nans start to happen, lower the learning rate.
                total_error += net.mean_square_error();
			}
            System.out.println(total_error);
			count++;
		}while(total_error > 0.001);
		System.out.println();
		System.out.println(count);
		for (int jj = 0; jj < in.length; jj++) {
			System.out.println(Arrays.toString(net.evaluate(in[jj])));
		}
	}


}
