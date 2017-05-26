package com.lili.net.mina;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lili.net.CommonMessage;

public class CommonMessageDecoder extends CumulativeProtocolDecoder
{
    private static Logger LOGGER = LoggerFactory
            .getLogger(CommonMessageEncoder.class);

    public CommonMessageDecoder()
    {

    }

    /**
     * 把二进制流解码为服务器使用的数据包格式
     */
    @Override
    public boolean doDecode(IoSession session, IoBuffer in,
            ProtocolDecoderOutput out) throws Exception
    {
        if (in.remaining() < CommonMessage.HDR_SIZE)
        {
            return false;
        }

        final int positionBK = in.position();
        short headerFlag = in.getShort();
        if (CommonMessage.HEADER != headerFlag)
        {
            LOGGER.debug("Illegal client request,can not match header flag. drop a packet,close connection.");
            // session.close();
            return false;
        }

        // 长度
        int lenght = in.getShort();
        if (lenght <= 0 || lenght >= Short.MAX_VALUE)
        {
            // 非法的数据长度
            LOGGER.debug("Message Length Invalid Length = " + lenght
                    + ", drop this Message.");
            return true;
        }

        if (lenght > in.remaining() + 4)
        {
            // 数据还不够读取,等待下一次读取
            in.position(positionBK); // 复位
            return false;
        }

        byte[] pktBytes = new byte[lenght];
        in.position(positionBK); // 复位
        in.get(pktBytes);

        CommonMessage packet = CommonMessage.build(pktBytes);
        if (packet != null)
        {
            // 调试打印IP和包头
            out.write(packet);
        }
        return true;
    }
}
