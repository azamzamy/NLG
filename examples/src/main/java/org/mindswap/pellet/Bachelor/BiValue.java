package org.mindswap.pellet.Bachelor;

public class BiValue {

	String key;
	String value;

	public BiValue(){
		
	}
	
	public BiValue(String k, String v){
		key = k;
		value = v;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "BiValue [key=" + key + ", value=" + value + "]";
	}

	
}
