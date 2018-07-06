/*
 * Copyright (c) 2002-2018, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.workflow.modules.tipi.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import fr.paris.lutece.plugins.tipi.business.MockTipi;
import fr.paris.lutece.plugins.tipi.business.MockTipiRefDetHistory;
import fr.paris.lutece.plugins.tipi.business.task.MockTaskTipiConfig;
import fr.paris.lutece.plugins.workflow.modules.tipi.business.Tipi;
import fr.paris.lutece.plugins.workflow.modules.tipi.business.TipiRefDetHistory;
import fr.paris.lutece.plugins.workflow.modules.tipi.business.task.TaskTipiConfig;
import fr.paris.lutece.plugins.workflow.modules.tipi.business.task.TaskTipiConfigDAO;
import fr.paris.lutece.plugins.workflow.modules.tipi.exception.TipiNotFoundException;
import fr.paris.lutece.plugins.workflow.modules.tipi.exception.TransactionResultException;
import fr.paris.lutece.plugins.workflow.modules.tipi.util.TipiConstants;
import fr.paris.lutece.test.LuteceTestCase;
import fr.paris.vdp.tipi.create.url.enumeration.TransactionResult;

public class TipiPaymentServiceTest extends LuteceTestCase
{
    private MockTipiService _tipiService;
    private MockTipiRefDetHistoryService _tipiRefDetHistoryService;
    private SpyTipiWorkflowStateService _tipiWorkflowStateService;
    private SpyTipiServiceCaller _tipiServiceCaller;
    private TaskTipiConfigDAO _taskTipiConfigDAO;

    private TipiPaymentService _tipiPaymentService;

    public void setUp( ) throws Exception
    {
        super.setUp( );

        _tipiService = new MockTipiService( );
        _tipiRefDetHistoryService = new MockTipiRefDetHistoryService( );
        _tipiWorkflowStateService = new SpyTipiWorkflowStateService( );
        _tipiServiceCaller = new SpyTipiServiceCaller( );
        _tipiPaymentService = new TipiPaymentService( _tipiService, _tipiRefDetHistoryService, _tipiWorkflowStateService, _tipiServiceCaller );

        _taskTipiConfigDAO = new TaskTipiConfigDAO( );
    }

    public void testPaymentProcessedWithAcceptedPayment( ) throws TransactionResultException, TipiNotFoundException
    {
        TipiRefDetHistory tipiRefDetHistory = MockTipiRefDetHistory.create( );
        Tipi tipi = MockTipi.createWithIdOp( );
        tipi.setRefDet( tipiRefDetHistory.getRefDet( ) );
        TaskTipiConfig _taskTipiConfig = MockTaskTipiConfig.create( );
        _taskTipiConfig.setIdTask( tipiRefDetHistory.getIdTask( ) );
        _tipiRefDetHistoryService.create( tipiRefDetHistory );
        _tipiService.create( tipi );
        _taskTipiConfigDAO.insert( _taskTipiConfig );

        _tipiPaymentService.paymentProcessed( tipi.getIdOp( ) );

        assertThat( tipi.getTransactionResult( ), is( TransactionResult.PAYMENT_SUCCEEDED.getValueStr( ) ) );
        assertThat( _tipiWorkflowStateService._nIdState, is( _taskTipiConfig.getIdStateAfterSuccessPayment( ) ) );
    }

    public void testPaymentProcessedWithCanceledPayment( ) throws TransactionResultException, TipiNotFoundException
    {
        TipiRefDetHistory tipiRefDetHistory = MockTipiRefDetHistory.create( );
        Tipi tipi = MockTipi.createWithIdOp( );
        tipi.setRefDet( tipiRefDetHistory.getRefDet( ) );
        TaskTipiConfig _taskTipiConfig = MockTaskTipiConfig.create( );
        _taskTipiConfig.setIdTask( tipiRefDetHistory.getIdTask( ) );
        _tipiRefDetHistoryService.create( tipiRefDetHistory );
        _tipiService.create( tipi );
        _taskTipiConfigDAO.insert( _taskTipiConfig );
        _tipiServiceCaller._strTransactionResult = TipiConstants.TRANSACTION_RESULT_PAYMENT_CANCELED;

        _tipiPaymentService.paymentProcessed( tipi.getIdOp( ) );

        assertThat( tipi.getTransactionResult( ), is( TipiConstants.TRANSACTION_RESULT_PAYMENT_CANCELED ) );
        assertThat( _tipiWorkflowStateService._nIdState, is( _taskTipiConfig.getIdStateAfterCanceledPayment( ) ) );
    }

    public void testPaymentProcessedWithRefusedPayment( ) throws TransactionResultException, TipiNotFoundException
    {
        TipiRefDetHistory tipiRefDetHistory = MockTipiRefDetHistory.create( );
        Tipi tipi = MockTipi.createWithIdOp( );
        tipi.setRefDet( tipiRefDetHistory.getRefDet( ) );
        TaskTipiConfig _taskTipiConfig = MockTaskTipiConfig.create( );
        _taskTipiConfig.setIdTask( tipiRefDetHistory.getIdTask( ) );
        _tipiRefDetHistoryService.create( tipiRefDetHistory );
        _tipiService.create( tipi );
        _taskTipiConfigDAO.insert( _taskTipiConfig );
        _tipiServiceCaller._strTransactionResult = TransactionResult.PAYMENT_FAILED.getValueStr( );

        _tipiPaymentService.paymentProcessed( tipi.getIdOp( ) );

        assertThat( tipi.getTransactionResult( ), is( TransactionResult.PAYMENT_FAILED.getValueStr( ) ) );
        assertThat( _tipiWorkflowStateService._nIdState, is( _taskTipiConfig.getIdStateAfterFailurePayment( ) ) );
    }

    public void testPaymentProcessedWithUnknownTransactionResult( ) throws TransactionResultException, TipiNotFoundException
    {
        TipiRefDetHistory tipiRefDetHistory = MockTipiRefDetHistory.create( );
        Tipi tipi = MockTipi.createWithIdOp( );
        tipi.setRefDet( tipiRefDetHistory.getRefDet( ) );
        TaskTipiConfig _taskTipiConfig = MockTaskTipiConfig.create( );
        _taskTipiConfig.setIdTask( tipiRefDetHistory.getIdTask( ) );
        _tipiRefDetHistoryService.create( tipiRefDetHistory );
        _tipiService.create( tipi );
        _taskTipiConfigDAO.insert( _taskTipiConfig );
        _tipiServiceCaller._strTransactionResult = "U";

        _tipiPaymentService.paymentProcessed( tipi.getIdOp( ) );

        assertThat( tipi.getTransactionResult( ), is( "U" ) );
        assertThat( _tipiWorkflowStateService._nIdState, is( -1 ) );
    }

    public void testPaymentProcessedWithTransactionResultException( ) throws TipiNotFoundException
    {
        TipiRefDetHistory tipiRefDetHistory = MockTipiRefDetHistory.create( );
        Tipi tipi = MockTipi.createWithIdOp( );
        tipi.setRefDet( tipiRefDetHistory.getRefDet( ) );
        TaskTipiConfig _taskTipiConfig = MockTaskTipiConfig.create( );
        _taskTipiConfig.setIdTask( tipiRefDetHistory.getIdTask( ) );
        _tipiRefDetHistoryService.create( tipiRefDetHistory );
        _tipiService.create( tipi );
        _taskTipiConfigDAO.insert( _taskTipiConfig );
        _tipiServiceCaller._bMustThrowTransactionResultException = true;

        try
        {
            _tipiPaymentService.paymentProcessed( tipi.getIdOp( ) );
        }
        catch( TransactionResultException e )
        {
            // Correct behavior
        }

        assertThat( tipi.getTransactionResult( ), is( nullValue( ) ) );
        assertThat( _tipiWorkflowStateService._nIdState, is( -1 ) );
    }

    public void testPaymentProcessedWithUnknownIdOp( ) throws TransactionResultException
    {
        TipiRefDetHistory tipiRefDetHistory = MockTipiRefDetHistory.create( );
        Tipi tipi = MockTipi.createWithIdOp( );
        tipi.setRefDet( tipiRefDetHistory.getRefDet( ) );
        TaskTipiConfig _taskTipiConfig = MockTaskTipiConfig.create( );
        _taskTipiConfig.setIdTask( tipiRefDetHistory.getIdTask( ) );
        _tipiRefDetHistoryService.create( tipiRefDetHistory );
        _tipiService.create( tipi );
        _taskTipiConfigDAO.insert( _taskTipiConfig );
        _tipiServiceCaller._strTransactionResult = "U";

        try
        {
            _tipiPaymentService.paymentProcessed( "testPaymentProcessedWithUnknownIdOp" );
        }
        catch( TipiNotFoundException e )
        {
            // Correct behavior
        }

        assertThat( tipi.getTransactionResult( ), is( nullValue( ) ) );
        assertThat( _tipiWorkflowStateService._nIdState, is( -1 ) );
    }
}
