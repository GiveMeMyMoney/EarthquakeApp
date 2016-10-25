package com.example.styczen.marcin.earthquakeapp.core;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Marcin on 2016-10-24.
 */

@AllArgsConstructor(suppressConstructorProperties = true)
@NoArgsConstructor

public
@Data
@DatabaseTable(tableName = Geometry.GEOMETRY_TABLE_NAME)
class Geometry implements Parcelable, Serializable {
    public static final String GEOMETRY_TABLE_NAME = "t_geometry";
    public static final String GEOMETRY_COL_TYPE = "geo_type";
    public static final String GEOMETRY_COL_MAPS_COORDINATES = "geo_coordinates";
    public static final String GEOMETRY_COL_DEPTH = "geo_depth";

    @DatabaseField(columnName = "_id", generatedId = true)
    private long id;
    @DatabaseField(columnName = GEOMETRY_COL_TYPE)
    private String type;
    @DatabaseField(columnName = GEOMETRY_COL_MAPS_COORDINATES, dataType = DataType.SERIALIZABLE)
    private Map<String, Double> coordinatesMap;
    @DatabaseField(columnName = GEOMETRY_COL_DEPTH)
    private Double depth;

    //PARCEL
    protected Geometry(Parcel in) {
        this.id = in.readLong();
        this.type = in.readString();
        int coordinatesMapSize = in.readInt();
        this.coordinatesMap = new HashMap<>(coordinatesMapSize);
        for (int i = 0; i < coordinatesMapSize; i++) {
            String key = in.readString();
            Double value = (Double) in.readValue(Double.class.getClassLoader());
            this.coordinatesMap.put(key, value);
        }
        this.depth = (Double) in.readValue(Double.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.type);
        dest.writeInt(this.coordinatesMap.size());
        for (Map.Entry<String, Double> entry : this.coordinatesMap.entrySet()) {
            dest.writeString(entry.getKey());
            dest.writeValue(entry.getValue());
        }
        dest.writeValue(this.depth);
    }

    public static final Creator<Geometry> CREATOR = new Creator<Geometry>() {
        @Override
        public Geometry createFromParcel(Parcel source) {
            return new Geometry(source);
        }

        @Override
        public Geometry[] newArray(int size) {
            return new Geometry[size];
        }
    };
}
