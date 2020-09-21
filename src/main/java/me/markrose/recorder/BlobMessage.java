package me.markrose.recorder;

public class BlobMessage {
	
	byte[] data;
	
	public BlobMessage(byte[] data) {
		this.data = data;
	}
	
	public byte[] getData() {
		return data;
	}
	
	public void setData(byte[] data) {
		this.data = data;
	}

}
