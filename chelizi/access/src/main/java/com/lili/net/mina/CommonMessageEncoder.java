package com.lili.net.mina;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lili.net.CommonMessage;

public class CommonMessageEncoder extends ProtocolEncoderAdapter
{
    private static Logger LOGGER = LoggerFactory
            .getLogger(CommonMessageEncoder.class);

    public CommonMessageEncoder()
    {
    }

    /**
     * 对数据包进行二进制流编码
     */
    public void encode(IoSession session, Object message,
            ProtocolEncoderOutput out) throws Exception
    {
        CommonMessage rsp = (CommonMessage) message;
        IoBuffer buffer = IoBuffer.wrap(rsp.toByteBuffer());

        if (buffer == null)
        {
            // 丢弃这个数据包
            LOGGER.error("Drop this packet: {}.", rsp.headerToStr());
            return;
        }

        out.write(buffer);
    }
}
