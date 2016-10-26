package com.example.styczen.marcin.earthquakeapp.core;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

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
class Geometry implements Parcelable {
    static final String GEOMETRY_TABLE_NAME = "t_geometry";
    private static final String GEOMETRY_COL_TYPE = "geo_type";
    private static final String GEOMETRY_COL_MAPS_COORDINATES_Y = "geo_coordinates_y";
    private static final String GEOMETRY_COL_MAPS_COORDINATES_X = "geo_coordinates_x";
    private static final String GEOMETRY_COL_DEPTH = "geo_depth";

    @DatabaseField(columnName = "_id", generatedId = true)
    private long id;
    @DatabaseField(columnName = GEOMETRY_COL_TYPE)
    private String type;
    @DatabaseField(columnName = GEOMETRY_COL_MAPS_COORDINATES_Y)
    private Double coordinatesMapY;
    @DatabaseField(columnName = GEOMETRY_COL_MAPS_COORDINATES_X)
    private Double coordinatesMapX;
    @DatabaseField(columnName = GEOMETRY_COL_DEPTH)
    private Double depth;

    public Geometry(String type, Double coordinatesMapY, Double coordinatesMapX, Double depth) {
        this.type = type;
        this.coordinatesMapY = coordinatesMapY;
        this.coordinatesMapX = coordinatesMapX;
        this.depth = depth;
    }

    @Override
    public String toString() {
        return  type + " (" +
                "Y= " + coordinatesMapY +
                ", X= " + coordinatesMapX +
                ", depth= " + depth +
                ")";
    }

    //PARCEL
    protected Geometry(Parcel in) {
        this.id = in.readLong();
        this.type = in.readString();
        this.coordinatesMapY = (Double) in.readValue(Double.class.getClassLoader());
        this.coordinatesMapX = (Double) in.readValue(Double.class.getClassLoader());
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
        dest.writeValue(this.coordinatesMapY);
        dest.writeValue(this.coordinatesMapX);
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
