/**
 * 
 */
package com.lili.command;

import com.google.protobuf.InvalidProtocolBufferException;
import com.lili.LiliAccess;
import com.lili.location.service.LocationService;
import com.lili.location.vo.CoachLoc;
import com.lili.net.CommonMessage;
import com.lili.net.IClientConnection;
import com.lili.net.command.AbstractUserCmd;
import com.lili.net.command.AppUser;
import com.lili.net.command.ICode;
import com.lili.proto.ProtocolIn;
import com.lili.proto.java.CoachReportProto.CoachReportMsg;

/**
 * @author linbo
 *
 */
@ICode(code = ProtocolIn.USER_REPORT, desc = "用户数据上报")
public class CoachReportCmd extends AbstractUserCmd
{
    /* (non-Javadoc)
     * @see com.lili.net.command.AbstractUserCmd#execute(com.lili.net.command.IAppUser, com.lili.net.CommonMessage)
     */
    @Override
    public void execute(AppUser player, CommonMessage packet)
    {
        try
        {
            CoachReportMsg coachReportMsg = CoachReportMsg.parseFrom(packet.getBody());
            CoachLoc t = new CoachLoc();
            t.setCoachId(player.getUserId());
            t.setLge(coachReportMsg.getLge());
            t.setLae(coachReportMsg.getLae());
            t.setDir(coachReportMsg.getDir());
            
            LocationService locationService = (LocationService) LiliAccess.getDubboContext().getBean(LocationService.class);
            locationService.saveTeacher(t);
            logger.debug("CoachReportCmd-->execute-->saveTeacher-->"+t);
            if (!player.isOnline())
            {
                player.setActive(true);
            }
        }
        catch (InvalidProtocolBufferException e)
        {
            logger.error("CoachReportCmd=====", e);
        }
        catch (Exception e)
        {
            logger.error("CoachReportCmd=====", e);
        }
    }

    public void executeConnect(IClientConnection conn, CommonMessage packet)
    {
        try
        {
            CoachReportMsg coachReportMsg = CoachReportMsg.parseFrom(packet.getBody());
            CoachLoc t = new CoachLoc();
            t.setCoachId((long) conn.getAttribute("coachId"));
            t.setLge(coachReportMsg.getLge());
            t.setLae(coachReportMsg.getLae());
            t.setDir(coachReportMsg.getDir());
            
            LocationService locationService = (LocationService) LiliAccess.getDubboContext().getBean(LocationService.class);
            locationService.saveTeacher(t);
            logger.debug("CoachReportCmd-->executeConnect-->saveTeacher-->"+t);
        }
        catch (InvalidProtocolBufferException e)
        {
            logger.error("CoachReportCmd=====", e);
        }
        catch (Exception e)
        {
            logger.error("CoachReportCmd=====", e);
        }
    }
}
