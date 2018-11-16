package fr.paris.lutece.plugins.workflow.modules.tipi.business;

import java.util.ArrayList;
import java.util.List;

import fr.paris.lutece.util.sql.DAOUtil;

public class TipiRefDetIdOpHistoryDAO implements ITipiRefDetIdOpHistoryDAO
{

    private static final String SQL_QUERY_SELECT_ALL = "select ref_det,id_op  FROM workflow_task_tipi_refdet_idop_history ";
    private static final String SQL_QUERY_SELECT_BY_REF_DET = SQL_QUERY_SELECT_ALL + " WHERE ref_det = ? ";
    private static final String SQL_QUERY_INSERT = "INSERT INTO workflow_task_tipi_refdet_idop_history ( ref_det,id_op ) VALUES ( ?, ?) ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM workflow_task_tipi_refdet_idop_history WHERE id_op = ? ";

    @Override
    public void insert( TipiRefDetIdOpHistory tipiRefDetHistory )
    {

        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT );

        daoUtil.setString( 1, tipiRefDetHistory.getRefDet( ) );
        daoUtil.setString( 2, tipiRefDetHistory.getIdOp( ) );

        daoUtil.executeUpdate( );
        daoUtil.close( );
    }

    @Override
    public void deleteByIdop( String strIdIdop )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE );

        daoUtil.setString( 1, strIdIdop );
        daoUtil.executeUpdate( );
        daoUtil.close( );

    }

    @Override
    public List<TipiRefDetIdOpHistory> getByRefDet( String strRefDet )
    {
        List<TipiRefDetIdOpHistory> listRefDetIdopTipi = new ArrayList<>( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_BY_REF_DET );
        daoUtil.setString( 1, strRefDet );
        daoUtil.executeQuery( );
        TipiRefDetIdOpHistory tipiRefDetIdopHistory;

        while ( daoUtil.next( ) )
        {
            tipiRefDetIdopHistory=new TipiRefDetIdOpHistory( daoUtil.getString( 1 ) , daoUtil.getString( 2 ));
          
             listRefDetIdopTipi.add( tipiRefDetIdopHistory );
            
          
        }

        daoUtil.close( );

        return listRefDetIdopTipi;
    }

}
