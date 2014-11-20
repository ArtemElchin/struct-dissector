package net.taunova.protocol.rtp;

import com.taunova.util.field.Fields;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import net.taunova.protocol.*;
import javolution.io.Struct;

public class RtpDissectorDemo extends AbstractDissector {

    public Dissection dissect(DataInput input, Dissection d) {
        d.addProtocol(new RtcpProtocol());
 
       
 class ProtocolFields1 extends Struct {
     BitField version= new BitField(2);
     BitField padding= new BitField(1);
     BitField extension= new BitField(1);
     BitField csrc_count= new BitField(4);
     BitField marker= new BitField(1);
     BitField payload_type=new BitField(7);
     Unsigned16 sequence_number=new Unsigned16();
     Unsigned32 timestamp= new Unsigned32();
     Unsigned32 ssrc=new Unsigned32();
     Unsigned32 csrc=new Unsigned32();
     
     
 }
        ProtocolFields1 objectProtocolFields1=new ProtocolFields1();
        byte[] b=input.get(0, 16);
       ByteBuffer buff= ByteBuffer.wrap(b);
       objectProtocolFields1.setByteBuffer(buff, 0);
      
         
     
        d.addField(Fields.createByte(RtcpProtocol.PADDING, objectProtocolFields1.padding.byteValue()));  
        d.addField(Fields.createByte(RtcpProtocol.VERSION, objectProtocolFields1.version.byteValue()));
        d.addField(Fields.createByte(RtcpProtocol.RC, objectProtocolFields1.extension.byteValue()));
        d.addField(Fields.createByte(RtcpProtocol.PT, objectProtocolFields1.csrc_count.byteValue()));
        d.addField(Fields.createByte(RtcpProtocol.LENGTH, objectProtocolFields1.marker.byteValue()));
        d.addField(Fields.createByte(RtcpProtocol.TSTAMP_NTP, objectProtocolFields1.payload_type.byteValue()));
        d.addField(Fields.createInteger(RtcpProtocol.TSTAMP_RTP, objectProtocolFields1.sequence_number.get()));
        d.addField(Fields.createLong(RtcpProtocol.PCOUNT, objectProtocolFields1.timestamp.get()));
        d.addField(Fields.createLong(RtcpProtocol.OCOUNT, objectProtocolFields1.ssrc.get()));
        d.addField(Fields.createLong(RtcpProtocol.SSRC, objectProtocolFields1.csrc.get()));
       
        return d;
    }

    @Override
    public boolean isProtocol(DataInput input, Dissection dissection) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Dissecting RTP packet");

        Dissection dissection = new BasicDissection();
        byte[] buffer = ProtocolDataHelper.getFrameData(new File("/example/rtp_default_1.bin"));
        DataInput packetInput =  new ByteArrayDataInput(buffer);
        RtpDissectorDemo dissectionDemo = new RtpDissectorDemo();
        dissectionDemo.dissect(packetInput, dissection);
        
        System.out.println("Loaded data: " + packetInput);
        System.out.println("Decoded data: " + dissection);        
    }
}
