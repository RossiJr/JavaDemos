package org.rossijr.elasticsearchcrud.dto;

public class AggregationDTO {
    private String key;
    private Long count;

    public AggregationDTO() {
    }

    public AggregationDTO(String key, Long count) {
        this.key = key;
        this.count = count;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
