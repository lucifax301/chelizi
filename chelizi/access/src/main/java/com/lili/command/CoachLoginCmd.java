package com.lili.command;

import com.google.protobuf.InvalidProtocolBufferException;
import com.lili.LiliAccess;
import com.lili.coach.service.CoachService;
import com.lili.net.CommonMessage;
import com.lili.net.IClientConnection;
import com.lili.net.command.AbstractUserCmd;
import com.lili.net.command.AppUser;
import com.lili.net.command.ICode;
import com.lili.proto.ProtocolIn;
import com.lili.proto.ProtocolOut;
import com.lili.proto.java.CoachLoginProto.CoachLoginAckMsg;
import com.lili.proto.java.CoachLoginProto.CoachLoginMsg;

@ICode(code = ProtocolIn.COACH_LOGIN, desc = "教练登陆")
public class CoachLoginCmd extends AbstractUserCmd
{
    @Override
    public void execute(AppUser player, CommonMessage packet)
    {

    }

    public void executeConnect(IClientConnection conn, CommonMessage packet)
    {
        try
        {
            CoachLoginMsg coachLoginMsg = CoachLoginMsg.parseFrom(packet.getBody());
            String token = coachLoginMsg.getToken();
            String phoneNum = coachLoginMsg.getPhoneNum();
            long coachId = coachLoginMsg.getCoachId();
            CoachService coachService = LiliAccess.getDubboContext().getBean(CoachService.class);

            // 登陆成功
            boolean isSuccess = true;
           // if (coachService.isExist(String.valueOf(coachId), token))
            {
                conn.setAttribute("coachId", coachId);
                AppUser appUser = new AppUser(coachId);
                conn.setHolder(appUser);
                appUser.setActive(true);
            }
//            else
//            {
//                logger.error("phoneNum:" + phoneNum + "|token:" + token + "|coachId:" + coachId + "login failed..");
//                isSuccess = false;
//            }
            sendLoginAck(conn, isSuccess);
        }
        catch (InvalidProtocolBufferException e)
        {
            logger.error("CoachLoginCmd=====", e);
            sendLoginAck(conn, false);
        }
        catch (Exception e)
        {
            logger.error("CoachLoginCmd=====", e);
            sendLoginAck(conn, false);
        }
    }

    private void sendLoginAck(IClientConnection conn, boolean result)
    {
        CommonMessage commonMessage = new CommonMessage(ProtocolOut.COACH_LOGIN_ACK);
        CoachLoginAckMsg.Builder coachLoginAckMsg = CoachLoginAckMsg.newBuilder();
        coachLoginAckMsg.setIsSuccess(result);
        commonMessage.setBody(coachLoginAckMsg.build().toByteArray());
        conn.send(commonMessage);
    }
}
