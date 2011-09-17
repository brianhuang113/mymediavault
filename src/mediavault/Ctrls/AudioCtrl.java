package mediavault.Ctrls;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;

import mediavault.Models.*;

public class AudioCtrl {

	private static final int tagSize = 128;

	private static AudioTag populateTag(ByteBuffer bBuf) {
		byte[] tag = new byte[3];
		byte[] tagTitle = new byte[30];
		byte[] tagArtist = new byte[30];
		byte[] tagAlbum = new byte[30];
		byte[] tagYear = new byte[4];
		byte[] tagComment = new byte[30];
		byte[] tagGenre = new byte[1];
		bBuf.get(tag).get(tagTitle).get(tagArtist).get(tagAlbum).get(tagYear)
				.get(tagComment).get(tagGenre);
		if (!"TAG".equals(new String(tag))) {
			throw new IllegalArgumentException(
					"ByteBuffer does not contain ID3 tag data");
		}
		AudioTag tagOut = new AudioTag();
		tagOut.setTitle(new String(tagTitle).trim());
		tagOut.setArtist(new String(tagArtist).trim());
		tagOut.setAlbum(new String(tagAlbum).trim());
		tagOut.setYear(new String(tagYear).trim());
		tagOut.setComment(new String(tagComment).trim());
		tagOut.setGenre(tagGenre[0]);
		return tagOut;
	}

	public static AudioTag readTag(InputStream in) throws Exception {
		BufferedInputStream bIn = new BufferedInputStream(in);
		int match = 'T' << 16 | 'A' << 8 | 'G';
		int tmp = 0;
		while (true) {
			if ((tmp & 0x00ffffff) == match) {
				break;
			}
			tmp <<= 8;
			tmp |= bIn.read();
		}
		byte[] tagData = new byte[125];
		bIn.read(tagData);
		ByteBuffer bBuf = ByteBuffer.allocate(tagSize);
		bBuf.put("TAG".getBytes());
		bBuf.put(tagData);
		bBuf.rewind();
		AudioTag tagOut = populateTag(bBuf);
		return tagOut;
	}
}
