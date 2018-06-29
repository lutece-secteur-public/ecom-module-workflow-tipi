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
package fr.paris.lutece.plugins.workflow.modules.tipi.util;

import fr.paris.lutece.plugins.workflow.modules.tipi.service.TipiPlugin;

/**
 * 
 * Constants used
 *
 */
public final class TipiConstants
{
    public static final String TRANSACTION_RESULT_PAYMENT_CANCELED = "A";

    public static final String URL_NOTIFICATION_BASE = "rest/" + TipiPlugin.PLUGIN_NAME + "/callback/";
    public static final String URL_NOTIFICATION_PAYMENT = "payment";

    // PROPERTIES
    public static final String PROPERTY_TRUSTSTORE = "tipi.ssl.truststore";
    public static final String PROPERTY_TRUSTSTORE_PASSWORD = "tipi.ssl.truststore.password";
    public static final String PROPERTY_KEYSTORE = "tipi.ssl.keystore";
    public static final String PROPERTY_KEYSTORE_PASSWORD = "tipi.ssl.keystore.password";
    public static final String PROPERTY_URLWDSL = "tipi.urlwsdl";
    public static final String PROPERTY_REFERENCE_CLIENT = "tipi.numcli";
    public static final String PROPERTY_TIPI_OBJET = "tipi.objet";
    public static final String PROPERTY_URL_NOTIF = "tipi.urlnotif";
    public static final String PROPERTY_URL_REDIRECT = "tipi.urlredirect";
    public static final String PROPERTY_PAYMENT_TYPE = "tipi.saisie";
    public static final String PROPERTY_TIPI_URL = "tipi.url";

    /**
     * 
     * Constructor
     * 
     */
    private TipiConstants( )
    {

    }

}
