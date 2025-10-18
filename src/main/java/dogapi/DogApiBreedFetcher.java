package dogapi;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;

/**
 * BreedFetcher implementation that relies on the dog.ceo API.
 * Note that all failures get reported as BreedNotFoundException
 * exceptions to align with the requirements of the BreedFetcher interface.
 */



public class DogApiBreedFetcher implements BreedFetcher {
    private final OkHttpClient client = new OkHttpClient();

    /**
     * Fetch the list of sub breeds for the given breed from the dog.ceo API.
     * @param breed the breed to fetch sub breeds for
     * @return list of sub breeds for the given breed
     * @throws BreedNotFoundException if the breed does not exist (or if the API call fails for any reason)
     */

// this method is for running the url
// put the run method under this class


    @Override
    public List<String> getSubBreeds(String breed) {
        // TODO Task 1: Complete this method based on its provided documentation
        //      and the documentation for the dog.ceo API. You may find it helpful
        //      to refer to the examples of using OkHttpClient from the last lab,
        //      as well as the code for parsing JSON responses.
        // return statement included so that the starter code can compile and run.

        String url = "https://dog.ceo/api/breeds/list/all";
        String jsonResponse = null;
        try {
            jsonResponse = run(url);
        } catch (IOException e) {
//            throw new RuntimeException(e); //i dont know why we are doing this
        }

        //parse json response; two nested arrays
        JSONObject jsonObject = new JSONObject(jsonResponse);
        JSONObject messageObject = jsonObject.getJSONObject("message");

        if (messageObject.has(breed)){
            JSONArray subbreedArray = messageObject.getJSONArray(breed);

            List<String> subbreed = new ArrayList<>();
            for (int i = 0; i < subbreedArray.length();i++){
                subbreed.add(subbreedArray.getString(i));
            }

            return subbreed;

        }
        else {
            throw new BreedNotFoundException("Breed Not Found");
        }

    }

    public String run (String url) throws IOException {
        Request request = new Request.Builder().url(url).build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
}