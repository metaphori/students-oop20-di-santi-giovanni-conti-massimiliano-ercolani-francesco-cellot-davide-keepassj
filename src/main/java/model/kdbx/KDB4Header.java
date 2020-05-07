package model.kdbx;

import java.util.HashMap;
import java.util.Map;
import static java.util.Map.entry;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

public class KDB4Header {

    private Map<String, Integer> fields;
    private Map<byte[], String> ciphers;
    private Map<Integer, String> protectedStreams;

    public final Map<String, Integer> getFields() {
        return fields;
    }
    public final void setFields(final Map<String, Integer> fields) {
        this.fields = fields;
    }
    public final Map<byte[], String> getCiphers() {
        return ciphers;
    }
    public final void setCiphers(final Map<byte[], String> ciphers) {
        this.ciphers = ciphers;
    }
    public final Map<Integer, String> getProtectedStreams() {
        return protectedStreams;
    }
    public final void setProtectedStreams(final Map<Integer, String> protectedStreams) {
        this.protectedStreams = protectedStreams;
    }
    KDB4Header() throws DecoderException {
        this.fields = Map.ofEntries(
                entry("EndOfHeader", 0),
                entry("Comment", 1),
                entry("CipherID", 2),
                entry("CompressionFlags", 3),
                entry("MasterSeed", 4),
                entry("TransformSeed", 5),
                entry("TransformRounds", 6),
                entry("EncryptionIV", 7),
                entry("ProtectedStreamKey", 8),
                entry("StreamStartBytes", 9),
                entry("InnerRandomStreamID", 10)
                );
        this.ciphers = Map.ofEntries(
                entry(Hex.decodeHex("31c1f2e6bf714350be5805216afc5aff"), "AES"),
                entry(Hex.decodeHex("31c1f2e6bf714350be5805216afc5aff"), "TwoFish"),
                entry(Hex.decodeHex("31c1f2e6bf714350be5805216afc5aff"), "ChaCha20")
                );
        this.protectedStreams = Map.ofEntries(
                entry(1, "ArcFourVariant"),
                entry(2, "Salsa20")
                );
    }
    private void setFields() {
       this.fields.put("EndOfHeader", 0);
    }
}
