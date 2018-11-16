package fr.paris.lutece.plugins.workflow.modules.tipi.business;

public class TipiRefDetIdOpHistory
{
    public TipiRefDetIdOpHistory( String strRefDet, String strIdOp )
    {

        this._strRefDet = strRefDet;
        this._strIdOp = strIdOp;
    }

    private String _strRefDet;
    private String _strIdOp;

    public String getRefDet( )
    {
        return _strRefDet;
    }

    public void setRefDet( String _strRefDet )
    {
        this._strRefDet = _strRefDet;
    }

    public String getIdOp( )
    {
        return _strIdOp;
    }

    public void setIdOp( String _strIdop )
    {
        this._strIdOp = _strIdop;
    }

}
