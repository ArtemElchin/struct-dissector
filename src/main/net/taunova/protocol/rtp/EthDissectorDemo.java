package net.taunova.protocol.rtp;

import com.taunova.util.field.Fields;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import net.taunova.protocol.*;
import javolution.io.Struct;

public class EthDissectorDemo extends AbstractDissector {

    public Dissection dissect(DataInput input, Dissection d) {
        d.addProtocol(new RtcpProtocol());
 
       
 class ProtocolFields extends Struct {
     BitField destination_address= new BitField(48);
     BitField source_address= new BitField(48);
     Unsigned16 ether_type=new Unsigned16();
     
     
     
 }
        ProtocolFields objectProtocolFields=new ProtocolFields();
        byte[] b=input.get(0, 14);
       ByteBuffer buff= ByteBuffer.wrap(b);
       objectProtocolFields.setByteBuffer(buff, 0);
      
         
     
        d.addField(Fields.createLong(1, objectProtocolFields.destination_address.longValue()));  
        d.addField(Fields.createLong(2, objectProtocolFields.source_address.longValue()));
        d.addField(Fields.createInteger(3, objectProtocolFields.ether_type.get()));
        
       
        return d;
    }

    @Override
    public boolean isProtocol(DataInput input, Dissection dissection) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Dissecting ETH packet");

        Dissection dissection = new BasicDissection();
        byte[] buffer = ProtocolDataHelper.getFrameData(new File("example/default_eth_1.bin"));
        DataInput packetInput =  new ByteArrayDataInput(buffer);
        EthDissectorDemo dissectionDemo = new EthDissectorDemo();
        dissectionDemo.dissect(packetInput, dissection);
        
        System.out.println("Loaded data: " + packetInput);
        System.out.println("Decoded data: " + dissection);        
    }
}
