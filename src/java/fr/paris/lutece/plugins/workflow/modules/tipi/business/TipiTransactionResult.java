package fr.paris.lutece.plugins.workflow.modules.tipi.business;

/**
 * 
 * TipiTransactionResult
 *
 */
public class TipiTransactionResult
{
    private String _strIdop;
    private String _strRefDet;
    private String _strTransactionResult;
    
    public TipiTransactionResult(String strIdop,String strRefDet,String strTransactionResult)
    {
        _strIdop=strIdop;
        _strRefDet=strRefDet;
        _strTransactionResult=strTransactionResult;
    }
    
    
    public String getIdop( )
    {
        return _strIdop;
    }
    public void setIdop( String _strIdop )
    {
        this._strIdop = _strIdop;
    }
    public String getRefDet( )
    {
        return _strRefDet;
    }
    public void setRefDet( String _strRefDet )
    {
        this._strRefDet = _strRefDet;
    }
    public String getTransactionResult( )
    {
        return _strTransactionResult;
    }
    public void setTransactionResult( String _strTransactionResult )
    {
        this._strTransactionResult = _strTransactionResult;
    }
    
    
  
}
