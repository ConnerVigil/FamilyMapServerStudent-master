package DataStorage;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

/**
 * Called by register and fill services to generate the family tree.
 */
public class DataCache {

    private static LocationData locationData;
    private static FemaleNameData femaleNameData;
    private static MaleNameData maleNameData;
    private static SurnameData surnameData;

    /**
     * Constructor to load all of the json data for storage.
     */
    static {
        // Locations
        Reader reader = null;
        try {
            reader = new FileReader("json/locations.json");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        Gson gson = new Gson();
        assert reader != null;
        locationData = gson.fromJson(reader, LocationData.class);

        // Female Names
        reader = null;
        try {
            reader = new FileReader("json/fnames.json");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        Gson gson2 = new Gson();
        assert reader != null;
        femaleNameData = gson.fromJson(reader, FemaleNameData.class);

        // Male Names
        reader = null;
        try {
            reader = new FileReader("json/mnames.json");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        Gson gson3 = new Gson();
        assert reader != null;
        maleNameData = gson.fromJson(reader, MaleNameData.class);

        // Surnames
        reader = null;
        try {
            reader = new FileReader("json/snames.json");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        Gson gson4 = new Gson();
        assert reader != null;
        surnameData = gson.fromJson(reader, SurnameData.class);
    }

    public static LocationData getLocationData() {
        return locationData;
    }

    public static void setLocationData(LocationData locationData) {
        DataCache.locationData = locationData;
    }

    public static FemaleNameData getFemaleNameData() {
        return femaleNameData;
    }

    public static void setFemaleNameData(FemaleNameData femaleNameData) {
        DataCache.femaleNameData = femaleNameData;
    }

    public static MaleNameData getMaleNameData() {
        return maleNameData;
    }

    public static void setMaleNameData(MaleNameData maleNameData) {
        DataCache.maleNameData = maleNameData;
    }

    public static SurnameData getSurnameData() {
        return surnameData;
    }

    public static void setSurnameData(SurnameData surnameData) {
        DataCache.surnameData = surnameData;
    }
}
