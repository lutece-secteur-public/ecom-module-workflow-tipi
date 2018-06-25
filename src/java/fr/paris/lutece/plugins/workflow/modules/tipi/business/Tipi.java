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
package fr.paris.lutece.plugins.workflow.modules.tipi.business;

/**
 * This is the business class for the object Tipi
 */
public class Tipi
{
    // Variables declarations
    private String _strRefDet;
    private int _nAmount;
    private String _strEmail;
    private String _strIdOp;
    private String _strTransactionResult;

    /**
     * Returns the RefDet
     * 
     * @return The RefDet
     */
    public String getRefDet( )
    {
        return _strRefDet;
    }

    /**
     * Sets the RefDet
     * 
     * @param strRefDet
     *            The RefDet
     */
    public void setRefDet( String strRefDet )
    {
        _strRefDet = strRefDet;
    }

    /**
     * Returns the Amount
     * 
     * @return The Amount
     */
    public int getAmount( )
    {
        return _nAmount;
    }

    /**
     * Sets the Amount
     * 
     * @param nAmount
     *            The Amount
     */
    public void setAmount( int nAmount )
    {
        _nAmount = nAmount;
    }

    /**
     * Returns the Email
     * 
     * @return The Email
     */
    public String getEmail( )
    {
        return _strEmail;
    }

    /**
     * Sets the Email
     * 
     * @param strEmail
     *            The Email
     */
    public void setEmail( String strEmail )
    {
        _strEmail = strEmail;
    }

    /**
     * Returns the IdOp
     * 
     * @return The IdOp
     */
    public String getIdOp( )
    {
        return _strIdOp;
    }

    /**
     * Sets the IdOp
     * 
     * @param strIdOp
     *            The IdOp
     */
    public void setIdOp( String strIdOp )
    {
        _strIdOp = strIdOp;
    }

    /**
     * Returns the TransactionResult
     * 
     * @return The _strTransactionResult
     */
    public String getTransactionResult( )
    {
        return _strTransactionResult;
    }

    /**
     * Sets the TransactionResult
     * 
     * @param strTransactionResult
     *            The TransactionResult
     */
    public void setTransactionResult( String strTransactionResult )
    {
        _strTransactionResult = strTransactionResult;
    }
}
