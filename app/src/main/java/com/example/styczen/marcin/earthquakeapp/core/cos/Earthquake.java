package com.example.styczen.marcin.earthquakeapp.core.cos;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Marcin on 2016-10-10.
 */

@AllArgsConstructor(suppressConstructorProperties = true)
@NoArgsConstructor
public
@Data
@DatabaseTable(tableName = Earthquake.EARTQUAKE_TABLE_NAME)
class Earthquake implements Parcelable {
    public static final String EARTQUAKE_TABLE_NAME = "t_earthquake";
    public static final String EARTQUAKE_COL_ID = "eq_id";
    public static final String EARTQUAKE_COL_NAME = "eq_name";
    public static final String EARTQUAKE_COL_TIME = "eq_time";
    public static final String EARTQUAKE_COL_MAGNITUDE = "eq_magnitude";

    @DatabaseField(columnName = EARTQUAKE_COL_ID, generatedId = true)
    private int id;
    @DatabaseField(columnName = EARTQUAKE_COL_NAME)
    private String name;
    @DatabaseField(columnName = EARTQUAKE_COL_TIME)
    private String time;
    @DatabaseField(columnName = EARTQUAKE_COL_MAGNITUDE)
    private Double magnitude;

    //TODO lombok
    public Earthquake(String name, String time, Double magnitude) {
        this.name = name;
        this.time = time;
        this.magnitude = magnitude;
    }

    //PARCEL - lombok nie obsluguje przynajmnij nic o tym nie wiem.
    public Earthquake(Parcel in) {
        this.name = in.readString();
        this.time = in.readString();
        this.magnitude = (Double) in.readValue(Double.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.time);
        dest.writeValue(this.magnitude);
    }

    public static final Creator<Earthquake> CREATOR = new Creator<Earthquake>() {
        @Override
        public Earthquake createFromParcel(Parcel source) {
            return new Earthquake(source);
        }

        @Override
        public Earthquake[] newArray(int size) {
            return new Earthquake[size];
        }
    };

    public boolean isValid() {
        return !(name == null || time == null || magnitude == null);
    }

}
