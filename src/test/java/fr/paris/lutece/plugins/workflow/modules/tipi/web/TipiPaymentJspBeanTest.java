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

import fr.paris.lutece.plugins.workflow.modules.tipi.service.url.SpyTipiUrlService;
import fr.paris.lutece.portal.service.message.SiteMessageException;
import junit.framework.TestCase;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TipiPaymentJspBeanTest extends TestCase
{
    private MockHttpServletRequest _request;
    private SpyTipiUrlService _tipiUrlService;
    private TipiPaymentJspBean _jspBean;

    public void setUp( ) throws Exception
    {
        super.setUp( );

        _request = new MockHttpServletRequest( );
        _tipiUrlService = new SpyTipiUrlService( );
        _jspBean = new TipiPaymentJspBean( _tipiUrlService );
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
        String strUrl = _jspBean.doProcessPayment( _request );

        assertThat( strUrl, is( _tipiUrlService._strGeneratedTipiUrl ) );
    }

    public void testProcessPaymentWithAlreadyPaidPayment( )
    {
        // try
        // {
        // _jspBean.doProcessPayment( _request );
        // fail( "Expected a SiteMessageException to be thrown" );
        // } catch ( SiteMessageException e )
        // {
        // // correct behavior
        // }
    }

    public void testProcessPaymentWithCancelPayment( )
    {

    }

    public void testProcessPaymentWithRefusePayment( )
    {

    }
}
