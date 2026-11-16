public class OldPaymentSystem {
    private long lastPaidCents;
    public void payInCents(long cents) {
        lastPaidCents=cents;
        System.out.println("Paid:"+cents+" cents");
    }
    public long getLastPaidCents() {
        return lastPaidCents;
    }
}