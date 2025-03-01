package org.rossijr.elasticsearchcrud.repository;

import org.rossijr.elasticsearchcrud.model.User;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends ElasticsearchRepository<User, String> {
    /*
     * As it is a demo, the objective is to go through some basic and more complex operations
     * that can be performed with Elasticsearch. So you can take those as a base and change it to your
     * needs.
     *
     * The queries can be performed in many ways, here we are going to show three of them:
     * 1. Using the ElasticsearchRepository, which is a Spring Data Elasticsearch interface that provides
     *   some basic operations like save, delete, findById, findAll, etc.
     * 2. Using the ElasticsearchOperations, which is a Spring Data Elasticsearch class that provides
     *  more complex queries such as aggregations and related.
     * 3. Using the SpEL (Spring Expression Language), which is going to be further addressed.
     *
     * With that in mind, here I will introduce some examples of those queries, which you can take as base
     * and adapt to your needs.
     * The following queries will be using the SpEL, which is a powerful expression language that can be used
     *  in many ways in Spring applications. Here we are using it to create simple queries mainly to show this tool
     *  and provide a better understanding of how to use it.
     *
     */

    /**
     * <p>Find user by name</p>
     * <p>This is an example on how to use simple queries with ElasticsearchRepository</p>
     * @param name name
     * @return list of users
     */
    @Query("""
            {
                "match": {
                    "name": "#{#name}"
                }
            }
            """)
    List<User> findByName(String name);

    /**
     * <p>Find users created within the last days</p>
     * <p>This is an example on how to use range queries with ElasticsearchRepository</p>
     * @param days days as integer
     * @return list of users
     */
    @Query("""
        {
          "range": {
            "createdAt": {
              "gte": "now-#{#days}d/d",
              "lte": "now/d"
            }
          }
        }
    """)
    List<User> findUsersCreatedWithinLastDays(int days);

    /**
     * <p>Find user by keyword</p>
     * <p>This is an example on how to use full-text search queries within multiple fields
     * with also a fuzziness parameter, meaning that it is typo-tolerant (can find similar words)</p>
     * @param keyword keyword used to search across name and city fields
     * @return list of users
     */
    @Query("""
        {
          "multi_match": {
            "query": "#{#keyword}",
            "fields": ["name", "city"],
            "fuzziness": "AUTO"
          }
        }
    """)
    List<User> findByKeyword(String keyword);

}
