package net.taunova.protocol.rtp;

import com.taunova.util.field.Fields;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import net.taunova.protocol.*;
import javolution.io.Struct;

public class UdpDissectorDemo extends AbstractDissector {

    public Dissection dissect(DataInput input, Dissection d) {
        d.addProtocol(new RtcpProtocol());
 
       
 class ProtocolFields extends Struct {
     BitField source_port=new BitField(16);
     BitField destination_port=new BitField(16);
     BitField length=new BitField(16);
     BitField checksum=new BitField(16);
     
     
 }
        ProtocolFields objectProtocolFields=new ProtocolFields();
        byte[] b=input.get(0, 8);
       ByteBuffer buff= ByteBuffer.wrap(b);
       objectProtocolFields.setByteBuffer(buff, 0);
      
         
     
        d.addField(Fields.createShort(1, objectProtocolFields.source_port.shortValue()));  
        d.addField(Fields.createShort(2, objectProtocolFields.destination_port.shortValue()));
        d.addField(Fields.createShort(3, objectProtocolFields.length.shortValue()));
        d.addField(Fields.createShort(4, objectProtocolFields.checksum.shortValue()));
       
        return d;
    }

    @Override
    public boolean isProtocol(DataInput input, Dissection dissection) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Dissecting UDP packet");

        Dissection dissection = new BasicDissection();
        byte[] buffer = ProtocolDataHelper.getFrameData(new File("example/default_udp_1.bin"));
        DataInput packetInput =  new ByteArrayDataInput(buffer);
        UdpDissectorDemo dissectionDemo = new UdpDissectorDemo();
        dissectionDemo.dissect(packetInput, dissection);
        
        System.out.println("Loaded data: " + packetInput);
        System.out.println("Decoded data: " + dissection);        
    }
}
