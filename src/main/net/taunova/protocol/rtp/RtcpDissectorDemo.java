package net.taunova.protocol.rtp;

import com.taunova.util.field.Fields;
import java.io.File;
import java.io.IOException;
import net.taunova.protocol.*;

public class RtcpDissectorDemo extends AbstractDissector {

    // ------------------------------------------------------
    // Only this method should be modified
    // ------------------------------------------------------
    
    public Dissection dissect(DataInput input, Dissection d) {
        d.addProtocol(new RtcpProtocol());

        // ------------------------------------------------------
        // 1. decoding logic is to be placed here
        // ------------------------------------------------------

        // ------------------------------------------------------
        // 2. correct values should be submited instead of the default values
        // ------------------------------------------------------
        
        d.addField(Fields.createInteger(RtcpProtocol.PADDING, 1));  // 1 is a default value
        d.addField(Fields.createInteger(RtcpProtocol.VERSION, 1));
        d.addField(Fields.createInteger(RtcpProtocol.RC, 1));
        d.addField(Fields.createInteger(RtcpProtocol.PT, 1));
        d.addField(Fields.createInteger(RtcpProtocol.LENGTH, 1));
        d.addField(Fields.createInteger(RtcpProtocol.TSTAMP_NTP, 1));
        d.addField(Fields.createInteger(RtcpProtocol.TSTAMP_RTP, 1));
        d.addField(Fields.createInteger(RtcpProtocol.PCOUNT, 1));
        d.addField(Fields.createInteger(RtcpProtocol.OCOUNT, 1));
        d.addField(Fields.createInteger(RtcpProtocol.SSRC, 1));
        
        return d;
    }

    @Override
    public boolean isProtocol(DataInput input, Dissection dissection) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Dissecting RTCP packet");

        Dissection dissection = new BasicDissection();
        byte[] buffer = ProtocolDataHelper.getFrameData(new File("example/rtcp-example.data"));
        DataInput packetInput =  new ByteArrayDataInput(buffer);
        RtcpDissectorDemo dissectionDemo = new RtcpDissectorDemo();
        dissectionDemo.dissect(packetInput, dissection);

        System.out.println("Loaded data: " + packetInput);
        System.out.println("Decoded data: " + dissection);        
    }
}
