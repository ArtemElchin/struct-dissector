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
     Struct.BitField version= new Struct.BitField(2);
     Struct.BitField padding= new Struct.BitField(1);
     Struct.BitField extension= new Struct.BitField(1);
     Struct.BitField csrc_count= new Struct.BitField(4);
     Struct.BitField marker= new Struct.BitField(1);
     Struct.BitField payload_type=new Struct.BitField(7);
     Struct.BitField sequence_number=new Struct.BitField(16);
     Struct.BitField timestamp= new Struct.BitField(32);
     Struct.BitField ssrc=new Struct.BitField(32);
     Struct.BitField csrc=new Struct.BitField(32);
     
     
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
        d.addField(Fields.createShort(RtcpProtocol.TSTAMP_RTP, objectProtocolFields1.sequence_number.shortValue()));
        d.addField(Fields.createInteger(RtcpProtocol.PCOUNT, objectProtocolFields1.timestamp.intValue()));
        d.addField(Fields.createInteger(RtcpProtocol.OCOUNT, objectProtocolFields1.ssrc.intValue()));
        d.addField(Fields.createInteger(RtcpProtocol.SSRC, objectProtocolFields1.csrc.intValue()));
       
        return d;
    }

    @Override
    public boolean isProtocol(DataInput input, Dissection dissection) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Dissecting RTP packet");

        Dissection dissection = new BasicDissection();
        byte[] buffer = ProtocolDataHelper.getFrameData(new File("E:/struct-dissector/example/rtp_default_1.bin"));
        DataInput packetInput =  new ByteArrayDataInput(buffer);
        RtcpDissectorDemo dissectionDemo = new RtcpDissectorDemo();
        dissectionDemo.dissect(packetInput, dissection);

        System.out.println("Loaded data: " + packetInput);
        System.out.println("Decoded data: " + dissection);        
    }
}
