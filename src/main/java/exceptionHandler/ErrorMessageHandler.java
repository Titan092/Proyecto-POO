package exceptionHandler;

public class ErrorMessageHandler {

    //Error messages
    private static final String ERRORMESSAGE = "There has been a typo error, please, try again";
    private static final String VALIDNUMBER = "The price must be a valid number.";
    private static final String VALIDCATEGORY = "Invalid category. Allowed values: MERCH, STATIONERY, CLOTHES, BOOK, ELECTRONICS";
    private static final String WRONGID="The ID must be a non-negative integer.";
    private static final String NOTFINDGID="Error, the ID entered is invalid.";
    private static final String NOSPACETICKET="There is no space available on the ticket for the number of products you wish to add. The remaining capacity is: ";
    private static final String PRODUCTNOTEXIST="The product you want to add to the ticket does not exist.";
    private static final String IDNOTEXIST="The product ID is invalid because it does not exist on the ticket.";
    private static final String FIELDERROR="Error entering the field to be updated.";
    private static final String NOMOREPRODUCTS="Error, exceeds the number of products allowed.";


    public static String getERRORMESSAGE() {
        return ERRORMESSAGE;
    }
    public static String getVALIDNUMBER() {
        return VALIDNUMBER;
    }
    public static String getVALIDCATEGORY() {
        return VALIDCATEGORY;
    }
    public static String getWRONGID() {
        return WRONGID;
    }
    public static String getNOSPACETICKET() {
        return NOSPACETICKET;
    }
    public static String getPRODUCTNOTEXIST() {
        return PRODUCTNOTEXIST;
    }
    public static String getIDNOTEXIST() {
        return IDNOTEXIST;
    }

    public static String getNOTFINDGID() {
        return NOTFINDGID;
    }

    public static String getFIELDERROR() {
        return FIELDERROR;
    }

    public static String getNOMOREPRODUCTS() {
        return NOMOREPRODUCTS;
    }

}
