package com.example.styczen.marcin.earthquakeapp.core;

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
    public static final String EARTQUAKE_COL_MAGNITUDE = "eq_magnitude";
    public static final String EARTQUAKE_COL_PLACE = "eq_place";
    public static final String EARTQUAKE_COL_URL = "eq_url";
    public static final String EARTQUAKE_COL_CODE = "eq_code";
    public static final String EARTQUAKE_COL_TYPE = "eq_type";
    public static final String EARTQUAKE_COL_NAME = "eq_title";
    public static final String EARTQUAKE_COL_TIME = "eq_time";
    public static final String EARTQUAKE_COL_GEOMETRY = "eq_geometry";

    @DatabaseField(columnName = "_id", generatedId = true)
    private long id;
    @DatabaseField(columnName = EARTQUAKE_COL_ID)
    private String uniqueId;
    @DatabaseField(columnName = EARTQUAKE_COL_MAGNITUDE)
    private Double magnitude;
    @DatabaseField(columnName = EARTQUAKE_COL_PLACE)
    private String place;
    @DatabaseField(columnName = EARTQUAKE_COL_URL)
    private String urlDetail;
    @DatabaseField(columnName = EARTQUAKE_COL_CODE)
    private String code;
    @DatabaseField(columnName = EARTQUAKE_COL_TYPE)
    private String type;
    @DatabaseField(columnName = EARTQUAKE_COL_NAME)
    private String title;
    @DatabaseField(columnName = EARTQUAKE_COL_TIME)
    private long time;
    @DatabaseField(foreign = true, columnName = EARTQUAKE_COL_GEOMETRY, foreignAutoRefresh = true)
    private Geometry geometries;

    public Earthquake(String title, String place, Double magnitude) {
        this.title = title;
        this.place = place;
        this.magnitude = magnitude;
    }

    public boolean isValid() {
        return !(title == null  || magnitude == null);
    }

    //PARCEL - lombok nie obsluguje przynajmnij nic o tym nie wiem.
    //TODO hrisey
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.uniqueId);
        dest.writeValue(this.magnitude);
        dest.writeString(this.place);
        dest.writeString(this.urlDetail);
        dest.writeString(this.code);
        dest.writeString(this.type);
        dest.writeString(this.title);
        dest.writeLong(this.time);
        dest.writeParcelable(this.geometries, flags);
    }

    protected Earthquake(Parcel in) {
        this.id = in.readLong();
        this.uniqueId = in.readString();
        this.magnitude = (Double) in.readValue(Double.class.getClassLoader());
        this.place = in.readString();
        this.urlDetail = in.readString();
        this.code = in.readString();
        this.type = in.readString();
        this.title = in.readString();
        this.time = in.readLong();
        this.geometries = in.readParcelable(Geometry.class.getClassLoader());
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
}
