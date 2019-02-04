package model;

public class UnaryArithmeticNode extends ArithmeticNode{

    private double secondInput = 1;

    @Override
    public int getNumberOfInputs() {
        return 1;
    }

    public double getSecondInput() {
        return secondInput;
    }

    public void setSecondInput(double secondInput) {
        this.secondInput = secondInput;
    }
}
