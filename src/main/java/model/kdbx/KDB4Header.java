package model.kdbx;

import java.util.HashMap;
import java.util.Map;
import static java.util.Map.entry;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

public class KDB4Header {

    private final Map<String, Integer> fields;
    private final Map<byte[], String> ciphers;
    private final Map<Integer, String> protectedStreams;

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
