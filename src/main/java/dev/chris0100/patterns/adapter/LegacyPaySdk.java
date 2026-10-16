package dev.chris0100.patterns.adapter;

/** Third-party SDK we cannot change: cents as int, numeric codes, its own naming. */
public class LegacyPaySdk {

    public static final int OK = 0;
    public static final int DECLINED = 51;

    public int doTransaction(String accountRef, int amountInCents, StringBuilder outTxId) {
        if (amountInCents <= 0 || amountInCents > 1_000_000) {
            return DECLINED;
        }
        outTxId.append("TX-").append(accountRef).append('-').append(amountInCents);
        return OK;
    }
}
