package rim.mr.covtest.service.dto;

public class WhatsAppMessageResponse {
    private boolean ok;
    private String result;

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
