package net.taunova.protocol.rtp;

import com.taunova.util.field.Fields;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import net.taunova.protocol.*;
import javolution.io.Struct;

public class RtcpDissectorDemo extends AbstractDissector {

    // ------------------------------------------------------
    // Only this method should be modified
    // ------------------------------------------------------
    

    
    public Dissection dissect(DataInput input, Dissection d) {
        d.addProtocol(new RtcpProtocol());

        // ------------------------------------------------------
        // 1. decoding logic is to be placed here
        // ------------------------------------------------------
 class Date extends Struct {
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
        Date s=new Date();
        byte[] b=input.get(0, 28);
    
       ByteBuffer buff= ByteBuffer.wrap(b);
       s.setByteBuffer(buff, 0);
      
         
     
        // ------------------------------------------------------
        // 2. correct values should be submited instead of the default values
        // ------------------------------------------------------
        
        d.addField(Fields.createInteger(RtcpProtocol.PADDING, s.padding.byteValue()));  // 1 is a default value
        d.addField(Fields.createInteger(RtcpProtocol.VERSION, s.version.byteValue()));
        d.addField(Fields.createInteger(RtcpProtocol.RC, s.rc.byteValue()));
        d.addField(Fields.createInteger(RtcpProtocol.PT, s.pt.shortValue()));
        d.addField(Fields.createInteger(RtcpProtocol.LENGTH, s.length.shortValue()));
        d.addField(Fields.createLong(RtcpProtocol.TSTAMP_NTP, s.tstamp_ntp.get()));
        d.addField(Fields.createInteger(RtcpProtocol.TSTAMP_RTP, s.tstamp_rtp.intValue()));
        d.addField(Fields.createInteger(RtcpProtocol.PCOUNT, s.pcount.intValue()));
        d.addField(Fields.createInteger(RtcpProtocol.OCOUNT, s.ocount.intValue()));
        d.addField(Fields.createLong(RtcpProtocol.SSRC, s.ssrc.get()));
       
        return d;
    }

    @Override
    public boolean isProtocol(DataInput input, Dissection dissection) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Dissecting RTCP packet");

        Dissection dissection = new BasicDissection();
        byte[] buffer = ProtocolDataHelper.getFrameData(new File("E:/struct-dissector/example/rtcp-example.data"));
        DataInput packetInput =  new ByteArrayDataInput(buffer);
        RtcpDissectorDemo dissectionDemo = new RtcpDissectorDemo();
        dissectionDemo.dissect(packetInput, dissection);

        System.out.println("Loaded data: " + packetInput);
        System.out.println("Decoded data: " + dissection);        
    }
}
