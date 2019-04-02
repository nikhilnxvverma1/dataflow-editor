package model;

import java.util.Date;

public class DateInputNode extends DataFlowNode {
    private Date date;

    public DateInputNode(double x, double y) {
        super(x, y);
    }

    public DateInputNode(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
