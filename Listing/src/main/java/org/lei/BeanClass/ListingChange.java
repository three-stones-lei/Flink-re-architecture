package org.lei.BeanClass;

/**
 * ClassName: Listing
 * Package: org.lei.BeanClass
 * Description:
 *
 * @Author Lei
 * @Create 15/4/2024 10:30 am
 * @Version 1.0
 */
public class ListingChange {
    private Derived_1 derived_1;
    private String documentType;
    private String processedAt;

    private Operation operation;
    private String eventId;
    private String listingsegment;

    public ListingChange() {
    }

    public ListingChange(Derived_1 derived_1, String documentType, String processedAt, Operation operation, String eventId, String listingsegment) {
        this.derived_1 = derived_1;
        this.documentType = documentType;
        this.processedAt = processedAt;
        this.operation = operation;
        this.eventId = eventId;
        this.listingsegment = listingsegment;
    }

    public Derived_1 getDerived_1() {
        return derived_1;
    }

    public void setDerived_1(Derived_1 derived_1) {
        this.derived_1 = derived_1;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getProcessedAt() {
        return processedAt;
    }

    public void setProcessedAt(String processedAt) {
        this.processedAt = processedAt;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getListingsegment() {
        return listingsegment;
    }

    public void setListingsegment(String listingsegment) {
        this.listingsegment = listingsegment;
    }

    @Override
    public String toString() {
        return "ListingChange{" +
                "derived_1=" + derived_1 +
                ", documentType='" + documentType + '\'' +
                ", processedAt='" + processedAt + '\'' +
                ", operation=" + operation +
                ", eventId='" + eventId + '\'' +
                ", listingsegment='" + listingsegment + '\'' +
                '}';
    }
}
