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
    private static final String EXISTINGID = "The product ID is invalid, because there is already a product with the same id";
    private static final String EXISTINGIDCASH = "The cash ID is invalid, because there is already a cash with the same id";
    private static final String WRONGCASHID = "The cash ID is invalid, it must use this format: (UW+7digits)";
    private static final String WRONGDNIFORMAT = "The DNI is invalid, it must have 9 characters with the last one being a letter.";
    private static final String DNINOTEXIST = "The DNI entered does not belong to any client.";
    private static final String CASHIDNOTEXIST = "The Cash ID entered does not belong to any cash.";

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

    public static String getEXISTINGID(){return EXISTINGID;}

    public static String getEXISTINGIDCASH(){return EXISTINGIDCASH;}

    public static String getWRONGCASHID(){return WRONGCASHID;}

    public static String getWRONGDNIFORMAT(){return WRONGDNIFORMAT;}

    public static String getDNINOTEXIST(){return DNINOTEXIST;}

    public static String getCASHIDNOTEXIST(){return CASHIDNOTEXIST;}


}
