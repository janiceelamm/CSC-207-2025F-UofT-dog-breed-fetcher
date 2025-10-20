package dogapi;

import java.util.*;

/**
 * This BreedFetcher caches fetch request results to improve performance and
 * lessen the load on the underlying data source. An implementation of BreedFetcher
 * must be provided. The number of calls to the underlying fetcher are recorded.
 *
 * If a call to getSubBreeds produces a BreedNotFoundException, then it is NOT cached
 * in this implementation. The provided tests check for this behaviour.
 *
 * The cache maps the name of a breed to its list of sub breed names.
 */
public class CachingBreedFetcher implements BreedFetcher {
    // TODO Task 2: Complete this class
    private int callsMade = 0;
    private Map<String, List<String>> cache = new HashMap<>();
    private BreedFetcher fetcher;


    public CachingBreedFetcher(BreedFetcher fetcher) {
        this.callsMade = 0;
        this.cache = new HashMap <>();
        this.fetcher = fetcher; //to fetch fresh data on cache miss

    }

    @Override
    public List<String> getSubBreeds(String breed) throws BreedNotFoundException {
        if (this.cache.containsKey(breed)) {
            return this.cache.get(breed);
        }
        else {
            try {
                this.callsMade++;
                List<String> result = fetcher.getSubBreeds(breed);
                cache.put(breed, result);
                return result;
            } catch  (BreedNotFoundException ex){
                throw ex;
            }
        }

    }

    public int getCallsMade() {
        return callsMade;
    }
}