/**
 * Date: May 15, 2013
 * 
 * Copyright (C) 2013-2015 7Road. All rights reserved.
 */
package com.lili.net.mina;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;

/**
 * 
 * @author weihua.cui
 * 
 */

public class CommonCodecFactory implements ProtocolCodecFactory
{

    private static ProtocolEncoderAdapter ENCODER_INSTANCE;
    private static CumulativeProtocolDecoder DECODER_INSTANCE;

    public CommonCodecFactory()
    {
        ENCODER_INSTANCE = new CommonMessageEncoder();
        DECODER_INSTANCE = new CommonMessageDecoder();
    }

    @Override
    public ProtocolEncoder getEncoder(IoSession paramIoSession)
            throws Exception
    {
        return ENCODER_INSTANCE;
    }

    @Override
    public ProtocolDecoder getDecoder(IoSession paramIoSession)
            throws Exception
    {
        return DECODER_INSTANCE;
    }
}
