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
package fr.paris.lutece.plugins.workflow.modules.tipi.web;

import org.springframework.mock.web.MockHttpServletRequest;

import fr.paris.lutece.plugins.tipi.business.MockTipi;
import fr.paris.lutece.plugins.tipi.business.MockTipiRefDetHistory;
import fr.paris.lutece.plugins.workflow.modules.tipi.business.Tipi;
import fr.paris.lutece.plugins.workflow.modules.tipi.business.TipiRefDetHistory;
import fr.paris.lutece.plugins.workflow.modules.tipi.service.ITipiPaymentService;
import fr.paris.lutece.plugins.workflow.modules.tipi.service.ITipiWorkflowStateService;
import fr.paris.lutece.plugins.workflow.modules.tipi.service.MockTipiRefDetHistoryService;
import fr.paris.lutece.plugins.workflow.modules.tipi.service.MockTipiService;
import fr.paris.lutece.plugins.workflow.modules.tipi.service.SpyTipiServiceCaller;
import fr.paris.lutece.plugins.workflow.modules.tipi.service.SpyTipiWorkflowStateService;
import fr.paris.lutece.plugins.workflow.modules.tipi.service.TipiPaymentService;
import fr.paris.lutece.plugins.workflow.modules.tipi.service.url.SpyTipiUrlService;
import fr.paris.lutece.portal.service.message.SiteMessageException;
import junit.framework.TestCase;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TipiPaymentJspBeanTest extends TestCase
{
    private static final String PARAMETER_ID_HISTORY = "id_history";

    private MockHttpServletRequest _request;
    private SpyTipiUrlService _tipiUrlService;
    private MockTipiService _tipiService;
    private MockTipiRefDetHistoryService _tipiRefDetHistoryService;
    private SpyTipiServiceCaller _tipiServiceCaller;
    private ITipiPaymentService _tipiPaymentService;

    
    
    private TipiPaymentJspBean _jspBean;

    public void setUp( ) throws Exception
    {
        super.setUp( );

        _request = new MockHttpServletRequest( );
        _tipiUrlService = new SpyTipiUrlService( );
        _tipiService = new MockTipiService( );
        _tipiRefDetHistoryService = new MockTipiRefDetHistoryService( );
        _tipiServiceCaller = new SpyTipiServiceCaller( );
        
       
        _tipiPaymentService = new TipiPaymentService( _tipiService, _tipiRefDetHistoryService, new SpyTipiWorkflowStateService( ), _tipiServiceCaller );
        _jspBean = new TipiPaymentJspBean( _tipiUrlService, _tipiService, _tipiRefDetHistoryService, _tipiServiceCaller,_tipiPaymentService );
    }

    public void testProcessPaymentWithUnauthenticatedUrl( )
    {
        _tipiUrlService._bIsPaymentUrlAuthenticated = false;

        try
        {
            _jspBean.doProcessPayment( _request );
            fail( "Expected a SiteMessageException to be thrown" );
        }
        catch( SiteMessageException e )
        {
            // correct behavior
        }
    }

    public void testProcessPaymentWithNewPayment( ) throws SiteMessageException
    {
        TipiRefDetHistory tipiRefDetHistory = MockTipiRefDetHistory.create( );
        Tipi tipi = MockTipi.create( );
        tipi.setRefDet( tipiRefDetHistory.getRefDet( ) );
        _tipiRefDetHistoryService.create( tipiRefDetHistory );
        _tipiService.create( tipi );
        _request.addParameter( PARAMETER_ID_HISTORY, String.valueOf( tipiRefDetHistory.getIdHistory( ) ) );

        String strUrl = _jspBean.doProcessPayment( _request );

        assertThat( strUrl, is( _tipiUrlService._strGeneratedTipiUrl ) );
    }

    public void testProcessPaymentWithAlreadyPaidPayment( )
    {
        TipiRefDetHistory tipiRefDetHistory = MockTipiRefDetHistory.create( );
        Tipi tipi = MockTipi.createAcceptedPayment( );
        tipi.setRefDet( tipiRefDetHistory.getRefDet( ) );
        _tipiRefDetHistoryService.create( tipiRefDetHistory );
        _tipiService.create( tipi );
        _request.addParameter( PARAMETER_ID_HISTORY, String.valueOf( tipiRefDetHistory.getIdHistory( ) ) );
        String strIdOpBeforeProcessPayment = tipi.getIdOp( );

        try
        {
            _jspBean.doProcessPayment( _request );
            fail( "Expected a SiteMessageException to be thrown" );
        }
        catch( SiteMessageException e )
        {
            // correct behavior
        }

        assertThat( tipi.getIdOp( ), is( strIdOpBeforeProcessPayment ) );
    }

    public void testProcessPaymentWithCanceledPayment( ) throws SiteMessageException
    {
        TipiRefDetHistory tipiRefDetHistory = MockTipiRefDetHistory.create( );
        Tipi tipi = MockTipi.createCanceledPayment( );
        tipi.setRefDet( tipiRefDetHistory.getRefDet( ) );
        _tipiRefDetHistoryService.create( tipiRefDetHistory );
        _tipiService.create( tipi );
        _tipiServiceCaller._strIdOp = "testProcessPaymentWithCanceledPayment";
        _request.addParameter( PARAMETER_ID_HISTORY, String.valueOf( tipiRefDetHistory.getIdHistory( ) ) );

        String strUrl = _jspBean.doProcessPayment( _request );

        assertThat( tipi.getIdOp( ), is( "testProcessPaymentWithCanceledPayment" ) );
        assertThat( strUrl, is( _tipiUrlService._strGeneratedTipiUrl ) );
    }

    public void testProcessPaymentWithRefusedPayment( ) throws SiteMessageException
    {
        TipiRefDetHistory tipiRefDetHistory = MockTipiRefDetHistory.create( );
        Tipi tipi = MockTipi.createRefusedPayment( );
        tipi.setRefDet( tipiRefDetHistory.getRefDet( ) );
        _tipiRefDetHistoryService.create( tipiRefDetHistory );
        _tipiService.create( tipi );
        _tipiServiceCaller._strIdOp = "testProcessPaymentWithRefusedPayment";
        _request.addParameter( PARAMETER_ID_HISTORY, String.valueOf( tipiRefDetHistory.getIdHistory( ) ) );

        String strUrl = _jspBean.doProcessPayment( _request );

        assertThat( tipi.getIdOp( ), is( "testProcessPaymentWithRefusedPayment" ) );
        assertThat( strUrl, is( _tipiUrlService._strGeneratedTipiUrl ) );
    }
}
