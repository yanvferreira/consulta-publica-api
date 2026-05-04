package br.jus.tjrn.consulta.shared.response;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class PageResponse<T> {

    private long totalElements;
    private int totalPages;
    private int page;
    private int size;
    private List<T> content;

    public PageResponse(Page<T> page) {
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();
        this.page = page.getNumber();
        this.size = page.getSize();
        this.content = page.getContent();
    }

}
