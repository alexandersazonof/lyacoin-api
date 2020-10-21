package com.lyacoin.api.ssh;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.lyacoin.api.exception.AppException;
import com.lyacoin.api.exception.ExceptionMessageClient;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.Properties;

@Component
public class RemoteMachine {

    public String execute(String host, String username, String password, String command) {
        try {
            String result = "";
            Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");

            JSch jsch = new JSch();

            Session session = jsch.getSession(username, host, 22);
            session.setConfig(config);
            session.setPassword(password);
            session.connect();

            Channel channel=session.openChannel("exec");
            ((ChannelExec)channel).setCommand(command);
            channel.setInputStream(null);
            ((ChannelExec)channel).setErrStream(System.err);

            InputStream in=channel.getInputStream();
            channel.connect();
            byte[] tmp=new byte[1024];
            while(true){
                while(in.available()>0){
                    int i=in.read(tmp, 0, 1024);
                    if(i<0)break;
                    result = new String(tmp, 0, i);
                }
                if (channel.isClosed()) {
                    break;
                }
                try{Thread.sleep(1000);}catch(Exception ee){}
            }
            channel.disconnect();
            session.disconnect();

            return result;
        } catch (Exception e) {

            throw new AppException(ExceptionMessageClient.INTERNAL_ERROR, "Exception during connect to remote machine :" + host);
        }
    }
}
