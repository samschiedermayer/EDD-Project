import com.google.cloud.firestore.GeoPoint;

import java.util.HashMap;

public class DatabaseEntry {

    GeoPoint location;
    long time;
    String userId, name;

    public DatabaseEntry(String parseableData) {

        String[] data = parseableData.split(",");
        if(data.length!= 4) {
            System.err.println("DatabaseEntry: Invalid parseableData");
            return;
        }

        String[] latLong = data[0].split(" ");
        double latitude =  Double.parseDouble(latLong[0]);
        double longitude = Double.parseDouble(latLong[1]);
        this.location = new GeoPoint(latitude, longitude);

        this.time = Long.parseLong(data[1]);

        this.userId = data[2];

        this.name = data[3];

    }

    public HashMap<String, Object> toMap () {

        HashMap<String, Object> map = new HashMap<>();

        map.put("location", location);
        map.put("time", time);
        map.put("userId", userId);

        return map;

    }


}
