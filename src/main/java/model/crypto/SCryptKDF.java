package model.crypto;

import com.google.common.primitives.Bytes;
import com.lambdaworks.crypto.SCryptUtil;

public class SCryptKDF implements KDF {

    private static final int ROUNDS = 10;
    @Override
    public final byte[] generateKey(final byte[] password, final byte[] salt, final int rounds) {
        byte[] composite = Bytes.concat(password, salt);
        String hash = SCryptUtil.scrypt(new String(composite), 10, 10, 10);
        return hash.getBytes();
    }

    @Override
    public final int getDefaultRounds() {
        return SCryptKDF.ROUNDS; 
    }

}
