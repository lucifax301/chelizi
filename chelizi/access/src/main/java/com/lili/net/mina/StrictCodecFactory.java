package com.lili.net.mina;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;

public class StrictCodecFactory implements ProtocolCodecFactory
{

    private static ProtocolEncoderAdapter ENCODER_INSTANCE;
    private static CumulativeProtocolDecoder DECODER_INSTANCE;

    public StrictCodecFactory()
    {
        ENCODER_INSTANCE = new StrictMessageEncoder();
        DECODER_INSTANCE = new StrictMessageDecoder();
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
