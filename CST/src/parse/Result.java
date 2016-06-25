package parse;

/**
 * Created by admin on 2016/6/25.
 */
public class Result {
    public String operand1;

    public String operator;
    public String operand2;
    public Result(){

    }

    public Result(String operand1, String operator, String operand2) {
        this.operand1 = operand1;
        this.operand2 = operand2;
        this.operator = operator;
    }

    public void setOperand1(String operand1) {
        this.operand1 = operand1;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public void setOperand2(String operand2) {
        this.operand2 = operand2;
    }


}
