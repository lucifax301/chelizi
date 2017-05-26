/**
 * 
 */
package com.lili.command;

import com.google.protobuf.InvalidProtocolBufferException;
import com.lili.net.CommonMessage;
import com.lili.net.IClientConnection;
import com.lili.net.command.AbstractUserCmd;
import com.lili.net.command.AppUser;
import com.lili.net.command.ICode;
import com.lili.proto.ProtocolIn;
import com.lili.proto.ProtocolOut;
import com.lili.proto.java.PingProto.PingMsg;
import com.lili.proto.java.PingProto.PongMsg;

/**
 * @author linbo
 *
 */
@ICode(code = ProtocolIn.PING, desc = "心跳包")
public class PingCmd extends AbstractUserCmd
{

    /* (non-Javadoc)
     * @see com.lili.net.command.AbstractUserCmd#execute(com.lili.net.command.IAppUser, com.lili.net.CommonMessage)
     */
    @Override
    public void execute(AppUser player, CommonMessage packet)
    {
        try
        {
            PingMsg pingMsg = PingMsg.parseFrom(packet.getBody());
            long clientTimestamp = pingMsg.getClientTimestamp();
            
            CommonMessage pongMSg = new CommonMessage(ProtocolOut.USER_PING_ACK);
            PongMsg.Builder pongBuilder = PongMsg.newBuilder();
            pongBuilder.setClientTimestamp(clientTimestamp);
            pongMSg.setBody(pongBuilder.build().toByteArray());
            player.getClientConnection().send(pongMSg);
            
            if (!player.isOnline())
            {
                player.setActive(true);
            }
        }
        catch (InvalidProtocolBufferException e)
        {
            e.printStackTrace();
            logger.error("PingCmd=====", e);
        } 
    }

    @Override
    public void executeConnect(IClientConnection conn, CommonMessage packet)
    {
        try
        {
            PingMsg pingMsg = PingMsg.parseFrom(packet.getBody());
            long clientTimestamp = pingMsg.getClientTimestamp();
            
            CommonMessage pongMSg = new CommonMessage(ProtocolOut.USER_PING_ACK);
            PongMsg.Builder pongBuilder = PongMsg.newBuilder();
            pongBuilder.setClientTimestamp(clientTimestamp);
            pongMSg.setBody(pongBuilder.build().toByteArray());
            conn.send(pongMSg);
        }
        catch (InvalidProtocolBufferException e)
        {
            e.printStackTrace();
            logger.error("PingCmd=====", e);
        } 
    }
}
