package UltimateGrapher;

public class FunctionCalc{

    private MatrixStorage storage;
    private MatrixOperation operation;

    public FunctionCalc() throws IncorrectInputException {
        storage = new MatrixStorage();
        operation = new MatrixOperation();

        while(storage.getMaxError() < errorCalc() && errorCalc() > 0.1){
            System.out.println(storage.mValue);
            System.out.println(storage.nValue +"\n");
            storage.addToThreshold();
            storage = new MatrixStorage(storage.getCoordsArray(), storage.getThreshold() + 1, storage.getMaxError());
        }
        finalOutput();
    }

    private void finalOutput(){
        System.out.println("Final Equation: ");
        double[][] temp = leastSquareMatrix();
        for(int i = 0; i < temp.length; i++){
            if(i == temp.length - 1){
                System.out.print("(" + temp[i][0] + "x^" + i + ") ");
                break;
            }
            System.out.print("(" + temp[i][0] + "x^" + i + ") + ");
        }
        System.out.println("= y");
        System.out.println("Error Value: " + errorCalc());
    }

    private double[][] leastSquareMatrix(){
        double[][] tempMatrixA = storage.getMatrixA();
        double[][] tempMatrixB = storage.getMatrixB();

        operation.print(tempMatrixA);

        double[][] tempLeft = operation.multiplyByMat(operation.transpose(tempMatrixA), tempMatrixA);
        double[][] tempRight = operation.multiplyByMat(operation.transpose(tempMatrixA), tempMatrixB);
        double[][] result = operation.multiplyByMat(operation.inverse(tempLeft), tempRight);

        return result;
    }

    private double errorCalc(){
        double[][] temp = operation.multiplyByMat(storage.getMatrixA(), leastSquareMatrix());

        double error = 0;
        double[][] tempMatrixB = storage.getMatrixB();
        for(int i = 0; i < tempMatrixB.length; i++){
            double errorTemp = (tempMatrixB[i][0] - temp[i][0]);
            error += Math.pow(errorTemp, 2);
        }
        return error;
    }

}
