package wiseasily.pair;

import java.io.Serializable;

/**
 * بِسْمِ اللّهِ الرَّحْمَنِ
 * Created by putrabangga on 13/02/18.
 */

public class Pair<SupplicantState,String> implements Serializable {

    private final SupplicantState supplicantState;
    private final String ssid;

    public Pair(SupplicantState supplicantState, String ssid) {
        this.supplicantState = supplicantState;
        this.ssid = ssid;
    }

    public SupplicantState getSupplicantState() { return supplicantState; }

    public String getSsid() { return ssid; }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Pair)) return false;
        Pair pairo = (Pair) o;
        return this.supplicantState.equals(pairo.getSupplicantState()) &&
                this.ssid.equals(pairo.getSsid());
    }

    @Override
    public java.lang.String toString() {
        return "Pair{" +
                "supplicantState=" + supplicantState +
                ", ssid=" + ssid +
                '}';
    }
}
