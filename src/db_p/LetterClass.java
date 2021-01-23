package db_p;

import java.io.Serializable;

public class LetterClass implements Serializable{
	private static final long serialVersionUID = 1;
	String to_id;
	String from_id;
	
	String coment;
	String kind; // string , file, image
	
	String filename;
	
	byte[] filebyte;

	public LetterClass(String to_id, String from_id, String coment, String kind) { // ���ڿ�
		super();
		this.to_id = to_id;
		this.from_id = from_id;
		this.coment = coment;
		this.kind = kind;
	}

	public LetterClass(String to_id, String from_id, String kind, String filename, byte[] filebyte) { // ���Ͽ�
		super();
		this.to_id = to_id;
		this.from_id = from_id;
		this.kind = kind;
		this.filename = filename;
		this.filebyte = filebyte;
	}
	
	

}