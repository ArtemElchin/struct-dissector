package net.taunova.protocol.rtp;

import com.taunova.util.field.Fields;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import net.taunova.protocol.*;
import javolution.io.Struct;

public class TcpDissectorDemo extends AbstractDissector {

    public Dissection dissect(DataInput input, Dissection d) {
        d.addProtocol(new RtcpProtocol());
 
       
 class ProtocolFields1 extends Struct {
     Unsigned16 source_port=new Unsigned16();
     Unsigned16 destination_port=new Unsigned16();
     Unsigned32 sequence_number =new Unsigned32();
     Unsigned32 acknowledgment_number=new Unsigned32();
     BitField data_offset=new BitField(4);
     BitField reserved=new BitField(3);
     BitField ns=new BitField(1);
     BitField cwr= new BitField(1);
     BitField ece=new BitField(1);
     BitField urg=new BitField(1);
     BitField ack=new BitField(1);
     BitField psh=new BitField(1);
     BitField syn=new BitField(1);
     BitField fin=new BitField(1);
     Unsigned16 window_size=new Unsigned16();
     Unsigned16 checksum=new Unsigned16();
     Unsigned16 urgent_poin=new Unsigned16();
     
     
 }
        ProtocolFields1 objectProtocolFields=new ProtocolFields1();
        byte[] b=input.get(0, 20);
       ByteBuffer buff= ByteBuffer.wrap(b);
       objectProtocolFields.setByteBuffer(buff, 0);
      
         
     
        d.addField(Fields.createInteger(1, objectProtocolFields.source_port.get()));  
        d.addField(Fields.createInteger(2, objectProtocolFields.destination_port.get()));
        d.addField(Fields.createLong(3, objectProtocolFields.sequence_number.get()));
        d.addField(Fields.createLong(4, objectProtocolFields.acknowledgment_number.get()));
        d.addField(Fields.createByte(5, objectProtocolFields.data_offset.byteValue()));
        d.addField(Fields.createByte(6, objectProtocolFields.reserved.byteValue()));
        d.addField(Fields.createByte(7, objectProtocolFields.ns.byteValue()));
        d.addField(Fields.createByte(8, objectProtocolFields.cwr.byteValue()));
        d.addField(Fields.createByte(9, objectProtocolFields.ece.byteValue()));
        d.addField(Fields.createByte(10, objectProtocolFields.urg.byteValue()));
        d.addField(Fields.createByte(11, objectProtocolFields.ack.byteValue()));
        d.addField(Fields.createByte(12, objectProtocolFields.psh.byteValue()));
        d.addField(Fields.createByte(13, objectProtocolFields.syn.byteValue()));
        d.addField(Fields.createByte(14, objectProtocolFields.fin.byteValue()));
        d.addField(Fields.createInteger(15, objectProtocolFields.window_size.get()));  
        d.addField(Fields.createInteger(16, objectProtocolFields.checksum.get()));  
        d.addField(Fields.createInteger(17, objectProtocolFields.urgent_poin.get()));  
       
        return d;
    }

    @Override
    public boolean isProtocol(DataInput input, Dissection dissection) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Dissecting TCP packet");

        Dissection dissection = new BasicDissection();
        byte[] buffer = ProtocolDataHelper.getFrameData(new File("example/default_tcp_1.bin"));
        DataInput packetInput =  new ByteArrayDataInput(buffer);
        TcpDissectorDemo dissectionDemo = new TcpDissectorDemo();
        dissectionDemo.dissect(packetInput, dissection);
        
        System.out.println("Loaded data: " + packetInput);
        System.out.println("Decoded data: " + dissection);        
    }
}
