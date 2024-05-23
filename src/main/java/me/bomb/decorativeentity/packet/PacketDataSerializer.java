package me.bomb.decorativeentity.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.EncoderException;
import io.netty.util.ByteProcessor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

class PacketDataSerializer extends ByteBuf {

    private final ByteBuf bytebuf;

    protected PacketDataSerializer(ByteBuf bytebuf) {
        this.bytebuf = bytebuf;
    }

    public static int countBytes(int i) { return PacketDataSerializer.a(i); } // Paper - Anti-Xray - OBFHELPER
    public static int a(int i) {
        for (int j = 1; j < 5; ++j) {
            if ((i & -1 << j * 7) == 0) {
                return j;
            }
        }

        return 5;
    }

    public PacketDataSerializer a(byte[] abyte) {
        this.writeNum(abyte.length);
        this.writeBytes(abyte);
        return this;
    }

    public byte[] a() {
        return this.b(this.readableBytes());
    }

    public byte[] b(int i) {
        int j = this.readNum();

        if (j > i) {
            throw new DecoderException("ByteArray with size " + j + " is bigger than allowed " + i);
        } else {
            byte[] abyte = new byte[j];

            this.readBytes(abyte);
            return abyte;
        }
    }

    public PacketDataSerializer a(int[] aint) {
        this.writeNum(aint.length);
        int[] aint1 = aint;
        int i = aint.length;

        for (int j = 0; j < i; ++j) {
            int k = aint1[j];

            this.writeNum(k);
        }

        return this;
    }

    public int[] b() {
        return this.c(this.readableBytes());
    }

    public int[] c(int i) {
        int j = this.readNum();

        if (j > i) {
            throw new DecoderException("VarIntArray with size " + j + " is bigger than allowed " + i);
        } else {
            int[] aint = new int[j];

            for (int k = 0; k < aint.length; ++k) {
                aint[k] = this.readNum();
            }

            return aint;
        }
    }

    public PacketDataSerializer a(long[] along) {
        this.writeNum(along.length);
        long[] along1 = along;
        int i = along.length;

        for (int j = 0; j < i; ++j) {
            long k = along1[j];

            this.writeLong(k);
        }

        return this;
    }

    public <T extends Enum<T>> T readClass(Class<T> oclass) {
        return ((T[]) oclass.getEnumConstants())[this.readNum()]; // CraftBukkit - fix decompile error
    }

    public PacketDataSerializer writeEnum(Enum<?> oenum) {
        return this.writeNum(oenum.ordinal());
    }

    public int readNum() {
        int i = 0;
        int j = 0;

        byte b0;

        do {
            b0 = this.readByte();
            i |= (b0 & 127) << j++ * 7;
            if (j > 5) {
                throw new RuntimeException("VarInt too big");
            }
        } while ((b0 & 128) == 128);

        return i;
    }

    public long h() {
        long i = 0L;
        int j = 0;

        byte b0;

        do {
            b0 = this.readByte();
            i |= (long) (b0 & 127) << j++ * 7;
            if (j > 10) {
                throw new RuntimeException("VarLong too big");
            }
        } while ((b0 & 128) == 128);

        return i;
    }

    public PacketDataSerializer writeUUID(UUID uuid) {
        this.writeLong(uuid.getMostSignificantBits());
        this.writeLong(uuid.getLeastSignificantBits());
        return this;
    }

    public UUID readUUID() {
        return new UUID(this.readLong(), this.readLong());
    }

    public PacketDataSerializer writeNum(int i) {
        while ((i & -128) != 0) {
            this.writeByte(i & 127 | 128);
            i >>>= 7;
        }

        this.writeByte(i);
        return this;
    }

    public PacketDataSerializer writeNum(long i) {
        while ((i & -128L) != 0L) {
            this.writeByte((int) (i & 127L) | 128);
            i >>>= 7;
        }

        this.writeByte((int) i);
        return this;
    }

    public String readString(int i) {
        int j = this.readNum();

        if (j > i * 4) {
            throw new DecoderException("The received encoded string buffer length is longer than maximum allowed (" + j + " > " + i * 4 + ")");
        } else if (j < 0) {
            throw new DecoderException("The received encoded string buffer length is less than zero! Weird string!");
        } else {
            String s = this.toString(this.readerIndex(), j, StandardCharsets.UTF_8);

            this.readerIndex(this.readerIndex() + j);
            if (s.length() > i) {
                throw new DecoderException("The received string length is longer than maximum allowed (" + j + " > " + i + ")");
            } else {
                return s;
            }
        }
    }

    public PacketDataSerializer writeString(String s) {
        byte[] abyte = s.getBytes(StandardCharsets.UTF_8);

        if (abyte.length > 44767) { // Paper - raise limit a bit more as normal means can trigger this
            throw new EncoderException("String too big (was " + s.length() + " bytes encoded, max " + 44767 + ")"); // Paper
        } else {
            this.writeNum(abyte.length);
            this.writeBytes(abyte);
            return this;
        }
    }

    public Date readDate() {
        return new Date(this.readLong());
    }

    public PacketDataSerializer writeDate(Date date) {
        this.writeLong(date.getTime());
        return this;
    }

    public int capacity() {
        return this.bytebuf.capacity();
    }

    public ByteBuf capacity(int i) {
        return this.bytebuf.capacity(i);
    }

    public int maxCapacity() {
        return this.bytebuf.maxCapacity();
    }

    public ByteBufAllocator alloc() {
        return this.bytebuf.alloc();
    }

    @Deprecated
    public ByteOrder order() {
        return this.bytebuf.order();
    }
    
    @Deprecated
    public ByteBuf order(ByteOrder byteorder) {
        return this.bytebuf.order(byteorder);
    }

    public ByteBuf unwrap() {
        return this.bytebuf.unwrap();
    }

    public boolean isDirect() {
        return this.bytebuf.isDirect();
    }

    public boolean isReadOnly() {
        return this.bytebuf.isReadOnly();
    }

    public ByteBuf asReadOnly() {
        return this.bytebuf.asReadOnly();
    }

    public int readerIndex() {
        return this.bytebuf.readerIndex();
    }

    public ByteBuf readerIndex(int i) {
        return this.bytebuf.readerIndex(i);
    }

    public int writerIndex() {
        return this.bytebuf.writerIndex();
    }

    public ByteBuf writerIndex(int i) {
        return this.bytebuf.writerIndex(i);
    }

    public ByteBuf setIndex(int i, int j) {
        return this.bytebuf.setIndex(i, j);
    }

    public int readableBytes() {
        return this.bytebuf.readableBytes();
    }

    public int writableBytes() {
        return this.bytebuf.writableBytes();
    }

    public int maxWritableBytes() {
        return this.bytebuf.maxWritableBytes();
    }

    public boolean isReadable() {
        return this.bytebuf.isReadable();
    }

    public boolean isReadable(int i) {
        return this.bytebuf.isReadable(i);
    }

    public boolean isWritable() {
        return this.bytebuf.isWritable();
    }

    public boolean isWritable(int i) {
        return this.bytebuf.isWritable(i);
    }

    public ByteBuf clear() {
        return this.bytebuf.clear();
    }

    public ByteBuf markReaderIndex() {
        return this.bytebuf.markReaderIndex();
    }

    public ByteBuf resetReaderIndex() {
        return this.bytebuf.resetReaderIndex();
    }

    public ByteBuf markWriterIndex() {
        return this.bytebuf.markWriterIndex();
    }

    public ByteBuf resetWriterIndex() {
        return this.bytebuf.resetWriterIndex();
    }

    public ByteBuf discardReadBytes() {
        return this.bytebuf.discardReadBytes();
    }

    public ByteBuf discardSomeReadBytes() {
        return this.bytebuf.discardSomeReadBytes();
    }

    public ByteBuf ensureWritable(int i) {
        return this.bytebuf.ensureWritable(i);
    }

    public int ensureWritable(int i, boolean flag) {
        return this.bytebuf.ensureWritable(i, flag);
    }

    public boolean getBoolean(int i) {
        return this.bytebuf.getBoolean(i);
    }

    public byte getByte(int i) {
        return this.bytebuf.getByte(i);
    }

    public short getUnsignedByte(int i) {
        return this.bytebuf.getUnsignedByte(i);
    }

    public short getShort(int i) {
        return this.bytebuf.getShort(i);
    }

    public short getShortLE(int i) {
        return this.bytebuf.getShortLE(i);
    }

    public int getUnsignedShort(int i) {
        return this.bytebuf.getUnsignedShort(i);
    }

    public int getUnsignedShortLE(int i) {
        return this.bytebuf.getUnsignedShortLE(i);
    }

    public int getMedium(int i) {
        return this.bytebuf.getMedium(i);
    }

    public int getMediumLE(int i) {
        return this.bytebuf.getMediumLE(i);
    }

    public int getUnsignedMedium(int i) {
        return this.bytebuf.getUnsignedMedium(i);
    }

    public int getUnsignedMediumLE(int i) {
        return this.bytebuf.getUnsignedMediumLE(i);
    }

    public int getInt(int i) {
        return this.bytebuf.getInt(i);
    }

    public int getIntLE(int i) {
        return this.bytebuf.getIntLE(i);
    }

    public long getUnsignedInt(int i) {
        return this.bytebuf.getUnsignedInt(i);
    }

    public long getUnsignedIntLE(int i) {
        return this.bytebuf.getUnsignedIntLE(i);
    }

    public long getLong(int i) {
        return this.bytebuf.getLong(i);
    }

    public long getLongLE(int i) {
        return this.bytebuf.getLongLE(i);
    }

    public char getChar(int i) {
        return this.bytebuf.getChar(i);
    }

    public float getFloat(int i) {
        return this.bytebuf.getFloat(i);
    }

    public double getDouble(int i) {
        return this.bytebuf.getDouble(i);
    }

    public ByteBuf getBytes(int i, ByteBuf bytebuf) {
        return this.bytebuf.getBytes(i, bytebuf);
    }

    public ByteBuf getBytes(int i, ByteBuf bytebuf, int j) {
        return this.bytebuf.getBytes(i, bytebuf, j);
    }

    public ByteBuf getBytes(int i, ByteBuf bytebuf, int j, int k) {
        return this.bytebuf.getBytes(i, bytebuf, j, k);
    }

    public ByteBuf getBytes(int i, byte[] abyte) {
        return this.bytebuf.getBytes(i, abyte);
    }

    public ByteBuf getBytes(int i, byte[] abyte, int j, int k) {
        return this.bytebuf.getBytes(i, abyte, j, k);
    }

    public ByteBuf getBytes(int i, ByteBuffer bytebuffer) {
        return this.bytebuf.getBytes(i, bytebuffer);
    }

    public ByteBuf getBytes(int i, OutputStream outputstream, int j) throws IOException {
        return this.bytebuf.getBytes(i, outputstream, j);
    }

    public int getBytes(int i, GatheringByteChannel gatheringbytechannel, int j) throws IOException {
        return this.bytebuf.getBytes(i, gatheringbytechannel, j);
    }

    public int getBytes(int i, FileChannel filechannel, long j, int k) throws IOException {
        return this.bytebuf.getBytes(i, filechannel, j, k);
    }

    public CharSequence getCharSequence(int i, int j, Charset charset) {
        return this.bytebuf.getCharSequence(i, j, charset);
    }

    public ByteBuf setBoolean(int i, boolean flag) {
        return this.bytebuf.setBoolean(i, flag);
    }

    public ByteBuf setByte(int i, int j) {
        return this.bytebuf.setByte(i, j);
    }

    public ByteBuf setShort(int i, int j) {
        return this.bytebuf.setShort(i, j);
    }

    public ByteBuf setShortLE(int i, int j) {
        return this.bytebuf.setShortLE(i, j);
    }

    public ByteBuf setMedium(int i, int j) {
        return this.bytebuf.setMedium(i, j);
    }

    public ByteBuf setMediumLE(int i, int j) {
        return this.bytebuf.setMediumLE(i, j);
    }

    public ByteBuf setInt(int i, int j) {
        return this.bytebuf.setInt(i, j);
    }

    public ByteBuf setIntLE(int i, int j) {
        return this.bytebuf.setIntLE(i, j);
    }

    public ByteBuf setLong(int i, long j) {
        return this.bytebuf.setLong(i, j);
    }

    public ByteBuf setLongLE(int i, long j) {
        return this.bytebuf.setLongLE(i, j);
    }

    public ByteBuf setChar(int i, int j) {
        return this.bytebuf.setChar(i, j);
    }

    public ByteBuf setFloat(int i, float f) {
        return this.bytebuf.setFloat(i, f);
    }

    public ByteBuf setDouble(int i, double d0) {
        return this.bytebuf.setDouble(i, d0);
    }

    public ByteBuf setBytes(int i, ByteBuf bytebuf) {
        return this.bytebuf.setBytes(i, bytebuf);
    }

    public ByteBuf setBytes(int i, ByteBuf bytebuf, int j) {
        return this.bytebuf.setBytes(i, bytebuf, j);
    }

    public ByteBuf setBytes(int i, ByteBuf bytebuf, int j, int k) {
        return this.bytebuf.setBytes(i, bytebuf, j, k);
    }

    public ByteBuf setBytes(int i, byte[] abyte) {
        return this.bytebuf.setBytes(i, abyte);
    }

    public ByteBuf setBytes(int i, byte[] abyte, int j, int k) {
        return this.bytebuf.setBytes(i, abyte, j, k);
    }

    public ByteBuf setBytes(int i, ByteBuffer bytebuffer) {
        return this.bytebuf.setBytes(i, bytebuffer);
    }

    public int setBytes(int i, InputStream inputstream, int j) throws IOException {
        return this.bytebuf.setBytes(i, inputstream, j);
    }

    public int setBytes(int i, ScatteringByteChannel scatteringbytechannel, int j) throws IOException {
        return this.bytebuf.setBytes(i, scatteringbytechannel, j);
    }

    public int setBytes(int i, FileChannel filechannel, long j, int k) throws IOException {
        return this.bytebuf.setBytes(i, filechannel, j, k);
    }

    public ByteBuf setZero(int i, int j) {
        return this.bytebuf.setZero(i, j);
    }

    public int setCharSequence(int i, CharSequence charsequence, Charset charset) {
        return this.bytebuf.setCharSequence(i, charsequence, charset);
    }

    public boolean readBoolean() {
        return this.bytebuf.readBoolean();
    }

    public byte readByte() {
        return this.bytebuf.readByte();
    }

    public short readUnsignedByte() {
        return this.bytebuf.readUnsignedByte();
    }

    public short readShort() {
        return this.bytebuf.readShort();
    }

    public short readShortLE() {
        return this.bytebuf.readShortLE();
    }

    public int readUnsignedShort() {
        return this.bytebuf.readUnsignedShort();
    }

    public int readUnsignedShortLE() {
        return this.bytebuf.readUnsignedShortLE();
    }

    public int readMedium() {
        return this.bytebuf.readMedium();
    }

    public int readMediumLE() {
        return this.bytebuf.readMediumLE();
    }

    public int readUnsignedMedium() {
        return this.bytebuf.readUnsignedMedium();
    }

    public int readUnsignedMediumLE() {
        return this.bytebuf.readUnsignedMediumLE();
    }

    public int readInt() {
        return this.bytebuf.readInt();
    }

    public int readIntLE() {
        return this.bytebuf.readIntLE();
    }

    public long readUnsignedInt() {
        return this.bytebuf.readUnsignedInt();
    }

    public long readUnsignedIntLE() {
        return this.bytebuf.readUnsignedIntLE();
    }

    public long readLong() {
        return this.bytebuf.readLong();
    }

    public long readLongLE() {
        return this.bytebuf.readLongLE();
    }

    public char readChar() {
        return this.bytebuf.readChar();
    }

    public float readFloat() {
        return this.bytebuf.readFloat();
    }

    public double readDouble() {
        return this.bytebuf.readDouble();
    }

    public ByteBuf readBytes(int i) {
        return this.bytebuf.readBytes(i);
    }

    public ByteBuf readSlice(int i) {
        return this.bytebuf.readSlice(i);
    }

    public ByteBuf readRetainedSlice(int i) {
        return this.bytebuf.readRetainedSlice(i);
    }

    public ByteBuf readBytes(ByteBuf bytebuf) {
        return this.bytebuf.readBytes(bytebuf);
    }

    public ByteBuf readBytes(ByteBuf bytebuf, int i) {
        return this.bytebuf.readBytes(bytebuf, i);
    }

    public ByteBuf readBytes(ByteBuf bytebuf, int i, int j) {
        return this.bytebuf.readBytes(bytebuf, i, j);
    }

    public ByteBuf readBytes(byte[] abyte) {
        return this.bytebuf.readBytes(abyte);
    }

    public ByteBuf readBytes(byte[] abyte, int i, int j) {
        return this.bytebuf.readBytes(abyte, i, j);
    }

    public ByteBuf readBytes(ByteBuffer bytebuffer) {
        return this.bytebuf.readBytes(bytebuffer);
    }

    public ByteBuf readBytes(OutputStream outputstream, int i) throws IOException {
        return this.bytebuf.readBytes(outputstream, i);
    }

    public int readBytes(GatheringByteChannel gatheringbytechannel, int i) throws IOException {
        return this.bytebuf.readBytes(gatheringbytechannel, i);
    }

    public CharSequence readCharSequence(int i, Charset charset) {
        return this.bytebuf.readCharSequence(i, charset);
    }

    public int readBytes(FileChannel filechannel, long i, int j) throws IOException {
        return this.bytebuf.readBytes(filechannel, i, j);
    }

    public ByteBuf skipBytes(int i) {
        return this.bytebuf.skipBytes(i);
    }

    public ByteBuf writeBoolean(boolean flag) {
        return this.bytebuf.writeBoolean(flag);
    }

    public ByteBuf writeByte(int i) {
        return this.bytebuf.writeByte(i);
    }

    public ByteBuf writeShort(int i) {
        return this.bytebuf.writeShort(i);
    }

    public ByteBuf writeShortLE(int i) {
        return this.bytebuf.writeShortLE(i);
    }

    public ByteBuf writeMedium(int i) {
        return this.bytebuf.writeMedium(i);
    }

    public ByteBuf writeMediumLE(int i) {
        return this.bytebuf.writeMediumLE(i);
    }

    public ByteBuf writeInt(int i) {
        return this.bytebuf.writeInt(i);
    }

    public ByteBuf writeIntLE(int i) {
        return this.bytebuf.writeIntLE(i);
    }

    public ByteBuf writeLong(long i) {
        return this.bytebuf.writeLong(i);
    }

    public ByteBuf writeLongLE(long i) {
        return this.bytebuf.writeLongLE(i);
    }

    public ByteBuf writeChar(int i) {
        return this.bytebuf.writeChar(i);
    }

    public ByteBuf writeFloat(float f) {
        return this.bytebuf.writeFloat(f);
    }

    public ByteBuf writeDouble(double d0) {
        return this.bytebuf.writeDouble(d0);
    }

    public ByteBuf writeBytes(ByteBuf bytebuf) {
        return this.bytebuf.writeBytes(bytebuf);
    }

    public ByteBuf writeBytes(ByteBuf bytebuf, int i) {
        return this.bytebuf.writeBytes(bytebuf, i);
    }

    public ByteBuf writeBytes(ByteBuf bytebuf, int i, int j) {
        return this.bytebuf.writeBytes(bytebuf, i, j);
    }

    public ByteBuf writeBytes(byte[] abyte) {
        return this.bytebuf.writeBytes(abyte);
    }

    public ByteBuf writeBytes(byte[] abyte, int i, int j) {
        return this.bytebuf.writeBytes(abyte, i, j);
    }

    public ByteBuf writeBytes(ByteBuffer bytebuffer) {
        return this.bytebuf.writeBytes(bytebuffer);
    }

    public int writeBytes(InputStream inputstream, int i) throws IOException {
        return this.bytebuf.writeBytes(inputstream, i);
    }

    public int writeBytes(ScatteringByteChannel scatteringbytechannel, int i) throws IOException {
        return this.bytebuf.writeBytes(scatteringbytechannel, i);
    }

    public int writeBytes(FileChannel filechannel, long i, int j) throws IOException {
        return this.bytebuf.writeBytes(filechannel, i, j);
    }

    public ByteBuf writeZero(int i) {
        return this.bytebuf.writeZero(i);
    }

    public int writeCharSequence(CharSequence charsequence, Charset charset) {
        return this.bytebuf.writeCharSequence(charsequence, charset);
    }

    public int indexOf(int i, int j, byte b0) {
        return this.bytebuf.indexOf(i, j, b0);
    }

    public int bytesBefore(byte b0) {
        return this.bytebuf.bytesBefore(b0);
    }

    public int bytesBefore(int i, byte b0) {
        return this.bytebuf.bytesBefore(i, b0);
    }

    public int bytesBefore(int i, int j, byte b0) {
        return this.bytebuf.bytesBefore(i, j, b0);
    }

    public int forEachByte(ByteProcessor byteprocessor) {
        return this.bytebuf.forEachByte(byteprocessor);
    }

    public int forEachByte(int i, int j, ByteProcessor byteprocessor) {
        return this.bytebuf.forEachByte(i, j, byteprocessor);
    }

    public int forEachByteDesc(ByteProcessor byteprocessor) {
        return this.bytebuf.forEachByteDesc(byteprocessor);
    }

    public int forEachByteDesc(int i, int j, ByteProcessor byteprocessor) {
        return this.bytebuf.forEachByteDesc(i, j, byteprocessor);
    }

    public ByteBuf copy() {
        return this.bytebuf.copy();
    }

    public ByteBuf copy(int i, int j) {
        return this.bytebuf.copy(i, j);
    }

    public ByteBuf slice() {
        return this.bytebuf.slice();
    }

    public ByteBuf retainedSlice() {
        return this.bytebuf.retainedSlice();
    }

    public ByteBuf slice(int i, int j) {
        return this.bytebuf.slice(i, j);
    }

    public ByteBuf retainedSlice(int i, int j) {
        return this.bytebuf.retainedSlice(i, j);
    }

    public ByteBuf duplicate() {
        return this.bytebuf.duplicate();
    }

    public ByteBuf retainedDuplicate() {
        return this.bytebuf.retainedDuplicate();
    }

    public int nioBufferCount() {
        return this.bytebuf.nioBufferCount();
    }

    public ByteBuffer nioBuffer() {
        return this.bytebuf.nioBuffer();
    }

    public ByteBuffer nioBuffer(int i, int j) {
        return this.bytebuf.nioBuffer(i, j);
    }

    public ByteBuffer internalNioBuffer(int i, int j) {
        return this.bytebuf.internalNioBuffer(i, j);
    }

    public ByteBuffer[] nioBuffers() {
        return this.bytebuf.nioBuffers();
    }

    public ByteBuffer[] nioBuffers(int i, int j) {
        return this.bytebuf.nioBuffers(i, j);
    }

    public boolean hasArray() {
        return this.bytebuf.hasArray();
    }

    public byte[] array() {
        return this.bytebuf.array();
    }

    public int arrayOffset() {
        return this.bytebuf.arrayOffset();
    }

    public boolean hasMemoryAddress() {
        return this.bytebuf.hasMemoryAddress();
    }

    public long memoryAddress() {
        return this.bytebuf.memoryAddress();
    }

    public String toString(Charset charset) {
        return this.bytebuf.toString(charset);
    }

    public String toString(int i, int j, Charset charset) {
        return this.bytebuf.toString(i, j, charset);
    }

    public int hashCode() {
        return this.bytebuf.hashCode();
    }

    public boolean equals(Object object) {
        return this.bytebuf.equals(object);
    }

    public int compareTo(ByteBuf bytebuf) {
        return this.bytebuf.compareTo(bytebuf);
    }

    public String toString() {
        return this.bytebuf.toString();
    }

    public ByteBuf retain(int i) {
        return this.bytebuf.retain(i);
    }

    public ByteBuf retain() {
        return this.bytebuf.retain();
    }

    public ByteBuf touch() {
        return this.bytebuf.touch();
    }

    public ByteBuf touch(Object object) {
        return this.bytebuf.touch(object);
    }

    public int refCnt() {
        return this.bytebuf.refCnt();
    }

    public boolean release() {
        return this.bytebuf.release();
    }

    public boolean release(int i) {
        return this.bytebuf.release(i);
    }
}
