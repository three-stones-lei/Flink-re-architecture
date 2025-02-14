package org.lei.BeanClass;

/**
 * ClassName: Operation
 * Package: org.lei.BeanClass
 * Description:
 *
 * @Author Lei
 * @Create 15/4/2024 10:42 am
 * @Version 1.0
 */
public class Operation {
    private Object delete;
    private Object put;

    public Operation() {
    }

    public Operation(Object delete, Object put) {
        this.delete = delete;
        this.put = put;
    }

    public Object getDelete() {
        return delete;
    }

    public void setDelete(Object delete) {
        this.delete = delete;
    }

    public Object getPut() {
        return put;
    }

    public void setPut(Object put) {
        this.put = put;
    }

    @Override
    public String toString() {
        return "Operation{" +
                "delete=" + delete +
                ", put=" + put +
                '}';
    }
}
