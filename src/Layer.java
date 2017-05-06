import java.util.Arrays;
import java.util.Random;

/**
 * Created by benjamin on 5/4/17.
 */
public class Layer {
    static Random rand = new Random();
    int insize, outsize;
    float[][] weights;
    float[][] exec_val;
    float[][] deltas;
    float[] outputs;
    float[] before_funct;
    float[] inputs;
    float[] error;
    float[] back;
    float bias;

    Activation function;

    public Layer(int insize, int outsize, float bias, Activation funct){
        this.insize = insize;
        this.outsize =outsize;
        this.bias = bias;
        this.weights = new float[outsize][insize];
        this.exec_val = new float[outsize][insize];
        this.deltas = new float[outsize][insize];
        this.outputs = new float[outsize];
        this.before_funct = new float[outsize];
        this.inputs = new float[insize];
        this.error = new float[outsize];
        this.back = new float[insize];
        this.function = funct;


        for (int ii = 0; ii < outsize; ii++){
            for (int jj = 0; jj < insize; jj++){
                weights[ii][jj] = rand.nextFloat();
            }
        }
    }

    public float[] evaluate(float[] in){
        for (int jj = 0; jj < insize; jj++){
            inputs[jj] = in[jj];
        }

        for (int ii = 0; ii < outsize; ii++){
            before_funct[ii] = bias;
            for (int jj = 0; jj < insize; jj++){
                before_funct[ii] += (inputs[jj] * weights[ii][jj]);

            }
            outputs[ii] = function.output(before_funct[ii]);
        }

        return outputs;
    }

    public void backpropagate(float[] in, float[] out, float factor){
        evaluate( in );
        for (int ii = 0; ii < outsize; ii++){
            error[ii] = (out[ii] - outputs[ii]) * factor;
        }

	    // apply the gradient to the error
	    for (int ii = 0; ii < outsize; ii++){
		    error[ii] = error[ii] * function.gradient(before_funct[ii]);
	    }


        for (int jj = 0; jj < insize; jj++){
            back[jj] = 0;
        }

        // calc deltas
        for (int ii = 0; ii < outsize; ii++){
            for (int jj = 0; jj < insize; jj++){
                deltas[ii][jj] = inputs[jj] * error[ii];
                back[jj] += weights[ii][jj] * error[ii];
            }
        }

        // change weights
        for (int ii = 0; ii < outsize; ii++){
            for (int jj = 0; jj < insize; jj++){
                weights[ii][jj] += deltas[ii][jj];
            }
        }

    }


    public static void main(String args[]){
        Layer l = new Layer(2, 1, 0.01f, new LeakyReLU());
        float[][] in = {{0.0f, 0.0f}, {0.0f, 1.0f},{1.0f, 0.0f},{1.0f, 1.0f}};
        float[][] out = {{0.0f},{0.0f},{0.0f},{1.0f}};

        System.out.println(Arrays.deepToString(l.weights));

        for (int ii = 0; ii < 20; ii++){
            for (int jj = 0; jj < in.length; jj++) {
                l.backpropagate(in[jj], out[jj], 0.1f);
            }
            System.out.println(Arrays.toString(l.error));
            System.out.println(Arrays.toString(l.outputs));
	        System.out.println(Arrays.toString(l.back));
        }
        for (int jj = 0; jj < in.length; jj++) {
            System.out.println(Arrays.toString(l.evaluate(in[jj])));
        }

	    System.out.println(Arrays.toString(l.back));


    }

}
