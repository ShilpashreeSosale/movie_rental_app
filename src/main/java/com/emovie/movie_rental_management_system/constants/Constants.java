package com.emovie.movie_rental_management_system.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {
    //Movie codes
    public static final String REGULAR = "regular";
    public static final String CHILDRENS = "childrens";
    public static final String NEW = "new";

    //Constant string values
    public static final String AMOUNT_OWED_IS = "Amount owed is ";
    public static final String YOU_EARNED = "You earned ";
    public static final String FREQUENCY_POINTS = " frequent points\n";
    public static final String RENTAL_RECORD_FOR = "Rental Record for ";

    //Validation error messages
    public static final String INVALID_CUSTOMER_DATA_ERROR = "Please check if the rental movies details provided are correct.";
    public static final String INVALID_MOVIEID_ERROR = "Please provide a valid Movie ID";
    public static final String INVALID_RENT_DAYS_ERROR = "Please provide valid number of days for the rental period";
    public static final String INVALID_CUSTOMER_ID_ERROR = "Please check if the provided customer id is correct";
}
