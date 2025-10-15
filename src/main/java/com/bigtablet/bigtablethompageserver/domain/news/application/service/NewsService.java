package com.bigtablet.bigtablethompageserver.domain.news.application.service;

import com.bigtablet.bigtablethompageserver.domain.news.client.dto.request.RegisterNewsRequest;
import jakarta.transaction.Transactional;

public interface NewsService {

    void saveNews(RegisterNewsRequest request);

    @Transactional
    void deleteNews(Long idx);
}
