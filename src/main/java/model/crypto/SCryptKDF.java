package model.crypto;

import com.google.common.primitives.Bytes;
import com.lambdaworks.crypto.SCryptUtil;

public class SCryptKDF implements KDF {

    private static final int ROUNDS = 10;
    private static final int DEFAULT_MEMORY = 16384;
    private static final int DEFAULT_PARALLELISM = 2;
    private int memory = DEFAULT_MEMORY;
    private int parallelism = DEFAULT_PARALLELISM;
    @Override
    public final byte[] generateKey(final byte[] password, final byte[] salt, final int rounds) {
        byte[] composite = Bytes.concat(password, salt);
        String hash = SCryptUtil.scrypt(new String(composite), 10, this.memory, this.parallelism);
        return hash.getBytes();
    }

    @Override
    public final int getDefaultRounds() {
        return SCryptKDF.ROUNDS; 
    }

    @Override
    public final boolean isTweakable() {
        return true;
    }

    @Override
    public final void setMemory(final int memory) {
        this.memory = memory;
    }

    @Override
    public final void setParallelism(final int parallelism) {
    }

}
