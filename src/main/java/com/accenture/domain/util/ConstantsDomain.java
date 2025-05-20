package com.accenture.domain.util;

public final  class ConstantsDomain {
    
    private ConstantsDomain(){
        throw new IllegalStateException("Utility class");
    }

    public static final String ERR_NAME_AND_FRANCHISE_ID_NULL = "Name and franchiseId cannot be null";
    public static final String ERR_BRANCH_ALREADY_EXISTS = "Branch name already exists";
    public static final String ERR_FRANCHISE_NOT_FOUND = "Franchise not found: ";
    public static final String ERR_NAME_NULL = "Name cannot be null";
    public static final String ERR_FRANCHISE_ALREADY_EXISTS = "Franchise already exists";
    public static final String ERR_FRANCHISE_NOT_FOUND_ID = "Franchise not found: ";
    public static final String ERR_PRODUCT_NAME_NULL = "Product name cannot be null";
    public static final String ERR_PRODUCT_ALREADY_EXISTS = "Product already exists";
    public static final String ERR_PRODUCT_NOT_FOUND = "Product not found: ";
    public static final String ERR_PRODUCT_STOCK_NEGATIVE = "Stock cannot be negative";
    public static final String ERR_BRANCH_NOT_FOUND = "Branch not found: ";
    public static final String ERR_PRODUCT_NOT_IN_BRANCH = "Product is not assigned to the branch";
    public static final String ERR_FRANCHISE_ID_NULL = "Franchise id cannot be null";
    public static final String ERR_ID_NULL = "ID cannot be null";
}
