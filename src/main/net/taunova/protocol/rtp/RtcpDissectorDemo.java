package net.taunova.protocol.rtp;

import com.taunova.util.field.Fields;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import net.taunova.protocol.*;
import javolution.io.Struct;

public class RtcpDissectorDemo extends AbstractDissector {

    public Dissection dissect(DataInput input, Dissection d) {
        d.addProtocol(new RtcpProtocol());
 
       
 class ProtocolFields extends Struct {
     BitField version= new BitField(2);
     BitField padding= new BitField(1);
     BitField rc= new BitField(5);
     BitField pt= new BitField(8);
     BitField length= new BitField(16);
     Unsigned32 ssrc=new Unsigned32();
     Signed64 tstamp_ntp=new Signed64();
     BitField tstamp_rtp= new BitField(32);
     BitField pcount=new BitField(32);
     BitField ocount=new BitField(32);
     
     
 }
        ProtocolFields objectProtocolFields=new ProtocolFields();
        byte[] b=input.get(0, 28);
       ByteBuffer buff= ByteBuffer.wrap(b);
       objectProtocolFields.setByteBuffer(buff, 0);
      
         
     
        d.addField(Fields.createInteger(RtcpProtocol.PADDING, objectProtocolFields.padding.byteValue()));  
        d.addField(Fields.createInteger(RtcpProtocol.VERSION, objectProtocolFields.version.byteValue()));
        d.addField(Fields.createInteger(RtcpProtocol.RC, objectProtocolFields.rc.byteValue()));
        d.addField(Fields.createInteger(RtcpProtocol.PT, objectProtocolFields.pt.shortValue()));
        d.addField(Fields.createInteger(RtcpProtocol.LENGTH, objectProtocolFields.length.shortValue()));
        d.addField(Fields.createLong(RtcpProtocol.TSTAMP_NTP, objectProtocolFields.tstamp_ntp.get()));
        d.addField(Fields.createInteger(RtcpProtocol.TSTAMP_RTP, objectProtocolFields.tstamp_rtp.intValue()));
        d.addField(Fields.createInteger(RtcpProtocol.PCOUNT, objectProtocolFields.pcount.intValue()));
        d.addField(Fields.createInteger(RtcpProtocol.OCOUNT, objectProtocolFields.ocount.intValue()));
        d.addField(Fields.createLong(RtcpProtocol.SSRC, objectProtocolFields.ssrc.get()));
       
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
