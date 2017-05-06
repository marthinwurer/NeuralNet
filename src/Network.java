import java.util.ArrayList;

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
	}

	public Network addlayer(Layer toadd){
		// check whether the layer will work with the previous one.

		if (layers.get(layers.size()-1).outsize != toadd.insize){
			throw new IllegalArgumentException();
		}





		return this;
	}


}
