package org.rossijr.elasticsearchcrud.sevice;

import co.elastic.clients.elasticsearch._types.aggregations.Aggregate;
import co.elastic.clients.elasticsearch._types.aggregations.Aggregation;
import org.rossijr.elasticsearchcrud.dto.AggregationDTO;
import org.rossijr.elasticsearchcrud.model.User;
import org.rossijr.elasticsearchcrud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchAggregations;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * <p>Service class for user operations</p>
 * <p>Important to note that the parameters were not validated and sanitized, which is a good practice in production
 * codes and environments to avoid the arising of security vulnerabilities</p>
 * <p>You can also check the inline comments for more specific details about each operation. And, if they are not enough,
 * you can check the official documentation of the Elasticsearch and the Spring Data Elasticsearch project</p>
 *
 * @see <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/elasticsearch-intro.html">Elasticsearch documentation</a>
 * @see <a href="https://docs.spring.io/spring-data/elasticsearch/reference/elasticsearch/clients.html">Spring Data Elasticsearch documentation</a>
 */
@Service
public class UserService {
    private final UserRepository userRepository;
    private final ElasticsearchOperations elasticsearchOperations;


    @Autowired
    public UserService(UserRepository userRepository, ElasticsearchOperations elasticsearchOperations) {
        this.userRepository = userRepository;
        this.elasticsearchOperations = elasticsearchOperations;
    }


    public User saveUser(User user) {
        user.setCreatedAt(Instant.now());
        return userRepository.save(user);
    }

    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    public List<?> searchUsersByParams(Map<String, String> params) {
        // Basic search operations, using only the ElasticsearchRepository
        if (params.get("name") != null) {
            return userRepository.findByName(params.get("name"));
        }
        if (params.get("createdWithin") != null) {
            return userRepository.findUsersCreatedWithinLastDays(Integer.parseInt(params.get("createdWithin")));
        }
        if (params.get("keyword") != null) {
            return userRepository.findByKeyword(params.get("keyword"));
        }

        // More complex queries, with a new way of doing them: dynamically

        // This example shows two important ways when aggregating data: when working with
        //  String and Numeric fields as the aggregation key
        if (params.get("countBy") != null) {
            return getAggregationCountBy(params.get("countBy"));
        }

        // In this example, we are going to explore the Nested Aggregation type, which here we are going to
        //  aggregate users in a specified range by the city, finding the top cities with those users
        if (params.get("topCitiesByAgeRange") != null) {
            String[] range = params.get("topCitiesByAgeRange").split("-");
            return getTopCitiesByAgeRange(Integer.parseInt(range[0]), Integer.parseInt(range[1]));
        }


        return StreamSupport.stream(userRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    /**
     * <p>Get the count of users by a specified field</p>
     * <p>This method is designed to address how to create dynamic aggregation queries with Elasticsearch</p>
     * <p>For more details, enter the method and check the inline comments</p>
     * @param field field to aggregate by
     * @return list of aggregation results
     */
    private List<AggregationDTO> getAggregationCountBy(String field) {
        // In case of a String field, use the ".keyword" suffix for better aggregation
        String finalField = field.equals("city") ? "city.keyword" : field;

        // Construct the base query for Elasticsearch
        Query query = NativeQuery.builder()
                // Add an aggregation named "users_by_<field>" where <field> is dynamically set
                .withAggregation("users_by_" + field,
                        // Define the aggregation as a "terms" aggregation, which groups documents by
                        //  unique values of the specified field
                        Aggregation.of(b -> b.terms(t ->
                                // Set the field to be aggregated;
                                //  uses "<field>.keyword" when working with String fields for a better
                                //  aggregation
                                t.field(finalField)
                        ))
                )
                // Finalize the query construction
                .build();


        // Execute the query and retrieve the search results (called search hits by Elasticsearch)
        SearchHits<User> searchHits = elasticsearchOperations.search(query, User.class);

        // Extract the aggregation results from the search response
        Aggregate aggregate = ((ElasticsearchAggregations) searchHits.getAggregations())
                .get("users_by_" + field) // Retrieve the aggregation with the name "users_by_<field>"
                .aggregation() // Get the specific aggregation object
                .getAggregate(); // Extract the generic Aggregate object containing term buckets

        // Initialize an empty list to store the aggregation results - as you call different functions
        //  when you have different types of keys (for example, if it is by age, you can't use a string key,
        //  so you need to use a long key, and if it is by a city, you need to use a string key) - with the name
        //  of the methods as "sterms" and "lterms", for example.
        List<AggregationDTO> result = new ArrayList<>();

        // Check if the aggregation result is of type String Terms (for keyword-based fields)
        if (aggregate.isSterms()) {
            // Extract the buckets from the string terms aggregation and map them to AggregationDTO objects
            result = aggregate.sterms().buckets().array().stream()
                    .map(bucket ->
                            new AggregationDTO(bucket.key().stringValue(), bucket.docCount()) // Store the key (string) and document count
                    )
                    .toList();
        }
        // Check if the aggregation result is of type Long Terms (for numeric-based fields)
        else if (aggregate.isLterms()) {
            // Extract the buckets from the long terms aggregation and map them to AggregationDTO objects
            result = aggregate.lterms().buckets().array().stream()
                    .map(bucket ->
                            new AggregationDTO(String.valueOf(bucket.key()), bucket.docCount()) // Convert the key to a string and store the count
                    )
                    .toList();
        }

        // Return the list of aggregation results
        return result;
    }

    /**
     * <p>Get the top cities by age range</p>
     * <p>This method is designed to address how to create dynamic and more complex aggregation queries with Elasticsearch</p>
     * <p>For more details, enter the method and check the inline comments</p>
     * @param from starting age
     * @param to ending age
     * @return list of aggregation results
     */
    private List<AggregationDTO> getTopCitiesByAgeRange(Integer from, Integer to) {
        // Construct the base query for Elasticsearch
        Query query = NativeQuery.builder()
                // Define a range query to filter users based on the "age" field
                .withQuery(q -> q.range(r -> r.number(n ->
                                n.field("age") // Specify the field to filter on - age
                                        .gte(from.doubleValue()) // Set the lower bound (inclusive) of the range
                                        .lte(to.doubleValue())   // Set the upper bound (inclusive) of the range
                        )
                ))
                // Add an aggregation named "popular_cities" to group documents by city
                .withAggregation("top_cities",
                        Aggregation.of(a -> a.terms(t ->
                                t.field("city.keyword") // Aggregate by the "city.keyword" field for better aggregation results
                        ))
                )
                // Finalize the query construction
                .build();

        // Execute the query and retrieve the search results (called search hits by Elasticsearch)
        SearchHits<User> searchHits = elasticsearchOperations.search(query, User.class);
        // Extract the aggregation results from the search response
        Aggregate aggregate = ((ElasticsearchAggregations) searchHits.getAggregations())
                .get("top_cities") // Retrieve the aggregation with the name "popular_cities"
                .aggregation() // Get the specific aggregation object
                .getAggregate(); // Extract the generic Aggregate object containing term buckets

        // Extract the buckets from the string terms aggregation and map them to AggregationDTO objects
        return aggregate.sterms().buckets().array().stream()
                .map(bucket ->
                        // Create a new AggregationDTO object for each bucket
                        // Store the key (city name) and document count (number of users in that city)
                        new AggregationDTO(bucket.key().stringValue(), bucket.docCount())
                )
                .toList(); // Convert the stream to a list and return the result
    }
}
