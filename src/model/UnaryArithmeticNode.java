package model;

public class UnaryArithmeticNode extends ArithmeticNode{

    private double secondInput = 1;

    public UnaryArithmeticNode(double x, double y) {
        super(x, y);
    }

    public double getSecondInput() {
        return secondInput;
    }

    public void setSecondInput(double secondInput) {
        this.secondInput = secondInput;
    }
}
