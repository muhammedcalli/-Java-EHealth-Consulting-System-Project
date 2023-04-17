package Maps;

import com.teamdev.jxmaps.swing.MapView;
import main.java.DatabaseConnector;
import main.java.Doctor;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import static java.lang.Math.*;

/**
 * The type Map.
 */
public class Map extends MapView
{
    /**
     * Gets distance between two points (latitude and longitude, respectively).
     * User can choose return type between kilometers and miles
     * <p>
     * calculation formula: https://www.movable-type.co.uk/scripts/latlong.html
     *
     * @param latitude1  the latitude 1
     * @param longitude1 the longitude 1
     * @param latitude2  the latitude 2
     * @param longitude2 the longitude 2
     * @param unit       the unit
     * @return the distance
     */
    public static double getDistance(double latitude1, double longitude1,
                                     double latitude2, double longitude2, char unit)
    {
        double theta = longitude1 - longitude2;
        double distance = (sin(toRadians(latitude1)) *
                sin(toRadians(latitude2))) + (cos(toRadians(latitude1)) *
                cos(toRadians(latitude2)) * cos(toRadians(theta)));
        distance = acos(distance);
        distance = toDegrees(distance);
        distance = distance * 60 * 1.1515;
        switch (unit) {
            case 'm':
                break;
            case 'K':
                distance = distance * 1.609344;
        }
        return (Math.round(distance * 100.0) / 100.0);
    }

    /**
     * Get coordinates from address double [2].
     *
     * @param address the address
     * @param plz     the plz
     * @return the double [2]
     * @throws IOException          the io exception
     * @throws InterruptedException the interrupted exception
     */
    public static double[] getCoordinatesFromAddress(String address, int plz) throws IOException, InterruptedException
    {
        double[] coordinates = new double[2];

        address = URLEncoder.encode(address, StandardCharsets.UTF_8.toString());
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://nominatim.openstreetmap.org/search?q=" + plz + ",%20" + address + "&format=json&addressdetails=1")).build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JSONArray jsonArr = new JSONArray(response.body());
        JSONObject jsonObj = jsonArr.getJSONObject(0);

        coordinates[0] = jsonObj.getDouble("lat");
        coordinates[1] = jsonObj.getDouble("lon");

        return coordinates;
    }

    /**
     * Helper function to determine if doctor is within radius given by user
     *
     * @param doctor   the doctor
     * @param userID   the user id
     * @param distance the distance of search (radius)
     * @return the boolean
     * @throws IOException          the io exception
     * @throws InterruptedException the interrupted exception
     */
    public static boolean calculate(Doctor doctor, int userID, int distance) throws IOException, InterruptedException
    {
        String doctorAddress = doctor.getAddress();
        int doctorPlz = doctor.getPlz();

        String userAddress = DatabaseConnector.getInstance().getUserAddress(userID);
        int userPlz = DatabaseConnector.getInstance().getUserPlz(userID);

        double[] doctorCoordinantes = getCoordinatesFromAddress(doctorAddress, doctorPlz);
        double[] userCoordinates = getCoordinatesFromAddress(userAddress, userPlz);

        double distanceBetween = getDistance(doctorCoordinantes[0], doctorCoordinantes[1], userCoordinates[0], userCoordinates[1], 'K');

        System.out.println("Distance between user and doctor (in KM): " + distanceBetween);

        return distanceBetween < distance;
    }

}
