package net.taunova.protocol.rtp;

import com.taunova.util.field.Fields;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import net.taunova.protocol.*;
import javolution.io.Struct;

public class IcmpDissectorDemo extends AbstractDissector {

    public Dissection dissect(DataInput input, Dissection d) {
        d.addProtocol(new RtcpProtocol());
 
       
 class ProtocolFields extends Struct {
     BitField type= new BitField(8);
     BitField code= new BitField(8);
     Unsigned16 checksum=new Unsigned16();
     
     
     
 }
        ProtocolFields objectProtocolFields=new ProtocolFields();
        byte[] b=input.get(0, 4);
       ByteBuffer buff= ByteBuffer.wrap(b);
       objectProtocolFields.setByteBuffer(buff, 0);
      
           
     
        d.addField(Fields.createShort(1, objectProtocolFields.type.shortValue()));  
        d.addField(Fields.createShort(2, objectProtocolFields.code.shortValue()));
        d.addField(Fields.createInteger(3, objectProtocolFields.checksum.get()));
        
       
        return d;
    }

    @Override
    public boolean isProtocol(DataInput input, Dissection dissection) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Dissecting ICMP packet");

        Dissection dissection = new BasicDissection();
        byte[] buffer = ProtocolDataHelper.getFrameData(new File("example/default_icmp_1.bin"));
        DataInput packetInput =  new ByteArrayDataInput(buffer);
        IcmpDissectorDemo dissectionDemo = new IcmpDissectorDemo();
        dissectionDemo.dissect(packetInput, dissection);
        
        System.out.println("Loaded data: " + packetInput);
        System.out.println("Decoded data: " + dissection);        
    }
}
