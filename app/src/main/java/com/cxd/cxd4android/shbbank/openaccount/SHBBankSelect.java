package com.cxd.cxd4android.shbbank.openaccount;

/**
 * Created by administrator on 17/8/30.
 */

public class SHBBankSelect {
    private String mName;
    private String mBankname;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getBankname() {
        return mBankname;
    }

    public void setBankname(String bankname) {
        mBankname = bankname;
    }

    public SHBBankSelect(String name, String bankname) {
        mName = name;

        mBankname = bankname;
    }
}
