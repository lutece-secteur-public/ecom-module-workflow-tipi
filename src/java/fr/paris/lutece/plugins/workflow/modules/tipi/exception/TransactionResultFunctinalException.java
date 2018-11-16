package fr.paris.lutece.plugins.workflow.modules.tipi.exception;

public class TransactionResultFunctinalException extends TransactionResultException
{

    /**
     * 
     */
    private static final long serialVersionUID = 6305022217521653948L;
    private String _strCode;
    
    /**
     * Constructor
     *
     * @param strMessage
     *            The error message
     * @param exception
     *            The initial exception
     */
    public TransactionResultFunctinalException( String strCode,String strMessage, Exception exception )
    {
       super( strMessage, exception );
       _strCode = strCode ;
    }

    public String getCode( )
    {
        return _strCode;
    }

  

}
