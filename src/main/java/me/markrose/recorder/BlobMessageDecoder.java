package me.markrose.recorder;

import java.nio.ByteBuffer;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

public class BlobMessageDecoder implements Decoder.Binary<BlobMessage> {

	@Override
	public void init(EndpointConfig config) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public BlobMessage decode(ByteBuffer bytes) throws DecodeException {
		return new BlobMessage(bytes.array());
	}

	@Override
	public boolean willDecode(ByteBuffer bytes) {
		return bytes != null;
	}
	
}
