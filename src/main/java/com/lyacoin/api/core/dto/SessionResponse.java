package com.lyacoin.api.core.dto;

import com.lyacoin.api.core.model.session.SessionAction;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SessionResponse {

    private SessionAction result;
}
