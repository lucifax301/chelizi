package com.lili.net.mina;

import java.net.InetSocketAddress;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lili.net.CommonConst;
import com.lili.net.CommonMessage;

public class StrictMessageEncoder extends ProtocolEncoderAdapter
{
    private static final Logger LOGGER = LoggerFactory
            .getLogger(StrictMessageEncoder.class);

    public StrictMessageEncoder()
    {

    }

    @Override
    public void encode(IoSession session, Object message,
            ProtocolEncoderOutput out) throws Exception
    {
        synchronized (session)
        {
            try
            {
                // 若存在不同线程给同一玩家发送数据的情况，因此加密过程需要同步处理
                CommonMessage msg = (CommonMessage) message;

                int lastCipherByte = 0;
                int[] encryptKey = getContext(session);
                byte[] plainText = msg.toByteBuffer().array();

                int length = plainText.length;
                IoBuffer cipherBuffer = IoBuffer.allocate(length);

                // 加密首字节
                lastCipherByte = (byte) ((plainText[0] ^ encryptKey[0]) & 0xff);
                cipherBuffer.put((byte) lastCipherByte);

                // 循环加密
                int keyIndex = 0;
                for (int i = 1; i < length; i++)
                {
                    keyIndex = i & 0x7;
                    encryptKey[keyIndex] = ((encryptKey[keyIndex] + lastCipherByte) ^ i) & 0xff;
                    lastCipherByte = (((plainText[i] ^ encryptKey[keyIndex]) & 0xff) + lastCipherByte) & 0xff;
                    cipherBuffer.put((byte) lastCipherByte);
                }

                if (msg.getCode() == 1)
                {
                    int[] keys = new int[] { 0xae, 0xbf, 0x56, 0x78, 0xab,
                            0xcd, 0xef, 0xf1 };
                    StrictMessageEncoder.setKey(session, keys.clone());
                    StrictMessageDecoder.setKey(session, keys.clone());
                }

                out.write(cipherBuffer.flip());
                out.flush();

                // 调试打印IP和包头
                String ip = ((InetSocketAddress) session.getRemoteAddress())
                        .getAddress().toString();
                LOGGER.debug("send: {}, {}", ip, msg.headerToStr());
            }
            catch (Exception ex)
            {
                LOGGER.error("catch error for encoding packet:", ex);
                throw ex;
            }
        }
    }

    // 获取当前加密密钥
    private int[] getContext(IoSession session)
    {
        int[] keys = (int[]) session.getAttribute(CommonConst.ENCRYPTION_KEY);
        if (keys == null)
        {
            keys = new int[] { 0xae, 0xbf, 0x56, 0x78, 0xab, 0xcd, 0xef, 0xf1 };
            // LOGGER.error("getContext keys is null, set default keys");
        }
        return keys;
    }

    public static void setKey(IoSession session, int[] key)
    {
        session.setAttribute(CommonConst.ENCRYPTION_KEY, key);
    }

    public static String toHexDump(String description, int[] dump, int start,
            int count)
    {
        String hexDump = "";
        if (description != null)
        {
            hexDump += description;
            hexDump += "\n";
        }
        int end = start + count;
        for (int i = start; i < end; i += 16)
        {
            String text = "";
            String hex = "";

            for (int j = 0; j < 16; j++)
            {
                if (j + i < end)
                {
                    int val = dump[j + i];
                    if (val < 0)
                        val = (val + 256) & 0xFF;
                    if (val < 16)
                    {
                        hex += "0" + Integer.toHexString(val) + " ";
                    }
                    else
                    {
                        hex += Integer.toHexString(val) + " ";
                    }

                    if (val >= 32 && val <= 127)
                    {
                        text += (char) val;
                    }
                    else
                    {
                        text += ".";
                    }
                }
                else
                {
                    hex += "   ";
                    text += " ";
                }
            }
            hex += "  ";
            hex += text;
            hex += '\n';
            hexDump += hex;
        }
        return hexDump;
    }

    public static String toHexDump(String description, byte[] dump, int start,
            int count)
    {
        int[] temps = new int[dump.length];
        System.arraycopy(dump, 0, temps, 0, dump.length);
        return toHexDump(description, temps, start, count);
    }
}
