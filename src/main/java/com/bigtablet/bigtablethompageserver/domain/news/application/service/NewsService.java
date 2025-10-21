package com.bigtablet.bigtablethompageserver.domain.news.application.service;

import com.bigtablet.bigtablethompageserver.domain.news.client.dto.News;
import com.bigtablet.bigtablethompageserver.domain.news.client.dto.request.EditNewsRequest;
import com.bigtablet.bigtablethompageserver.domain.news.client.dto.request.RegisterNewsRequest;
import jakarta.transaction.Transactional;

public interface NewsService {

    void saveNews(RegisterNewsRequest request);

    News getNews(Long idx);

    void editNews(EditNewsRequest request);

    void deleteNews(Long idx);

}
