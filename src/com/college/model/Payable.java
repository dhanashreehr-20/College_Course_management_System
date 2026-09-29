package com.college.model;

/**
 * Requirement A.18 - Interface
 * Any entity in the system that has money flowing to/from it
 * (a Student paying fees, a Faculty member receiving salary)
 * implements this contract. This lets the service layer treat
 * very different classes uniformly through a single reference type.
 */
public interface Payable {
    /**
     * @return the amount payable for this entity
     * (outstanding fee for a Student, monthly salary for a Faculty member)
     */
    double calculatePayment();

    /**
     * @return a short human-readable label describing what the payment is for.
     */
    String paymentDescription();
}
