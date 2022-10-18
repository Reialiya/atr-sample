package hu.icellmobilsoft.atr.sample.util;

public enum Status {
    SUCCESS(1), SUCCESS_NO_UPDATE(0), SUCCESS_NO_INFO(0), EXECUTE_FAILED(0), UNKNOWN(0);

    private int rowsAffected;

    private Status(int rowsAffected) {
        this.rowsAffected = rowsAffected;
    }

    public Status setRowsAffected(int rowsAffected) {
        this.rowsAffected = rowsAffected;
        return this;
    }

    public int getRowsAffected() {
        return this.rowsAffected;
    }
}
