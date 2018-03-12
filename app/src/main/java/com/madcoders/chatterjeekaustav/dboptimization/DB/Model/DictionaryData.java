package com.madcoders.chatterjeekaustav.dboptimization.DB.Model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Kaustav on 11-03-2018.
 */

@Entity
public class DictionaryData {

    @Id
    String id;

    @NotNull
    String data;

    @Generated(hash = 1028148832)
    public DictionaryData(String id, @NotNull String data) {
        this.id = id;
        this.data = data;
    }

    @Generated(hash = 2141477900)
    public DictionaryData() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getData() {
        return this.data;
    }

    public void setData(String data) {
        this.data = data;
    }


}
