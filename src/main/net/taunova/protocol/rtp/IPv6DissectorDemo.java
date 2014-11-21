package net.taunova.protocol.rtp;

import com.taunova.util.field.Fields;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import net.taunova.protocol.*;
import javolution.io.Struct;

public class IPv6DissectorDemo extends AbstractDissector {

    public Dissection dissect(DataInput input, Dissection d) {
        d.addProtocol(new RtcpProtocol());
 
      
 class ProtocolFields1 extends Struct {
     BitField version= new BitField(4);
     BitField traffic_class= new BitField(8);
     BitField flow_label= new BitField(20);
     Unsigned16 payload_length= new Unsigned16();
     BitField next_header= new BitField(8);
     BitField hop_limit=new BitField(8);
     Signed64 source_address_1=new Signed64();
     Signed64 source_address_2=new Signed64();
     Signed64 destination_address_1=new Signed64();
     Signed64 destination_address_2=new Signed64();
     
     
 }
        ProtocolFields1 objectProtocolFields1=new ProtocolFields1();
        byte[] b=input.get(0, 40);
       ByteBuffer buff= ByteBuffer.wrap(b);
       objectProtocolFields1.setByteBuffer(buff, 0);
      
          
        d.addField(Fields.createByte(1, objectProtocolFields1.version.byteValue()));
        d.addField(Fields.createShort(2, objectProtocolFields1.traffic_class.shortValue()));
        d.addField(Fields.createInteger(3, objectProtocolFields1.flow_label.intValue()));
        d.addField(Fields.createInteger(4, objectProtocolFields1.payload_length.get()));
        d.addField(Fields.createShort(5, objectProtocolFields1.next_header.shortValue()));
        d.addField(Fields.createShort(6, objectProtocolFields1.hop_limit.shortValue()));
        d.addField(Fields.createLong(7, objectProtocolFields1.source_address_1.get()));
        d.addField(Fields.createLong(8, objectProtocolFields1.source_address_2.get()));
        d.addField(Fields.createLong(9, objectProtocolFields1.destination_address_1.get()));
        d.addField(Fields.createLong(10, objectProtocolFields1.destination_address_2.get()));
       
        return d;
    }

    @Override
    public boolean isProtocol(DataInput input, Dissection dissection) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Dissecting IPv6 packet");

        Dissection dissection = new BasicDissection();
        byte[] buffer = ProtocolDataHelper.getFrameData(new File("/example/default_ipv6_1.bin"));
        DataInput packetInput =  new ByteArrayDataInput(buffer);
        IPv6DissectorDemo dissectionDemo = new IPv6DissectorDemo();
        dissectionDemo.dissect(packetInput, dissection);
        
        System.out.println("Loaded data: " + packetInput);
        System.out.println("Decoded data: " + dissection);        
    }
}
