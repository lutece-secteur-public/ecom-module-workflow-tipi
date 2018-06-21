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
package fr.paris.lutece.plugins.workflow.modules.tipi.service.url;

import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.HttpMethodBase;
import org.springframework.mock.web.MockHttpServletRequest;

import fr.paris.lutece.plugins.workflowcore.business.resource.MockResourceHistory;
import fr.paris.lutece.plugins.workflowcore.business.resource.ResourceHistory;
import fr.paris.lutece.plugins.workflowcore.service.task.ITask;
import fr.paris.lutece.plugins.workflowcore.service.task.MockTask;
import fr.paris.lutece.test.LuteceTestCase;
import fr.paris.lutece.util.signrequest.AbstractPrivateKeyAuthenticator;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.assertThat;

public class TipiUrlServiceTest extends LuteceTestCase
{
    private SpyPrivateKeyAuthenticator _requestAuthenticator;
    private TipiUrlService _tipiUrlService;
    private HttpServletRequest _request;

    public void setUp( ) throws Exception
    {
        super.setUp( );

        _requestAuthenticator = new SpyPrivateKeyAuthenticator( );
        _tipiUrlService = new TipiUrlService( _requestAuthenticator );
        _request = new MockHttpServletRequest( );
    }

    public void testBuildSignature( )
    {
        ResourceHistory resourceHistory = MockResourceHistory.create( );
        ITask task = MockTask.create( );

        String strUrl = _tipiUrlService.generatePaymentUrl( resourceHistory, task, _request );

        assertThat( strUrl, startsWith( "http" ) );
        assertThat( strUrl, containsString( "jsp/site/plugins/workflow/modules/tipi/DoProcessPayment.jsp?id_history=" + resourceHistory.getId( ) + "&id_task="
                + task.getId( ) + "&signature=" + SpyPrivateKeyAuthenticator._strSignature ) );
    }

    private static class SpyPrivateKeyAuthenticator extends AbstractPrivateKeyAuthenticator
    {
        private static final String SIGNATURE_PREFIX = "SpyPrivateKeyAuthenticator_signature";
        private static final Random _random = new Random( );
        private static String _strSignature;

        @Override
        public String buildSignature( List<String> listElements, String strTimestamp )
        {
            _strSignature = SIGNATURE_PREFIX + _random.nextInt( 50 );

            return _strSignature;
        }

        @Override
        public void authenticateRequest( HttpMethodBase arg0, List<String> arg1 )
        {
            throw new UnsupportedOperationException( );
        }

        @Override
        public boolean isRequestAuthenticated( HttpServletRequest arg0 )
        {
            throw new UnsupportedOperationException( );
        }

    }
}
