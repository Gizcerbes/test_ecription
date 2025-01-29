package com.uogames.sequre;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;

/**
 * 
 * @author Andrei Naumets
 * @date 2018-09-14
 */
public class Hex implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9216501365351711411L;
	
	private final ArrayList<Byte> bytesList = new ArrayList<>();
	private long compilitTime;

	public Hex() {
		byte[] bytes = new byte[0];
		newLinkedHex(bytes);
	}

	public Hex(byte b) {
		byte[] bytes = ByteBuffer.allocate(1).put(b).array();
		newLinkedHex(bytes);
	}

	public Hex(byte... bs) {
		newLinkedHex(bs);
	}

	public Hex(Byte... bs) {
		newLinkedHex(bs);
	}

	public Hex(short sh) {
		byte[] bytes = ByteBuffer.allocate(2).putShort(sh).array();
		newLinkedHex(bytes);
	}

	public Hex(char ch) {
		byte[] bytes = ByteBuffer.allocate(2).putChar(ch).array();
		newLinkedHex(bytes);
	}

	public Hex(int i) {
		byte[] bytes = ByteBuffer.allocate(4).putInt(i).array();
		newLinkedHex(bytes);
	}

	public Hex(long l) {
		byte[] bytes = ByteBuffer.allocate(8).putLong(l).array();
		newLinkedHex(bytes);
	}

	public Hex(float f) {
		byte[] bytes = ByteBuffer.allocate(4).putFloat(f).array();
		newLinkedHex(bytes);
	}

	public Hex(double d) {
		byte[] bytes = ByteBuffer.allocate(8).putDouble(d).array();
		newLinkedHex(bytes);
	}

	public Hex(String s) {
		s = s.toUpperCase();
		while (s.contains(" ")) {
			s = s.replace(" ", "");
		}
		if (s.length() % 2 > 0) s = "0" + s;

		byte[] by = new byte[s.length() / 2];
		for (int i = 0; i < s.length() / 2; i++) {
			by[i] = (byte) (Integer.parseInt(String.valueOf(s.charAt(i * 2)) + String.valueOf(s.charAt(i * 2 + 1)), 16)
					- 256);
		}
		newLinkedHex(by);
	}

	public Hex(boolean... bits) {
		byte[] bytes = bitsToBytes(bits);
		newLinkedHex(bytes);
	}

	public Hex(Collection<Byte> bytes) {
		//bytesList = new ArrayList<>();
		bytesList.addAll(bytes);
	}

	private void newLinkedHex(final byte[] by) {
		//bytesList = new ArrayList<>();
		for (byte b : by) {
			bytesList.add(b);
		}
	}

	private void newLinkedHex(final Byte[] by) {
		//bytesList = new ArrayList<>();
		for (byte b : by) {
			bytesList.add(b);
		}
	}

	private byte[] bitsToBytes(final boolean[] bs) {
		int bl = bs.length / 8;
		if (bs.length%8 > 0) bl++;
		byte[] bt = new byte[bl]; 
		
		for (int i = 0; i < bs.length; i++) {
			int numB = bt.length - 1 - i / 8;
			if (bs[i]) bt[numB] = (byte) (bt[numB] | 1 << (i % 8));
		}
		return bt;
	}

	private boolean[] bytesToBits(Byte[] bs) {
		boolean[] bools = new boolean[bs.length * 8];
		for (int i = 0; i < bools.length; i++) {
			int b = i / 8;
			byte by = bs[bs.length - 1 - b];
			int n = i % 8;
			bools[i] = (by & 1 << n) > 0;
		}
		return bools;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < bytesList.size(); i++) {
			String s = Integer.toHexString(Byte.toUnsignedInt(bytesList.get(i)));
			if (s.length() == 2) builder.append(s);
			else builder.append("0" + s);
		}
		return builder.toString().toUpperCase();
	}

	public byte getByte() {
		return bytesList.get(0);
	}

	public byte getByte(int i) {
		return bytesList.get(i);
	}

	public byte[] getBytes() {
		byte[] bs = new byte[bytesList.size()];
		for (int i = 0; i < bs.length; i++) {
			bs[i] = bytesList.get(i);
		}
		return bs;
	}

	public boolean[] getBits() {
		Byte[] bs = new Byte[bytesList.size()];
		for (int i = 0; i < bs.length; i++) {
			bs[i] = bytesList.get(i);
		}
		return bytesToBits(bs);
	}

	public String getStringBits() {
		StringBuilder builder = new StringBuilder();
		boolean[] bs = getBits();
		for (int i = 0; i < bs.length; i++) {
			builder.insert(0, (bs[i] ? 1 : 0));
		}
		return builder.toString();
	}

	public byte[] getBytes(int start, int end) {
		end++;
		byte[] bs = new byte[end - start];
		for (int i = 0; i < bs.length; i++, start++) {
			bs[i] = bytesList.get(start);
		}
		return bs;
	}

	public short getShort() {
		return getShort(0, length() -1);
	}
	
	public short getShort(final int start,final int end){
		int res = 0;
		for (int i = 1, j = end, l = 0; i >= 0 && j >= start; i--, j--, l++) {			
			byte b = bytesList.get(j);
			int va = 0;
			if ((b & 0x8000) > 0) {				
				va = (b ^ (byte) 0xFF) << l * 8;
				va ^= 0x00FF << l * 8;
			} else {
				va = b << l * 8;
			}
			res ^= va;
		}
		return (short) res;
	}
	
	public short getInvertShort(final int start,final int end){
		int res = 0;
		for (int i = 1, j = start, l = 0; i >= 0 && j <= end; i--, j++, l++) {			
			byte b = bytesList.get(j);
			int va = 0;
			if ((b & 0x8000) > 0) {				
				va = (b ^ (byte) 0xFF) << l * 8;
				va ^= 0x00FF << l * 8;
			} else {
				va = b << l * 8;
			}
			res ^= va;
		}
		return (short) res;
	}

	public char getChar() {
		return ByteBuffer.wrap(format(1)).getChar();
	}

	public int getInt() {
		return getInt(0, length() -1);
	}
	
	public int getInt(final int start,final int end){
		int res = 0;
		int form = 3;
		for (int i = form, j = end, l = 0; i >= 0 && j >= start; i--, j--, l++) {
			byte b = bytesList.get(j);
			int va = 0;
			if ((b & 0x8000) > 0) {				
				va = (b ^ (byte) 0xFF) << l * 8;
				va ^= 0x00FF << l * 8;
			} else {
				va = b << l * 8;
			}
			res ^= va;
		}
		return res;
	}
	
	public int getInvertInt(final int start,final int end){
		int res = 0;
		int form = 3;
		for (int i = form, j = start, l = 0; i >= 0 && j <= end; i--, j++, l++) {
			byte b = bytesList.get(j);
			int va = 0;
			if ((b & 0x8000) > 0) {				
				va = (b ^ (byte) 0xFF) << l * 8;
				va ^= 0x00FF << l * 8;
			} else {
				va = b << l * 8;
			}
			res ^= va;
		}
		return res;
	}

	public long getLong() {
		return getLong(0, length() -1);
	}
	
	public long getLong(final int start,final int end){
		long res = 0;
		int form = 7;
		long l = 0;
		for (int i = form, j = end; i >= 0 && j >= start; i--, j--, l++) {
			byte b = bytesList.get(j);
			long va = 0;
			if ((b & 0x8000) > 0) {			
				va = ((long)(b ^ (byte) 0xFF)) << l * 8;
				va ^= 0x00FFL << l * 8;
			} else {
				va = (long) b << l * 8;
			}		
			res ^= va;		
		}
		return res;
	}

	public long getLong2(final int start, final int end) {
		long res = 0;
		for (int i = 7, j = end, l = 0; i >= 0 && j >= start; i--, j--, l++) {
			byte b = bytesList.get(j);
			long va = (b & 0xFFL) << (l * 8);
			res |= va;
		}
		return res;
	}

	public long getInvertLong(final int start,final int end){
		long res = 0;
		int form = 7;
		long l = 0;
		for (int i = form, j = start; i >= 0 && j <= end; i--, j++, l++) {
			byte b = bytesList.get(j);
			long va = 0;
			if ((b & 0x8000) > 0) {			
				va = ((long)(b ^ (byte) 0xFF)) << l * 8;
				va ^= 0x00FFL << l * 8;
			} else {
				va = (long) b << l * 8;
			}		
			res ^= va;		
		}
		return res;
	}

	public long getInvertLong2(final int start, final int end) {
		long res = 0;
		for (int i = 7, j = start, l = 0; i >= 0 && j <= end; i--, j++, l++) {
			byte b = bytesList.get(j);
			long va = (b & 0xFFL) << (l * 8);
			res |= va;
		}
		return res;
	}


	public float getFloat() {
		return Float.intBitsToFloat(getInt());
	}
	
	public float getFloat(int start, int end){
		return Float.intBitsToFloat(getInt(start, end));
	}
	
	public float getInvertFloat(int start, int end){
		return Float.intBitsToFloat(getInvertInt(start, end));
	}

	public double getDouble() {
		return Double.longBitsToDouble(getLong());
	}
	
	public double getDouble(int start, int end){
		return Double.longBitsToDouble(getLong(start,end));
	}
	
	public double getInvertDouble(int start,int end){
		return Double.longBitsToDouble(getInvertLong(start,end));
	}

	public Hex getHEX(int start, int end) {
		Hex hex = new Hex();
		for (int i = start; i <= end; i++) {
			hex.bytesList.add(bytesList.get(i));
		}
		return hex;
	}
		
	public Hex getInvertHEX() {
		return invertBytes();
	}
	
	public Hex getInvertHEX(int start, int end){
		Hex hex = new Hex();
		for (int i = end; i >= start; i--) {
			hex.bytesList.add(bytesList.get(i));
		}
		return hex;
	}

	public Hex getHEX(int i) {
		return new Hex(bytesList.get(i));
	}

	public void add(Hex hex) {
		bytesList.addAll(hex.bytesList);
	}

	public void add(byte b) {
		bytesList.add(b);
	}

	public int length() {
		return bytesList.size();
	}

	public Hex invertBytes() {
		ArrayList<Byte> hex = new ArrayList<>();
		for (int i = bytesList.size() - 1; i >= 0; i--) {
			hex.add(bytesList.get(i));
		}
		Hex bs = new Hex(hex);
		//bs.bytesList = hex;
		return bs;
	}

	public Hex invertBits() {
		boolean[] bs = getBits();
		int i = 0;
		int j = bs.length - 1;
		for (; i < j; i++, j--) {
			boolean t1 = bs[i];
			boolean t2 = bs[j];
			bs[i] = t2;
			bs[j] = t1;
		}
		return new Hex(bitsToBytes(bs));
	}

	public void clear() {
		bytesList.clear();
	}

	@Override
	public boolean equals(final Object object) {
		if (object == null) return false;
		if (!object.getClass().getName().equals(getClass().getName())) return false;
		Hex hex = (Hex) object;
		return equals(hex);
	}

	public boolean equals(final Hex hex) {
		if (length() != hex.length()) return false;
		ArrayList<Byte> i1 = bytesList;
		ArrayList<Byte> i2 = hex.bytesList;
		int len = this.length();
		for (int i = 0; i < len; i++) {
			byte b1 = i1.get(i);
			byte b2 = i2.get(i);
			if (b1 != b2) return false;
		}
		return true;
	}

	private byte[] format(int bytes) {
		byte[] bs = new byte[bytes];
		int len = bytes - length();
		for (int i = bs.length - 1, j = i - len; j >= 0 && i >= 0; i--, j--) {
			if (j >= 0) bs[i] = bytesList.get(j);
		}
		return bs;
	}

	public long getCompilitTime() {
		return compilitTime;
	}

	public void setCompilitTime(long compilitTime) {
		this.compilitTime = compilitTime;
	}
}
