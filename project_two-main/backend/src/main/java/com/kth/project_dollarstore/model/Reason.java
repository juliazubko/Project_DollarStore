package com.kth.project_dollarstore.model;

/**
 * Enum representing reasons for deleting a user account.
 */
public enum Reason {
    /**
     * Did not find products the user wanted.
     */
    NOT_FOUND_PRODUCTS("Did not find products I wanted"),

    /**
     * Poor customer service experience.
     */
    POOR_CUSTOMER_SERVICE("Poor customer service experience"),

    /**
     * Found better prices elsewhere.
     */
    BETTER_PRICES("Found better prices elsewhere"),

    /**
     * Privacy concerns.
     */
    PRIVACY_CONCERNS("Privacy concerns"),

    /**
     * Other reason specified by the user.
     */
    OTHER("Other (please specify)");

    private final String description;

    Reason(String description) {
        this.description = description;
    }

    /**
     * Retrieves the description of the reason if its specified.
     * 
     * @return The description of the reason.
     */
    public String getDescription() {
        return description;
    }
}